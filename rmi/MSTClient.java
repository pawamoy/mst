import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.ArrayList;

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
		Command cmd;
		// mon interface de communication
		app.me.comm = GetCommInterface(app.me.ipAddress, app.me.port);
		
		/*
		 * Application client à foutre ici
		 */
		 
		String line = "";
		Scanner sc = new Scanner(System.in);
		
		while (line.compareToIgnoreCase("EOF") != 0) {
			System.out.print("> ");
			line = sc.nextLine();
			if ( !line.isEmpty() )
			{
				cmd = Interpreter.StringToCommand(line);
				if (cmd != null)
				{
					TreatCommand(cmd);
				}
			}
		}
		
		/*
		 * Application client terminée
		 */
	}
	
    public static CommInterface GetCommInterface(String host, int port)
    {
		CommInterface comm = null;
		
        try
        {
			comm = (CommInterface)Naming.lookup("rmi://"+host/*+":"+port*/+"/my,own,comm");
		}
		catch (MalformedURLException mue)
		{
			System.err.println("Errora: " + mue.getMessage());
		}
		catch (NotBoundException nbe)
		{
			System.err.println("Errorb: " + nbe.getMessage());
		}
		catch (RemoteException re)
		{
			System.err.println("Errorc: " + re.getMessage());
		}
		
		return comm;
    }
    
    public void TreatCommand(Command cmd)
    {	
		switch(cmd.type)
		{
			case MESSAGE:
				TreatMessage(cmd);
				break;
				
			case EXIT:
				
				break;
				
			case BROADCAST:
				
				break;
				
			case SEARCH:
				
				break;
				
			case WIZZ:
				
				break;
				
			case REFRESH_CONFIG:
				
				break;
				
			case HELP:
				
				break;
				
			case CONTACT:
			default:
				break;
		}
	}
	
	public void TreatMessage(Command cmd)
	{
		if (cmd.target.isEmpty())
		{
			if (app.current != null)
				SendMessage(app.current, cmd.args);
			else
				System.err.println("Error: client: no current contact, abort");
		}
		else
		{
			Contact ctt = app.contacts.GetContact(cmd.target);
			
			if (ctt != null)
			{
				SendMessage(ctt, cmd.args);
			}
			else
			{
				Group grp = app.contacts.GetGroup(cmd.target);
				
				if (grp != null)
				{
					ArrayList<Contact> group_ctt = grp.GetAllContacts();
					
					for (int i=0; i<group_ctt.size(); i++)
						SendMessage(group_ctt.get(i), cmd.args);
				}
				else
				{
					System.err.println("Error: client: unknown reference \""+cmd.target+"\"");
				}
			}
		}
	}
	
	public void SendMessage(Contact ctt, String msg)
	{
		if (ctt.comm == null)
		{
			if (ctt.ipAddress.compareTo("?") == 0)
				System.err.println("Error: client: no known address for "+ctt.name);
			else
				ctt.comm = GetCommInterface(ctt.ipAddress, ctt.port);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				ctt.comm.Message(msg);
			}
			catch (RemoteException re)
			{
				System.err.println("Error: " + re.getMessage());
			}
		}
	}
}
