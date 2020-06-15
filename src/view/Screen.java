package view;

import javax.swing.JPanel;

public abstract class Screen {

	JPanel screenPanel;
	JPanel container;
	
	public abstract void setContainer(JPanel container);

	public abstract JPanel getScreen();
}
