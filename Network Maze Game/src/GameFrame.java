import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements WindowListener
{
	GamePanel game;
	public GameFrame()
	{
		super("Maze Game");
		addWindowListener(this);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		game = new GamePanel();
		setContentPane(game);
	}

	public static void main(String[] args)
	{
		GameFrame frame = new GameFrame();
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		try
		{
			game.disconnect();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
}