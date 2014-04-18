public class Command
{
	public CommandType type;
	public String target;
	public String args;
	
	private CommandType default_type = CommandType.MESSAGE;
	
	public Command()
	{
		type = default_type;
		target = "";
		args = "";
	}
	
	public Command(CommandType t)
	{
		type = t;
		target = "";
		args = "";
	}
	
	public Command(String target)
	{
		type = default_type;
		this.target = target;
		args = "";
	}
	
	public Command(String target, String args)
	{
		type = default_type;
		this.target = target;
		this.args = args;
	}
	
	public Command(CommandType t, String target)
	{
		type = t;
		this.target = target;
		args = "";
	}
	
	public Command(CommandType t, String target, String args)
	{
		type = t;
		this.target = target;
		this.args = args;
	}
}
