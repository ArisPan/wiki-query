package main;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.MainWindowController;
import model.Indexer;
import model.TextFileFilter;
import view.MainWindow;

public class Main {

	private static final String indexDirectoryPath = System.getProperty("user.dir") + File.separator + "Index";
	private static final String corpusPath = System.getProperty("user.dir") + File.separator + "WikiArticles";
	
	public static void main(String[] args) throws IOException {

		Main main = new Main();
		main.createIndex();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					MainWindowController mwc = MainWindowController.getInstance();
					MainWindow mainWindow = mwc.getMainWindow();

					mainWindow.initialize();

				}catch (ClassNotFoundException | InstantiationException | 
						IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
			}
		});
	}
	
	private void createIndex() throws IOException {
		
		deleteExistingIndex();

		Indexer indexer = new Indexer(indexDirectoryPath);
		
		TextFileFilter filter = new TextFileFilter();
		
		long startTime = System.currentTimeMillis();
		int numberOfIndexedArticles = indexer.createIndex(corpusPath, filter);
		long finishTime = System.currentTimeMillis();
		
		indexer.closeWriter();
		
		System.out.println("Indexed " + numberOfIndexedArticles + " articles in " + (finishTime - startTime) + "ms.");
	}
	
	private void deleteExistingIndex() {
		
		File indexDirectory = new File(indexDirectoryPath);
		File[] files = indexDirectory.listFiles();
		
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
		
		indexDirectory.delete();
		System.out.println("Deleted existing Index @ " + indexDirectoryPath);
	}
}
