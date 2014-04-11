import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;

public class MSTClient extends Thread
{
	public static String myId;
	
	public MSTClient(String id)
	{
		super();
		myId = id;
	}
	
	public void run()
	{
		CommInterface myComm = GetCommInterface("//localhost", myId);
		
		try
		{
			/*
			 * Application client à foutre ici
			 */
			 
			String line = "";
			Scanner sc = new Scanner(System.in);
			
			while (line.compareToIgnoreCase("EOF") != 0) {
				System.out.print("> ");
				line = sc.nextLine();
				myComm.SendCommand("MSG", line);
			}
			
			/*
			 * Application client terminée
			 */
		}
		catch (RemoteException re)
		{
			System.err.println("Error: " + re.getMessage());
		}
	}
	
    public static CommInterface GetCommInterface(String host, String id)
    {
		CommInterface comm = null;
		
        try
        {
			comm = (CommInterface)Naming.lookup("rmi:"+host+"/"+id);
		}
		catch (MalformedURLException mue)
		{
			System.err.println("Error: " + mue.getMessage());
		}
		catch (NotBoundException nbe)
		{
			System.err.println("Error: " + nbe.getMessage());
		}
		catch (RemoteException re)
		{
			System.err.println("Error: " + re.getMessage());
		}
		
		return comm;
    }
}
