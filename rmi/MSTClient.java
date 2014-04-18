import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.ArrayList;

public class MSTClient extends Thread
{	
	private AppData app;
	private volatile boolean loop;
	
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
		
		loop = true;
		
		while (loop == true) {
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
			comm = (CommInterface)Naming.lookup("rmi://"+host+":"+port+"/my,own,comm");
		}
		catch (MalformedURLException mue)
		{
			System.err.println("Error: client: " + mue.getMessage());
		}
		catch (NotBoundException nbe)
		{
			System.err.println("Error: client: " + nbe.getMessage());
		}
		catch (RemoteException re)
		{
			System.err.println("Error: client: " + re.getMessage());
		}
		
		return comm;
    }
    
    public void TreatCommand(Command cmd)
    {	
		switch(cmd.type)
		{
			case MESSAGE:
				Message(cmd);
				break;
				
			case EXIT:
				ExitClient();
				break;
				
			case BROADCAST:
				Broadcast(cmd);
				break;
				
			case SEARCH:
				//~ Search(cmd);
				break;
				
			case WIZZ:
				//~ Wizz(cmd);
				break;
				
			case REFRESH:
				//~ Refresh();
				break;
				
			case HELP:
				//~ Help(cmd);
				break;
				
			default:
				System.err.println("Error: client: wrong command type (???)");
				break;
		}
	}
	
	public void Message(Command cmd)
	{
		if (cmd.target.isEmpty())
		{
			if (app.cur_con != null)
			{
				SendMessage(app.cur_con, cmd.args);
			}
			else if (app.cur_grp != null)
			{
				ArrayList<Contact> group_ctt = app.cur_grp.GetAllContacts();
						
				for (int i=0; i<group_ctt.size(); i++)
					SendMessage(group_ctt.get(i), cmd.args);
			}
			else
			{
				System.err.println("Error: client: no previous communication");
			}
		}
		else
		{
			if (cmd.target.compareTo("all") == 0)
			{
				for (int i=0; i<app.contacts.ContactSize(); i++)
					SendMessage(app.contacts.GetContact(i), cmd.args);
					
				app.cur_grp = (Group)app.contacts;
				app.cur_con = null;
			}
			else if (cmd.target.compareTo("me") == 0)
			{
				SendMessage(app.me, cmd.args);
				
				app.cur_grp = null;
				app.cur_con = app.me;
			}
			else
			{
				Contact ctt = app.contacts.GetContact(cmd.target);
				
				if (ctt != null)
				{
					SendMessage(ctt, cmd.args);
					
					app.cur_grp = null;
					app.cur_con = ctt;
				}
				else
				{
					Group grp = app.contacts.GetGroup(cmd.target);
					
					if (grp != null)
					{
						ArrayList<Contact> group_ctt = grp.GetAllContacts();
						
						for (int i=0; i<group_ctt.size(); i++)
							SendMessage(group_ctt.get(i), cmd.args);
					
						app.cur_con = null;
						app.cur_grp = grp;
					}
					else
					{
						System.err.println("Error: client: unknown reference \""+cmd.target+"\"");
					}
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
				System.err.println("Error: client: " + re.getMessage());
			}
		}
	}
	
	public void ExitClient()
	{
		loop = false;
	}
	
	public void Broadcast(Command cmd)
	{
		for (int i=0; i<app.contacts.ContactSize(); i++)
			SendMessage(app.contacts.GetContact(i), cmd.args);
	}
}
