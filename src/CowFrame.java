
import java.awt.*;
import javax.swing.JFrame;

public class CowFrame extends JFrame {
	private final Color BISQUE = new Color(0xcdb79e);
	private final Color w = Color.WHITE; // less typing
	private final Color b = Color.BLACK;
	
	public CowFrame () {
		init();
	}
	
	/**
	 * sets dimensions/base settings
	 */
	public void init() {
		setSize(700,600);
		setBackground(Color.WHITE);
		setTitle("Daniel's Cow");
		repaint();
	}
	
	public void paint(Graphics g1) {
		
		Graphics2D g = (Graphics2D) g1;
		
		g.setColor(new Color(51, 204, 255)); //light turquoise
		g.fillRect(0,0, 700, 390);
		g.setColor(Color.GREEN);
		g.fillRect(0, 390, 700, 230);
		
		//tail
		g.setColor(BISQUE);
		g.fillRoundRect(15, 100, 60, 15, 10, 10); //top of tail
		g.fillRoundRect(15, 110, 15, 125, 10, 10); // bottom of tail
		g.setColor(b);
		g.drawRoundRect(15, 100, 60, 15, 10, 10);
		g.drawRoundRect(15, 110, 15, 125, 10, 10);
		g.setColor(BISQUE);
		g.fillRect(16, 105, 14, 30); // to cover black lines
		
		g.setColor(BISQUE); //legs
		g.fillRect(50, 270, 20, 165);
		g.fillRect(85, 300, 20, 115);
		g.fillRect(395, 300, 20, 135);
		g.fillRect(430, 270, 20, 145);
		g.setColor(b);
		g.drawRect(50, 270, 20, 165);
		g.drawRect(85, 300, 20, 115);
		g.drawRect(395, 300, 20, 135);
		g.drawRect(430, 270, 20, 145);
		
		g.fillRect(50, 420, 20, 15);//feet
		g.fillRect(85, 400, 20, 15);
		g.fillRect(395, 420, 20, 15);
		g.fillRect(430, 400, 20, 15);
		
		g.setColor(BISQUE);
		g.fillRoundRect(50, 100, 400, 200, 50, 50); //main body
		g.setColor(b);
		g.drawRoundRect(50, 100, 400, 200, 50, 50);
		 
		g.setColor(BISQUE); //ears
		Polygon ear1 = new Polygon(new int[]{440, 500, 460}, new int[]{65, 40, 100}, 3);
		
		Polygon ear2 = new Polygon(new int[]{440, 380, 410}, new int[]{65, 40, 100}, 3);
		g.fillPolygon(ear1);
		g.fillPolygon(ear2);
		
		g.setColor(b);
		g.drawPolygon(ear1);
		g.drawPolygon(ear2);
		
		
		g.setColor(BISQUE); // head
		g.fillOval(390, 50, 100, 150);
		g.setColor(b);
		g.drawOval(390, 50, 100, 150);
		
		g.setColor(Color.PINK); //nose
		g.fillOval(390, 140, 100, 70);
		g.setColor(b);
		g.drawOval(390, 140, 100, 70);
		
		g.fillOval(454, 170, 12, 12); //spots on nose
		g.fillOval(414, 170, 12, 12);
		
		//eyes
		g.fillOval(410, 100, 15, 15);
		g.fillOval(460, 100, 15, 15);
		
		// spots on body
		g.setColor(w);
		g.fillOval(256, 206, 50, 60);
		g.fillOval(181, 150, 70, 50);
		g.fillOval(120, 190, 65, 60);
		g.fillOval(320, 130, 60, 45);
		g.fillOval(350, 220, 45, 60);
		g.fillOval(75, 120, 45, 45);
		

	}
	
	public static void main(String[] args) {
		CowFrame c = new CowFrame();
		c.setDefaultCloseOperation(c.EXIT_ON_CLOSE);
		c.setVisible(true);
	}
	
}