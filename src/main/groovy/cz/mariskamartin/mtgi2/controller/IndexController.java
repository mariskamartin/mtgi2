package cz.mariskamartin.mtgi2.controller;

import cz.mariskamartin.mtgi2.CardService;
import cz.mariskamartin.mtgi2.db.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("/")
@RestController
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    CardService cardService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String index() {
        return "GET / ... this overview \n" +
                "\n" +
                "GET /log-test \n" +
                "\n";
    }

    @RequestMapping(value = "/log-test", method = RequestMethod.GET)
    public String timeBasic() {
        String text = String.format("Basic log some r=%f.2", Math.random());
        log.info(text);
        log.debug(text);
        return text;
    }


    @RequestMapping(value = "/card", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Card findCard(@RequestParam("name") String name) {
        Card card = cardService.findCard(name);
        log.info("found card = {}", card);
        return card;
    }

    @RequestMapping(value = "/card/fetch", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Card> fetchCard(@RequestParam("name") String name) throws IOException {
        List<Card> cards = cardService.fetchCards(name);
        log.info("fetch cards = {}", cards);
        return cards;
    }

}
