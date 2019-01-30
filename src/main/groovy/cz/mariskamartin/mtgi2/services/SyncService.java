package cz.mariskamartin.mtgi2.services;

public class SyncService {

    public boolean syncCards() {


        // select all rows where lastUpdate > local last sync / fields id, lastUpdate from central table
        // compare to local
            // a) no entry for ID > local INSERT
            // b) has entry, check TS ( update server to local, otherwise update local to server)
            // c) ids that is missing in resultSet... local lastUpdate > lastSync INSERT into server || lastUpdate < lastSync, it was deleted on server and we need to delete locally

        return false;
    }
}
