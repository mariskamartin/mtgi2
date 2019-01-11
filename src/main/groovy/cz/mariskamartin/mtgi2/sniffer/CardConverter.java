package cz.mariskamartin.mtgi2.sniffer;

import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardRarity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Trida pro transormaci elementu na entity
 *
 * @author MAR
 */
public final class CardConverter {
    private static final String CR_FOIL = " - foil";
    private static final String FOIL_TOLARIE = "(foil)";

    private static final String CR_PREDOBJEDNAVKA = " (předobjednávka, vychází 23.1)";
    private static final String CR_PREDOBJEDNAVKA_ALL = "(.*)(\\(.+edobjedn.*\\))";//Blood Crypt (předobjednávka, vychází 25.1) - foil

    private CardConverter() {
    }

    public static Card valueOfTolarieElement(Element element) {
        Elements innerValues = element.children();
        String name = innerValues.get(0).children().get(0).text().trim();
        return new Card(name.replaceAll(Pattern.quote(FOIL_TOLARIE), "").trim(),
                name.contains(FOIL_TOLARIE),
                CardRarity.valueFrom(innerValues.select("a[data-tooltip]").attr("data-tooltip").toUpperCase()),
                CardEdition.valueFromName(innerValues.get(4).text().trim()));
    }

    public static Card valueOfCernyRytirElement(Element nameE, Element ediceTypE, Element dataE) {
        String name = nameE.select("div").first().text();
        Pattern p = Pattern.compile(CR_PREDOBJEDNAVKA_ALL);
        Matcher m = p.matcher(name);
        String cardName = m.find() ? m.replaceAll("$1") : name;
        cardName = cardName.replaceAll(Pattern.quote(CR_FOIL), "").trim();
        CardRarity rarity = CardRarity.valueFrom(dataE.select("td").first().text().toUpperCase());
        CardEdition edition = CardEdition.valueFromName(ediceTypE.select("td").get(0).text());
        return new Card(cardName, name.contains(CR_FOIL), rarity, edition);
    }

    public static Card valueOfNajadaElement(Element element) {
        Elements innerValues = element.children();
        return new Card(innerValues.get(0).text().trim(),
                innerValues.get(1).text().toLowerCase().contains("ano"),
                CardRarity.valueFrom(innerValues.get(5).text().trim().toUpperCase()),
                CardEdition.valueFromName(innerValues.get(6).text().trim()));
    }

    public static Card valueOfRishadaElement(Element element) {
//        Card c = new Card();
//        Elements innerValues = element.children();
//        c.setName(innerValues.get(0).text().trim());
//        c.setFoil(innerValues.get(1).text().toLowerCase().contains("ano"));
//        c.setRarity(CardRarity.valueFrom(innerValues.get(4).text().trim().toUpperCase()));
//        c.setEdition(CardEdition.valueFromName(innerValues.get(1).text().trim()));
//        return c;
        throw new UnsupportedOperationException();
    }
}
