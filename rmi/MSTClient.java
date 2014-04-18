import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;

public class MSTClient extends Thread
{	
	private AppData app;
	
	public MSTClient(AppData a)
	{
		super();
		app = a;
	}
	
	public void run()
	{
		// mon interface de communication
		app.me.comm = GetCommInterface(app.me.ipAddress, app.me.port);
		
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
				app.me.comm.SendCommand("MSG", line);
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
	
    public static CommInterface GetCommInterface(String host, int port)
    {
		CommInterface comm = null;
		
        try
        {
			comm = (CommInterface)Naming.lookup("rmi:"+host+":"+port+"/my,own,comm");
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
