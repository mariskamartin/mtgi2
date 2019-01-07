package cz.mariskamartin.mtgi2.db;

import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DailyCardInfoRepository extends CrudRepository<DailyCardInfo, String> {
    List<DailyCardInfo> findByShop(String shop);
    List<DailyCardInfo> findByCard(Card card);
    List<DailyCardInfo> findAllByDay(Date day);

//    this is somehow wrong - it expects Card type not string...
    @Query("SELECT a FROM DailyCardInfo a WHERE a.card-id=?1")
    List<DailyCardInfo> findByCardId(String cardId);
}
