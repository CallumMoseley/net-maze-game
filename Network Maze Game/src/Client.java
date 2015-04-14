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
	}
	
	public void listen()
	{
		while (!sock.isClosed())
		{
			try 
			{
				int type = is.read();
				switch (type)
				{
				case 2:
					int keys = is.read();
					p.handleInput(keys);
					break;
				default:
					System.out.println("Invalid packet recieved.");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
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