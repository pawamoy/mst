import java.util.List;
import java.util.ArrayList;

public class Group
{
	public ArrayList<Group> groups;
	public ArrayList<Contact> contacts;
	
	public Group()
	{
		contacts = new ArrayList<Contact>();
		groups = new ArrayList<Group>();
	}
	
	public void Add(Contact c)
	{
		contacts.add(c);
	}
	
	public void Add(Group g)
	{
		groups.add(g);
	}
	
	public void Del(Contact c)
	{
		contacts.remove(c);
	}
	
	public void Del(Group g)
	{
		groups.remove(g);
	}
	
	public Group GetGroup(int index)
	{
		groups.get(index);
	}
	
	/*public Group GetGroup(Group group)
	{
		int index = groups.indexOf(group);
		
		if (index != -1)
			groups.get(index);
		else
			return null;
	}
	*/
	
	public Contact GetContact(int index)
	{
		contacts.get(index);
	}
	
	/*public Contact GetContact(Contact contact)
	{
		int index = contacts.indexOf(contact);
		
		if (index != -1)
			contacts.get(index);
		else
			return null;
	}
	*/
	
	public boolean Contains(Contact c)
	{
		return contacts.contains(c);
	}
	
	public boolean Contains(Group g)
	{
		return groups.contains(g);
	}
	
	public int GroupSize()
	{
		return groups.size();
	}
	
	public int ContactSize()
	{
		return contacts.size();
	}
	
	public int TotalSize()
	{
		int total = ContactSize();
		
		for (int i=0; i<GroupSize(); i++)
		{
			total += GetGroup(i).TotalSize();
		}
		
		return total;
	}
