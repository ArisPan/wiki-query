package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Logo {

	private JLabel logoLabel;
	private ImageIcon logoIcon;
	
	/*
	 * In order to keep the proportions of this specific image intact,
	 * the ration between width and height must be 0.2.
	 * width = 0.2 * height.
	 */
	
	public Logo(int width, int height) {
		
		BufferedImage logo = getImage("/WikiQuery-Logo2.png");
		
		this.logoIcon = resizeImage(logo, width, height);
		this.logoLabel = new JLabel(resizeImage(logo, width, height));
	}
	
	public JLabel getLogoLabel() {
		return this.logoLabel;
	}
	
	public ImageIcon getLogoIcon() {
		return this.logoIcon;
	}
	
	private BufferedImage getImage(String path) {
		
		java.net.URL url = getClass().getResource(path);
		if (url != null)
			try {
				return (ImageIO.read(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	private ImageIcon resizeImage(BufferedImage initialImage, int width, int height) {
		
		if (initialImage == null)
			return null;
		
		Image newImage = initialImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		
		return new ImageIcon(newImage);
	}
}
