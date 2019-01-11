package cz.mariskamartin.mtgi2.db;

import cz.mariskamartin.mtgi2.db.model.Card;
import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.CardRarity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, String> {
    List<Card> findByName(String name);
    List<Card> findByNameContaining(String name);
    List<Card> findByRarity(CardRarity rarity);
    List<Card> findByEdition(CardEdition edition);
    List<Card> findByFoil(boolean foil);
}
