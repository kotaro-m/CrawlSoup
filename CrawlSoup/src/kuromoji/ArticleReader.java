package kuromoji;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ArticleReader {
	public static ArrayList<String> URL_Forder(String filePath) throws IOException{
		String readText = null;
		String url = null;
		ArrayList<String> Return = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while ((readText = br.readLine()) != null){
			if(readText.startsWith("URL:")){
				url = readText.replace("URL: ", "");
				Return.add(url);
			}
		}
		br.close();
		return Return;
	}

	public static ArrayList<String> URL_Forder() throws IOException{
		String readText = null;
		String url = null;
		ArrayList<String> Return = new ArrayList<String>();
		String filePath = "/data/article.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		while ((readText = br.readLine()) != null){
			if(readText.startsWith("URL:")){
				url = readText.replace("URL:", "");
				Return.add(url);
			}
		}
		br.close();

		return Return;
	}

	public static ArrayList<String> Title_Forder() throws IOException{
		String readText = null;
		String title = null;
		ArrayList<String> Return = new ArrayList<String>();
		String filePath = "/data/article.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		while ((readText = br.readLine()) != null){
			if(readText.startsWith("title:")){
				title = readText.replace("title:", "");
				Return.add(title);
			}
		}
		br.close();

		return Return;
	}

	public static ArrayList<String> Time_Forder() throws IOException{
		String readText = null;
		String time = null;
		ArrayList<String> Return = new ArrayList<String>();
		String filePath = "/data/article.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		while ((readText = br.readLine()) != null){
			if(readText.startsWith("time:")){
				time = readText.replace("time:", "");
				Return.add(time);
			}
		}
		br.close();

		return Return;
	}

	public static ArrayList<String> Article_Forder() throws IOException{
		String readText = null;
		String article = null;
		int Frag = 0;

		ArrayList<String> Return = new ArrayList<String>();
		String filePath = "/data/article.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();

		while ((readText = br.readLine()) != null){
			if(readText.startsWith("article:")){
				readText = readText.replace("article:", "");
				Frag++;
			}
			if(readText.startsWith("--")){
				Frag=0;
				article = new String(sb);
				Return.add(article);
				sb = new StringBuilder();
			}
			if(Frag==1)
				sb.append(readText);

		}
		br.close();

		return Return;
	}

	public static ArrayList<String> Publisher_Forder() throws IOException{
		String readText = null;
		String publisher = null;
		ArrayList<String> Return = new ArrayList<String>();
		String filePath = "/data/article.txt";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while ((readText = br.readLine()) != null){
			if(readText.startsWith("publisher:")){
				publisher = readText.replace("publisher:", "");
				Return.add(publisher);
			}
		}
		br.close();
		return Return;
	}
}