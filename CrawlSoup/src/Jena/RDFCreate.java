package Jena;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import kuromoji.tfidf;

public class RDFCreate {
	public static void RDFCreater(ArrayList<String> title,ArrayList<String> time,ArrayList<String> article) throws IOException{
		int j = 0;
		ArrayList<HashMap<String, Double>> temp = new ArrayList<HashMap<String, Double>>(tfidf.tf_idf(article));

		final String NEWS_NS = "http://localhost:8080/resource/";
		final String SCHEMA_NS = "http://schema.org/";
		final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
		final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		Model model = ModelFactory.createDefaultModel();

		model.setNsPrefix("news", NEWS_NS);
		model.setNsPrefix("schema", SCHEMA_NS);
		model.setNsPrefix("rdf", RDF_NS);
		model.setNsPrefix("rdfs", RDFS_NS);
		Resource SCHEMA_newsArticleClass = model.createResource(SCHEMA_NS + "NewsArticle");

		for(int i = 0; i < title.size(); i++){
			Resource news = model.createResource(NEWS_NS + i);
			model.add(news, RDF.type, SCHEMA_newsArticleClass);

			Property dataline = model.createProperty(SCHEMA_NS + "dataline");
			model.add(news, dataline, time.get(i));

			Property headline = model.createProperty(SCHEMA_NS + "headline");
			model.add(news, headline, title.get(i));

			Property articleBody = model.createProperty(SCHEMA_NS + "articleBody");
			model.add(news, articleBody, article.get(i));

			List<Entry<String, Double>> entries = new ArrayList<Entry<String, Double>>(temp.get(i).entrySet());
			//Comparator で Map.Entry の値を比較
			Collections.sort(entries, new Comparator<Entry<String, Double>>() {
			    //比較関数
			    @Override
			    public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
			        return o2.getValue().compareTo(o1.getValue());    //降順
			    }
			});
			for(Entry<String,Double> Tag_Forder : entries){
				if(j == 10)
					break;
				Property keywords = model.createProperty(SCHEMA_NS + "keyword" + j);
				model.add(news, keywords, Tag_Forder.getKey() + ":" + Tag_Forder.getValue() + "");
				j++;
			}
			j = 0;
			System.out.println(i);
		}

		FileOutputStream out = new FileOutputStream("/data/RDF/news.rdf");
		RDFWriter writer = model.getWriter("RDF/XML");

		writer.write(model, out, "RDF/XML");
		model.write(out);
	}
}
