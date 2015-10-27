package Jsoup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kuromoji.kuromoji;

public class HtmlParser {
	public static void HtmlParser_livedoor(){
			try{
			ArrayList<String> URL_Forder = new ArrayList<String>();

			String filePath = "/data/test1.txt";
			File readfile = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String readText = null;
			while ( (readText = br.readLine()) != null ){
				if(readText.startsWith("URL")){
					readText = readText.replace("URL: ","");
		        	URL_Forder.add(readText);
		        }
			}
			br.close();
			readfile.delete();

			File file = new File("/data/article1.txt");
			FileWriter pw = new FileWriter(file,true);

			ArrayList<String> temp = new ArrayList<String>();
			for(int i=0;i<URL_Forder.size();i++){
		        String url = URL_Forder.get(i);
		        Document document = Jsoup.connect(url).timeout(0).get();
		        pw.write("URL:" + url);
				Element content = document.select("article").first();
				if(content!=null){
					String title = content.select("h1").first().text();
					String time = content.select("time").first().text();
					Element article = content.select("[class=articleBody]").first();
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
					temp = kuromoji.kuromoji(text);
					pw.write("tag:");
					for(int j=0;j<temp.size();j++)
						pw.write(temp.get(j));
					pw.write("---------------------------\n" + "\r\n\n\r");
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

	public static void HtmlParser_yn(){
		try{
		ArrayList<String> URL_Forder = new ArrayList<String>();

		String filePath = "/data/test2.txt";
		File readfile = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String readText = null;
		while ( (readText = br.readLine()) != null ){
			if(readText.startsWith("URL")){
				readText = readText.replace("URL: ","");
	        	URL_Forder.add(readText);
	        }
		}
		br.close();
		readfile.delete();

		File file = new File("/data/article2.txt");
		FileWriter pw = new FileWriter(file,true);

		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0;i<URL_Forder.size();i++){
	        String url = URL_Forder.get(i);
	        Document document = Jsoup.connect(url).timeout(0).get();
			pw.write("URL:" + url +"\r\n");
			Element content = document.select("[class=article]").first();
			if(content!=null){
				String title = content.select("h1").text();
				String time = content.select("[class=ynLastEditDate yjSt]").text();
				String article = content.select("[class=ynDetailText]").text().replace("。", "。\r\n");
				pw.write("title:" + title + "\r\n");
				pw.write("time:" + time + "\r\n");
				pw.write("article:" + article + "\r\n");
				temp = kuromoji.kuromoji(article);
				pw.write("tag:");
				for(int j=0;j<temp.size();j++)
					pw.write(temp.get(j));
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
}