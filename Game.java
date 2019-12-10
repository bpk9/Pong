package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener
{

	public static Game pong;

	public final int WIDTH = 700, HEIGHT = 700;

	public Renderer renderer;

	public Player player1, player2;

	public Ball ball;

	public boolean w, s, up, down; // active controls
	
	public boolean paused;

	public Random random;

	public JFrame jframe;

	public Game()
	{	
		this.random = new Random();

		// init graphics
		this.renderer = new Renderer();
		this.jframe = new JFrame("Pong");
		this.jframe.setSize(WIDTH + 15, HEIGHT + 35);
		this.jframe.setVisible(true);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jframe.add(renderer);
		this.jframe.validate();
		this.jframe.addKeyListener(this);
		
		// init game
		this.paused = true;
		this.player1 = new Player(this, 1);
		this.player2 = new Player(this, 2);
		this.ball = new Ball(this);
		
		// timer
		Timer timer = new Timer(20, this);
		timer.start();
		
		// add graphics
		update();
		renderer.repaint();
	}

	public void update()
	{
		
		// Player 1 controls
		if (w)
			player1.move(true);
		if (s)
			player1.move(false);

		// Player 2 controls
		if (up)
			player2.move(true);
		if (down)
			player2.move(false);

		ball.update(player1, player2);
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.WHITE);

		g.setStroke(new BasicStroke(5f));

		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

		g.setStroke(new BasicStroke(2f));

		g.drawOval(WIDTH / 2 - 150, HEIGHT / 2 - 150, 300, 300);

		g.setFont(new Font("Arial", 1, 50));

		g.drawString(String.valueOf(player1.score), WIDTH / 2 - 90, 50);
		g.drawString(String.valueOf(player2.score), WIDTH / 2 + 65, 50);

		if (this.paused)
			g.drawString("PAUSED", (WIDTH / 2) - 100, (HEIGHT / 4));
		
		player1.render(g);
		player2.render(g);
		ball.render(g);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!paused)
			update();
		
		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Game();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
			w = true;
		
		else if (id == KeyEvent.VK_S)
			s = true;
		
		else if (id == KeyEvent.VK_UP)
			up = true;
		
		else if (id == KeyEvent.VK_DOWN)
			down = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
			this.w = false;
		
		else if (id == KeyEvent.VK_S)
			this.s = false;
		
		else if (id == KeyEvent.VK_UP)
			this.up = false;
		
		else if (id == KeyEvent.VK_DOWN)
			this.down = false;
		
		else if (id == KeyEvent.VK_SPACE)
			this.paused = !this.paused;
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// no-op
	}
}
