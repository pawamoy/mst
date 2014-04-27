import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.ArrayList;

public class MSTClient extends Thread
{	
	public static AppData app;
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
		// rien à mettre ici
	}
	
    public static CommInterface GetCommInterface(String name, String host, int port)
    {
		CommInterface comm = null;
		
        try
        {
			comm = (CommInterface)Naming.lookup("rmi://"+host+":"+port+"/my,own,comm");
		}
		catch (MalformedURLException mue)
		{
            app.mf.Print("Error: rmi: unable to join "+name, "error");
		}
		catch (NotBoundException nbe)
		{
            app.mf.Print("Error: rmi: unable to join "+name, "error");
		}
		catch (RemoteException re)
		{
            app.mf.Print("Error: rmi: unable to join "+name, "error");
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
				Search(cmd);
				break;
				
			case WIZZ:
				Wizz(cmd);
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
				Delete(cmd);
				break;
				
			case MODIFY:
				//~ Modify(cmd);
				break;
                
            case NICKNAME:
                app.me.name = cmd.target.get(0);
                app.mf.Print("You are now known as " + app.me.name, "info");
                break;
				
			case LIST:
				List(cmd);
				break;
				
			default:
                app.mf.Print("Error: client: wrong command type (???)", "error");
				break;
		}
	}
    
    public void Wizz(Command cmd)
    {
        if (AddressBook.MatchKeyword(cmd.target.get(0)))
		{
			app.mf.Print("Error: cannot wizz with app reserved keyword", "error");
		}
        else
        {
            Contact ctt = app.contacts.GetContact(cmd.target.get(0));
                
            if (ctt != null)
            {
                if (SendWizz(ctt) == true)
                    app.mf.Print("You wizzed "+cmd.target.get(0)+"!", "info");
            }
            else
            {
                app.mf.Print("Error: client: wizz: unknown contact \""+cmd.target.get(0)+"\"", "error");
            }
        }
    }
    
    public void Delete(Command cmd)
    {
        if (AddressBook.MatchKeyword(cmd.target.get(0)))
		{
			app.mf.Print("Error: cannot delete with app reserved keyword", "error");
		}
        else
        {
            Contact ctt = app.contacts.GetContact(cmd.target.get(0));
                
            if (ctt != null)
            {
                app.mf.Print(ctt.name + "deleted", "info");
                app.contacts.Del(ctt);
            }
            else
            {
                Group grp = app.contacts.GetGroup(cmd.target.get(0));
                
                if (grp != null)
                {
                    app.mf.Print("-- Group "+grp.name+ " deleted", "info");
                    app.contacts.Del(grp);
                }
                else
                {
                    app.mf.Print("Error: client: unknown reference \""+cmd.target.get(0)+"\"", "error");
                }
            }
        }
    }
	
	public void Message(Command cmd)
	{
		String atWho = "";
		int ntarg = cmd.NbTarget();
		String targ;
		boolean err = false;
		boolean send_ok = true;
		boolean send_atleast1 = false;
		
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
					send_ok = SendMessage(app.cur_con.get(i), cmd.args);
					send_atleast1 |= send_ok;
					
					if (app.toAll == false && send_ok == true)
						atWho = atWho.concat("@"+app.cur_con.get(i).name+" ");
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
						send_atleast1 |= SendMessage(group_ctt.get(i), cmd.args);
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
						send_atleast1 |= SendMessage(app.contacts.GetContact(i), cmd.args);
						app.toAll = true;
						app.cur_con.add(app.contacts.GetContact(i));
					}
				}
				else if (targ.compareTo("me") == 0)
				{
					atWho = atWho.concat("@me ");
					send_ok = SendMessage(app.me, cmd.args);
					send_atleast1 |= send_ok;
					app.cur_con.add(app.me);
				}
				else
				{
					Contact ctt = app.contacts.GetContact(targ);
					
					if (ctt != null)
					{
						send_ok = SendMessage(ctt, cmd.args);
						send_atleast1 |= send_ok;
						if (send_ok == true)
							atWho = atWho.concat("@"+ctt.name+" ");
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
								send_atleast1 |= SendMessage(group_ctt.get(i), cmd.args);
						
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
		
		if (err == false && send_atleast1 == true)
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
			return GetCommInterface(ctt.name, ctt.ipAddress, ctt.port);
		}
	}
	
    public boolean SendWizz(Contact ctt)
    {
		if (ctt.comm == null)
		{
			ctt.comm = GetComm(ctt);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				ctt.comm.Wizz(app.me.port);
				return true;
			}
			catch (RemoteException re)
			{
				app.mf.Print("Error: rmi: unable to wizz "+ctt.name, "error");
				return false;
			}
		}
		return false;
    }
    
	public boolean SendMessage(Contact ctt, String msg)
	{
		if (ctt.comm == null)
		{
			ctt.comm = GetComm(ctt);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				ctt.comm.Message(msg, app.me.port);
				return true;
			}
			catch (RemoteException re)
			{
				app.mf.Print("Error: rmi: unable to join "+ctt.name, "error");
				return false;
			}
		}
		return false;
	}
	
	public void ExitClient()
	{
        AddressBook.WriteContacts("../appdata/addressbook", app.contacts);
        System.exit(0);
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
		app.mf.Print("", "help");
		Contact ctt;
		Group grp;
	
		int ntarg = cmd.target.size();
		
		if (ntarg == 0)
		{			
			app.mf.Print("-- All Contacts & Groups --", "help");
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
					{
						app.mf.Print("-- Group "+grp.name, "help");
						ListGroup(grp);
					}
					else
					{
						app.mf.Print("Error: client: unknown reference \""+cmd.target.get(0)+"\"", "error");
					}
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
			app.mf.Print("** Contacts **", "help");
			
			for (int i=0; i<cs; i++)
				PrintContact(grp.GetContact(i));
		}
		
		if (gs > 0)
		{
			app.mf.Print("** Groups **", "help");

			for (int i=0; i<gs; i++)
				PrintGroup(grp.GetGroup(i));
		}
	}
	
	public void PrintContact(Contact ctt)
	{
		app.mf.Print(String.format("%-20s", ctt.name)+ctt.ipAddress+":"+ctt.port, "help");
	}
	
	public void PrintGroup(Group grp)
	{
		app.mf.Print(String.format("%-20s", grp.name)+"("+grp.TotalSize()+" contacts)", "help");
	}
	
	public void Help(Command cmd)
	{
		app.mf.Print("", "help");
		
		int size = cmd.target.size();
		
		if (size == 0)
		{
			app.mf.Print("Available commands are:", "help");
			app.mf.Print(":help [CMD]               - print this help or detailed command help", "help");
			app.mf.Print(":bye,end,stop,quit,q,exit - quit the program", "help");
			app.mf.Print(":NOM_CONTACT MSG          - specify which contact to join", "help");
			app.mf.Print(":search,seek,who [NOM]    - search someone in friend's contacts", "help");
			app.mf.Print(":add NOM [ADR] [PORT]     - add a contact in your book", "help");
			app.mf.Print(":delete,del [NOM/ADR]     - delete a contact from your book", "help");
			app.mf.Print(":modify,mod [CTT/GRP]     - modify a contact in your book", "help");
			app.mf.Print(":list [GRP]               - list a group or properties of a contact", "help");
			app.mf.Print(":wizz [CTT/GRP]           - send a wizz to someone", "help");
			app.mf.Print(":broadcast,bc MSG         - broadcast a message", "help");
			app.mf.Print(":refresh                  - reload address book", "help");
		}
		else
		{
			switch (Interpreter.SwitchType(cmd.target.get(0)))
			{					
				case EXIT:
					app.mf.Print("Save the address book and quit the program.", "help");
					app.mf.Print("Equivalents: exit, quit, q, end, stop, bye.", "help");
					break;
					
				case BROADCAST:
					app.mf.Print("Broadcast a message through your contacts and their contacts.", "help");
					app.mf.Print("Use like this: :broadcast hi everyone !", "help");
					app.mf.Print("Equivalents: broadcast, bc.", "help");
					break;
					
				case SEARCH:
					app.mf.Print("Search someone in your contacts' lists.", "help");
					app.mf.Print("Use like this: :search someone hey you", "help");
					app.mf.Print("If found, added to your book.", "help");
					app.mf.Print("If message provided, sended to him/her.", "help");
					app.mf.Print("Equivalents: search, seek, who.", "help");
					break;
					
				case WIZZ:
					app.mf.Print("Send a wizz to a contact or a group. Cannot be broadcasted.", "help");
					app.mf.Print("Use like this: :wizz someone.", "help");
					break;
					
				case REFRESH:
					app.mf.Print("Reload the address book file.", "help");
					break;
					
				case HELP:
					app.mf.Print("Show help for the program or a command.", "help");
					app.mf.Print("Use like this: :help who.", "help");
					break;
				
				// a modifier
				case ADD:
					app.mf.Print("Add a contact or a group in your address book.", "help");
					app.mf.Print("You have to provide a name, and may provide address and port.", "help");
					app.mf.Print("Use like this: :add buddy 100.200.0.1 1100.", "help");
					break;
					
				case DELETE:
					app.mf.Print("Delete a contact or group from your address book.", "help");
					app.mf.Print("Use like this: :delete badguy.", "help");
					app.mf.Print("Equivalents: delete, del.", "help");
					break;
				
				case LIST:
					app.mf.Print("List contacts in a group, or properties of a contact.", "help");
					break;
					
				// a modifier
				case MODIFY:
					app.mf.Print("Modify name, address and/or port of a contact, or name of a group.", "help");
					app.mf.Print("Equivalents: modify, mod.", "help");
					break;
				
				case MESSAGE:
				default:
					app.mf.Print("Error: help: unknown command \""+cmd.target.get(0)+"\"", "error");
					break;
			}
		}
	}
	
	public String WhoSentIt(int port)
	{
		Contact c = app.contacts.GetContactByPort(port);
		if (c != null) return c.name;
		else return "";
	}
	
	public void Search(Command cmd)
	{	
		Contact ctt;
		Group grp;
		int new_id = -1;
		
		if (AddressBook.MatchKeyword(cmd.target.get(0)))
		{
			app.mf.Print("Error: cannot search for app reserved keyword", "error");
		}
		else
		{
			ctt = app.contacts.GetContact(cmd.target.get(0));
				
			if (ctt != null)
			{
				app.mf.Print("This contact already exists in your list", "info");
			}
			else
			{
				grp = app.contacts.GetGroup(cmd.target.get(0));
				
				if (grp != null)
				{
					app.mf.Print("You already have a group using this name, please change it before retry", "info");
				}
				else
				{
					app.mf.Print("Searching contact \""+cmd.target.get(0)+"\"...", "info");
					
					for (int i=0; i<app.contacts.ContactSize(); i++)
					{
						new_id = SendSearch(app.contacts.GetContact(i), cmd.target.get(0));
						if (new_id != -1)
						{
							app.mf.Print("Contact found !", "info");
							Contact new_contact = new Contact(cmd.target.get(0), "localhost", new_id);
							app.contacts.Add(new_contact);
							if (SendMessage(new_contact, cmd.args) == true)
								app.mf.Print("@"+new_contact.name+": "+cmd.args, "sent_message");
							break;
						}
					}
					
					if (new_id == -1)
						app.mf.Print("Contact not found...", "info");
				}
			}
		}
	}
	
	public int SendSearch(Contact ctt, String search)
	{
		int result;
		
		if (ctt.comm == null)
		{
			ctt.comm = GetComm(ctt);
		}
		
		if (ctt.comm != null)
		{
			try
			{
				result = ctt.comm.Search(search);
				return result;
			}
			catch (RemoteException re)
			{
				app.mf.Print("Error: rmi: unable to join "+ctt.name, "error");
				return -1;
			}
		}
		return -1;
	}
}
