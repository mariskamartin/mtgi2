package cz.mariskamartin.mtgi2.db.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Slouzi pro zachyceni informaci, ktere edice se maji stahovat
 *
 * @author MAR
 */
public enum ManagedCardEditions {
    instance;

    private Map<CardEdition, EditionInfo> managedEditions = Maps.newHashMap();

    ManagedCardEditions() {
        add(CardEdition.STX, null, null);
        add(CardEdition.STE, null, null);
        add(CardEdition.TSF, null, null);
        add(CardEdition.TSR, null, null);

        add(CardEdition.KALDHEIM, null, null);
        add(CardEdition.KHE, null, null);

        add(CardEdition.AETHER_REVOLT, "edition_Aether_Revolt","173");
        add(CardEdition.MAGIC_ORIGINS, "edition_Magic_Origins","17");

        add(CardEdition.RAVNICA_ALLEGIANCE, "edition_RNA","180");
        add(CardEdition.GUILDS_OF_RAVNICA, "edition_GRN","179");
        add(CardEdition.KHANS_OF_TARKIR, "edition_Khans_of_Tarkir","165");

        add(CardEdition.MODERN_HORIZONS, null,null);
        add(CardEdition.DOUBLE_MASTERS, null,null);
        add(CardEdition.MODERN_MASTERS_2017, "edition_vanoce",null);
        add(CardEdition.MODERN_MASTERS, "edition_Modern_Masters",null);
        add(CardEdition.MODERN_MASTERS_2015, "edition_Modern_Masters_2015",null);
        add(CardEdition.MASTERS_25, "edition_M25",null);
        add(CardEdition.MAGIC_2019, "edition_M19", null);

        add(CardEdition.JOURNEY_INTO_NYX, "edition_Journey_Into_Nyx","164");
        add(CardEdition.BORN_OF_THE_GODS, "edition_Born_of_Gods","163");
        add(CardEdition.THEROS, "edition_Theros","162");

//        add(CardEdition.TIMESHIFTED, "edition_Timeshifted", null);
//        managed.add(new EditionInfo(CardEdition.COMMANDER_2014, null, "3037"));
//
//        //cr predprodej
//        managed.add(new EditionInfo(CardEdition.FATE_REFORGED, "Fate_Reforged", "166"));
//
//        managed.add(new EditionInfo(CardEdition.MORNINGTIDE, "Morningtide", "144"));
//        managed.add(new EditionInfo(CardEdition.LORWYN, "Lorwyn", "143"));
//        managed.add(new EditionInfo(CardEdition.MAGIC_2014, "M14", null));
//        managed.add(new EditionInfo(CardEdition.RETURN_TO_RAVNICA, "Return_to_Ravnica", "159"));
//        managed.add(new EditionInfo(CardEdition.GATECRASH, "Gatecrash","160"));
//        managed.add(new EditionInfo(CardEdition.DRAGONS_MAZE, "Dragons_Maze", "161"));
//        managed.add(new EditionInfo(CardEdition.INNISTRAD, "Innistrad", "156"));
//        managed.add(new EditionInfo(CardEdition.ZENDIKAR, "Zendikar", "150"));
//        managed.add(new EditionInfo(CardEdition.MODERN_EVENT_DECK, null, "3029"));
//        managed.add(new EditionInfo(CardEdition.MODERN_MASTERS, "Modern_Masters", "3021"));
//        managed.add(new EditionInfo(CardEdition.NEW_PHYREXIA, "New_Phyrexia", "155"));
//        managed.add(new EditionInfo(CardEdition.SCARS_OF_MIRRODIN, "Scars_of_Mirrodin", "153"));
//
    }

    /*
TOALREIE mapping
 =
Standard = custom_f_3
Modern = custom_f_4
Core Set 2019 = custom_f_7
Guilds of Ravnica = custom_f_9
Dominaria = custom_f_6
Battlebond = custom_f_8
------------------------- = -
4th Edition = edition_4th_Edition
5th Edition = edition_5th_Edition
6th Edition = edition_6th_edition
7th Edition = edition_7th Edition
8th Edition = edition_8th_Edition
9th Edition = edition_9th_Edition
10th Edition = edition_10th_Edition
Aether Revolt = edition_Aether_Revolt
Alara Reborn = edition_Alara_Reborn
Amonkhet = edition_Amonkhet
Apocalypse = edition_Apocalyspe
Archenemy = edition_Archenemy
Avacyn Restored = edition_Avacyn_Restored
Battle for Zendikar = edition_Battle_for_Zendikar
Battlebond = edition_BBD
Betrayers of Kamigawa = edition_Betrayers_of_Kamigawa
Born of Gods = edition_Born_of_Gods
Clash Pack = edition_Clash_Pack
Coldsnap = edition_Coldsnap
Commander = edition_Commander
Commander's Arsenal = edition_Commanders_Arsenal
Commander 2013 = edition_Commander_2013
Commander 2014 = edition_Commander_2014
Conflux = edition_Conflux
Core Set 2019 = edition_M19
Commander - různé edice = edition_CXY
Conspiracy = edition_Conspiracy
Dark Ascension = edition_Dark_Ascension
Darksteel = edition_Darksteel
DD: Ajani vs. Nicol Bolas = edition_DD_Ajani_vs_Bolas
DD: Divine vs. Demonic = edition_DD_Divine_vs_Demonic
DD: Elspeth vs. Kiora = edition_DD_Elspeth_vs_Kiora
DD: Elspeth vs. Tezzeret = edition_DD_Elspeth_vs_Tezzeret
DD: Elves vs. Goblins = edition_DD_Elves_vs_Goblins
DD: Garruk vs. Liliana = edition_DD_Garruk_vs_Liliana
DD: Heroes vs. Monsters = edition_DD_Heroes_vs_Monsters
DD: Izzet vs. Golgari = edition_DD_Izzet_vs_Golgari
DD: Jace vs. Chandra = edition_DD_Jace_vs_Chandra
DD: Jace vs. Vraska = edition_DD_Jace_vs_Vraska
DD: Knights vs. Dragons = edition_DD_Knights_vs_Dragons
DD: Phyrexia vs. Coalition = edition_DD_phyrexian_vs_coalition
DD: Sorin vs. Tibalt = edition_DD_Sorin_vs_Tibalt
DD: Speed vs. Cunning = edition_dd_speed_vs_cunning
DD: Venser vs. Koth = edition_DD_Koth_vs_Venser
Dissension = edition_Dissension
Dominaria = edition_dominaria
Dragon's Maze = edition_Dragons_Maze
Dragons of Tarkir = edition_Dragons_of_Tarkir
Duels of the Planeswalkers = edition_dotp
Eldritch Moon = edition_Eldritch_Moon
Eternal Masters = edition_Eternal_Masters
Eventide = edition_Eventide
Exodus = edition_Exodus
Fate Reforged = edition_Fate_Reforged
Fifth Dawn = edition_Fifth_Dawn
FTV: Angels = edition_FTV_Angels
FTV: Annhilation = edition_FTV_Annhilation
FTV: Exiled = edition_FTV_Exiled
FTV: Legends = edition_FTV_Legends
FTV: Lore = edition_FTV_Lore
FTV: Realms = edition_FTV_Realms
FTV: Relics = edition_FTV_Relics
FTV: Twenty = edition_FTV_Twenty
Future Sight = edition_Future_Sight
Gatecrash = edition_Gatecrash
Guildpact = edition_Guildpact
Hour of Devastation = edition_Hour_of_Devastation
Guilds of Ravnica = edition_GRN
Champions of Kamigawa = edition_Champions_of_Kamigawa
Chronicles = edition_Chronicles
Iconic Masters = edition_IMA
Ice Age = edition_Ice_Age
Innistrad = edition_Innistrad
Invasion = edition_Invasion
Ixalan = edition_XLN
Journey Into Nyx = edition_Journey_Into_Nyx
Judgement = edition_Judgement
Kaladesh = edition_Kaladesh
Kaladesh Inventions = edition_kld_inventions
Khans of Tarkir = edition_Khans_of_Tarkir
Legends = edition_Legends
Legions = edition_Legions
Lorwyn = edition_Lorwyn
Magic Origins = edition_Magic_Origins
Magic 10 = edition_M10
Magic 11 = edition_M11
Magic 12 = edition_M12
Magic 13 = edition_M13
Magic 14 = edition_M14
Magic 15 = edition_M15
Mercadian Masques = edition_Mercadian_Masques
Masters 25 = edition_M25
Masters Series = edition_MMS
Mirrodin = edition_Mirrodin
Mirrodin Besieged = edition_Mirrodin_Besieged
Modern Event Deck = edition_Modern_Event_Deck
Modern Masters = edition_Modern_Masters" selected="selected
Modern Masters 2015 = edition_Modern_Masters_2015
Modern Masters 2017 = edition_vanoce
Morningtide = edition_Morningtide
Nemesis = edition_Nemesis
New Phyrexia = edition_New_Phyrexia
Oath of the Gatewatch = edition_Oath_of_the_Gatewatch
Odyssey = edition_Odyssey
Onslaught = edition_Onslaught
Planar Chaos = edition_Planar_Chaos
Planechase = edition_Planechase
Planeshift = edition_Planeshift
Premium: Fire and Lightning = edition_Premium_Fire_and_Lightning
Premium: Graveborn = edition_Premium_Graveborn
Promo = edition_Foil
Promo: Arena = edition_Promo_Arena
Promo: Buy a Box = edition_Promo_Buy_a_Box
Promo: FNM = edition_Promo_FNM
Promo: Game Day = edition_Promo_Game_Day
Promo: Gateway = edition_Promo_Gateway
Promo: Grand Prix = edition_Promo_Grand_Prix
Promo: Judge Rewards = edition_Promo_Judge_Rewards
Promo: Player Rewards = edition_Promo_Player_Rewards
Promo: Prerelease, Release = edition_Promo_Prerelease_Release
Promo: Pro Tour = edition_Promo_Pro_Tour
Prophecy = edition_Prophecy
Ravnica Allegiance = edition_RNA
Ravnica: City of Guilds = edition_Ravnica
Return to Ravnica = edition_Return_to_Ravnica
Revised = edition_revised
Rise of the Eldrazi = edition_Rise_of_the_Eldrazi
Rivals of Ixalan = edition_Rivals_of_Ixalan
Saviors of Kamigawa = edition_Saviors_of_Kamigawa
Scars of Mirrodin = edition_Scars_of_Mirrodin
Scourge = edition_Scourge
Shadowmoor = edition_Shadowmoor
Shadows over Innistrad = edition_Shadows_over_Innistrad
Shards of Alara = edition_Shards_of_Alara
Stronghold = edition_Stronghold
Tempest = edition_Tempest
Theros = edition_Theros
Timeshifted = edition_Timeshifted
Time Spiral = edition_Time_Spiral
Torment = edition_Torment
Unstable = edition_UST
Urza's Destiny = edition_Urzas_Destiny
Urza's Legacy = edition_Urzas_Legacy
Urza's Saga = edition_Urzas_Saga
Visions = edition_Visions
Weartherlight = edition_Weatherlight
Worldwake = edition_Worldwake
Zendikar = edition_Zendikar
Zendikar Expeditions = edition_Zendikar_Expeditions
Legacy = edition_Legacy
Tokens = edition_tokens

    */


    private void add(CardEdition edition, String tolarieUrlKey, String rishadaUrlKey) {
        managedEditions.put(edition, new EditionInfo(edition, tolarieUrlKey, rishadaUrlKey));
    }

    /**
     * Vraci seznam monitorovanych edic
     *
     * @return
     */
    public Collection<CardEdition> getManagedEditions() {
        return Collections.unmodifiableCollection(managedEditions.keySet());
    }

    /**
     * Vrati dodatecn informace k edici
     *
     * @param edition
     * @return
     */
    public EditionInfo getInfo(CardEdition edition) {
        return managedEditions.get(edition);
    }

    /**
     * Pro uchovani specifickych informaci k edici
     *
     * @author MAR
     */
    public static class EditionInfo {
        private String tolarieUrlKey;
        private CardEdition edition;
        private String rishadaUrlKey;
        private String najadaUrlKey;

        public EditionInfo(CardEdition edition, String tolarieUrlKey, String rishadaUrlKey, String najadaUrlKey) {
            super();
            this.edition = edition;
            this.tolarieUrlKey = tolarieUrlKey;
            this.rishadaUrlKey = rishadaUrlKey;
            this.najadaUrlKey = najadaUrlKey;
        }

        public EditionInfo(CardEdition edition, String tolarieUrlKey, String rishadaUrlKey) {
            this(edition, tolarieUrlKey, rishadaUrlKey, null);
        }

        public String getTolarieUrlKey() {
            return tolarieUrlKey;
        }

        public String getCernyRytirUrlKey() {
            return edition.getKey();
        }

        public CardEdition getEdition() {
            return edition;
        }

        public String getRishadaUrlKey() {
            return rishadaUrlKey;
        }

        public String getNajadaUrlKey() { return najadaUrlKey; }
    }
}
