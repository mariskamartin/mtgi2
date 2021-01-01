package cz.mariskamartin.mtgi2.sniffer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import cz.mariskamartin.mtgi2.Utils;
import cz.mariskamartin.mtgi2.db.model.*;
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

public class RishadaLoader implements ISniffer {
    private static final Logger log = LoggerFactory.getLogger(RishadaLoader.class);
    public static final String HTML_NBSP = "\u00A0";

    @Override
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException {
        //throw new UnsupportedOperationException("Tato metoda jeste neni zprovoznena");
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        parseRishada(fetchFromRishadaKusovky(name, null), builder);
        return builder.build();
    }

    @Override
    public List<DailyCardInfo> sniffByEdition(CardEdition cardEdition) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        String edition = ManagedCardEditions.instance.getInfo(cardEdition).getRishadaUrlKey();
        if (edition == null) return builder.build();
        parseRishada(fetchFromRishadaKusovky(null, edition), builder);
        return builder.build();
    }

    /**
     * Parsuje karty na blacklotus.cz
     * 
     * @param doc
     * @param builder
     * @throws IOException
     */
    private void parseRishada(Document doc, Builder<DailyCardInfo> builder) throws IOException {
        Elements values = doc.select("div.results tbody tr");
        if (!values.isEmpty()) {
            values.remove(0); //hlavicka
        }
        for (Element element : values) {
            Card card = CardConverter.valueOfRishadaElement(element);
            if (card.getName().replace(HTML_NBSP,"").trim().length() == 0) continue;
            try {
                Elements innerValues = element.children();
                long skladem = Utils.parseLong(innerValues.get(8).text(), 0);
                long cena = Utils.parseLong(innerValues.get(7).text().replace(HTML_NBSP, "").replace("Kč", "").trim(), 0);
                DailyCardInfo dci = new DailyCardInfo(card, BigDecimal.valueOf(cena), skladem, new Date(), CardShop.RISHADA);
                builder.add(dci);
            } catch (NumberFormatException e) {
                log.warn("NumberFormatException for card: {}", card);
            }
        }
    }

    private Document fetchFromRishadaKusovky(String findString, String edition) throws IOException {
        if (edition == null) edition = "1000000";
        if (findString == null) findString = "";
        String urlRequest = "http://www.rishada.cz/kusovky/vysledky-hledani?searchtype=basic&xxwhichpage=1&xxcardname="+findString.replace(" ", "+")+"&xxedition="+edition+"&xxpagesize=10000&search=Vyhledat";
        Document doc = Jsoup.connect(urlRequest).validateTLSCertificates(true).ignoreHttpErrors(true).followRedirects(true).timeout(10000).get();
//        Document doc = Jsoup.parse(new File("/Users/martinmariska/rishada.html"), "utf-8");
        return doc;
    }

    public static void main(String[] args) {
        try {
//            List<DailyCardInfo> cards = new RishadaLoader().sniffByCardName("Breeding Pool");
            List<DailyCardInfo> cards = new RishadaLoader().sniffByEdition(CardEdition.THEROS);
            log.debug("count={} dcis={}", cards.size(), cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
