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
		ICommand local_command = GetICommand("//localhost");
		
		try
		{
			/*
			 * Application client à foutre ici
			 */
			local_command.SendCommand("MSG1", "FUMIER");
			local_command.SendCommand("MSG2", "TOM LA BRICOLE");
			/*
			 * Application client terminée
			 */
		}
		catch (RemoteException re)
		{
			System.out.println(re);
		}
	}
	
    public static ICommand GetICommand(String host)
    {
		ICommand cmd = null;
		
        try
        {
			cmd = (ICommand)Naming.lookup("rmi:"+host+"/command");
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
		
		return cmd;
    }
}
