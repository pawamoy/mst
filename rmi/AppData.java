public class AppData
{
	private static AppData instance = null;
	public static RootGroup contacts = null;
	public static Contact current = null;
	public static Contact me = null;
	
	protected AppData()
	{
        contacts = AddressBook.ReadContacts("appdata/addressbook");
	}
    
	public static AppData getInstance()
	{
		if (instance == null)
		{
			instance = new AppData();
		}
		return instance;
	}
}

