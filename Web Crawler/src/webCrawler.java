import java.util.Scanner;

/**
 * Created by Mohammad Yaqoob.
 *
 * WEB CRAWLER.
 */
public class webCrawler {
    public static void main(String lol[]) throws  Exception{
        Scanner in = new Scanner(System.in);
        crawler spider = new crawler();
        System.out.println();
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("                         WEB CRAWLER                       ");
        System.out.println("\n" +
                        "                          /      \\\n" +
                        "                       \\  \\  ,,  /  /\n" +
                        "                        '-.`\\()/`.-'\n" +
                        "                       .--_'(  )'_--.\n" +
                        "                      / /` /`\"\"`\\ `\\ \\\n" +
                        "                       |  |  ><  |  |\n" +
                        "                       \\  \\      /  /\n" +
                        "                           '.__." +"\n");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");

        System.out.print("Enter the domain name (eg: www.xxx.yyy ):    ");
        String domain = in.next(); //base DOMAIN
        domain = domain.toLowerCase();
        domain = "http://" + domain ;

        System.out.print("Enter the no. of pages to crawl:             ");
        int pageSize = in.nextInt(); //MAX PAGES TO CRAWL

        System.out.print("Enter the timeout period in Seconds:         ");
        int timeoutPeriod = in.nextInt(); //TIME OUT PERIOD, AFTER WHICH THE PROGRAM TERMINATES
        System.out.println("\nTimer Running for "+timeoutPeriod+ " seconds...\n");
        System.out.println("Printing "+pageSize+" pages...\n");

        spider.crawl(domain,pageSize,timeoutPeriod);

    }
}
