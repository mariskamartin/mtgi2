package cz.mariskamartin.mtgi2.controller;

import com.google.common.collect.Lists;
import cz.mariskamartin.mtgi2.CardService;
import cz.mariskamartin.mtgi2.db.CardRepository;
import cz.mariskamartin.mtgi2.db.DailyCardInfoRepository;
import cz.mariskamartin.mtgi2.db.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rest")
@RestController
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    DailyCardInfoRepository dailyCardInfoRepository;


    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String index() {
        return "GET / ... this overview \n" +
                "\n" +
                "GET /log-test \n" +
                "GET /db/init \n" +
                "GET /db/test \n" +
                "GET /card/find/CardC \n" +
                "GET /dci/fetch?name=Watery+Grave \n" +
                "\n";
    }

    @RequestMapping(value = "/log-test", method = RequestMethod.GET)
    public String timeBasic() {
        String text = String.format("Basic log some r=%f.2", Math.random());
        log.info(text);
        log.debug(text);
        return text;
    }

    @RequestMapping(value = "/db/init", method = RequestMethod.GET)
    public String initDb() {
        // save a couple of customers
        cardRepository.save(new Card("CardD", false, CardRarity.UNCOMMON, CardEdition.ALPHA));
        cardRepository.save(new Card("CardA", false, CardRarity.COMMON, CardEdition.ALPHA));
        cardRepository.save(new Card("CardB", false, CardRarity.LAND, CardEdition.MAGIC_2011));
        cardRepository.save(new Card("CardC", false, CardRarity.RARE, CardEdition.BETA));
        return "done";
    }

    @RequestMapping(value = "/db/test", method = RequestMethod.GET)
    public String dbTest() {
        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (Card card : cardRepository.findAll()) {
            log.info(card.toString());
        }

        // fetch an individual customer by ID
        cardRepository.findById("CardA|false|COMMON|LEA")
                .ifPresent(c -> {
                    log.info("Card found with findById(CardA|false|COMMON|LEA): {}", c);
                });

        // fetch customers by last name
        log.info("--------------------------------------------");
        cardRepository.findByName("CardB").forEach(byName -> log.info(byName.toString()));
        log.info("--------------------------------------------");
        cardRepository.findByRarity(CardRarity.RARE).forEach(c -> log.info(c.toString()));
        log.info("--------------------------------------------");
        cardRepository.findByEdition(CardEdition.ALPHA).forEach(c -> log.info(c.toString()));
        log.info("--------------------------------------------");
        cardRepository.findByFoil(false).forEach(c -> log.info(c.toString()));
        return "done";
    }


    @RequestMapping(value = "/cards/find/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Card> findCard(@PathVariable("name") String name) {
        List<Card> card = cardService.findCard(name);
        log.info("found card = {}", card);
        return card;
    }

    @GetMapping("/cards/detail/card-id/**")
    public Card getCard(HttpServletRequest request) throws UnsupportedEncodingException {
        String cardId = getCardIdFromLastEncodedPath(request, "/cards/detail/card-id/");
        Optional<Card> card = cardRepository.findById(cardId);
        log.info("get card = {}", card);
        return card.get();
    }

    @DeleteMapping("/cards/detail/card-id/**")
    public List<Card> deleteCard(HttpServletRequest request) throws UnsupportedEncodingException {
        String cardId = getCardIdFromLastEncodedPath(request, "/cards/detail/card-id/");
        List<Card> cards = cardService.deleteCardAndDciById(cardId);
        log.info("deleted cards = {}", cards);
        return cards;
    }


    @GetMapping("/dci/detail/card-id/**")
    public List<DailyCardInfo> getDciForCard(HttpServletRequest request) throws UnsupportedEncodingException {
        String cardId = getCardIdFromLastEncodedPath(request, "/dci/detail/card-id/");
        List<DailyCardInfo> dciList = dailyCardInfoRepository.findByCard(new Card(cardId));
        return dciList;
    }

    @RequestMapping(value = "/cards/fetch/managed/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> fetchManaged() throws IOException {
        List<Card> cards = cardService.fetchAllManagedEditions();
        return cards;
    }

    @RequestMapping(value = "/dci/fetch/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> fetchCards(@PathVariable("name") String name) throws IOException {
        List<DailyCardInfo> dcis = cardService.fetchCardsByName(name);
        Collection<Card> cards = cardService.saveCardsIntoDb(dcis);
        return cards;
    }

    @RequestMapping(value = "/dci/clean/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> cleanFoundCardsDci(@PathVariable("name") String name) throws IOException {
        List<Card> cards = cardService.findCard(name);
        for (Card card : cards) {
            cardService.cleanCardsDailyCardInfoById(card.getId());
        }
        return cards;
    }

    @GetMapping("/dci/clean/card-id/**")
    public boolean cleanDciForCard(HttpServletRequest request) throws UnsupportedEncodingException {
        String cardId = getCardIdFromLastEncodedPath(request, "/dci/clean/card-id/");
        cardService.cleanCardsDailyCardInfoById(cardId);
        return true;
    }

    @RequestMapping(value = "/cards/find/edition/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> findCardsByEdition(@PathVariable("name") String name) throws IOException {
        return cardService.findCardByEdition(CardEdition.valueFromName(name));
    }

    @RequestMapping(value = "/dci/fetch/edition/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> fetchDciByEdition(@PathVariable("name") String name) throws IOException {
        List<DailyCardInfo> dcis = cardService.fetchCardsByEdition(CardEdition.valueFromName(name));
        Collection<Card> cards = cardService.saveCardsIntoDb(dcis);
        return cards;
    }

    @RequestMapping(value = "/dci/test", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> dciTest() throws IOException, InterruptedException {
        DailyCardInfo cardDCI1 = new DailyCardInfo(new Card("CardDCI1", true, CardRarity.SPECIAL, CardEdition.EDITION_5TH), BigDecimal.TEN, 2L, new Date(), CardShop.CERNY_RYTIR, null);
        Thread.sleep(1000);
        DailyCardInfo cardDCI2 = new DailyCardInfo(new Card("CardDCI1", true, CardRarity.SPECIAL, CardEdition.EDITION_5TH), BigDecimal.ONE, 2L, new Date(), CardShop.CERNY_RYTIR, null);
        List<DailyCardInfo> dcis = Lists.newArrayList(cardDCI1, cardDCI2);
        Collection<Card> cards = cardService.saveCardsIntoDb(dcis);
        log.info("fetch cards = {}", cards);
        return cards;
    }

    @RequestMapping(value = "/dci/clean-all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String cleanAllDcis() {
        for (Card card : cardRepository.findAll()) {
            log.info("clean dci {}", card.toString());
            cardService.cleanCardsDailyCardInfoById(card.getId());
        }
        return "ok";
    }

    private String getCardIdFromLastEncodedPath(HttpServletRequest request, String mappingPath) throws UnsupportedEncodingException {
        String cardIdEncoded = request.getRequestURI().split(request.getContextPath() + mappingPath)[1];
        return URLDecoder.decode(cardIdEncoded, StandardCharsets.UTF_8.toString());
    }


}
