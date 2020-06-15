package model;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class QueryFactory {

	public static Query createQuery(String field, String inputText) throws IOException, ParseException {
		
		if (field.contentEquals("All")) {
			
			Query query1 = new TermQuery(new Term("Title", inputText));
			Query query2 = new TermQuery(new Term("Body", inputText));
			
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			builder.add(query1, Occur.SHOULD);
			builder.add(query2, Occur.SHOULD);
			
			BooleanQuery booleanQuery = builder.build();
			return booleanQuery;			
		}
		
		QueryParser qp = new QueryParser(field, new StandardAnalyzer());
		
		Query query = qp.parse(inputText);
		
		return query;
	}
}
