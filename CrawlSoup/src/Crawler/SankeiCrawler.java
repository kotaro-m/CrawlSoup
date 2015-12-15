package Crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar [lastname at gmail dot com]
 */
public class SankeiCrawler extends WebCrawler {


    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && (href.startsWith("http://www.sankei.com/"));
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         File file = new File("/data/test3.txt");



         if (page.getParseData() instanceof HtmlParseData) {
             int docid = page.getWebURL().getDocid();
             try {
     			FileWriter pw = new FileWriter(file,true);

     			if(url.startsWith("http://www.sankei.com/")
     					&& url.matches(".*" + "news" + ".*")
     					&& !url.matches(".*" + "newslist" + ".*")
     					&& !url.matches(".*" + "photo" + ".*")){
	                pw.write("Docid: " + docid + "\r\n");
	                pw.write("URL: " + url + "\r\n");
	                pw.write("---------------------------------------------------\r\n");
     			}

     			pw.close();
     		} catch (IOException e) {
     			e.printStackTrace();
     		}

         }
    }
}