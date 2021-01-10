package cz.mariskamartin.mtgi2;

import com.subgraph.orchid.TorClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class TorApp {
    private static final Logger log = LoggerFactory.getLogger(TorApp.class);
    private static final int TOR_TIMEOUT_SECONDS = 240;

    public static void main(String[] args) {
        TorClient torClient = new TorClient();
        File directory = new File("mtgi-tor");

        torClient.getConfig().setDataDirectory(directory);
        try {
            log.info("Starting ...");
            if (torClient != null) {
                log.info("Starting Tor/Orchid ...");
                torClient.enableSocksListener(9050);
                torClient.start();
                try {
                    torClient.waitUntilReady(TOR_TIMEOUT_SECONDS * 1000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                log.info("Tor ready");
            }

            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050));

            Document docIP = Jsoup.connect("https://api.ipify.org/?format=xml").proxy(proxy).timeout(5000).get();
            System.out.println(docIP.toString());

            String findString = "Breeding Pool";
            String urlRequest = "https://www.tolarie.cz/koupit_karty/?name=" + findString.replace(" ", "+")
                    + "&o=name&od=a";
            Document doc = Jsoup.connect(urlRequest).proxy(proxy).timeout(5000).get();

            System.out.println(doc.toString());
//            Jsoup.connect("xy")

        } catch (Throwable e) {
            log.error("Exception when starting up", e);  // The executor swallows exceptions :(
        } finally {
            torClient.stop();
        }
    }
}
