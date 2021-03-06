

import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel {
	Player p;
	Mho[] Mhos = new Mho[12];
	Tile[][] board = new Tile[12][12];
	String gameState = "StartMenu";
	int MhosDead = 0;
	
	JButton b2 = new JButton("Start");
	JButton b1 = new JButton("Quit");

	public static void main(String[] args) {
		Game g = new Game();
		JFrame j = new JFrame();
		j.setSize(620, 640);
		j.setTitle("Hivolts");
		j.add(g);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
		j.setLocationRelativeTo(null);
	}

	public Game() {
		this.setLayout(null);
		Font f = new Font("Helvetica Neue", Font.PLAIN, 15);
		b2.setFont(f);
		b1.setFont(f);

	//	final JButton b2 = new JButton("Start");
	//	final JButton b1 = new JButton("Quit");
		b2.setBounds(240, 300, 160, 75);
		b1.setBounds(240, 400, 160, 75);
		b1.setBackground(Color.GRAY);
		b2.setBackground(Color.GRAY);
		b2.setVerticalTextPosition(AbstractButton.CENTER);
		b2.setHorizontalTextPosition(AbstractButton.CENTER);
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setHorizontalTextPosition(AbstractButton.CENTER);
		b1.setActionCommand("quit");
		b2.setActionCommand("start");
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("quit")) {
					System.exit(0);
				}
			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("start")) {
					gameState = "Game";
					init();
					repaint();
					b2.setVisible(false);
					b1.setVisible(false);
				}

			}

		});
		this.add(b2);
		this.add(b1);
		KeyListener listener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int xVel = 0, yVel = 0;
				boolean jumped = false;
				switch (e.getKeyChar()) {
				case 'w':
					yVel = -1;
					break;
				case 'a':
					xVel = -1;
					break;
				case 's':
					break;
				case 'd':
					xVel = 1;
					break;
				case 'q':
					yVel = -1;
					xVel = -1;
					break;
				case 'e':
					yVel = -1;
					xVel = 1;
					break;
				case 'z':
					yVel = 1;
					xVel = -1;
					break;
				case 'c':
					yVel = 1;
					xVel = 1;
					break;
				case 'x':
					yVel = 1;
					break;
				case 'j':
					jump();
					jumped = true;
				}
				if (!(board[p.x + xVel][p.y + yVel] instanceof Fence)
						&& (!(board[p.x + xVel][p.y + yVel] instanceof Mho))) {
					p.x += xVel;
					p.y += yVel;
					board[p.x - xVel][p.y - yVel] = new Tile();
					board[p.x][p.y] = p;
					if (!jumped)
						updateMhos();
					if(MhosDead == 12){
						gameOver(true);
					}
				} else {
					gameOver(false);
				}
				repaint();

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
		addKeyListener(listener);
		setFocusable(true);
		repaint();
	}

	public void init() {
		setSize(620, 640);
		MhosDead = 0;
		initBoard();
		initPlayer();
	}

	public void jump() {
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while (board[x][y] instanceof Fence) {
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p.x = x;
		p.y = y;
	}

	public void initBoard() {
		Random r = new Random();
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				if (x == 0 || x == 11 || y == 0 || y == 11) {
					board[x][y] = new Fence(x, y);
				} else {
					board[x][y] = new Tile();
				}
			}
		}
		for (int i = 0; i < 20; i++) {
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while (board[x][y] instanceof Fence) {
				x = r.nextInt(11);
				y = r.nextInt(11);
			}
			board[x][y] = new Fence(x, y);
		}
		for (int i = 0; i < 12; i++) {
			int x = r.nextInt(11);
			int y = r.nextInt(11);
			while (board[x][y] instanceof Fence) {
				x = r.nextInt(11);
				y = r.nextInt(11);
			}
			Mhos[i] = new Mho(x, y);
			board[x][y] = Mhos[i];
		}

	}
	public boolean allMhosDead(){
		for(int i = 0; i < 12; i++){
			if(Mhos[i].isAlive){
				return false;
			}
		}
		return true;
	}
	public void initPlayer() {
		Random r = new Random();
		int x = r.nextInt(11);
		int y = r.nextInt(11);
		while (board[x][y] instanceof Fence || board[x][y] instanceof Mho) {
			x = r.nextInt(11);
			y = r.nextInt(11);
		}
		p = new Player(x, y);
		board[x][y] = p;
	}
	
	/**
	 * moves all remaining Mhos according to Mho AI after player moves
	 */
	public void updateMhos() { 

		for (int i = 0; i < 12; i++) {
			if (Mhos[i].isAlive) {// Mho AI
				
				if (Mhos[i].x == p.x) // directly vertical
					if (Mhos[i].y > p.y) // move up
						moveMho(i, 0, -1);
					else // move down
						moveMho(i, 0, 1);

				else if (Mhos[i].y == p.y) { // directly horizontal
					if (Mhos[i].x > p.x) {
						moveMho(i, -1, 0);
					} else {
						moveMho(i, 1, 0);
					}
				} else {
					int relPos = Mhos[i].relToPlayer(p.x, p.y);
					int horDist = Math.abs(Mhos[i].x - p.x);
					int verDist = Math.abs(Mhos[i].y - p.y);

					if (relPos == 1) { // top right
						if (!(board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, -1, 1);
						} else if (horDist >= verDist && !(board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, -1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence) // vertical
							moveMho(i, 0, 1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x - 1][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, -1, 1);
						else if (horDist >= verDist && board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence)
							moveMho(i, -1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 0, 1);

					} else if (relPos == 2) { // top left
						if (!(board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, 1, 1);
						} else if (horDist >= verDist && !(board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, 1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence) // vertical
							moveMho(i, 0, 1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x + 1][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 1, 1);
						else if (horDist >= verDist && board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence)
							moveMho(i, 1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y + 1] instanceof Fence)
							moveMho(i, 0, 1);
						
					} else if (relPos == 3) { // bottom left
						if (!(board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, 1, -1);
						} else if (horDist >= verDist && !(board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x + 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, 1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence) // vertical
							moveMho(i, 0, -1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x + 1][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 1, -1);
						else if (horDist >= verDist && board[Mhos[i].x + 1][Mhos[i].y] instanceof Fence)
							moveMho(i, 1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 0, -1);
						
					} else if (relPos == 4) { // bottom right
						if (!(board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Mho)) {
							// directly diagonal
							moveMho(i, -1, -1);
						} else if (horDist >= verDist && !(board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence
								|| board[Mhos[i].x - 1][Mhos[i].y] instanceof Mho)) // horizontal
							moveMho(i, -1, 0);

						else if (horDist <= verDist && !(board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
								|| board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence) // vertical
							moveMho(i, 0, -1);
						
						// tries to move on a fence now
						else if (board[Mhos[i].x - 1][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, -1, -1);
						else if (horDist >= verDist && board[Mhos[i].x - 1][Mhos[i].y] instanceof Fence)
							moveMho(i, -1, 0);
						else if (horDist >= verDist && board[Mhos[i].x][Mhos[i].y - 1] instanceof Fence)
							moveMho(i, 0, -1);
						
					}

				}
			}

		}

	}

	/**
	 * 
	 * @param id
	 *            the index of the Mho (Mhos are stored in an array)
	 * @param dx
	 *            the amount moved in the x direction
	 * @param dy
	 *            the amount moved in the y direction
	 */
	public void moveMho(int id, int dx, int dy) {
		board[Mhos[id].x][Mhos[id].y] = new Tile();
		Mhos[id].x += dx;
		Mhos[id].y += dy;
		if (board[Mhos[id].x][Mhos[id].y] instanceof Fence){
			Mhos[id].die();
			MhosDead++;}
		else
			board[Mhos[id].x][Mhos[id].y] = Mhos[id];

		if (contact(Mhos[id])) {
			gameOver(false);
		}
		
		
	}

	/**
	 * 
	 * @param m the specific Mho m
	 * @return true if the Mho has touched the player, false if not
	 */
	public boolean contact(Mho m) {
		if (m.isAlive && m.y == p.y && m.x == p.x)
			return true;
		return false;
	}
	
	/**
	 * Displays game over message.
	 * @param won true if the game has been won, false if not
	 */
	public void gameOver(boolean won){
		if(won){
			gameState = "Won";
		}else{
			gameState = "Lost";
		}
		b1.setVisible(true);
		b2.setText("Play Again");
		b2.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		System.out.println(MhosDead);
		if (gameState.equals("StartMenu")) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 620, 640);
			Image i;
			try {
				// image from http://homepage.cs.uiowa.edu/~jones/plato/
				i = ImageIO.read(new File("images/hivolts1.png"));
				g.drawImage(i, 0, 0, 620, 600, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (gameState.equals("Game")) {
			g.fillRect(0, 0, 600, 600);
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 12; x++) {
					board[x][y].draw(g);
				}
			}
		} else if (gameState.equals("Lost")) {
			g.fillRect(0, 0, 620, 640);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("YOU LOST!", 200, 200);
		}  else if (gameState.equals("Won")) {
			g.fillRect(0, 0, 620, 640);
			g.setFont(new Font("SansSerif", 12, 48));
			g.setColor(Color.WHITE);
			g.drawString("YOU WON!", 200, 200);
		}
	}

}