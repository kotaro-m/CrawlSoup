package Test;

import Jena.RDFCreate;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import kuromoji.ArticleReader;

public class TestProgram {
	public static void main(String[] args) throws Exception{
		long start = System.currentTimeMillis();

		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 1;
		int maxDepthOfCrawling=2;

		CrawlConfig config1 = new CrawlConfig();
		CrawlConfig config2 = new CrawlConfig();
		config1.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config2.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config1.setCrawlStorageFolder(crawlStorageFolder + "/crawler1");
		config2.setCrawlStorageFolder(crawlStorageFolder + "/crawler2");
		config1.setMaxPagesToFetch(1000);
		config2.setMaxPagesToFetch(100);


		PageFetcher pageFetcher1 = new PageFetcher(config1);
		PageFetcher pageFetcher2 = new PageFetcher(config2);
		RobotstxtConfig robotstxtConfig1 = new RobotstxtConfig();
		RobotstxtConfig robotstxtConfig2 = new RobotstxtConfig();
		RobotstxtServer robotstxtServer1 = new RobotstxtServer(robotstxtConfig1, pageFetcher1);
		RobotstxtServer robotstxtServer2 = new RobotstxtServer(robotstxtConfig2, pageFetcher2);
		CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer1);
		CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer2);

		controller1.addSeed("http://news.livedoor.com/");
		controller2.addSeed("http://news.yahoo.co.jp/");

		//livedoorニュース クローリング
		controller1.start(Crawler.TestCrawling.class, numberOfCrawlers);
	    //livedoorHTMLパース
		Jsoup.HtmlParser.HtmlParser_livedoor();


		//Yahoo!ニュース クローリング
		controller2.start(Crawler.ynCrawling.class, numberOfCrawlers);
	    //Yahoo!ニュース HTMLパース
		Jsoup.HtmlParser.HtmlParser_yn();

		RDFCreate.RDFCreater(ArticleReader.Title_Forder(), ArticleReader.Time_Forder(), ArticleReader.Article_Forder());
		//時間計測
		long end = System.currentTimeMillis();
		System.out.println((end - start)  + "ms\n");
	}
}


