package cz.mariskamartin.mtgi2.db;

import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardShop;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DailyCardInfoRepository extends CrudRepository<DailyCardInfo, String> {
    List<DailyCardInfo> findByShop(String shop);
    List<DailyCardInfo> findByCard(Card card);
    List<DailyCardInfo> findByCardOrderByShopAsc(Card card);
//    List<DailyCardInfo> findByCardIdOrderByShopAsc(String id); //not work properly
    List<DailyCardInfo> findByCardAndShopOrderByDay(Card card, CardShop shop);
//    List<DailyCardInfo> findByCardOrderByShopDayAsc(Card card);
//    List<DailyCardInfo> findByCardOrderByShopAndDayAsc(Card card);

    List<DailyCardInfo> findAllByDay(Date day);
    Long countByDay(Date day);
    Long countByDayAndShop(Date day, CardShop shop);
    Long countByDayAndShopAndCardEdition(Date day, CardShop shop, CardEdition edition);

//    this is somehow wrong - it expects Card type not string...
//    @Query("SELECT a FROM DailyCardInfo a WHERE a.card-id=?1")
//    List<DailyCardInfo> findByCardId(String cardId);

}
