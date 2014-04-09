public class Command
{
	public CommandType type;
	public Contact contact;
	public Group group;
	public String args;
	
	private CommandType default_type = CommandType.MESSAGE;
	
	public Command()
	{
		type = default_type;
		args = "";
		contact = null;
		group = null;
	}
	
	public Command(CommandType t)
	{
		type = t;
		args = "";
		contact = null;
		group = null;
	}
	
	public Command(String args)
	{
		type = default_type;
		this.args = args;
		contact = null;
		group = null;
	}
	
	public Command(Contact contact)
	{
		type = default_type;
		args = "";
		group = null;
		this.contact = contact;
	}
	
	public Command(Group group)
	{
		type = default_type;
		args = "";
		contact = null;
		this.group = group;
	}
	
	public Command(CommandType t, String args)
	{
		type = t;
		this.args = args;
		contact = null;
		group = null;
	}
	
	public Command(CommandType t, Contact contact)
	{
		type = t;
		args = "";
		this.contact = contact;
	}
	
	public Command(Contact contact, String args)
	{
		type = default_type;
		this.args = args;
		this.contact = contact;
	}
	
	public Command(CommandType t, Contact contact, String args)
	{
		type = t;
		this.args = args;
		this.contact = contact;
	}
}
