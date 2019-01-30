package cz.mariskamartin.mtgi2.controller;

import cz.mariskamartin.mtgi2.services.CardService;
import cz.mariskamartin.mtgi2.db.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/")
@Controller
public class UiController {
    private static final Logger log = LoggerFactory.getLogger(UiController.class);

    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String index() {
        return "GET / ... this overview \n" +
                "\n" +
                "GET /greeting \n" +
                "GET /index2" +
                "GET /rest/log-test \n" +
                "GET /rest/db/init \n" +
                "GET /rest/db/test \n" +
                "GET /rest/card/find/CardC \n" +
                "GET /rest/dci/fetch?name=Watery+Grave \n" +
                "\n";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/index2")
    public String index2(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index2";
    }

    @GetMapping("/")
    public String charts(Model model) {
        model.addAttribute("name", "test");
        return "charts";
    }

}
