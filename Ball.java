package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

	public int x, y, WIDTH = 25, HEIGHT = 25;

	public int motionX, motionY;

	public Random random;

	private Game pong;

	public int amountOfHits;

	public Ball(Game pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void update(Player paddle1, Player paddle2)
	{
		int speed = 5;

		this.x += motionX * speed;
		this.y += motionY * speed;

		if (this.y + HEIGHT - motionY > pong.HEIGHT || this.y + motionY < 0)
		{
			if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = random.nextInt(4);

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
			else
			{
				this.motionY = -random.nextInt(4);
				this.y = pong.HEIGHT - HEIGHT;

				if (motionY == 0)
				{
					motionY = -1;
				}
			}
		}

		if (checkCollision(paddle1) == 1)
		{
			this.motionX = 1 + (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0)
				motionY = 1;

			amountOfHits++;
		}
		else if (checkCollision(paddle2) == 1)
		{
			this.motionX = -1 - (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0)
				motionY = 1;

			amountOfHits++;
		}

		if (checkCollision(paddle1) == 2)
		{
			paddle2.score++;
			spawn();
		}
		else if (checkCollision(paddle2) == 2)
		{
			paddle1.score++;
			spawn();
		}
	}

	public void spawn()
	{
		this.amountOfHits = 0;
		this.x = pong.WIDTH / 2 - this.WIDTH / 2;
		this.y = pong.HEIGHT / 2 - this.HEIGHT / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0)
			motionY = 1;

		if (random.nextBoolean())
			motionX = 1;
		else
			motionX = -1;
	}

	public int checkCollision(Player paddle)
	{
		if (this.x < paddle.x + paddle.WIDTH && this.x + WIDTH > paddle.x && this.y < paddle.y + paddle.HEIGHT && this.y + HEIGHT > paddle.y)
			return 1; //bounce
		
		else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - WIDTH && paddle.paddleNumber == 2))
			return 2; //score

		return 0; //nothing
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(x, y, WIDTH, HEIGHT);
	}

}
