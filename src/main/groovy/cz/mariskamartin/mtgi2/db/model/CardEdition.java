package cz.mariskamartin.mtgi2.db.model;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Note: Pls do not change enum name when you have db records in cards!
 */
public enum CardEdition {
    AETHER_REVOLT("AER", "Aether Revolt"),
    ALARA_REBORN("ARB", "Alara Reborn"),
    ALLIANCES("ALL", "Alliances"),
    ALPHA("LEA", "Alpha"),
    AMONKHET("AKH", "Amonkhet"),
    AMONKHET_INVOCATIONS("AKI", "Amonkhet Invocations"),
    ANTIQUITIES("ATQ", "Antiquities"),
    APOCALYPSE("APC", "Apocalypse"),
    ARABIAN_NIGHTS("ARN", "Arabian Nights"),
    ARCHENEMY("ARC", "Archenemy"),
    AVACYN_RESTORED("AVR", "Avacyn Restored"),
    BETA("LEB", "Beta", "Limited Edition Beta", "Edition Beta"),
    BEATDOWN_BOX_SET("BDbs", "Beatdown Box Set"),
    BATTLE_ROYALE_BOX_SET("BRbs", "Battle Royale Box Set"),
    BATTLE_FOR_ZENDIKAR("BFZ","Battle for Zendikar"),
    BETRAYERS_OF_KAMIGAWA("BOK", "Betrayers of Kamigawa"),
    BORN_OF_THE_GODS("BNG", "Born of the Gods", "Born of Gods"),
    CHAMPIONS_OF_KAMIGAWA("CHK", "Champions of Kamigawa"),
    CHRONICLES("CHR", "Chronicles"),
    COLDSNAP("CSP", "Coldsnap"),
    COMMANDER("CMD", "Commander", "Magic: The Gathering-Commander"),
    COMMANDER_2013("C13", "Commander 2013", "Commander 2013 Edition"),
    COMMANDER_2014("C14", "Commander 2014", "Commander 2014 Edition"),
    COMMANDER_2015("C15", "Commander 2015", "Commander 2015 Edition"),
    COMMANDER_2016("C16", "Commander 2016", "Commander 2016 Edition"),
    COMMANDER_2017("C17", "Commander 2017", "Commander 2017 Edition"),
    COMMANDER_2018("C18", "Commander 2018", "Commander 2018 Edition"),
    COMMANDER_2019("C19", "Commander 2019", "Commander 2019 Edition"),
    COMMANDER_2020("C20", "Commander 2020", "Commander 2020 Edition"),
    COMMANDER_2021("C21", "Commander 2021", "Commander 2021 Edition"),
    COMMANDER_2021_EXTRAS("C1E", "Commander 2021 Extras", "Commander 2021 Extras Edition"),
    COMMANDERS_ARSENAL("COA", "Commander's Arsenal"),
    COMMANDERS_ANTHOLOGY("CMA", "Commander Anthology"),
    COMMANDERS_ANTHOLOGY2("CM2", "Commander Anthology II"),
    COMMANDERS_CMR("CMR", "Commander Legends"),
    COMMANDERS_CMR_EXTRAS("CLE", "Commander CMR Extras"),
    COMMANDERS_ZNC("ZNC", "Commander ZNR"),
    COMMANDERS_CC1("CC1", "Commander Coll.: Green"),
    COMMANDERS_KHC("KHC", "Commander KHM"),
    CONFLUX("CON", "Conflux"),
    CONSPIRACY("CNS", "Conspiracy","Magic: The Gathering—Conspiracy"),
    CLASH_PACK_2015("CP15", "Clash Pack 2015","Clash Pack 2015 foils"),
    DARK_ASCENSION("DKA", "Dark Ascension"),
    DARKSTEEL("DST", "Darksteel"),
    DD_AJANI_VS_NICOL_BOLAS("DDAvB", "DD: Ajani vs. Nicol Bolas", "Duel Decks: Ajani vs. Nicol Bolas", "Ajani vs. Nicol Bolas"),
    DD_ANTHOLOGY("DDA", "DD: Anthology", "Duel Decks: Anthology", "Duel Decks Anthology"),
    DD_DIVINE_VS_DEMONIC("DDDvD", "DD: Divine vs. Demonic", "Duel Decks: Divine vs. Demonic", "Divine vs. Demonic"),
    DD_ELSPETH_VS_TEZZERET("DDEvT", "DD: Elspeth vs. Tezzeret", "Duel Decks: Elspeth vs. Tezzeret", "Elspeth vs. Tezzeret"),
    DD_ELVES_VS_GOBLINS("DDEvG", "DD: Elves vs. Goblins", "Duel Decks: Elves vs. Goblins", "Elves vs. Goblins"),
    DD_GARRUK_VS_LILIANA("DDGvL", "DD: Garruk vs. Liliana", "Duel Decks: Garruk vs. Liliana", "Garruk vs. Liliana"),
    DD_HEROES_VS_MONSTERS("DDHvM", "DD: Heroes vs. Monsters", "Duel Decks: Heroes vs. Monsters", "Heroes vs. Monsters"),
    DD_IZZET_VS_GOLGARI("DDIvG", "DD: Izzet vs. Golgari", "Duel Decks: Izzet vs. Golgari","Izzet vs. Golgari"),
    DD_JACE_VS_CHANDRA("DDJvC", "DD: Jace vs. Chandra", "Duel Decks: Jace vs. Chandra", "Jace vs. Chandra"),
    DD_JACE_VS_VRASKA("DDJvV", "DD: Jace vs. Vraska", "Duel Decks: Jace vs. Vraska","Jace vs. Vraska"),
    DD_KNIGHTS_VS_DRAGONS("DDKvD", "DD: Knights vs Dragons", "DD: Knights vs. Dragons", "Duel Decks: Knights vs. Dragons", "Knights vs. Dragons"),
    DD_PHYREXIA_VS_COALITION("DDPvC", "DD: Phyrexia vs. Coalition", "Duel Decks: Phyrexia vs. Coalition", "Phyrexia vs. Coalition", "Duel Decks: Phyrexia vs. the Coalition", "Phyrexia vs. the Coalition"),
    DD_SPEED_VS_CUNNING("DDSvC", "DD: Speed vs. Cunning", "Duel Decks: Speed vs. Cunning", "Speed vs. Cunning"),
    DD_SORIN_VS_TIBALT("DDSvT", "DD: Sorin vs. Tibalt", "Duel Decks: Sorin vs. Tibalt", "Sorin vs. Tibalt"),
    DD_VENSER_VS_KOTH("DDVvK", "DD: Venser vs. Koth", "Duel Decks: Venser vs. Koth", "Venser vs. Koth"),
    DISSENSION("DIS", "Dissension"),
    DRAGONS_OF_TARKIR("DTK","Dragons of Tarkir"),
    EXI("EXI","Explorers of Ixalan"),
    DOMINARIA("DOM","Dominaria"),
    IXALAN("XLN","Ixalan"),
    RIVALS_OF_IXALAN("RIX","Rivals of Ixalan"),
    ETHERNAL_MASTERS("EMA","Eternal Masters"),
    ULTIMATE_MASTERS("UMA","Ultimate Masters"),
    ULTIMATE_BOX_TOPPERS("UTB","Ultimate Box Toppers"),
    DRAGONS_MAZE("DGM", "Dragon's Maze"),
    EDITION_3TH("3ED", "3rd Edition", "Thirdth Edition", "Revised Edition"),
    EDITION_4TH("4ED", "4th Edition", "Fourth Edition"),
    EDITION_5TH("5ED", "5th Edition", "Fifth Edition"),
    EDITION_6TH("6ED", "6th Edition", "Classic Sixth Edition", "Sixth Edition"),
    EDITION_7TH("7ED", "7th Edition", "Seventh Edition"),
    EDITION_8TH("8ED", "8th Edition", "Eighth Edition"),
    EDITION_9TH("9ED", "9th Edition"),
    EDITION_10TH("10E", "10th Edition"),
    ELDRITCH_MOON("EMN","Eldritch Moon"),
    EVENTIDE("EVE", "Eventide"),
    EXODUS("EXO", "Exodus"),
    FALLEN_EMPIRES("FEM", "Fallen Empires"),
    FATE_REFORGED("FRF", "Fate Reforged"),
    FIFTH_DAWN("5DN", "Fifth Dawn"),
    FORGOTTEN_REALMS("AFR","Forgotten Realms"),
    FORGOTTEN_REALMS_EXTRAS("AFE","Forgotten Realms Extras"),
    FTV_ANNIHILATION("FTVA", "FTV: Annihilation", "From the Vault: Annihilation"),
    FTV_DRAGONS("FTVD", "FTV: Dragons", "From the Vault: Dragons"),
    FTV_EXILED("FTVE", "FTV: Exiled", "From the Vault: Exiled"),
    FTV_LEGENDS("FTVL", "FTV: Legends", "From the Vault: Legends"),
    FTV_REALMS("FTVRlm", "FTV: Realms", "From the Vault: Realms"),
    FTV_RELICS("FTVRlc", "FTV: Relics", "From the Vault: Relics"),
    FTV_TWENTY("FTV20", "FTV: Twenty", "From the Vault: Twenty"),
    FUTURE_SIGHT("FUT", "Future Sight"),
    GATECRASH("GTC", "Gatecrash"),
    GUILDPACT("GPT", "Guildpact"),
    GS_JIANG_YANGGU("GS1","GS: Jiang Yanggu"),
    HOMELANDS("HML", "Homelands"),
    HOUR_OF_DEVASTATION("HOU","Hour of Devastation"),
    ICE_AGE("ICE", "Ice Age"),
    IMA("IMA","Iconic Masters"),
    INNISTRAD("ISD", "Innistrad"),
    INVASION("INV", "Invasion"),
    JOURNEY_INTO_NYX("JOU", "Journey into Nyx"),
    JUDGMENT("JUD", "Judgment"),
    JMP("JMP","Jumpstart"),
    KALDHEIM("KHM","Kaldheim"),
    KHE("KHE","Kaldheim Extras", "Kaldheim Extra"),
    KHANS_OF_TARKIR("KTK", "Khans of Tarkir"),
    KALADESH("KLD","Kaladesh"),
    KALADESH_INVENTIONS("KLI","Kaladesh Inventions"),
    LEGENDS("LEG", "Legends"),
    LEGIONS("LGN", "Legions"),
    LORWYN("LRW", "Lorwyn"),
    MASTERS_25("A25","Masters 25"),
    MAGIC_2010("M10", "Magic 2010", "MAGIC 10"),
    MAGIC_2011("M11", "Magic 2011", "MAGIC 11"),
    MAGIC_2012("M12", "Magic 2012", "MAGIC 12"),
    MAGIC_2013("M13", "Magic 2013", "MAGIC 13"),
    MAGIC_2014("M14", "Magic 2014", "MAGIC 14", "Magic 2014 Core Set"),
    MAGIC_2015("M15", "Magic 2015", "MAGIC 15", "Magic 2015 Core Set"),
    MAGIC_2019("M19", "Core Set 2019"),
    MAGIC_2020("M20", "Core Set 2020"),
    MAGIC_2021("M21", "Core Set 2021"),
    MAGIC_E21("E21", "Core Set 2021 Extras", "Core Set 2021 - Extras"),
    MAGIC_ORIGINS("ORI","Magic Origins"),
    MERCADIAN_MASQUES("MMQ", "Mercadian Masques"),
    MIRAGE("MIR", "Mirage"),
    MIRRODIN("MRD", "Mirrodin"),
    MIRRODIN_BESIEGED("MBS", "Mirrodin Besieged"),
    MODERN_EVENT_DECK("MED", "Modern Event Deck", "Modern Event Deck 2014"),
    MODERN_HORIZONS("MH1", "Modern Horizons"),
    MODERN_HORIZONS_2("MH2", "Modern Horizons II"),
    MODERN_HORIZONS_2_EXTRA("M2E", "Modern Horizons II Extras"),
    MODERN_MASTERS("MMA", "Modern Masters"),
    MASTERS_SERIES_TOLARIE("XTMS", "Masters Series"),
    MODERN_MASTERS_2015("MM2", "Modern Masters 2015", "Modern Masters 2015 Edition"),
    MODERN_MASTERS_2017("MM3", "Modern Masters 2017", "Modern Masters 2017 Edition"),
    MORNINGTIDE("MOR", "Morningtide"),
    NEMESIS("NMS", "Nemesis"),
    NEW_PHYREXIA("NPH", "New Phyrexia"),
    OATH_OF_THE_GATEWATCH("OGW","Oath of the Gatewatch"),
    ODYSSEY("ODY", "Odyssey"),
    ONSLAUGHT("ONS", "Onslaught"),
    P_ARENA("Parena", "P - Arena"),
    P_BUY_A_BOX("Pbox", "P - Buy a Box"),
    P_FRIDAY_NIGHT_MAGIC("Pfnm", "P - Friday Night Magic", "FNM Promo"),
    P_GAMEDAY_CHAMPS("Pgd", "P - Gameday, Champs"),
    P_GATEWAY("Pgw", "P - Gateway"),
    P_GP_PT_JSS("Pgp", "P - GP, PT, JSS"),
    P_JUDGE_REWARDS("Pjudge", "P - Judge Rewards", "Judge rewards"),
    P_MEDIA_INSERTS("Pmedia", "P - Media Inserts"),
    P_MISCELLANEOUS("P", "P - Miscellaneous", "Promo karty", "Promo"),
    P_PLAYER_REWARDS("Preward", "P - Player Rewards"),
    P_PRERELEASE_RELEASE("Prel", "P - Prerelease, Release"),
    PD_FIRE_AND_LIGHTNING("PDL", "PD: Fire and Lightning", "Premium Deck Series: Fire and Lightning", "PDS: Fire and Lightning"),
    PD_GRAVEBORN("PDG", "PD: Graveborn","Premium Deck Series: Graveborn", "PDS: Graveborn"),
    PD_SLIVERS("PDS", "PD: Slivers", "Premium Deck Series: Slivers", "PDS: Slivers"),
    PLANAR_CHAOS("PLC", "Planar Chaos"),
    PLANECHASE("HOP", "Planechase"),
    PLANECHASE_2012("PC2", "Planechase 2012", "Planechase 2012 Edition"),
    PCA("PCA","Planechase Anthology"),
    PLANESHIFT("PLS", "Planeshift"),
    PORTAL("POR", "Portal"),
    PORTAL_SECOND_AGE("P02", "Portal Second Age"),
    PORTAL_THREE_KINGDOMS("PTK", "Portal Three Kingdoms"),
    PROPHECY("PCY", "Prophecy"),
    RAVNICA("RAV", "Ravnica", "Ravnica: City of Guilds"),
    GUILDS_OF_RAVNICA("GRN", "Guilds of Ravnica"),
    RAVNICA_ALLEGIANCE("RNA","Ravnica Allegiance"),
    RETURN_TO_RAVNICA("RTR", "Return to Ravnica"),
    RISE_OF_THE_ELDRAZI("ROE", "Rise of the Eldrazi"),
    SAVIORS_OF_KAMIGAWA("SOK", "Saviors of Kamigawa"),
    SCARS_OF_MIRRODIN("SOM", "Scars of Mirrodin"),
    SCOURGE("SCG", "Scourge"),
    SECRET_LAIR("SLD","P - Secret Lair", "Secret Lair"),
    SHADOWMOOR("SHM", "Shadowmoor"),
    SHADOWS_OVER_INNISTRAD("SOI","Shadows over Innistrad"),
    SHARDS_OF_ALARA("ALA", "Shards of Alara"),
    STARTER_1999("S99", "Starter 1999"),
    STX("STX","Strixhaven"),
    STE("STE","Strixhaven Extras"),
    STRONGHOLD("STH", "Stronghold"),
    TEMPEST("TMP", "Tempest"),
    THE_DARK("DRK", "The Dark"),
    THEROS("THS", "Theros"),
    TIME_SPIRAL("TSP", "Time Spiral"),
    TSR("TSR","Time Spiral Remastered"),
    TIMESHIFTED("TSB", "Timeshifted", "Time Spiral \"Timeshifted\""),
    TSF("TSF","Timeshifted Remastered"),
    TORMENT("TOR", "Torment"),
    UNGLUED("UGL", "Unglued"),
    UNHINGED("UNH", "Unhinged", "Unhinged Edition"),
    UNLIMITED("2ED", "Unlimited", "Unlimited Edition"),
    UNSANCTIONED("UND", "Unsanctioned", "Unlimited Edition"),
    UNSTABLE("UST", "Unstable", "Unlimited Edition"),
    UTB("UTB", "Ultimate Box Toppers"),
    URZAS_DESTINY("UDS", "Urza's Destiny"),
    URZAS_LEGACY("ULG", "Urza's Legacy"),
    URZAS_SAGA("USG", "Urza's Saga"),
    VISIONS("VIS", "Visions"),
    WEATHERLIGHT("WTH", "Weatherlight"),
    WORLDWAKE("WWK", "Worldwake"),
    ZENDIKAR("ZEN", "Zendikar"),
    ZENDIKAR_EXPEDITIONS("ZEX", "Zendikar Expeditions", "Zendikar Expedition"),
    WHITE_BORDERED("WBBS", "Whitebordered Basic sets"),
    BLACK_BORDERED("BBBS", "Blackbordered Basic Sets"),
    ZENDIKAR_RISING("ZNR", "Zendikar Rising"),
    ZENDIKAR_RISING_EXTRAS("ZRE", "Zendikar Rising Extras", "Zendikar Rising - Extras"),
    ZNR_EXPEDITIONS("ZRX", "ZNR Expeditions"),
    DOUBLE_MASTERS("2XM", "Double Masters"),
    DOUBLE_MASTERS_EXTRAS("2ME", "Double Masters Extras", "Double Masters - Extras"),
    IKORIA("IKO", "Ikoria"),
    IKORIA_EXTRAS("IKE", "Ikoria Extras", "Ikoria - Extras"),
    MYSTERY_BOOSTER("MB1", "Mystery Booster"),
    THEROS_BEYOND_THE_DEATH("THB", "Theros Beyond Death"),
    THEROS_EXTRAS("THE", "Theros Extras"),
    THRONE_OF_ELDRANE_("ELD", "Throne of Eldraine"),
    THRONE_OF_ELDRANE_EXTRAS("ELE", "Throne of Eldraine Extras", "Throne of Eldraine - Extras"),
    WAR_OF_THE_SPARK("WAR","War of the Spark"),
    UNKNOWN("???", "Unknown", "Legacy", "Foreign Black Bordered");


    private static final Logger logger = LoggerFactory.getLogger(CardEdition.class);
    private static final Map<String, CardEdition> cache = Maps.newHashMap();
    private String name;
    private List<String> alternativeName;
    private String key;

    private CardEdition(String key, String name, String... alternatives) {
        this.name = name;
        this.key = key;
        if (alternatives.length > 0) {
            this.alternativeName = new LinkedList<String>();
            for (String string : alternatives) {
                alternativeName.add(string.toUpperCase());
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public static CardEdition valueFromName(String editionName) {
        String name = editionName.replace("´", "'").toUpperCase();
        CardEdition cardEdition = cache.get(name);
        if (cardEdition == null) {
            for (CardEdition e : values()) {
                if (e.name.toUpperCase().equals(name)) {
                    cache.put(name, e);
                    return e;
                } else if (e.alternativeName != null) {
                    if (e.alternativeName.contains(name)) {
                        cache.put(name, e);
                        return e;
                    }
                }
            }
        } else {
            return cardEdition;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("no recognized edition: " + editionName);
        }
        return UNKNOWN;
    }
}
