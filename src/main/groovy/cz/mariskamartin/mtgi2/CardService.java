package cz.mariskamartin.mtgi2;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import cz.mariskamartin.mtgi2.db.CardRepository;
import cz.mariskamartin.mtgi2.db.DailyCardInfoRepository;
import cz.mariskamartin.mtgi2.db.model.*;
import cz.mariskamartin.mtgi2.sniffer.CernyRytirLoader;
import cz.mariskamartin.mtgi2.sniffer.NajadaLoader;
import cz.mariskamartin.mtgi2.sniffer.TolarieLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Service
public class CardService {
    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;
    private final DailyCardInfoRepository dailyCardInfoRepository;
    private final ExecutorService executorService;

    @Autowired
    public CardService(CardRepository cardRepository, DailyCardInfoRepository dailyCardInfoRepository, @Qualifier("fixedThreadPool") ExecutorService executorService) {
        this.cardRepository = cardRepository;
        this.dailyCardInfoRepository = dailyCardInfoRepository;
        this.executorService = executorService;
    }

    public List<Card> findCard(String name) {
        return cardRepository.findByNameContaining(name);
    }

    public List<DailyCardInfo> fetchCardsByName(String name) throws IOException {
        List<DailyCardInfo> dailyCardInfos = Lists.newLinkedList();
        List<Future<List<DailyCardInfo>>> futures = new ArrayList<>();
        futures.add(executorService.submit(() -> new CernyRytirLoader().sniffByCardName(name)));
        futures.add(executorService.submit(() -> new NajadaLoader().sniffByCardName(name)));
        futures.add(executorService.submit(() -> new TolarieLoader().sniffByCardName(name)));
        waitForDci(dailyCardInfos, futures);
        return dailyCardInfos;
    }

    public List<DailyCardInfo> fetchCardsByEdition(CardEdition edition) {
        List<DailyCardInfo> dailyCardInfos = Lists.newLinkedList();

        List<Future<List<DailyCardInfo>>> futures = new ArrayList<>();
        if (dailyCardInfoRepository.countByDayAndShopAndCardEdition(new Date(), CardShop.CERNY_RYTIR, edition) == 0 ) {
            futures.add(executorService.submit(() -> new CernyRytirLoader().sniffByEdition(edition)));
        }
        if (dailyCardInfoRepository.countByDayAndShopAndCardEdition(new Date(), CardShop.TOLARIE, edition) == 0 ) {
            futures.add(executorService.submit(() -> new TolarieLoader().sniffByEdition(edition)));
        }
        waitForDci(dailyCardInfos, futures);
        return dailyCardInfos;
    }

    //    @Scheduled(cron = "0 0 10 * * *") //each day in 10.00
    public List<Card> fetchAllManagedEditions() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long editionProcessedCount = 0;
        List<Card> allCards = new ArrayList<>();

        Collection<CardEdition> managedEditions = ManagedCardEditions.instance.getManagedEditions();
        for (CardEdition cardEdition : managedEditions) {
            allCards.addAll(saveCardsIntoDb(fetchCardsByEdition(cardEdition)));
            editionProcessedCount++;
            log.debug("fetching processed {} ({}/{})  ", cardEdition.getName(), editionProcessedCount, managedEditions.size());
        }
        log.info("download editions elapsed time " + stopwatch.stop().elapsed(TimeUnit.MINUTES) + " minutes");
        return allCards;
    }

    public Collection<Card> saveCardsIntoDb(List<DailyCardInfo> cardList) {
        Map<String, Card> cacheCardsMap = new HashMap<String, Card>();
        Map<String, Card> managedCardsMap = new HashMap<String, Card>();
        try {
            for (DailyCardInfo dailyCardInfo : cardList) {
                // pokud je karta nezname edice nebo rarity, tak neukladame
                if (dailyCardInfo.getCard().getRarity().equals(CardRarity.UNKNOWN) || dailyCardInfo.getCard().getEdition().equals(CardEdition.UNKNOWN)) {
                    continue;
                }
                boolean tryToSave = true;
                while (tryToSave) {
                    tryToSave = false;
                    try {
                        //vlozit referenci z cache
                        if (cacheCardsMap.containsKey(Card.getIdKey(dailyCardInfo.getCard()))) {
                            dailyCardInfo.setCard(cacheCardsMap.get(Card.getIdKey(dailyCardInfo.getCard())));
                        }
                        Optional<Card> cardById = cardRepository.findById(dailyCardInfo.getCard().getId());
                        Card c = cardById.orElseGet(() -> cardRepository.save(dailyCardInfo.getCard()));
                        dailyCardInfo.setCard(c);
                        dailyCardInfoRepository.save(dailyCardInfo);
                    } catch (DataIntegrityViolationException e) {
                        // speedup in storing, act only during constraint violation
                        if (e.getMessage().contains("ConstraintViolationException")) {
                            if (e.getMessage().contains("DailyCardInfo")) {
                                // pokud se nepovede vlozit kvuli unique dci
                                if (log.isTraceEnabled()) {
                                    log.trace("Dnes uz byly data o karte ulozeny. " + dailyCardInfo);
                                }
                            } else {
                                // pokud se nepovede vlozit kvuli unique card
                                for (Card card : cardRepository.findByEdition(dailyCardInfo.getCard().getEdition())) {
                                    cacheCardsMap.put(Card.getIdKey(card), card);
                                    tryToSave = true; //nacteny nova data, zkusit znovu
                                }
                            }
                        }
                    }
                }
                //pridani obslouzene karty
                managedCardsMap.put(Card.getIdKey(dailyCardInfo.getCard()), dailyCardInfo.getCard());
                cacheCardsMap.put(Card.getIdKey(dailyCardInfo.getCard()), dailyCardInfo.getCard());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return managedCardsMap.values();
    }

    /**
         * Ma za ukol projit historii cen karty a zrusit duplicitni zaznamy cen.. zachovat pouze ty co se meni.
            *
            * @param id card ID*/
    public void cleanCardsDailyCardInfoById(String id) {
        boolean change = true;
        List<DailyCardInfo> dciDaoByCard = dailyCardInfoRepository.findByCardIdOrderByShopAndDayAsc(id);
        Iterator<DailyCardInfo> iterator = dciDaoByCard.iterator();
        //setridene podle shopu, dne
        List<DailyCardInfo> deleteDci = Lists.newArrayList();
        DailyCardInfo dBefore = null;
        while (iterator.hasNext()) {
            DailyCardInfo d = iterator.next();
            log.debug("{}", d.toString());
            if (dBefore != null) {
                if (d.getShop().equals(dBefore.getShop())) {
                    //logika pro smazani
                    if (d.getPrice().equals(dBefore.getPrice())) {
                        if (!change) {
                            //smaz dci
                            deleteDci.add(dBefore);
                        }
                        change = false;
                    } else {
                        change = true;
                    }
                } else {
                    //jdeme na dalsi shop
                    change = true;
                }
            }
            dBefore = d;
        }
        dailyCardInfoRepository.deleteAll(deleteDci);
    }


    private void waitForDci(List<DailyCardInfo> dailyCardInfos, List<Future<List<DailyCardInfo>>> futures) {
        for (Future<List<DailyCardInfo>> future : futures) {
            try {
                dailyCardInfos.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.warn(e.getMessage());
            }
        }
    }

}
