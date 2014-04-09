public enum CommandType
{
	MESSAGE,
	EXIT,
	BROADCAST,
	SEARCH,
	WIZZ,
	REFRESH_CONFIG,
	HELP,
	CONTACT
}

public class Command
{
	public CommandType type;
	public Contact target;
	public String args;
	
	private CommandType default_type = CommandType.MESSAGE;
	
	public Command()
	{
		type = default_type;
		args = "";
		target = null;
	}
	
	public Command(CommandType t)
	{
		type = t;
		args = "";
		target = null;
	}
	
	public Command(String args)
	{
		type = default_type;
		this.args = args;
		target = null;
	}
	
	public Command(Contact target)
	{
		type = default_type;
		args = "";
		this.target = target;
	}
	
	public Command(CommandType t, String args)
	{
		type = t;
		this.args = args;
		target = null;
	}
	
	public Command(CommandType t, Contact target)
	{
		type = t;
		args = "";
		this.target = target;
	}
	
	public Command(Contact target, String args)
	{
		type = default_type;
		this.args = args;
		this.target = target;
	}
	
	public Command(CommandType t, Contact target, String args)
	{
		type = t;
		this.args = args;
		this.target = target;
	}
}
