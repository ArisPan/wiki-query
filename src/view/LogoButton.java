package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class LogoButton extends Button {
	
	/*
	 * Logo ratio:		height = 0.2 * width
	 * Button ratio:	height = 0.4 * width
	 * So	button.width == logo.width
	 * and	button.height == logo.height * 2
	 * 
	 * Since constructor's parameters address button size,
	 * we calculate logo's dimensions based on them.
	 */
	
	public LogoButton(int width, int height) {

		int logoWidth = width - 10;
		int logoHeight = (int) (logoWidth * 0.2);
		
		Logo logo = new Logo(logoWidth, logoHeight);
		ImageIcon logoIcon = logo.getLogoIcon();
		
		this.button = new JButton();
		button.setIcon(logoIcon);

		button.setPreferredSize(new Dimension(width, height));
		button.setBorderPainted(false);
		
		button.setBackground(new Color(240, 240, 240));
		button.setForeground(new Color(240, 240, 240));
		button.setBorder(new LineBorder(new Color(240, 240, 240)));
	}
	
	public JButton getButton() {
		return this.button;
	}
}
