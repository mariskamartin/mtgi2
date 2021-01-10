package cz.mariskamartin.mtgi2.sniffer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import cz.mariskamartin.mtgi2.Utils;
import cz.mariskamartin.mtgi2.db.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

public class TolarieLoader implements ISniffer {
    private static final Logger log = LoggerFactory.getLogger(TolarieLoader.class);

    private static final int END_PAGE_INDEX_OFSET = 2;

    public TolarieLoader() {
        try {
            enableSSLSocket();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public List<DailyCardInfo> sniffByCardName(String name) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        if (name == null) return builder.build();
        parseTolarie(fetchFromTolarieKusovky(name), builder);
        return builder.build();
    }

    @Override
    public List<DailyCardInfo> sniffByEdition(CardEdition edition) throws IOException {
        return sniffByEdition(ManagedCardEditions.instance.getInfo(edition).getTolarieUrlKey());
    }

    private List<DailyCardInfo> sniffByEdition(String edition) throws IOException {
        Builder<DailyCardInfo> builder = ImmutableList.builder();
        if (edition == null) return builder.build();
        Document doc = fetchFromTolarieKusovkyPaged(edition, 1);
        parseTolarie(doc, builder);

        Elements select = doc.select("div.pagination li");
        if (select.size() > 0) {
            int numberOfPages = Integer.parseInt(select.get(select.size() - END_PAGE_INDEX_OFSET).text());
            for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
                parseTolarie(fetchFromTolarieKusovkyPaged(edition, pageIndex), builder);
            }
        }
        return builder.build();
    }

    /**
     * Parsuje karty na Tolarii
     * 
     * @param doc
     * @param builder
     * @throws IOException
     */
    private void parseTolarie(Document doc, Builder<DailyCardInfo> builder) throws IOException {
        Elements values = doc.select("table.kusovky tbody tr");
        for (Element element : values) {
            try {
                Card card = CardConverter.valueOfTolarieElement(element);
                Elements innerValues = element.children();
                long skladem = Utils.parseLong(innerValues.get(0).text().replaceAll(".*skladem (\\d+).*","$1"), 0);
                long cena = Utils.parseLong(innerValues.get(6).text().replaceAll("(\\d+).*","$1"), 0);
                DailyCardInfo dci = new DailyCardInfo(card, BigDecimal.valueOf(cena), skladem, new Date(), CardShop.TOLARIE);
                builder.add(dci);
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }

    private Document fetchFromTolarieKusovky(String findString) throws IOException {
        String urlRequest = "http://www.tolarie.cz/koupit_karty/?name=" + findString.replace(" ", "+")
                + "&o=name&od=a";
        Document doc = Jsoup.connect(urlRequest).timeout(5000).get();
        return doc;
    }

    private Document fetchFromTolarieKusovkyPaged(String edice, int page) throws IOException {
        if (page > 30) {
            throw new IllegalStateException("Too much pages. Check if this is correct query.");
        }
        String urlRequest = "http://www.tolarie.cz/koupit_karty/?name=&edition=" + edice
                + "&o=name&od=a&foil=False&stored=False&p=" + page;

        Document doc = Jsoup.connect(urlRequest).ignoreHttpErrors(true).followRedirects(true).timeout(10000).get();
        return doc;
// return Jsoup.parse(new File("C://tolarie.html"), "utf-8"); //for DEBUG
// return Jsoup.parse(new File("C://tolarie.html"), "windows-1250"); // for DEBUG
    }


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        List<DailyCardInfo> temple_garden = new TolarieLoader().sniffByCardName("Temple Garden");
//        List<DailyCardInfo> temple_garden = new TolarieLoader().sniffByEdition(CardEdition.KHANS_OF_TARKIR);
        List<DailyCardInfo> temple_garden = new TolarieLoader().sniffByEdition("edition_M25");

        System.out.println("size: " + temple_garden.size());
        System.out.println(temple_garden);
    }


    /**
     * Enables unchecked ssl connection to https address
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
}
