public class Contact
{
	public String name;
	public String ipAddress;
	
	public Contact(String name)
	{
		this.name = name;
		ipAddress = "";
	}
	
	public Contact(String name, String address)
	{
		this.name = name;
		ipAddress = address;
	}
	
	public boolean HasName(String n)
	{
		return (name.compareToIgnoreCase(n) == 0);
	}
	
	public boolean HasAddress(String a)
	{
		return (ipAddress.compareToIgnoreCase(a) == 0);
	}
}
