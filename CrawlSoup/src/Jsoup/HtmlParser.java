package Jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kuromoji.ArticleReader;

public class HtmlParser {
	public static void HtmlParser_livedoor(){
		try{
			ArrayList<String> URL = new ArrayList<String>();
			String filePath = "/data/test1.txt";
			URL = distinct(ArticleReader.URL_Forder(filePath));


			File file = new File("/data/article.txt");
			FileWriter pw = new FileWriter(file,true);

			for(int i=0;i<URL.size();i++){
		        String url = URL.get(i);
		        Document document = Jsoup.connect(url).timeout(0).get();
		        pw.write("URL:" + url + "\r\n");
		    	pw.write("publisher:livedoor\r\n");
				Element content = document.select("article").first();
				if(content!=null){
					String title = content.select("h1").first().text();
					String time = content.select("time").first().text();
					Element article = content.select("[class=articleBody]").first();
					if(article != null){
						Element tweet = article.select("[class=twitter-tweet]").first();
						if(tweet != null)
							tweet.remove();
						String text = article.select("[itemprop=articleBody]").first().text();
						int remove =text.indexOf("【関連記事】");
						if(remove != -1)
							text = text.substring(0,remove);
						text = text.replace("。","。\r\n");
						pw.write("title:" + title + "\r\n");
						pw.write("time:" + time + "\r\n");
						pw.write("article:" + text + "\r\n");
						pw.write("---------------------------\n" + "\r\n\n\r");
					}
					else
						pw.write("記事がありません\r\n");
				}
		    }
			pw.close();
	    }
	    catch(FileNotFoundException e){
	        	  System.out.println(e);
	    }
	    catch(IOException e){
	    	System.out.println(e);
	    }
	}

	public static void HtmlParser_yn(){
		try{
			ArrayList<String> URL = new ArrayList<String>();
			String filePath = "/data/test2.txt";
			URL = distinct(ArticleReader.URL_Forder(filePath));


			File file = new File("/data/article.txt");
			FileWriter pw = new FileWriter(file,true);

			for(int i=0;i<URL.size();i++){
		        String url = URL.get(i);
		        Document document = Jsoup.connect(url).timeout(0).get();
				pw.write("URL:" + url + "\r\n");
				pw.write("publisher:yn\r\n");
				Element content = document.select("[class=article]").first();
				if(content!=null){
					String title = content.select("h1").text();
					String time = content.select("[class=ynLastEditDate yjSt]").text();
					String article = content.select("[class=ynDetailText]").text().replace("。", "。\r\n");
					pw.write("title:" + title + "\r\n");
					pw.write("time:" + time + "\r\n");
					pw.write("article:" + article + "\r\n");
					pw.write("---------------------------" + "\r\n\r\n");
				}
				else
					pw.write("記事がありません");
		    }
			pw.close();
	    }
	    catch(FileNotFoundException e){
	        	  System.out.println(e);
	    }
	    catch(IOException e){
	    	System.out.println(e);
	    }
	}

	public static void HtmlParser_sankei(){
		try{
			ArrayList<String> URL = new ArrayList<String>();
			String filePath = "/data/test3.txt";
			URL = distinct(ArticleReader.URL_Forder(filePath));

			File file = new File("/data/article.txt");
			FileWriter pw = new FileWriter(file,true);
			for(int i=0;i<URL.size();i++){
				String url = URL.get(i);
				Document document = Jsoup.connect(url).timeout(0).get();
				pw.write("URL:" + url +"\r\n");
				pw.write("publisher:sankei\r\n");
				Element content = document.select("article").first();
				if(content!=null){
					String title = content.select("h1").text().replace(" PR", "");
					String time = content.select("time").text();
					String article = content.select("[class=fontMiddiumText]").text().replace("。", "。\r\n");
					pw.write("title:" + title + "\r\n");
					pw.write("time:" + time + "\r\n");
					pw.write("article:" + article + "\r\n");
					pw.write("---------------------------" + "\r\n\r\n");
				}
				else
				pw.write("記事がありません\r\n");
			}
			pw.close();
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	public static ArrayList<String> distinct(ArrayList<String> slist){
		return new ArrayList<String>(new LinkedHashSet<String>(slist));
	}
}