package model;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;

public class TermFactory {
	
	public static ArrayList<Term> createTerms(String field, String inputText) throws IOException {
		
		ArrayList<Term> terms = new ArrayList<Term>();
				
		/*
		 * Enumerate the sequence of tokens from query text.
		 * 
		 * For the workflow of TokenStream API check:
		 * https://lucene.apache.org/core/8_5_1/core/org/apache/lucene/analysis/TokenStream.html
		 */

		@SuppressWarnings("resource")
		Analyzer analyzer = new StandardAnalyzer();
		
		// Returns a TokenStream suitable for given field, tokenizing the contents of inputText.
		TokenStream tokenStream = analyzer.tokenStream(field, inputText);
	    
		tokenStream.reset();
	    
		while (tokenStream.incrementToken()) {
	      CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
	      terms.add(new Term(field, charTermAttribute.toString()));
	    
		}
	    tokenStream.end();
	    tokenStream.close();
		
	    return terms;
	}
}
