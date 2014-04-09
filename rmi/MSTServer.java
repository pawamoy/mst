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
            Command command = new Command();
            Naming.rebind("command", command);
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
