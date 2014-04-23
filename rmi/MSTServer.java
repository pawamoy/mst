import java.net.*;
import java.rmi.*;

public class MSTServer extends Thread
{
	private static MSTClient local_client;
	
	public MSTServer(MSTClient client)
	{
		super();
		local_client = client;
	}
	
    public void run()
    {
        InitCommandObject();
    }
    
    public static void InitCommandObject()
    {
		Contact me = local_client.GetMyself();
		
		try
		{
			System.out.println("Server sur port "+me.port);
            Communication comm = new Communication(local_client);
            Naming.rebind("rmi://localhost:"+me.port+"/my,own,comm", comm);
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
