package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.CommandFactory;
import controller.Search;

public class HomeScreen extends Screen {
	
	public HomeScreen() {
		this.screenPanel = new JPanel(new GridBagLayout());
		initialize();
	}
	
	private void initialize() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		Logo logo = new Logo(350, 70);
		JLabel logoLabel = logo.getLogoLabel();
		
		c.gridx = 0;
		c.gridy = 0;
		screenPanel.add(logoLabel, c);
		
		SearchBar searchBar = new SearchBar(40);
		searchBar.setSize(40, 40);
		
		ButtonGroup group = new ButtonGroup();
		
		JPanel searchBarPanel = createSearchBarPanel(searchBar, group);
		
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(15, 0, 0, 0);
		screenPanel.add(searchBarPanel, c);
		
		SearchButton searchButton = new SearchButton(100, 40);
		JButton searchJButton = searchButton.getButton();
		searchJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (searchBar.getQuery().equals(""))
					return;
				
				CardLayout layout = (CardLayout) container.getLayout();
				layout.next(container);
				
				ActionListener search = CommandFactory.createCommand("Search");

				((Search) search).update(group.getSelection().getActionCommand(), searchBar.getQuery());
				search.actionPerformed(e);
			}
		});
		
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 2;
		screenPanel.add(searchJButton, c);
	}
	
	private JPanel createSearchBarPanel(SearchBar searchBar, ButtonGroup group) {
		
		JRadioButton title = new JRadioButton("Title");
		JRadioButton body = new JRadioButton("Body");
		JRadioButton all = new JRadioButton("All");
		
		setRadioButton(title);
		setRadioButton(body);
		setRadioButton(all);
		
		title.setSelected(true);

		group.add(title);
		group.add(body);
		group.add(all);
		
		JTextField searchBarTextField = searchBar.getSearchBar();
		
		JPanel searchBarPanel = new JPanel();
		GroupLayout layout = new GroupLayout(searchBarPanel);
		searchBarPanel.setLayout(layout);
		
		createSearchBarPanelLayout(layout, searchBarTextField, title, body, all);
		
		searchBarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		return searchBarPanel;
	}
	
	// Sets the decorations for the RadioButtons.
	private void setRadioButton(JRadioButton radioButton) {
		
		radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
		radioButton.setPreferredSize(new Dimension(100, 40));
		
		radioButton.setActionCommand(radioButton.getText());
	}
	
	// Sets the GroupLayout for topPanel.
	private void createSearchBarPanelLayout(GroupLayout layout, JComponent... component) {
		
		// For the workflow of GroupLayout, check:
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/groupExample.html
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(component[0], GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[1]))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[2]))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(component[3]))
						)
				)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(component[0], GroupLayout.DEFAULT_SIZE,
		                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    		.addGroup(layout.createSequentialGroup()
			    				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    						.addComponent(component[1])
			    						.addComponent(component[2])
			    						.addComponent(component[3]))
			    		)
			    )
		);
	}

	public void setContainer(JPanel container) {
		this.container = container;
	}
	
	public JPanel getScreen() {
		return this.screenPanel;
	}
}
