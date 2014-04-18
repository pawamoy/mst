public class Contact
{
	public String name;
	public String ipAddress;
	public int port;
	
	private int default_port = 1099;
	
	public Contact(String name)
	{
		this.name = name;
		ipAddress = "";
		port = default_port;
	}
	
	public Contact(String name, String address)
	{
		this.name = name;
		ipAddress = address;
		port = default_port;
	}
	
	public Contact(String name, String address, int port)
	{
		this.name = name;
		ipAddress = address;
		this.port = port;
	}
	
	public Contact(String name, String address, String port)
	{
		this.name = name;
		ipAddress = address;
		this.port = Integer.parseInt(port);
	}
	
	public boolean HasName(String n)
	{
		return (name.compareToIgnoreCase(n) == 0);
	}
	
	public boolean HasAddress(String a)
	{
		return (ipAddress.compareToIgnoreCase(a) == 0);
	}
	
	public boolean HasPort(int p)
	{
		return (port == p);
	}
}
