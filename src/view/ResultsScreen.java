package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.lucene.document.Document;

import controller.CommandFactory;
import controller.Search;

public class ResultsScreen extends Screen {
	
	private GroupLayout layout;
	private JPanel topPanel;
	
	public ResultsScreen() {
		
		this.topPanel = new JPanel();
		this.layout = new GroupLayout(topPanel);
		this.topPanel.setLayout(layout);

		this.screenPanel = new JPanel();
		this.screenPanel.setLayout(new BoxLayout(this.screenPanel, BoxLayout.PAGE_AXIS));

		initialize();
	}
	
	private void initialize() {
		
		createTopPanel();
		
		screenPanel.add(topPanel);
		
		screenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	/*
	 * topPanel is comprised of a home button (referred to as logoButton),
	 * a search bar and it's radio buttons plus a search button.
	 * It has a GroupLayout that is set with createTopPanelLayout().
	 * The topPanel must remain intact through changes in results.
	 */
	private void createTopPanel() {
		
		JRadioButton title = new JRadioButton("Title");
		JRadioButton body = new JRadioButton("Body");
		JRadioButton all = new JRadioButton("All");
		
		setRadioButton(title);
		setRadioButton(body);
		setRadioButton(all);
		
		title.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(title);
		group.add(body);
		group.add(all);
		
		Button logoButton = new LogoButton(140, 56);
		JButton logoJButton = logoButton.getButton();
		logoJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				CardLayout layout = (CardLayout) container.getLayout();
				layout.previous(container);
				
			}
		});
		
		SearchBar searchBar = new SearchBar(40);
		searchBar.setSize(40, 30);
		JTextField searchBarTextField = searchBar.getSearchBar();
		
		Button searchButton = new SearchButton(140, 56);
		JButton searchJButton = searchButton.getButton();
		searchJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (searchBar.getQuery().equals(""))
					return;

				ActionListener search = CommandFactory.createCommand("Search");

				((Search) search).update(group.getSelection().getActionCommand(), searchBar.getQuery());
				search.actionPerformed(e);
			}
		});

		createTopPanelLayout(logoJButton, searchBarTextField, searchJButton, title, body, all);
		
		topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	// Sets the decorations for the RadioButtons.
	private void setRadioButton(JRadioButton radioButton) {
		
		radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
		radioButton.setPreferredSize(new Dimension(100, 40));
		
		radioButton.setActionCommand(radioButton.getText());
	}
	
	// Sets the GroupLayout for topPanel.
	private void createTopPanelLayout(JComponent... component) {
		
		// For the workflow of GroupLayout, check:
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(component[0])
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(component[1], GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[3]))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[4]))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[5]))
						)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(component[2]))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(component[0])
						.addComponent(component[1], GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(component[2]))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    		.addGroup(layout.createSequentialGroup()
			    				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    						.addComponent(component[3])
			    						.addComponent(component[4])
			    						.addComponent(component[5]))
			    		)
			    )
		);
	}
	
	/*
	 * createResults is called upon search action in controller.Search
	 * Creates the Result panels for display based on search results.
	 */
	public void createResults(ArrayList<Document> documents, String query) {
		
		// Clear the screen of previously displayed results.
		removePreviousResults();

		// If no results are found, create a panel with the appropriate message.
		if (documents.size() == 0) {
			
			Result result = new Result("", "Your search - " + query + " - did not match any documents.");
			JPanel resultPanel = result.getResult();
			
			resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			screenPanel.add(Box.createRigidArea(new Dimension(10, 20)));
			
			screenPanel.add(resultPanel);
		}
		else {
			
			displayResults(documents, 0);
		}
	}

	// Removes previously displayed result panels.
	private void removePreviousResults() {

		// screenPanel contains topPanel and RigidArea at positions 0 & 1.
		// We want them to stay intact.
		if (this.screenPanel.getComponentCount() > 2) {
			
			int componentCount = this.screenPanel.getComponentCount();
			
			// Starting from the end due to component array rearranging after
			// removing elements.
			for (int i = componentCount - 1; i > 1; i--) {
				this.screenPanel.remove(this.screenPanel.getComponent(i));
			}
		}
		
		screenPanel.revalidate();
		screenPanel.repaint();
	}
	
	private void displayResults(ArrayList<Document> documents, int currentPage) {
		
		int documentsSize = documents.size(); // Calculate once, it's cheaper.
		
		// For each page display the page number and the number of matched documents.
		JLabel resultsDisplayInfo = new JLabel("Page " + (currentPage + 1) + " of " + documentsSize + " results.");
		resultsDisplayInfo.setFont(new Font("Arial", Font.PLAIN, 18));
		
		screenPanel.add(Box.createRigidArea(new Dimension(10, 20)));
		screenPanel.add(resultsDisplayInfo);
		
		for (int i = 0; i < 10; i++) {
			
			if (currentPage != 0) {
				
				if (documentsSize - (currentPage * 10) < 10)	// Last page.
					if (i == documentsSize % 10)				// Next of last document.
						break;
			}
			else {
				if (documentsSize < 10)				// We only have a single results page.
					if (i == documentsSize % 10)	// Next of last document.
						break;
			}

			JPanel resultPanel = createResultPanel(documents, currentPage, i);
			
			screenPanel.add(Box.createRigidArea(new Dimension(10, 20)));
			screenPanel.add(resultPanel);
		}
		
		if (documentsSize > 10) {
			
			JPanel buttonPane = createButtonPane(documents, currentPage);
			
			screenPanel.add(Box.createRigidArea(new Dimension(10, 20)));
			screenPanel.add(buttonPane);
		}

		screenPanel.revalidate();
		screenPanel.repaint();
	}
	
	/*
	 * Create a new resultPanel displaying matched document's title,
	 * highlighted text matching the query and an "open" button.
	 */
	private JPanel createResultPanel(ArrayList<Document> documents, int currentPage, int index) {
		
		Result result = new Result(documents.get((currentPage * 10) + index).get("Title"),
									documents.get((currentPage * 10) + index).get("Highlight"));
		JPanel resultPanel = result.getResult();
		
		JButton open = createOpenButton(documents.get((currentPage * 10) + index));
		
		open.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		resultPanel.add(open);
		resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return resultPanel;
	}
	
	// Creates the "open" button for the result panel.
	// Upon action, it creates a new JFrame displaying the corresponding article.
	private JButton createOpenButton(Document document) {
		
		// TODO Button "open" should be aligned to the right.
		JButton open = new JButton("Open");
		
		open.setFont(new Font("Arial", Font.PLAIN, 16));
		open.setBorderPainted(false);
		open.setBackground(new Color(240, 240, 240));
		open.setForeground(new Color(246, 102, 15));
		
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame newFrame = createOpenArticleFrame(document);
				
				newFrame.setVisible(true);
			}
		});
		
		return open;
	}
	
	// Creates a new JFrame displaying the contents of the article.
	// (new JFrame results in a new window, app runs independently)
	// Made up of a scrollable JTextArea.
	private JFrame createOpenArticleFrame(Document document) {
		
		JFrame newFrame = new JFrame(document.get("Title"));
		newFrame.setSize(1060, 760);
		newFrame.setLocationRelativeTo(null);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String articleBody = null;
		
		try {	articleBody = getBody(new File(document.get("Path")));	}
		catch (IOException e1) {	e1.getMessage();	}
		
		JTextArea body = new JTextArea(articleBody);
		body.setFont(new Font("Arial", Font.PLAIN, 16));
		body.setLineWrap(true);
		body.setWrapStyleWord(true);
		body.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(body);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		newFrame.add(scrollPane);
		
		return newFrame;
	}
	
	// Get a somewhat formated version of resulting document.
	// createResultFrame() uses it to open the document in question.
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
				lines.append(System.lineSeparator());
			}
			
			input.close();
			return lines.toString();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// Creates the <<Previous 1 2 3 Next>> button pane
	// residing in a JPanel with a BoxLayout.
	// Each of the above mentioned buttons has its own method implementing it.
	// This button pane will be created if the resulting documents are more than 10
	// (therefore we need a second page).
	private JPanel createButtonPane(ArrayList<Document> documents, int currentPage) {
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		
		JButton previous = createPreviousButton(documents, currentPage);
		
		buttonPane.add(previous);
		buttonPane.add(Box.createRigidArea(new Dimension(0, 0)));

		// For example, if 11 documents match the query,
		// we will need 2 pages for displaying them, 11 / 10 (=1) + 1 (=2).
		int numberOfPages = documents.size() / 10 + 1;
		
		// For every page, create the corresponding "number button".
		for (int i = 0; i < numberOfPages; i++) {
			
			JButton number = createNumberButton(documents, currentPage, i);

			buttonPane.add(number);
			buttonPane.add(Box.createRigidArea(new Dimension(0, 0)));
		}
		
		JButton next = createNextButton(documents, currentPage);
	
		buttonPane.add(next);
		buttonPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return buttonPane;
	}
	
	// Creates the "Previous" button.
	// Upon clicking it calls displayResults for the previous page.
	private JButton createPreviousButton(ArrayList<Document> documents, int currentPage) {
		
		JButton previous = new JButton("Previous");
		
		previous.setFont(new Font("Arial", Font.PLAIN, 16));
		previous.setBorderPainted(false);
		previous.setBackground(new Color(240, 240, 240));
		previous.setForeground(new Color(123, 64, 174));
		
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentPage == 0)	// First page.
					return;
				
				removePreviousResults();
				displayResults(documents, currentPage - 1);
			}
		});
		
		return previous;
	}
	
	// Creates a "Number" button. Parameter "currentPage"
	private JButton createNumberButton(ArrayList<Document> documents, int currentPage, int index) {
		
		JButton number = new JButton("" + (index + 1));
		
		number.setFont(new Font("Arial", Font.PLAIN, 16));
		number.setBorderPainted(false);
		number.setBackground(new Color(240, 240, 240));
		number.setForeground(new Color(100, 146, 107));
		
		// We want to use the local variable index inside the inner class ActionListener.
		// To do this, it has to be final.
		final int assignedPage = index;
		
		number.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentPage == assignedPage)
					return;
				
				removePreviousResults();
				displayResults(documents, assignedPage);
			}
		});
		
		return number;
	}
	
	// Creates the "Next" button.
	// Upon clicking it calls displayResults for the next page.
	private JButton createNextButton(ArrayList<Document> documents, int currentPage) {
		
		JButton next = new JButton("Next");
		
		next.setFont(new Font("Arial", Font.PLAIN, 16));
		next.setBorderPainted(false);
		next.setBackground(new Color(240, 240, 240));
		next.setForeground(new Color(123, 64, 174));
		
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentPage != 0)
					if (documents.size() - (currentPage * 10) < 10)	// Last page.
						return;

				removePreviousResults();
				displayResults(documents, currentPage + 1);
			}
		});
		
		return next;
	}
	
	public void setContainer(JPanel container) {
		this.container = container;
	}
	
	public JPanel getScreen() {
		return this.screenPanel;
	}
}
