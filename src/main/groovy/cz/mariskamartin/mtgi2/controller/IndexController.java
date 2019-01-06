package cz.mariskamartin.mtgi2.controller;

import com.google.common.collect.Lists;
import cz.mariskamartin.mtgi2.CardService;
import cz.mariskamartin.mtgi2.db.CardRepository;
import cz.mariskamartin.mtgi2.db.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequestMapping("/")
@RestController
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String index() {
        return "GET / ... this overview \n" +
                "\n" +
                "GET /log-test \n" +
                "GET /db/init \n" +
                "GET /db/test \n" +
                "GET /card?name=CardC \n" +
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


    @RequestMapping(value = "/card", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Card> findCard(@RequestParam("name") String name) {
        List<Card> card = cardService.findCard(name);
        log.info("found card = {}", card);
        return card;
    }

    @RequestMapping(value = "/dci/fetch", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> fetchCard(@RequestParam("name") String name) throws IOException {
        List<DailyCardInfo> dcis = cardService.fetchCardsByName(name);
        Collection<Card> cards = cardService.saveCardsIntoDb(dcis);
        log.info("fetch cards = {}", cards);
        return cards;
    }

    @RequestMapping(value = "/dci/test", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<Card> dciTest() throws IOException {
        List<DailyCardInfo> dcis = Lists.newArrayList(new DailyCardInfo(new Card("CardDCI1", true, CardRarity.SPECIAL, CardEdition.EDITION_5TH), BigDecimal.TEN, 2L, new Date(), CardShop.CERNY_RYTIR));
        Collection<Card> cards = cardService.saveCardsIntoDb(dcis);
        log.info("fetch cards = {}", cards);
        return cards;
    }


}
