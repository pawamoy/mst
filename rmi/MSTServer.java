import java.net.*;
import java.rmi.*;

public class MSTServer extends Thread
{
	public static String myId;
	
	public MSTServer(String id)
	{
		super();
		myId = id;
	}
	
    public void run()
    {
        InitCommandObject();
    }
    
    public static void InitCommandObject()
    {
		try
		{
            Communication comm = new Communication();
            Naming.rebind(myId, comm);
        }
        catch (RemoteException re)
        {
            System.err.println("Error: " + re.getMessage());
        }
        catch (MalformedURLException mue)
        {
            System.err.println("Error: " + mue.getMessage());
        }
	}
}
