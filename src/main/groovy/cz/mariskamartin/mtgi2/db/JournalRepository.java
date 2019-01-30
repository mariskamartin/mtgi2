package cz.mariskamartin.mtgi2.db;

import cz.mariskamartin.mtgi2.db.model.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, String> {

}
