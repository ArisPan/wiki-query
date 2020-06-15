package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SearchBar {

	private JTextField searchBar;
	
	public SearchBar(int columns) {
		
		this.searchBar = new JTextField(columns);
		this.searchBar.setFont(new Font("Arial", Font.PLAIN, 16));		
		this.searchBar.setBorder(new LineBorder(new Color(240, 240, 240)));
	}

	public void setSize(int width, int height) {
		this.searchBar.setPreferredSize(new Dimension(width, height));
	}
	
	public JTextField getSearchBar() {
		return this.searchBar;
	}
	
	public String getQuery() {
		return this.searchBar.getText();
	}
	
	public void clear() {
		this.searchBar.setText("");
	}
	
	@SuppressWarnings("unused")
	private void deprecatedInitialize() {
		
		JTextArea searchTextArea = new JTextArea(2, 40);
		searchTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JScrollPane scrollPane = new JScrollPane(searchTextArea, 
				JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		RoundBorder border = new RoundBorder(30);
		
		scrollPane.setBorder(border);
		
		JPanel searchBar = new JPanel();
		searchBar.add(scrollPane);
	}
}
