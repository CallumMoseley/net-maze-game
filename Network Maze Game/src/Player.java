import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Player
{
	private static final int SPEED = 2;
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 32, 32);
	}
	
	public void handleInput(int input)
	{
		int right = input >> 7;
		int up = (input >> 6) & 1;
		int left = (input >> 5) & 1;
		int down = (input >> 4) & 1;
		
		dx = 0;
		dy = 0;
		if (right == 1)
		{
			dx += SPEED;
		}
		if (up == 1)
		{
			dy -= SPEED;
		}
		if (left == 1)
		{
			dx -= SPEED;
		}
		if (down == 1)
		{
			dy += SPEED;
		}
		
		x += dx;
		y += dy;
	}

	public void updatePos(int x2, int y2)
	{
		x = x2;
		y = y2;
	}
}