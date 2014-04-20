import java.net.*;
import java.rmi.*;

public class MSTServer extends Thread
{
	private AppData app;
	private static MSTClient local_client;
	
	public MSTServer(AppData a, MSTClient client)
	{
		super();
		app = a;
		local_client = client;
	}
	
    public void run()
    {
        InitCommandObject();
    }
    
    public static void InitCommandObject()
    {
		try
		{
            Communication comm = new Communication(local_client);
            Naming.rebind("my,own,comm", comm);
        }
        catch (RemoteException re)
        {
            System.err.println("Error: server: " + re.getMessage());
        }
        catch (MalformedURLException mue)
        {
            System.err.println("Error: server: " + mue.getMessage());
        }
	}
}
