import javax.swing.JFrame;

public class GameFrame extends JFrame
{
	public GameFrame()
	{
		super("The Legend of Zelda");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GamePanel game = new GamePanel();
		setContentPane(game);
	}

	public static void main(String[] args)
	{
		GameFrame frame = new GameFrame();
		frame.pack();
		frame.setVisible(true);
	}
}