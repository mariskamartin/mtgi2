package cz.mariskamartin.mtgi2.services;

import cz.mariskamartin.mtgi2.Utils;
import cz.mariskamartin.mtgi2.db.CardRepository;
import cz.mariskamartin.mtgi2.db.model.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SyncService {
    private static final Logger log = LoggerFactory.getLogger(SyncService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CardRepository cardRepository;

    public boolean checkOrInitDb() {
        jdbcTemplate.execute("DROP TABLE cards_central IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE cards_central(ID VARCHAR(255), CREATED TIMESTAMP, EDITION VARCHAR(255), FOIL BOOLEAN NOT NULL,NAME VARCHAR(255),RARITY VARCHAR(255), UPDATED TIMESTAMP, SYNCED TIMESTAMP)");
        jdbcTemplate.execute("UPDATE card SET SYNCED = null");
        return true;
    }

    public boolean syncCards() {
        Date lastUpdated = Utils.parseDateTime("2019-01-01 00:00:00");
        Date maxUpdated = cardRepository.findMaxUpdated();
        log.debug(maxUpdated.toString());
        Date lastSync = new Date();
        // select all remote rows where lastUpdate > local last sync / fields id, lastUpdate from central table
        String sql = "SELECT ID, UPDATED FROM cards_central WHERE UPDATED > '" + Utils.date2timestamp(lastUpdated) + "'";
        List<Map<String, Object>> resultSet2 = jdbcTemplate.queryForList(sql);
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList("SELECT ID, UPDATED FROM cards_central WHERE UPDATED > ?", lastUpdated);
        for (Map<String, Object> result : resultSet) {
            String remoteId = (String) result.get("ID");
            Date remoteUpdated = (Date) result.get("UPDATED");
            List<Card> byCardId = cardRepository.findByCardId(remoteId);
            if (byCardId.size() > 1) {
                throw new IllegalStateException("For ID find more than one records");
            } else if(byCardId.isEmpty()) {
                // insert it
                List<Card> query = jdbcTemplate.query("SELECT * FROM cards_central WHERE ID = ?", new Object[] {remoteId}, new BeanPropertyRowMapper(Card.class));
                if (query.size() > 1) { throw new IllegalStateException("More than one record"); }
                Card entity = query.get(0);
                entity.setSynced(lastSync);
                cardRepository.save(entity);
            } else {
                // check last update
                Card card = byCardId.get(0);
                log.debug("updated - local {} compareTo remote {}", card.getUpdated(), remoteUpdated);
                int compare = card.getUpdated().compareTo(remoteUpdated);
                if (compare > 0) {
                    // local is newer > update remote
                    jdbcTemplate.update("UPDATE cards_central SET name = ?, rarity = ?, edition = ?, updated where id = ?", card.getName(), card.getRarity().name(), card.getEditionKey(), card.getId(), card.getUpdated());
                    card.setSynced(lastSync);
                    cardRepository.save(card);
                } else if (compare < 0){
                    // remote is newer > update local
                    List<Card> query = jdbcTemplate.query("SELECT * FROM cards_central WHERE ID = ?", new Object[] {remoteId}, new BeanPropertyRowMapper(Card.class));
                    if (query.size() > 1) { throw new IllegalStateException("More than one record"); }
                    Card entity = query.get(0);
                    entity.setSynced(lastSync);
                    cardRepository.save(entity);
                } else {
                    log.debug("Entity is up to date.");
                    card.setSynced(lastSync);
                    cardRepository.save(card);
                }
            }
        }
        //select all locals without sync

        List<Card> syncedLessThanList = cardRepository.findBySyncedIsNull();
        for (Card card : syncedLessThanList) {
            card.setSynced(lastSync);
            jdbcTemplate.update("INSERT INTO cards_central (id, name, rarity, edition, foil, created, updated, synced) VALUES (?,?,?,?,?,?,?,?)",
                    card.getId(), card.getName(), card.getRarity().name(), card.getEditionKey(),
                    card.isFoil(), card.getCreated(), card.getUpdated(), card.getSynced());
            cardRepository.save(card);
        }

        // compare to local
            // a) no entry for ID > local INSERT
            // b) has entry, check TS ( update server to local, otherwise update local to server)
            // c) ids that is missing in resultSet... local lastUpdate > lastSync INSERT into server || lastUpdate < lastSync, it was deleted on server and we need to delete locally



        return false;
    }

}
