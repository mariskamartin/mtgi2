package cz.mariskamartin.mtgi2.sniffer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import cz.mariskamartin.mtgi2.Utils;
import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardShop;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BlackLotusLoader implements ISniffer {
    private static final Logger log = LoggerFactory.getLogger(BlackLotusLoader.class);
    public static final String HTML_NBSP = "\u00A0";

    @Override
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException {
        //throw new UnsupportedOperationException("Tato metoda jeste neni zprovoznena");
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        parseBlackLotus(fetchFromBlackLotusKusovky(name), builder);
        return builder.build();
    }

    @Override
    public List<DailyCardInfo> sniffByEdition(CardEdition edition) throws IOException {
        throw new UnsupportedOperationException("Tato metoda jeste neni zprovoznena");
    }

    /**
     * Parsuje karty na blacklotus.cz
     * 
     * @param doc
     * @param builder
     * @throws IOException
     */
    private void parseBlackLotus(Document doc, Builder<DailyCardInfo> builder) throws IOException {
        Elements values = doc.select("#list div.inner");
        for (Element element : values) {
            Card card = CardConverter.valueOfBlackLotusElement(element);
            if (card.getName().replace(HTML_NBSP,"").trim().length() == 0) continue;
            try {
                Elements innerValues = element.children();
                long skladem = Utils.parseLong(innerValues.select("div.stateNew").select("span").get(2).text().trim(), 0);
                long cena = Utils.parseLong(innerValues.select("div.stateNew").select("span").get(0).text().replace(" ", "").trim(), 0);
                DailyCardInfo dci = new DailyCardInfo(card, BigDecimal.valueOf(cena), skladem, new Date(), CardShop.NAJADA);
                builder.add(dci);
            } catch (NumberFormatException e) {
                log.warn("NumberFormatException for card: {}", card);
            }
        }
    }

    private Document fetchFromBlackLotusKusovky(String findString) throws IOException {
        //rishada
        //http://www.rishada.cz/kusovky/vysledky-hledani?searchtype=basic&xxwhichpage=1&xxcardname=Breeding+Pool&xxedition=1000000&xxpagesize=10&search=Vyhledat
        String urlRequest = "http://www.blacklotus.cz/magic-vyhledavani/?form_name=param_search&catid=3&search%5Bnazev%5D="+findString.replace(" ", "+")+"&search%5Bpopis%5D=&search%5B15%5D=0&search%5B4%5D=0&search%5B7%5D=0&search%5Bfrom13%5D=&search%5Bto13%5D=&search%5Bfrom14%5D=&search%5Bto14%5D=&search%5Bfrom12%5D=&search%5Bto12%5D=&search%5Bpricemin%5D=&search%5Bpricemax%5D=&search%5B6%5D=0";
//        Document doc = Jsoup.connect(urlRequest).validateTLSCertificates(true).ignoreHttpErrors(true).followRedirects(true).timeout(10000).get();
        Document doc = Jsoup.parse(new File("/Users/martinmariska/blacklotus.html"), "utf-8");
        return doc;
    }

    public static void main(String[] args) {
        try {
            List<DailyCardInfo> cards = new BlackLotusLoader().sniffByCardName("Pool");
            log.debug("{}", cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
