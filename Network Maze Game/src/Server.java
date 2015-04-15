import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{

	private static ArrayList<Client> clients = new ArrayList<Client>();
	private static ServerSocket sock;
	
	public static void main(String[] args)
	{
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
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		
		acceptClients.start();
	}
}