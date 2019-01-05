package cz.mariskamartin.mtgi2;

import com.google.common.collect.Lists;
import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;
import cz.mariskamartin.mtgi2.sniffer.CernyRytirLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CardService {
    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_FIND_CARD = "select * from card where name = ?";
    private final String SQL_DELETE_PERSON = "delete from people where id = ?";
    private final String SQL_UPDATE_PERSON = "update people set first_name = ?, last_name = ?, age  = ? where id = ?";
    private final String SQL_GET_ALL = "select * from people";
    private final String SQL_INSERT_PERSON = "insert into people(id, first_name, last_name, age) values(?,?,?,?)";


    @Autowired
    public CardService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Card findCard(String name) {
        return jdbcTemplate.queryForObject(SQL_FIND_CARD, new Object[] { name }, new BeanPropertyRowMapper<Card>(Card.class));
    }

    public List<Card> fetchCards(String name) throws IOException {
        List<DailyCardInfo> dailyCardInfos = new CernyRytirLoader().sniffByCardName(name);
        LinkedList<Card> cards = Lists.newLinkedList();
        for (DailyCardInfo dci : dailyCardInfos) {
            cards.add(dci.getCard());
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(cards.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
                "INSERT INTO CARD (name, foil, created, updated, rarity, edition) VALUES (:name, :foil, :created, :updated, :rarity, :edition)", batch);
        log.info("insert count = {}", updateCounts);

        return cards;
    }
}
