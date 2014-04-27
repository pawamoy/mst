import java.util.ArrayList;

public class Command
{
	public CommandType type;
	public ArrayList<String> target;
	public String args;
	
	private CommandType default_type = CommandType.MESSAGE;
	
	public Command()
	{
		type = default_type;
		target = new ArrayList<String>();
		args = "";
	}
	
	public Command(CommandType t)
	{
		type = t;
		target = new ArrayList<String>();
		args = "";
	}
	
	public Command(String target)
	{
		type = default_type;
		this.target = new ArrayList<String>();
		AddTarget(target);
		args = "";
	}
	
	public Command(String target, String args)
	{
		type = default_type;
		this.target = new ArrayList<String>();
		AddTarget(target);
		this.args = args;
	}
	
	public Command(CommandType t, String target)
	{
		type = t;
		this.target = new ArrayList<String>();
		AddTarget(target);
		args = "";
	}
	
	public Command(CommandType t, String target, String args)
	{
		type = t;
		this.target = new ArrayList<String>();
		AddTarget(target);
		this.args = args;
	}
	
	public void AddTarget(String t)
	{
		if (!target.contains(t))
			target.add(t);
	}
	
	public int NbTarget()
	{
		return target.size();
	}
	
	public String GetTarget(int i)
	{
		return target.get(i);
	}
}
