package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player
{

	public int paddleNumber;

	public int x, y;
	
	public final int WIDTH = 50, HEIGHT = 250;

	public int score;

	public Player(Game game, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1)
			this.x = 0;

		if (paddleNumber == 2)
			this.x = game.WIDTH - this.WIDTH;

		this.y = game.HEIGHT / 2 - HEIGHT / 2;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, this.WIDTH, this.HEIGHT);
	}

	public void move(boolean up)
	{
		int speed = 15;

		if (up)
		{
			if (y - speed > 0)
				y -= speed;
			else
				y = 0;	
		}
		else
		{
			if (y + HEIGHT + speed < Game.pong.HEIGHT)
				y += speed;
			else
				y = Game.pong.HEIGHT - HEIGHT;
		}
	}

}
