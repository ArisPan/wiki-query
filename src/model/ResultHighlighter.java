	package model;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;

public class ResultHighlighter {

	private Highlighter highlighter;
	
	public ResultHighlighter(Query query) {
		
		/*
		 * Processes terms found in the original text by applying
		 * HTML &lt;B&gt;&lt;/B&gt; tag 
		 * to highlight terms in search results pages.
		 */
		Formatter formatter = new SimpleHTMLFormatter();
		
		/*
		 * Scores text fragments by the number of unique query terms found.
		 * Attempts to score only those terms that participated 
		 * in generating the 'hit' on the document.
		 */
		QueryScorer scorer = new QueryScorer(query);
		
		/*
		 * This is Lucene's original Highlighter.
		 * Marks up highlighted terms found in the best sections of text.
		 */
		highlighter = new Highlighter(formatter, scorer);
		
		// Breaks text into multiple same-size (size 20 in our case) fragments. 
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 20);
		
		// Set fragmenter to highlighter.
		highlighter.setTextFragmenter(fragmenter);
	}
	
	public String[] getHighlightedText(Document document, String field)
										throws IOException, InvalidTokenOffsetsException {
		// Create token stream.
		TokenStream stream = TokenSources.getTokenStream(field, null, document.get(field),
																	new StandardAnalyzer(),
																	highlighter.getMaxDocCharsToAnalyze() - 1);
		
		String[] highlightedFragments = highlighter.getBestFragments(stream, document.get(field), 20);

		return highlightedFragments;
	}
}
