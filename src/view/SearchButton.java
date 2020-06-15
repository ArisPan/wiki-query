package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class SearchButton extends Button {
	
	public SearchButton(int width, int height) {

		this.button = new JButton("Search");
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.setPreferredSize(new Dimension(width, height));
	}
	
	public JButton getButton() {
		return this.button;
	}
}
