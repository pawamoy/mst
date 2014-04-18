import java.net.*;
import java.rmi.*;

public class MSTServer extends Thread
{
	public MSTServer()
	{
		super();
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
            Naming.rebind("my,own,comm", comm);
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
