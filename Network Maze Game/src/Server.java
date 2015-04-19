import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{

	private static ArrayList<Client> clients = new ArrayList<Client>();
	private static ServerSocket sock;
	private static GameState g;
	
	public static void main(String[] args)
	{
		g = new GameState();
		sock = null;
		try
		{
			sock = new ServerSocket(54565);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Thread acceptClients = new Thread()
		{
			public void run()
			{
				while (!sock.isClosed())
				{
					try
					{
						Socket s = sock.accept();
						clients.add(new Client(s));
						g.players.add(clients.get(clients.size() - 1).getPlayer());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		acceptClients.start();
		
		Thread game = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		
		game.start();
	}
	
	public static void gameLoop()
	{
		while (true)
		{
			ArrayList<Client> cl = (ArrayList<Client>) clients.clone();
			for (Client c : cl)
			{
				c.update(g);
			}
		}
	}
}