import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mohammad Yaqoob :: BE/10025/2013.
 *
 * WEB CRAWLER.
 */
public class crawler {
    public int MAX_PAGES_TO_SEARCH;
    public int timeoutPeriod;
    public String url;

    public void crawl(String domain, int pageSize, int timeoutPeriod) throws Exception { //method for setting the parameters global
        this.MAX_PAGES_TO_SEARCH = pageSize;
        this.timeoutPeriod = timeoutPeriod;
        this.url = domain;
        //crawling(url);
        int lock=0;
        long savedStartTime = System.currentTimeMillis();
        while(System.currentTimeMillis() <= (savedStartTime+(timeoutPeriod*1000))) {

            if(lock == 0){
                crawling(url);
                lock =1;
            }
            //lock = 1;
        }
    }

    public void crawling(String domain) throws Exception {
        Set<String> visitedSet = new HashSet<String>(); //using SET to keep urls unique,hence no repetition
        visitedSet.add(domain); //adding the base URL to the set

        URL url = new URL(domain);

        pageSourceProvider sourceProvider = new pageSourceProvider();

        File file = new File("urlList.txt"); //output file of the crawled pages
        FileOutputStream fos = new FileOutputStream(file); //this is for the file
        PrintWriter pw = new PrintWriter(fos, true);

        File OUTPUTHTMLFILE;
        FileOutputStream OUTPUTSTREAMforHTMLPAGE;
        //System.out.println("hello");
        //String pageSource = sourceProvider.getWebSource(domain);
        String pageSource = sourceProvider.getWebSource(domain);
        pageSource.toLowerCase();
        //System.out.println(pageSource);

        //ArrayList links = retrieveLinks(pageSource, visitedSet, MAX_PAGES_TO_SEARCH);
        ArrayList links = getAllLinks(pageSource,visitedSet,MAX_PAGES_TO_SEARCH);

        if (links.size() == 0) {
            System.out.println("Sorry, No page found.");
            System.exit(0);
        }
        if(links.size() < MAX_PAGES_TO_SEARCH){
            System.out.println("Found "+links.size() + " pages out of "+MAX_PAGES_TO_SEARCH+"   :(");
        }
        if(links.size() == MAX_PAGES_TO_SEARCH){
            System.out.println("Found "+links.size() + " pages out of "+MAX_PAGES_TO_SEARCH+"   :)");
        }

        for(Object s: links){
            System.out.println(s);
            //String OUTPUTHTMLPAGE = s.toString()+".html";
            //OUTPUTHTMLFILE = new File(OUTPUTHTMLPAGE);
            //OUTPUTSTREAMforHTMLPAGE = new FileOutputStream(OUTPUTHTMLFILE);
            pw.write(s.toString());
            pw.println();
        }

    }

    private ArrayList retrieveLinks(String pageSource, Set crawledList, int MAX_PAGES_TO_SEARCH) {
        int total = 0;
        //Pattern p = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
        String regexp = "http://(\\w+\\.)+(\\w+)";
        Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pageSource);
        ArrayList linkList = new ArrayList(); // Create list of link matches.
        while (m.find()) {
            String link = m.group(1).trim();
            // Skip empty links.
            if (link.length() < 1) {
                    continue;
            }
            // Skip links that are just page anchors.
            if (link.charAt(0) == '#') {
                continue;
            }
            // Skip mailto links.
            if (link.contains("mailto")) {
                continue;
            }
            // Skip Javascript links.
            if (link.contains("javascript")) {
                continue;
            }

            // Remove anchors from link.
            int index = link.indexOf('#');
            if (index != -1) {
                link = link.substring(0, index);
            }// Remove leading "www" from URL's host if present.
            link = removeWwwFromUrl(link);
            // Verify link and skip if invalid.
            /*URL verifiedLink = verifyUrl(link);
            if (verifiedLink == null) {
                continue;
            }  */

            // Skip link if it has already been crawled.
            if (crawledList.contains(link)) {
                continue;
            }
            else{
                linkList.add(link); // Add link to list.
            }
            if(linkList.size() == MAX_PAGES_TO_SEARCH){
                break;
            }
            //linkList.add(link); // Add link to list.

        }
        return (linkList);
    }
    private URL verifyUrl(String url) { //to verify a given url
        if (!url.toLowerCase().startsWith("http://")) {
            return null;
        }
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }
        return verifiedUrl;
    }
    private String removeWwwFromUrl(String url) { //removing WWW from the URL
        int index = url.indexOf("://www.");
        if (index != -1) {
            return url.substring(0, index + 3) + url.substring(index + 7);
        }
        return (url);
    }

    public ArrayList getAllLinks(String pageSource, Set crawledList, int MAX_PAGES_TO_SEARCH) {
        pageSourceProvider provider = new pageSourceProvider();
        String urls[] = new String[MAX_PAGES_TO_SEARCH];
        int i = 0, j = 0, tmp = 0, total = 0, MAX = MAX_PAGES_TO_SEARCH;
        int start = 0, end = 0;
        ArrayList<String> list = new ArrayList<String>();
        end = pageSource.indexOf("<body");
        for (i = total; i < MAX; i++, total++) {
            start = pageSource.indexOf("http://", end);
            if (start == -1) {
                start = 0;
                end = 0;
                try {
                    pageSource = provider.getWebSource(urls[j++]);
                } catch (Exception e) {
                    //System.out.println("******************");
                    //System.out.println(urls[j-1]);
                    //System.out.println("Exception caught \n"+e);
                }

                end = pageSource.indexOf("<body");
                if (end == -1) {
                    end = start = 0;
                    continue;
                }
            }
            end = pageSource.indexOf("\"", start);
            tmp = pageSource.indexOf("'", start);
            if (tmp < end && tmp != -1) {
                end = tmp;
            }
            url = pageSource.substring(start, end);
            urls[i] = url;

            url = removeWwwFromUrl(url); //Remove leading "www" from URL's host if present.

            URL verifiedLink = verifyUrl(url); // Verify link and skip if invalid.

            if (verifiedLink == null) {
                continue;
            }

            if (!crawledList.contains(url)) {
                list.add(url);
            }

        }
        return list;
    }

}
