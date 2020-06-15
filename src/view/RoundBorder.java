package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.Border;

public class RoundBorder implements Border {

	private int radius;
	
	public RoundBorder(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(240, 240, 240));
		g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, getRadius(), getRadius()));
		g2d.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {

		int value = getRadius() / 2;
		return new Insets(value, value, value, value);
	}

	@Override
	public boolean isBorderOpaque() {

		return false;
	}

}
