package cz.mariskamartin.mtgi2.db.model;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum CardRarity {
    RARE, MYTHIC("Mythic Rare"), UNCOMMON, COMMON, UNKNOWN, TOKEN("Emblem"), LAND("basic land", "land"), SPECIAL("PROMO", "SPECIAL", "Timeshifted");

    private static final Logger logger = LoggerFactory.getLogger(CardRarity.class);
    private static final Map<String, CardRarity> cache = Maps.newHashMap();
    private List<String> alternativeNames;

    private CardRarity(String... alternatives) {
        if (alternatives.length > 0) {
            this.alternativeNames = new LinkedList<String>();
            for (String string : alternatives) {
                alternativeNames.add(string.toUpperCase());
            }
        }
    }

    public static CardRarity valueFrom(String rarityName) {
        String name = rarityName.toUpperCase();
        try {
            return CardRarity.valueOf(name);
        } catch (IllegalArgumentException e) {
            CardRarity cardRarity = cache.get(name);
            if (cardRarity == null) {
                for (CardRarity rarity : values()) {
                    if (rarity.alternativeNames != null) {
                        if (rarity.alternativeNames.contains(name)) {
                            cache.put(name, rarity);
                            return rarity;
                        }
                    }
                }
            } else {
                return cardRarity;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("no recognized rarity: " + rarityName);
            }
            return CardRarity.UNKNOWN;
        }
    }
}
