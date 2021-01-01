package cz.mariskamartin.mtgi2.sniffer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import cz.mariskamartin.mtgi2.Utils;
import cz.mariskamartin.mtgi2.controller.IndexController;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CernyRytirLoader implements ISniffer {
    private static final Logger log = LoggerFactory.getLogger(CernyRytirLoader.class);

    @Override
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        parseCernyRytir(fetchFromCernyRytirKusovky(name), builder);
        ImmutableList<DailyCardInfo> build = builder.build();
        log.info("sniff (card = {}) size = {}", name, build.size());
        return build;
    }

    @Override
    public List<DailyCardInfo> sniffByEdition(CardEdition edition) throws IOException {
        return sniffByEdition(edition, "A");
    }

    public List<DailyCardInfo> sniffByEdition(CardEdition edition, String rarity) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        Document doc = fetchFromCernyRytirKusovkyPaged(edition, rarity);
        Elements select = doc.select("a.kusovkytext");
        parseCernyRytir(doc, builder);
        if (select.size() > 0) {
            // pouze polovinu odkazu, protoze CR je ma 2x na strance (nahore a dole)
            for (int i = 0; i < select.size() / 2; i++) {
                String href = select.get(i).attributes().get("href");
                parseCernyRytir(fetchFromCernyRytirURL(href), builder);
            }
        }
        ImmutableList<DailyCardInfo> build = builder.build();
        log.info("sniff (edition = {}) size = {}", edition.getName(), build.size());
        return build;
    }

    /**
     * Parsuje karty na cernem rytiri
     * 
     * @param doc
     * @param builder
     * @throws IOException
     */
    private void parseCernyRytir(Document doc, Builder<DailyCardInfo> builder) throws IOException {
        Elements values = doc.select("table.kusovkytext tbody tr");
        // prvni tri jsou formular, preskocit
        // dale jsou vzdy informace po trech
        if (values.size() >= 6) {
            for (int i = 3; i < values.size(); i = i + 3) {
                Element nameE = values.get(i);
                Element ediceTypE = values.get(i + 1);
                Element dataE = values.get(i + 2);

                Card card = CardConverter.valueOfCernyRytirElement(nameE, ediceTypE, dataE);
                Elements select2 = dataE.select("font");
                long skladem = Utils.parseLong(select2.get(0).html().replace("&nbsp;", "").replace("ks", ""), 0);
                String[] val = select2.get(1).html().split("&nbsp;");
                long cena = Utils.parseLong(val[0], 0);

                DailyCardInfo dci = new DailyCardInfo(card, BigDecimal.valueOf(cena), skladem, new Date(), CardShop.CERNY_RYTIR);
                builder.add(dci);
            }
        } else {
            // empty
        }
    }

    private Document fetchFromCernyRytirKusovky(String cardName) throws IOException {
        Document doc;
        doc = Jsoup.connect("http://www.cernyrytir.cz/index.php3?akce=3")
                .data("edice_magic", "libovolna")
                .data("rarita", "A")
                .data("foil", "A")
                .data("jmenokarty", cardName)
                .data("triditpodle", "ceny")
                .data("submit", "Vyhledej")
                // and other hidden fields which are being passed in post request.
                .userAgent("Mozilla")
                .post();
        return doc;
// return Jsoup.parse(new File("C://cr.html"), "utf-8"); //for DEBUG
    }

    private Document fetchFromCernyRytirKusovkyPaged(CardEdition edice, String rarityCrPrefix) throws IOException {
        Document doc;
        doc = Jsoup.connect("http://www.cernyrytir.cz/index.php3?akce=3")
                .timeout(5000)
                .data("edice_magic", edice.getKey())
                .data("rarita", rarityCrPrefix)
                .data("foil", "A") // i s foil, R - bez, F - pouze foil
                .data("jmenokarty", "")
                .data("triditpodle", "ceny")
                .data("submit", "Vyhledej")
                // and other hidden fields which are being passed in post request.
                .userAgent("Mozilla")
                .post();
        return doc;
    }

    private Document fetchFromCernyRytirURL(String queryString) throws IOException {
        Document doc;
        doc = Jsoup.connect("http://www.cernyrytir.cz/" + queryString)
                .userAgent("Mozilla")
                .post();
        return doc;
    }

    public static void main(String[] args) {
        try {
            CardEdition modern_horizons = CardEdition.valueFromName("Eldritch Moon");
            List<DailyCardInfo> cards = new CernyRytirLoader().sniffByEdition(modern_horizons);
            log.debug("size: {}", cards.size());
            log.debug("{}", cards);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
