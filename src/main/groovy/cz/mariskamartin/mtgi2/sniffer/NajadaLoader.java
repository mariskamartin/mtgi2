package cz.mariskamartin.mtgi2.sniffer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
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

public class NajadaLoader implements ISniffer {
    private static final Logger log = LoggerFactory.getLogger(NajadaLoader.class);
    public static final String HTML_NBSP = "\u00A0";

    @Override
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        parseNajada(fetchFromNajadaKusovky(name), builder);
        return builder.build();
    }

    @Override
    public List<DailyCardInfo> sniffByEdition(CardEdition edition) throws IOException {
        throw new UnsupportedOperationException("Tato metoda jeste neni zprovoznena");
    }

    /**
     * Parsuje karty na Najada.cz
     * 
     * @param doc
     * @param builder
     * @throws IOException
     */
    private void parseNajada(Document doc, Builder<DailyCardInfo> builder) throws IOException {
        Elements values = doc.select("table.tabArt tbody tr");
        if (!values.isEmpty()) {
            values.remove(0); //hlavicka
        }
        for (Element element : values) {
            Card card = CardConverter.valueOfNajadaElement(element);
            if (card.getName().replace(HTML_NBSP,"").trim().length() == 0) continue;
            try {
                Elements innerValues = element.children();
                long skladem = Long.parseLong(innerValues.select("div.stateNew").select("span").get(2).text().trim());
                long cena = Long.parseLong(innerValues.select("div.stateNew").select("span").get(0).text().replace(" ", "").trim());
                DailyCardInfo dci = new DailyCardInfo(card, BigDecimal.valueOf(cena), skladem, new Date(), CardShop.NAJADA);
                builder.add(dci);
            } catch (NumberFormatException e) {
                log.warn("NumberFormatException for card: {}", card);
            }
        }
    }

    private Document fetchFromNajadaKusovky(String findString) throws IOException {
        String urlRequest = "http://www.najada.cz/cz/kusovky-mtg/?Search=" + findString.replace(" ", "+")
                + "&Sender=Submit&MagicCardSet=-1";
        Document doc = Jsoup.connect(urlRequest).get();
        return doc;
//        return Jsoup.parse(new File("C://najada.html"), "utf-8"); //for DEBUG
    }

    public static void main(String[] args) {
        try {
            List<DailyCardInfo> cards = new NajadaLoader().sniffByCardName("Watery Grave");
            log.debug("{}", cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
