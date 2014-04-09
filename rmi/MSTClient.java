import java.rmi.* ; 
import java.net.MalformedURLException ; 

public class MSTClient extends Thread
{
	public MSTClient()
	{
		super();
	}
	
	public void run()
	{
		CommInterface myComm = GetCommInterface("//localhost");
		
		try
		{
			/*
			 * Application client à foutre ici
			 */
			myComm.SendCommand("MSG1", "FUMIER");
			myComm.SendCommand("MSG2", "TOM LA BRICOLE");
			/*
			 * Application client terminée
			 */
		}
		catch (RemoteException re)
		{
			System.out.println(re);
		}
	}
	
    public static CommInterface GetCommInterface(String host)
    {
		CommInterface comm = null;
		
        try
        {
			comm = (CommInterface)Naming.lookup("rmi:"+host+"/command");
		}
		catch (MalformedURLException mue)
		{
			System.out.println(mue);
		}
		catch (NotBoundException nbe)
		{
			System.out.println(nbe);
		}
		catch (RemoteException re)
		{
			System.out.println(re);
		}
		
		return comm;
    }
}
