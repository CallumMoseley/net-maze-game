import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener
{
	private Player player;
	private int keys;
	
	public GamePanel()
	{
		setPreferredSize(new Dimension(1024, 768));
		setFocusable(true);
		addKeyListener(this);
		
		player = new Player(0, 0);
		keys = 0;
		
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		
		loop.start();
	}

	private void gameLoop()
	{
		while (true)
		{
			player.handleInput(keys);
			repaint();
			try
			{
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		player.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys |= 0x80;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys |= 0x40;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys |= 0x20;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys |= 0x10;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys &= ~0x80;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys &= ~0x40;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys &= ~0x20;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys &= ~0x10;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}