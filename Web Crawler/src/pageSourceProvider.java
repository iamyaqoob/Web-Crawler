import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * Created by Mohammad Yaqoob.
 *
 * WEB CRAWLER.
 */
public class pageSourceProvider {
    public String getWebSource(String domain) throws Exception{  //provides the source code of a domain
        try {
            URL url = new URL(domain);
            InputStreamReader inReader = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(inReader);
            String temp = "";

            String pageSource = "";
            while ((temp = br.readLine()) != null) {
                pageSource = temp + pageSource;
            }
            return pageSource;
        } catch (IOException e){
            System.out.println("ERROR: couldn't open URL ");
            return "";
        }
    }
    public String getWebPageSource(String sURL) throws IOException {
        URL url = new URL(sURL);
        URLConnection urlCon = url.openConnection();
        BufferedReader in = null;

        if (urlCon.getHeaderField("Content-Encoding") != null
                && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    urlCon.getInputStream())));
        } else {
            in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
        }

        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();

        return sb.toString();
    }
}
