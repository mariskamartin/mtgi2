package cz.mariskamartin.mtgi2.sniffer;

import cz.mariskamartin.mtgi2.db.model.CardEdition;
import cz.mariskamartin.mtgi2.db.model.DailyCardInfo;

import java.io.IOException;
import java.util.List;

/**
 * Rozhrani pro stahovani karet z obchodu
 * @author MAR
 *
 */
public interface ISniffer {
    /**
     * Stahne karty podle jmena
     * @param name
     * @return
     */
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException;

    /**
     * Stahne karty dane edice
     * @param edition
     * @return
     */
    public List<DailyCardInfo> sniffByEdition(CardEdition edition) throws IOException;
}
