package Crawler;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class TestCrawling extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

     @Override
     public boolean shouldVisit(WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && (href.startsWith("http://news.livedoor.com/article/detail/") || href.startsWith("http://news.livedoor.com/topics/"));
     }

     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         File file = new File("/data/test1.txt");
         if (page.getParseData() instanceof HtmlParseData) {
             int docid = page.getWebURL().getDocid();
             try {
     			FileWriter pw = new FileWriter(file,true);
     			if(url.startsWith("http://news.livedoor.com/article/detail/")){
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

