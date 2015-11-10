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
	public static ArrayList<String> distinct(ArrayList<String> slist){
		return new ArrayList<String>(new LinkedHashSet<String>(slist));
	}

	public static ArrayList<HashMap<String,Double>> tf_idf(ArrayList<String> article) throws IOException{
		ArrayList<HashMap<String,Double>> Return = new ArrayList<HashMap<String,Double>>();
		ArrayList<String> Noun = new ArrayList<String>();
		ArrayList<ArrayList<String>> Noun_Overlap = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> Noun_Only = new ArrayList<ArrayList<String>>();
		HashMap<String,Double> Noun_tf = new HashMap<String,Double>();
		HashMap<String,Double> Noun_idf = new HashMap<String,Double>();
		HashMap<String,Double> Noun_tfidf = new HashMap<String,Double>();
		ArrayList<Map<String,Double>> D_Noun_tf = new ArrayList<Map<String,Double>>();

		double count = 0;
		String temp;

		Tokenizer tokenizer = Tokenizer.builder().build();
		for(int i = 0; i < article.size();i++){
			List<Token> tokens =tokenizer.tokenize(article.get(i));
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
						Noun.add(str);
						Frag = 0;
						str=null;
					}
				}
			}
			Noun_Overlap.add(Noun);
			Noun_Only.add(distinct(Noun));
			Noun = new ArrayList<String>();
		}

		//idf
		for(int i = 0; i < Noun_Only.size(); i++){
			for(int j = 0; j < Noun_Only.get(i).size(); j++){
				temp=Noun_Only.get(i).get(j);
				for(int k = 0; k < Noun_Only.size(); k++){
					List<String> document = new ArrayList<String>();
					document.addAll(Noun_Only.get(k));
					if(document.contains(temp))
						count++;
				}
				Noun_idf.put((String) Noun_Only.get(i).get(j), Math.log((double)Noun_Only.size()/count));
				count=0;
			}
		}

		//tf
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
			D_Noun_tf.add(Noun_tf);
			Noun_tf = new HashMap<String,Double>();
		}

		for(int i = 0; i < D_Noun_tf.size(); i++){
			for(Entry<String,Double> entry : D_Noun_tf.get(i).entrySet()){
				String Target =entry.getKey();
				double tfidf = D_Noun_tf.get(i).get(Target)*Noun_idf.get(Target);
				Noun_tfidf.put(Target,tfidf);
			}
			Return.add(Noun_tfidf);
			Noun_tfidf = new HashMap<String,Double>();
		}
		return Return;
	}

}
