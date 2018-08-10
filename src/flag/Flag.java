package flag;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.*;

public class Flag extends JPanel {
	public static final Color RED = new Color(178, 34, 52);
	public static final Color BLUE = new Color(60, 59, 110);
	public int width = 760;
	public int height = 400;
	private Graphics g;

	public Flag() {
		init();

	}

	public void init() {
		setSize(width, height); // 19*40, 10*40
		setMinimumSize(new Dimension(380, 200)); // 19*20, 10*20
		repaint();

	}

	public void paint(Graphics g) {
		super.paint(g); // prevent from painting over previous
		drawRedWhiteBlue(g);
		drawStars(g);
	}

	/**
	 * Draws the red and white stripes and the blue rectangle in the upper left
	 * corner.
	 * 
	 * @param g
	 *            the Graphics object
	 */
	public void drawRedWhiteBlue(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		width = this.getWidth(); // actual available width for flag
		height = this.getHeight(); // actual available height for flag

		if (width > 19 * height / 10) { // wider, use height as basemark
			width = 19 * height / 10;
		} else { // use width as basemark
			height = 10 * width / 19;
		}

		// draw white stripes
		g.setColor(Color.WHITE);
		for (int y = 1; y < 13; y += 2) {
			g.fillRect(0, y * height / 13, width, height / 13);
		}

		// draw red stripes
		g.setColor(RED);
		for (int y = 0; y < 13; y += 2) {
			g.fillRect(0, y * height / 13, width, height / 13);
		}

		// blue union rectangle
		g.setColor(BLUE);
		g.fillRect(0, 0, (int) (0.76 * height), 6 * height / 13 + height / 13);
	}

	/**
	 * Draws a white, 5-pointed star of a specified size at a specified location.
	 * @param x
	 *            the x coordinate of the center of the star
	 * @param y
	 *            the y coordinate of the center of the star
	 * @param r
	 *            the radius of the circle cirumscribing the star
	 * @param smallR
	 *            the radius of the circle passing through the inner 5 points of
	 *            the star; equal to about .382 * r
	 * @param g the Graphics object
	 */
	public void drawStar(int x, int y, int r, int smallR, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		int[] xPoints = new int[10];
		int[] yPoints = new int[10];


		for (int i = 0; i < 10; i += 2) {
			xPoints[i] = (int) (Math.cos(Math.toRadians(18 + i * 36)) * r + x);
			yPoints[i] = (int) (-Math.sin(Math.toRadians(18 + i * 36)) * r + y);
		}
		for (int i = 1; i < 10; i += 2) {
			xPoints[i] = (int) (Math.cos(Math.toRadians(18 + i * 36)) * smallR + x);
			yPoints[i] = (int) (-Math.sin(Math.toRadians(18 + i * 36)) * smallR + y);
		}
		GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 10);
		p.moveTo(xPoints[0], yPoints[0]);
		for (int i = 1; i < 10; i++) {
			p.lineTo(xPoints[i], yPoints[i]);
		}
		p.closePath();
		g2.fill(p);

	}

	/**
	 * Draws all 50 stars on the flag with specified proportions.
	 * @param g the Graphics object
	 */
	public void drawStars(Graphics g) {
		double X = 0.063 * height;
		double Y = 0.054 * height;
		double r = 0.0308 * height;
		double smallR = r * 0.382;

		for (int x = 1; x < 12; x += 2)
			// 1, 3, 5, 7, 9, 11
			for (int y = 1; y < 10; y += 2)
				// 1, 3, 5, 7, 9
				drawStar((int) (x * X), (int) (y * Y) , (int) r, (int) smallR, g);

		for (int x = 2; x < 11; x += 2)
			// 2, 4, 6, 8, 10
			for (int y = 2; y < 9; y += 2)
				// 2, 4, 6, 8
				drawStar((int) (x * X), (int) (y * Y) , (int) r, (int) smallR, g);

	}

}
