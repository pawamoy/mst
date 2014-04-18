import java.util.ArrayList;

public class RootGroup
{
	private static RootGroup instance = null;
	
	public ArrayList<Group> groups;
	public ArrayList<Contact> contacts;
	
	protected RootGroup()
	{
		contacts = new ArrayList<Contact>();
		groups = new ArrayList<Group>();
	}
	
	public static RootGroup getInstance()
	{
		if (instance == null)
		{
			instance = new RootGroup();
		}
		return instance;
	}
	
	public void Add(Contact c)
	{
		contacts.add(c);
	}
	
	public void Add(Group g)
	{
		groups.add(g);
	}
	
	// suppression du contact
	// et suppression de sa référence dans tous les sous-groupes
	// pas besoin de récursivité: les suppressions se reportent
	public void Del(Contact c)
	{
		contacts.remove(c);
		
		for (int i=0; i<GroupSize(); i++)
			if (GetGroup(i).Contains(c))
				GetGroup(i).Del(c);
	}
	
	// suppression du groupe
	// et suppression de sa référence dans tous les sous-groupes
	// pas besoin de récursivité: les suppressions se reportent
	public void Del(Group g)
	{
		groups.remove(g);
		
		for (int i=0; i<GroupSize(); i++)
			if (GetGroup(i).Contains(g))
				GetGroup(i).Del(g);
	}
	
	public Group GetGroup(int index)
	{
		return groups.get(index);
	}
	
	public Group GetGroup(String name)
	{
		Group g;
		
		for (int i=0; i<GroupSize(); i++)
		{
			g = GetGroup(i);
			
			if (g.HasName(name))
				return g;
		}
		
		return null;
	}
	
	public Contact GetContact(int index)
	{
		return contacts.get(index);
	}
	
	public Contact GetContact(String s)
	{
		Contact c;
		
		for (int i=0; i<ContactSize(); i++)
		{
			c = GetContact(i);
			
			if (c.HasName(s) || c.HasAddress(s))
				return c;
		}
		
		return null;
	}
	
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
}
