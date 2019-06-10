package cz.mariskamartin.mtgi2.db;

import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardRarity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CardRepository extends CrudRepository<Card, String> {
    List<Card> findByName(String name);
    List<Card> findByNameOrderByNameAsc(String name);
    List<Card> findByNameContaining(String name);
    List<Card> findByRarity(CardRarity rarity);
    List<Card> findByEdition(CardEdition edition);
    List<Card> findByFoil(boolean foil);

    List<Card> findBySyncedLessThan(Date synced);
    List<Card> findBySyncedIsNull();

    @Query("select d from Card d where d.id = ?1")
    List<Card> findByCardId(String id);

    @Query("select max(d.updated) from Card d")
    Date findMaxUpdated();

}
