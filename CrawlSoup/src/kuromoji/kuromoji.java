package kuromoji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class kuromoji {
	public static ArrayList<String> kuromoji(String article) throws IOException{
		HashMap<String,Integer> count_item = new HashMap<String,Integer>();
		ArrayList<String> noun_count = new ArrayList<String>();
		ArrayList<String> Return = new ArrayList<String>();
 		int count = 0;

		Tokenizer tokenizer = Tokenizer.builder().build();
		List<Token> tokens =tokenizer.tokenize(article);
		int Frag = 0;
		String str= null;
		for (Token token : tokens) {


			if(token.getPartOfSpeech().startsWith("名詞")){
				if(Frag==0)
					str=token.getSurfaceForm();
				else
					str+=token.getSurfaceForm();
				Frag++;
			}
			if(!(token.getPartOfSpeech().startsWith("名詞"))){
				if(Frag>0){
					noun_count.add(str);
					Frag=0;
					str=null;
				}
			}
		}
		for(int i=0;i<noun_count.size();i++){

	        String temp = noun_count.get(i);
	        if(count_item.containsKey(temp)){
	        	count +=count_item.get(temp);
	        }
	        else{
	        	count =1;
	        }
	        count_item.put(temp,count);
		}

		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(count_item.entrySet());

		//Comparator で Map.Entry の値を比較
		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
		    //比較関数
		    @Override
		    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		        return o2.getValue().compareTo(o1.getValue());    //降順
		    }
		});

		int i=0;
		for (Entry<String, Integer> entry : entries) {
			if(i==10)
				break;
			double num=(float)entry.getValue()/noun_count.size();
		    String tf = entry.getKey() + ":" + num + "\r\n";
		    Return.add(tf);
		    i++;
		}
		return Return;
	}
}
