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
		int maxDepthOfCrawling=10;

		CrawlConfig config1 = new CrawlConfig();
		CrawlConfig config2 = new CrawlConfig();
		CrawlConfig config3 = new CrawlConfig();
		config1.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config2.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config3.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config1.setCrawlStorageFolder(crawlStorageFolder + "/crawler1");
		config2.setCrawlStorageFolder(crawlStorageFolder + "/crawler2");
		config3.setCrawlStorageFolder(crawlStorageFolder + "/crawler3");
		config1.setMaxPagesToFetch(2000);
		config2.setMaxPagesToFetch(2000);
		config3.setMaxPagesToFetch(2000);


		PageFetcher pageFetcher1 = new PageFetcher(config1);
		PageFetcher pageFetcher2 = new PageFetcher(config2);
		PageFetcher pageFetcher3 = new PageFetcher(config3);
		RobotstxtConfig robotstxtConfig1 = new RobotstxtConfig();
		RobotstxtConfig robotstxtConfig2 = new RobotstxtConfig();
		RobotstxtConfig robotstxtConfig3 = new RobotstxtConfig();
		RobotstxtServer robotstxtServer1 = new RobotstxtServer(robotstxtConfig1, pageFetcher1);
		RobotstxtServer robotstxtServer2 = new RobotstxtServer(robotstxtConfig2, pageFetcher2);
		RobotstxtServer robotstxtServer3 = new RobotstxtServer(robotstxtConfig3, pageFetcher3);
		CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer1);
		CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer2);
		CrawlController controller3 = new CrawlController(config3, pageFetcher3, robotstxtServer3);

		controller1.addSeed("http://news.livedoor.com/");
		controller2.addSeed("http://news.yahoo.co.jp/");
		controller3.addSeed("http://www.sankei.com/");

		//livedoorニュース クローリング
		//controller1.start(Crawler.TestCrawling.class, numberOfCrawlers);
	    //livedoorHTMLパース
		//HtmlParser.HtmlParser_livedoor();


		//Yahoo!ニュース クローリング
		//controller2.start(Crawler.ynCrawling.class, numberOfCrawlers);
	    //Yahoo!ニュース HTMLパース
		//HtmlParser.HtmlParser_yn();


		//controller3.start(Crawler.SankeiCrawler.class, numberOfCrawlers);
		//HtmlParser.HtmlParser_sankei();

		RDFCreate.RDFCreater(ArticleReader.Title_Forder(), ArticleReader.Time_Forder(), ArticleReader.Article_Forder(),ArticleReader.Publisher_Forder(),ArticleReader.URL_Forder());
		//時間計測
		long end = System.currentTimeMillis();
		System.out.println((end - start)  + "ms\n");
	}
}


