import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client
{
	private Socket sock;
	private InputStream is;
	private OutputStream os;
	private Player p;
	
	public Client(Socket s)
	{
		sock = s;
		try
		{
			is = sock.getInputStream();
			os = sock.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		p = new Player(0, 0);
		
		Thread listen = new Thread()
		{
			public void run()
			{
				listen();
			}
		};
		
		listen.start();
	}
	
	public void listen()
	{
		boolean listening = true;
		while (listening)
		{
			try 
			{
				int type = is.read();
				switch (type)
				{
				case 1:
					listening = false;
					break;
				case 2:
					int keys = is.read();
					p.handleInput(keys);
					break;
				default:
					System.out.println("Invalid packet recieved.");
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		this.close();
	}
	
	public void update(GameState g)
	{
		try
		{
			os.write(3);
			os.write(g.players.size());
			for (Player pl : g.players)
			{
				os.write(pl.getX() << 8);
				os.write(pl.getX() & 0xFF);
				
				os.write(pl.getY() << 8);
				os.write(pl.getY() & 0xFF);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try
		{
			is.close();
			os.close();
			sock.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}