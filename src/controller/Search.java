package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import model.Searcher;
import view.MainWindow;
import view.ResultsScreen;
import view.Screen;

public class Search implements ActionListener {

	private String query;
	private String field;
	
	private static final String indexDirectoryPath = System.getProperty("user.dir") + File.separator + "Index";
	
	public void update(String field, String query) {
		this.field = field;
		this.query = query;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {

		try {			
			Searcher searcher = new Searcher(indexDirectoryPath);

			ArrayList<Document> documents = searcher.search(this.field, this.query);
			
			MainWindowController mwc = MainWindowController.getInstance();
			MainWindow mainWindow = mwc.getMainWindow();
			
			Screen resultsScreen = mainWindow.getScreen("Results");
			
			((ResultsScreen) resultsScreen).createResults(documents, this.query);
			
			printStats(documents);
			
			searcher.closeReader();
			
		} catch (IOException | ParseException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	private void printResults(ArrayList<Document> documents) {
		
		System.out.println("Found " + documents.size() + " match(es).");
		
		for (Document document : documents) {
			System.out.println("Title: " + document.get("Title"));
			System.out.println("Highlight: " + document.get("Highlight"));
			System.out.println("URL: " + document.get("Path"));
			System.out.println();
		}
	}
	
	private void printStats(ArrayList<Document> documents) {
		
		System.out.println("Query \"" + query + "\" matches " + documents.size() + " document(s) in field \"" + field + "\".");
	}
}
