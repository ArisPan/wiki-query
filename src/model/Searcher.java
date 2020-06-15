package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

	private IndexReader indexReader;
	private IndexSearcher indexSearcher;
	
	private static final int MAX_HITS = 100;
	
	public Searcher(String indexDirectoryPath) throws IOException {

		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		
		this.indexReader = DirectoryReader.open(indexDirectory);
		this.indexSearcher = new IndexSearcher(indexReader);
	}
	
	public void closeReader() throws CorruptIndexException, IOException {
		this.indexReader.close();
	}
	
	public ArrayList<Document> search(String field, String inputText) throws IOException, ParseException {
		
		Query query = QueryFactory.createQuery(field, inputText);
		
		TopDocs topDocs = this.indexSearcher.search(query, MAX_HITS);
		
		ScoreDoc[] hits = topDocs.scoreDocs;
		
		ArrayList<Document> documents = new ArrayList<Document>();
		
		if (field.contentEquals("All")) {
			
			for (ScoreDoc hit : hits) {

				Document document = highlight(indexSearcher.doc(hit.doc), query, "Body");

				documents.add(document);
			}
		}
		else {
			
			for (ScoreDoc hit : hits) {

				Document document = highlight(indexSearcher.doc(hit.doc), query, field);
				
				documents.add(document);
			}
		}
		
		return documents;
	}
	
	// Updates each document in documents by adding an extra StoredField
	// containing the highlighted text results.
	// We will be using this field to access highlighted text in view.ResultsScreen	
	private Document highlight(Document document, Query query, String field) {
		
		ResultHighlighter highlighter = new ResultHighlighter(query);

		String[] highlightedText = null;
		
		try {
			highlightedText = highlighter.getHighlightedText(document, field);

		} catch (IOException | InvalidTokenOffsetsException e) {
			e.getMessage();
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (String word : highlightedText) {
			
			sb.append(word);
		}
		
		document.add(new StoredField("Highlight", sb.toString()));

		return document;
	}
	
	public void printIndex(String indexDirectoryPath) throws IOException {
		
		Directory newIndexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		IndexReader reader = DirectoryReader.open(newIndexDirectory);
		
		for (int i = 0; i < reader.numDocs(); i++) {
			
			Document doc = reader.document(i);
			System.out.println("Doc title: " + doc.get("Title"));
		}
	}
}
