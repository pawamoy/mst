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
            Naming.rebind("command", comm);
        }
        catch (RemoteException re)
        {
            System.out.println(re);
        }
        catch (MalformedURLException e)
        {
            System.out.println(e);
        }
	}
}
