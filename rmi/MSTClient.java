import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.ArrayList;

public class MSTClient extends Thread
{	
	public static AppData app;
	private volatile boolean loop;
	private int num_bc;
	
	public MSTClient(AppData a)
	{
		super();
		app = a;
		num_bc = 0;
	}
	
	public Contact GetMyself()
	{
		return app.me;
	}
	
	public void run()
	{
		//Command cmd;
		
		/*
		 * Application client à foutre ici
		 */
		 
		//~ String line = "";
		//~ Scanner sc = new Scanner(System.in);
		//~ 
		//~ loop = true;
		//~ 
		//~ while (loop == true) {
			//~ System.out.print("> ");
			//~ line = sc.nextLine();
			//~ if ( !line.isEmpty() )
			//~ {
				//~ cmd = Interpreter.StringToCommand(line);
				//~ if (cmd != null)
				//~ {
					//~ TreatCommand(cmd);
				//~ }
			//~ }
		//~ }
		
        loop = true;
        
        while (loop == true) { };
        
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
            app.mf.Print("Error: client: " + mue.getMessage(), "error");
		}
		catch (NotBoundException nbe)
		{
            app.mf.Print("Error: client: " + nbe.getMessage(), "error");
		}
		catch (RemoteException re)
		{
            app.mf.Print("Error: client: " + re.getMessage(), "error");
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
				Broadcast(app.me.port, num_bc, cmd.args);
				num_bc++;
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
				Help(cmd);
				break;
				
			case ADD:
				//~ Add(cmd);
				break;
				
			case DELETE:
				//~ Delete(cmd);
				break;
				
			case MODIFY:
				//~ Modify(cmd);
				break;
				
			case LIST:
				List(cmd);
				break;
				
			default:
                app.mf.Print("Error: client: wrong command type (???)", "error");
				break;
		}
	}
	
	public void Message(Command cmd)
	{
		String atWho = "";
		int ntarg = cmd.NbTarget();
		String targ;
		boolean err = false;
		
		if (ntarg == 0)
		{
			boolean no_previous_contact = false;
			boolean no_previous_group = false;
			
			int ncurc = app.cur_con.size();
			
			if (ncurc > 0)
			{
				if (app.toAll == true)
					atWho = atWho.concat("@all ");
					
				for (int i=0; i<ncurc; i++)
				{
					if (app.toAll == false)
						atWho = atWho.concat("@"+app.cur_con.get(i).name+" ");
					SendMessage(app.cur_con.get(i), cmd.args);
				}
			}
			else
			{
				no_previous_contact = true;
			}
			
			int ncurg = app.cur_grp.size();
				
			if (ncurg > 0)
			{
				for (int j=0; j<ncurg; j++)
				{
					atWho = atWho.concat("@"+app.cur_grp.get(j).name+" ");
					ArrayList<Contact> group_ctt = app.cur_grp.get(j).GetAllContacts();
				
					for (int i=0; i<group_ctt.size(); i++)
						SendMessage(group_ctt.get(i), cmd.args);
				}
			}
			else
			{
				no_previous_group = true;
			}
			
			if (no_previous_contact && no_previous_group)
			{
				err = true;
				app.mf.Print("Error: client: no previous communication", "error");
			}
		}
		else
		{
			app.toAll = false;
			app.cur_grp.clear();
			app.cur_con.clear();
		
			for (int j=0; j<ntarg; j++)
			{
				targ = cmd.GetTarget(j);
				
				if (targ.compareTo("all") == 0)
				{
					atWho = atWho.concat("@all ");
					for (int i=0; i<app.contacts.ContactSize(); i++)
					{
						SendMessage(app.contacts.GetContact(i), cmd.args);
						app.toAll = true;
						app.cur_con.add(app.contacts.GetContact(i));
					}
				}
				else if (targ.compareTo("me") == 0)
				{
					atWho = atWho.concat("@me ");
					SendMessage(app.me, cmd.args);
					app.cur_con.add(app.me);
				}
				else
				{
					Contact ctt = app.contacts.GetContact(targ);
					
					if (ctt != null)
					{
						atWho = atWho.concat("@"+ctt.name+" ");
						SendMessage(ctt, cmd.args);
						app.cur_con.add(ctt);
					}
					else
					{
						Group grp = app.contacts.GetGroup(targ);
						
						if (grp != null)
						{
							atWho = atWho.concat("@"+grp.name+" ");
							ArrayList<Contact> group_ctt = grp.GetAllContacts();
							
							for (int i=0; i<group_ctt.size(); i++)
								SendMessage(group_ctt.get(i), cmd.args);
						
							app.cur_grp.add(grp);
						}
						else
						{
							err = true;
							app.mf.Print("Error: client: unknown reference \""+cmd.target.get(0)+"\"", "error");
						}
					}
				}
			}
		}
		
		if (err == false)
			app.mf.Print(atWho+": "+cmd.args, "sent_message");
	}
	
	public CommInterface GetComm(Contact ctt)
	{
		if (ctt.ipAddress.compareTo("?") == 0)
		{
			System.err.println("Error: client: no known address for "+ctt.name);
			return null;
		}
		else
		{
			return GetCommInterface(ctt.ipAddress, ctt.port);
		}
	}
	
	public void SendMessage(Contact ctt, String msg)
	{
		if (ctt.comm == null)
		{
			ctt.comm = GetComm(ctt);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				ctt.comm.Message(msg);
			}
			catch (RemoteException re)
			{
				app.mf.Print("Error: client: " + re.getMessage(), "error");
			}
		}
	}
	
	public void ExitClient()
	{
		loop = false;
	}
	
	public void Broadcast(int id, int num, String msg)
	{
		for (int i=0; i<app.contacts.ContactSize(); i++)
			SendBroadcast(app.contacts.GetContact(i), msg, id, num);
	}
	
	public void SendBroadcast(Contact ctt, String msg, int id, int num)
	{
		if (ctt.comm == null)
		{
			ctt.comm = GetComm(ctt);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				ctt.comm.Broadcast(id, num, msg);
			}
			catch (RemoteException re)
			{
				app.mf.Print("Error: client: " + re.getMessage(), "error");
			}
		}
	}
	
	public void List(Command cmd)
	{	
		Contact ctt;
		Group grp;
	
		int ntarg = cmd.target.size();
		
		if (ntarg == 0)
		{			
			ListGroup(app.contacts);
		}
		else
		{
			if (cmd.target.get(0).compareTo("me") == 0)
			{
				PrintContact(app.me);
			}
			else
			{
				ctt = app.contacts.GetContact(cmd.target.get(0));
					
				if (ctt != null)
				{
					PrintContact(ctt);
				}
				else
				{
					grp = app.contacts.GetGroup(cmd.target.get(0));
					
					if (grp != null)
						ListGroup(grp);
					else
						app.mf.Print("Error: client: unknown reference \""+cmd.target.get(0)+"\"", "error");
				}
			}
		}
	}
	
	public void ListGroup(RootGroup grp)
	{
		int cs = grp.ContactSize();
		int gs = grp.GroupSize();
		
		if (cs > 0)
		{
			System.out.println("** Contacts **");
			
			for (int i=0; i<cs; i++)
				PrintContact(grp.GetContact(i));
		}
		
		if (gs > 0)
		{
			System.out.println("\n** Groups **");

			for (int i=0; i<gs; i++)
				PrintGroup(grp.GetGroup(i));
		}
	}
	
	public void PrintContact(Contact ctt)
	{
		System.out.println(String.format("%-20s", ctt.name)+ctt.ipAddress+":"+ctt.port);
	}
	
	public void PrintGroup(Group grp)
	{
		System.out.println(String.format("%-20s", grp.name)+"("+grp.TotalSize()+" contacts)");
	}
	
	public void Help(Command cmd)
	{
		if (cmd.target.isEmpty())
		{
			System.out.println("Available commands are:");
			System.out.println(":help [CMD]               - print this help or detailed command help");
			System.out.println(":bye,end,stop,quit,q,exit - quit the program");
			System.out.println(":NOM_CONTACT MSG          - specify which contact to join");
			System.out.println(":search,seek,who [NOM]    - search someone in friend's contacts");
			System.out.println(":add NOM [ADR] [PORT]     - add a contact in your book");
			System.out.println(":delete,del [NOM/ADR]     - delete a contact from your book");
			System.out.println(":modify,mod [CTT/GRP]     - modify a contact in your book");
			System.out.println(":list [GRP]               - list a group or properties of a contact");
			System.out.println(":wizz [CTT/GRP]           - send a wizz to someone");
			System.out.println(":broadcast,bc MSG         - broadcast a message");
			System.out.println(":refresh                  - reload address book");
		}
		else
		{
			switch (Interpreter.SwitchType(cmd.target.get(0)))
			{					
				case EXIT:
					System.out.println("Save the address book and quit the program.");
					System.out.println("Equivalents: exit, quit, q, end, stop, bye.");
					break;
					
				case BROADCAST:
					System.out.println("Broadcast a message through your contacts and their contacts.");
					System.out.println("Use like this: :broadcast hi everyone !");
					System.out.println("Equivalents: broadcast, bc.");
					break;
					
				case SEARCH:
					System.out.println("Search someone in your contacts' lists.");
					System.out.println("Use like this: :search someone hey you");
					System.out.println("If found, added to your book.");
					System.out.println("If message provided, sended to him/her.");
					System.out.println("Equivalents: search, seek, who.");
					break;
					
				case WIZZ:
					System.out.println("Send a wizz to a contact or a group. Cannot be broadcasted.");
					System.out.println("Use like this: :wizz someone.");
					break;
					
				case REFRESH:
					System.out.println("Reload the address book file.");
					break;
					
				case HELP:
					System.out.println("Show help for the program or a command.");
					System.out.println("Use like this: :help who.");
					break;
				
				// a modifier
				case ADD:
					System.out.println("Add a contact or a group in your address book.");
					System.out.println("You have to provide a name, and may provide address and port.");
					System.out.println("Use like this: :add buddy 100.200.0.1 1100.");
					break;
					
				case DELETE:
					System.out.println("Delete a contact or group from your address book.");
					System.out.println("Use like this: :delete badguy.");
					System.out.println("Equivalents: delete, del.");
					break;
				
				case LIST:
					System.out.println("List contacts in a group, or properties of a contact.");
					break;
					
				// a modifier
				case MODIFY:
					System.out.println("Modify name, address and/or port of a contact, or name of a group.");
					System.out.println("Equivalents: modify, mod.");
					break;
				
				case MESSAGE:
				default:
					System.err.println("Error: help: unknow command \""+cmd.target+"\"");
					break;
			}
		}
	}
}
