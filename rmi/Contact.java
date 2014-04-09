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
}
