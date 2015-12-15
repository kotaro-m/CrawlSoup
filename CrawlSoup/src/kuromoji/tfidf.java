package kuromoji;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class tfidf {
	//TF-IDFの計算
	public static ArrayList<HashMap<String,Double>> tf_idf(ArrayList<String> article) throws IOException{
		ArrayList<HashMap<String,Double>> Return = new ArrayList<HashMap<String,Double>>();
		ArrayList<ArrayList<String>> Noun_Overlap = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> Noun_Only = new ArrayList<ArrayList<String>>();
		HashMap<String,Double> idf = new HashMap<String,Double>();
		HashMap<String,Double> Noun_tfidf = new HashMap<String,Double>();
		ArrayList<Map<String,Double>> tf = new ArrayList<Map<String,Double>>();

		for(int i = 0; i < article.size();i++){
			Noun_Overlap.add(Tokenize(article.get(i)));
			Noun_Only.add(distinct(Tokenize(article.get(i))));
		}

		tf = TF(Noun_Overlap);
		idf = IDF(Noun_Only);

		for(int i = 0; i < tf.size(); i++){
			for(Entry<String,Double> entry : tf.get(i).entrySet()){
				String Target =entry.getKey();
				double tfidf = tf.get(i).get(Target)*idf.get(Target);
				Noun_tfidf.put(Target,tfidf);
			}
			Return.add(Noun_tfidf);
			Noun_tfidf = new HashMap<String,Double>();
		}
		return Return;
	}

	//List内の名詞の重複削除
	public static ArrayList<String> distinct(ArrayList<String> slist){
		return new ArrayList<String>(new LinkedHashSet<String>(slist));
	}

	//名詞抽出
	public static ArrayList<String> Tokenize(String article){
		ArrayList<String> Return = new ArrayList<String>();
		Tokenizer tokenizer = Tokenizer.builder().build();
		List<Token> tokens =tokenizer.tokenize(article);
		int Frag = 0;
		String str = null;
		for(Token token : tokens){
			if(token.getPartOfSpeech().startsWith("名詞")){
				if(Frag==0)
					str=token.getSurfaceForm();
				else
					str += token.getSurfaceForm();
				Frag++;
			}
			if(!(token.getPartOfSpeech().startsWith("名詞"))){
				if(Frag>0){
					Return.add(str);
					Frag = 0;
					str=null;
				}
			}
		}
		return Return;
	}

	//IDFの計算
	public static HashMap<String,Double> IDF(ArrayList<ArrayList<String>> Noun_Only){
		HashMap<String,Double>Return = new HashMap<String,Double>();
		String temp = null;
		double count = 0;

		for(int i = 0; i < Noun_Only.size(); i++){
			for(int j = 0; j < Noun_Only.get(i).size(); j++){
				temp=Noun_Only.get(i).get(j);
				for(int k = 0; k < Noun_Only.size(); k++){
					List<String> document = new ArrayList<String>();
					document.addAll(Noun_Only.get(k));
					if(document.contains(temp))
						count++;
				}
				Return.put((String) Noun_Only.get(i).get(j), Math.log((double)Noun_Only.size()/count));
				count=0;
			}
		}
		return Return;
	}

	//TFの計算
	public static ArrayList<Map<String,Double>>TF(ArrayList<ArrayList<String>> Noun_Overlap){
		ArrayList<Map<String,Double>> Return = new ArrayList<Map<String,Double>>();
		HashMap<String,Double> Noun_tf = new HashMap<String,Double>();
		String temp = null;
		double count = 0;

		for(int i = 0; i< Noun_Overlap.size(); i++){
			for(int j = 0; j < Noun_Overlap.get(i).size(); j++){
				ArrayList<String> Target_Document = new ArrayList<String>(Noun_Overlap.get(i));
				temp = Target_Document.get(j);
				if(Noun_tf.containsKey(temp))
					count += (double)Noun_tf.get(temp)/(double)Noun_Overlap.size();
				else
					count = 1.0/(double)Noun_Overlap.size();
				Noun_tf.put(temp,count);
			}
			Return.add(Noun_tf);
			Noun_tf = new HashMap<String,Double>();
		}
		return Return;
	}
}