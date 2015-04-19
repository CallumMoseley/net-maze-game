import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener
{
	private Player player;
	private int keys;
	private Socket server;
	private InputStream is;
	private OutputStream os;
	private ArrayList<Player> players;
	
	public GamePanel()
	{
		setPreferredSize(new Dimension(1024, 768));
		setFocusable(true);
		addKeyListener(this);
		
		player = new Player(0, 0);
		players = new ArrayList<Player>();
		keys = 0;
		
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		
		try
		{
			server = new Socket("localhost", 54565);
			is = server.getInputStream();
			os = server.getOutputStream();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		loop.start();
		ListenToServer l = new ListenToServer();
		l.start();
	}

	private void gameLoop()
	{
		while (true)
		{
			player.handleInput(keys);
			try
			{
				os.write(2);
				os.write(keys);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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
	
	class ListenToServer extends Thread
	{
		public void run()
		{
			while (!server.isClosed())
			{
				try
				{
					int type = is.read();
					switch (type)
					{
					case 3:
						int np = is.read();
						while (players.size() != np)
						{
							if (players.size() > np)
							{
								players.remove(players.size() - 1);
							}
							else
							{
								players.add(new Player(0, 0));
							}
						}
						for (int i = 0; i < np; i++)
						{
							int x = (is.read() << 8) | is.read();
							int y = (is.read() << 8) | is.read();
							players.get(i).updatePos(x, y);
						}
						
						int x = (is.read() << 8) | is.read();
						int y = (is.read() << 8) | is.read();
						player.updatePos(x, y);
						
						break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void disconnect()
	{
		try
		{
			os.write(1);
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		player.draw(g);
		for (Player p : players)
		{
			p.draw(g);
		}
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