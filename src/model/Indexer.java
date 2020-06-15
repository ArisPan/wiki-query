package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	// Used to create the index and interact with it.
	private IndexWriter writer;
	
	/*
	 * @param	indexDirectoryPath	The absolute path of the directory in which the Index will be stored.
	 */
	public Indexer(String indexDirectoryPath) throws IOException {
		
		// Use that directory to store everything that is necessary for the index.
		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		
		// An Analyzer builds TokenStreams, which analyze text.
		// It thus represents a policy for extracting index terms from text.
		Analyzer analyzer = new StandardAnalyzer();

		// Holds all the configuration that is used to create an IndexWriter.
		IndexWriterConfig writerConfiguration = new IndexWriterConfig(analyzer);
		
		// IndexWriter will create a new index if one does not already exist
		// at the provided path, otherwise open the existing index.
		this.writer = new IndexWriter(indexDirectory, writerConfiguration);
	}
	
	public void closeWriter() throws CorruptIndexException, IOException {
		
		this.writer.close();
	}
	
	/*
	 * @param	corpusPath	The absolute path of the directory holding our wiki articles.
	 * 						In other words, the "corpus".
	 * @param	filter		A FileFilter object used to check the validity of the files
	 * 						in the corpus. In this case we check for ".txt" suffix.
	 * @return				The number of indexed files.
	 */
	public int createIndex(String corpusPath, FileFilter filter) throws IOException {

		File[] corpus = new File(corpusPath).listFiles();
		
		for (File article : corpus) {
			
			if (article.exists() && article.canRead() && filter.accept(article))
				indexArticle(article);
		}
		
		IndexWriter.DocStats stats = this.writer.getDocStats();
		return stats.numDocs;
	}
	
	private void indexArticle(File article) throws IOException {
		
		Document document = getDocument(article);
		
		this.writer.addDocument(document);
	}

	/*
	 * Each Document object represents an individual physical text source.
	 * Documents are the building units of the Index and
	 * the Index is made up of different Document objects.
	 * Each Document object is populated with a collection of (Key, Value) pairs,
	 * the Fields.
	 */
	private Document getDocument(File article) throws IOException {
		
		Document document = new Document();
		String articleName = getTitle(article);
		String articleBody = getBody(article);
		
		if (articleBody == null) {
			System.out.println("Can't read file " + article.getName());
			throw new IOException();
		}
		
		// Article's title will be stored, indexed and tokenized.
		Field title = new TextField("Title", articleName, Field.Store.YES);
		
		// Article's body will be stored, indexed and tokenized.
		// Storing the body is expensive in disk memory but mandatory
		// in order to return it with the search results.
		Field body = new TextField("Body", articleBody, Field.Store.YES);
		
		// Article's path on hard disk will be stored but not index or tokenized.
		// We want the path returned with the results of a search but we won’t
		// actually be searching on this data.
		Field path = new StoredField("Path", article.getAbsolutePath());
		
		document.add(title);
		document.add(body);
		document.add(path);

		return document;
	}
	
	// Strips '.txt' from the end of article's name. 
	private String getTitle(File article) {
		
		String articleName = article.getName();
		articleName = articleName.substring(0, articleName.length() - 4);
		
		return articleName;
	}
	
	private String getBody(File article) throws IOException {
		
		try {
			// Wiki articles are notorious for their UTF8 characters.
			BufferedReader input = new BufferedReader (
					new InputStreamReader (
						new FileInputStream(article), "UTF8"));
			StringBuilder lines = new StringBuilder();
			String line;
			
			while ((line = input.readLine()) != null) {
				lines.append(line);
			}
			
			input.close();
			return lines.toString();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
