package lt.softec.aurelijus.fx;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final String PAYSERA_BASE_URL = "http://api.evp.lt/currency/commercial/exchange";
    private float Amount;
    private String sellCurrency;
    private String buyCurrency;

//calss constructor
    public NetworkUtils (float amount, String sellcurrency, String buycurrency) {
        Amount = amount;
        sellCurrency = sellcurrency;
        buyCurrency = buycurrency;
    }


        //http://api.evp.lt/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest

    public URL buildUrl() {

        Uri builtUri = Uri.parse(PAYSERA_BASE_URL).buildUpon()
                .appendPath(Float.toString(Amount)+"-"+sellCurrency)
                .appendPath(buyCurrency)
                .appendPath("latest")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
