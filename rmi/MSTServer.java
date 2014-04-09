import java.net.*;
import java.rmi.*;

public class MSTServer
{
    public static void main (String[] args)
    {
        InitCommandObject(args);
    }
    
    public static void InitCommandObject(String args[])
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
