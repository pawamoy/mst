import java.util.ArrayList;

public class AppData
{
	private static AppData instance = null;
	public static RootGroup contacts = null;
	public static ArrayList<Contact> cur_con = null;
	public static ArrayList<Group> cur_grp = null;
	public static boolean toAll;
	public static Contact me = null;
	public static MSTMainFrame mf;
	
	protected AppData()
	{
		toAll = false;
		cur_con = new ArrayList<Contact>();
		cur_grp = new ArrayList<Group>();
        contacts = AddressBook.ReadContacts("../appdata/addressbook");
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

