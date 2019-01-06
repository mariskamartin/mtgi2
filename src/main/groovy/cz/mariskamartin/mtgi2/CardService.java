package cz.mariskamartin.mtgi2;

import com.google.common.collect.Lists;
import cz.mariskamartin.mtgi2.db.CardRepository;
import cz.mariskamartin.mtgi2.db.DailyCardInfoRepository;
import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardRarity;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;
import cz.mariskamartin.mtgi2.sniffer.CernyRytirLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import javax.persistence.RollbackException;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@Service
public class CardService {
    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;
    private final DailyCardInfoRepository dailyCardInfoRepository;

    @Autowired
    public CardService(CardRepository cardRepository, DailyCardInfoRepository dailyCardInfoRepository) {
        this.cardRepository = cardRepository;
        this.dailyCardInfoRepository = dailyCardInfoRepository;
    }

    public List<Card> findCard(String name) {
        return cardRepository.findByName(name);
    }

    public List<DailyCardInfo> fetchCardsByName(String name) throws IOException {
        return new CernyRytirLoader().sniffByCardName(name);
    }

    public List<Card> fetchCards(String name) throws IOException {
        List<DailyCardInfo> dailyCardInfos = new CernyRytirLoader().sniffByCardName(name);
        LinkedList<Card> cards = Lists.newLinkedList();
        for (DailyCardInfo dci : dailyCardInfos) {
            cards.add(dci.getCard());
        }

//        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(cards.toArray());
//        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
//                "INSERT INTO CARD (name, foil, created, updated, rarity, edition) VALUES (:name, :foil, :created, :updated, :rarity, :edition)", batch);
//        log.info("insert count = {}", updateCounts);

        return cards;
    }



    public Collection<Card> saveCardsIntoDb(List<DailyCardInfo> cardList) {
//        DailyCardInfoDao dciDao = new DailyCardInfoDao(getEm());
//        EntityTransaction tx = getEm().getTransaction();
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
//                        tx.begin();
                        //vlozit referenci z cache
                        if (cacheCardsMap.containsKey(Card.getIdKey(dailyCardInfo.getCard()))) {
                            dailyCardInfo.setCard(cacheCardsMap.get(Card.getIdKey(dailyCardInfo.getCard())));
                        }
//                        Card card = cardRepository.save(dailyCardInfo.getCard());
//                        dailyCardInfo.setCard(card);
                        dailyCardInfoRepository.save(dailyCardInfo);
//                        dailyCardInfo = dciDao.update(dailyCardInfo);
//                        tx.commit();
                    } catch (RollbackException e) {
                        if (e.getMessage().contains("Unique constraint")) {
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
//            if (tx.isActive()) {
//                tx.rollback();
//            }
        }
        return managedCardsMap.values();
    }
}
