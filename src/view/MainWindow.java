package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frame;
	private JPanel container;
	private Screen homeScreen;
	private Screen resultsScreen;
	
	public void initialize() {
		
		this.container = new JPanel(new CardLayout());
		this.frame = initializeFrame();
		
		this.homeScreen = new HomeScreen();
		JPanel homeScreenPanel = homeScreen.getScreen();
		
		this.resultsScreen = new ResultsScreen();
		JPanel resultsScreenPanel = resultsScreen.getScreen();

		JScrollPane resultsPane = new JScrollPane(resultsScreenPanel);
		resultsPane.getVerticalScrollBar().setUnitIncrement(16);

		this.container.add(homeScreenPanel);
		this.container.add(resultsPane);
		
		homeScreen.setContainer(container);
		resultsScreen.setContainer(container);
		
		this.frame.add(this.container);
		
		this.frame.setVisible(true);
	}
	
	private JFrame initializeFrame() {
		
		JFrame frame = new JFrame("WikiQuery");
		frame.setSize(1060, 760);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return frame;
	}
	
	public Screen getScreen(String screen) {
		
		switch (screen) {
		case "Home":
			return this.homeScreen;
		case "Results":
			return this.resultsScreen;
		default:
			throw new IllegalArgumentException("Screen \"" + screen + "\" does not exist.");
		}
	}
}
