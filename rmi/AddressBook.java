import java.io.*;
import java.lang.String;
import java.util.ArrayList;

public abstract class AddressBook
{
	public static RootGroup ReadContacts(String filename)
	{
		RootGroup all_contacts = RootGroup.getInstance();
		
		Contact ct = null;
		Group gt = null;
		
		ArrayList<String> groups_id = new ArrayList<String>();
		ArrayList<String> contacts_id = new ArrayList<String>();
		
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		try
		{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line, field[], id[];
			
			int d, count = 0;

			while ((line = br.readLine()) != null)
			{
				count++;
					
				line = line.replaceAll(" ", "");
				if (line.startsWith("/")) continue;
				if (line.isEmpty()) continue;
				field = line.split(":");
				id = field[1].split(",");
				
				if ( !ValidName(field[0]) )
				{
					System.err.println("Error: addressbook: line "+count+": name \""+field[0]+"\" is unvalid");
				}
				else if (groups_id.contains(field[0]))
				{
					System.err.println("Error: addressbook: line "+count+": \""+field[0]+"\" already used");
				}
				else if (contacts_id.contains(field[0]))
				{
					System.err.println("Error: addressbook: line "+count+": \""+field[0]+"\" already used");
				}
				else
				{					
					if ( ValidHost(id[0]) )
					{ // il s'agit d'une adresse, donc on définit un contact
						if (id.length > 1)
						{
							if (ValidPort(id[1]))
							{
								ct = new Contact(field[0], id[0], id[1]);
							}
							else
							{
								System.err.print("Error: addressbook: line "+count+": port \""+id[1]+"\" is unvalid. ");
								System.err.println("Default value will be used");
								
								ct = new Contact(field[0], id[0]);
							}
						}
						else
						{
							ct = new Contact(field[0], id[0]);
						}
						
						// on ajoute le nouveau contact aux listes temporaires
						contacts_id.add(field[0]);
						contacts.add(ct);
					}
					else
					{ // on n'a pas trouvé d'host valide, on définit sûrement un groupe
						gt = new Group(field[0]);
						
						for (int i=0; i<id.length; i++)
						{
							// on va chercher s'il s'agit d'un contact ou d'un groupe
							d = contacts_id.indexOf(id[i]);
							
							if (d >= 0)
							{ // il s'agit d'un contact, on l'ajoute dans le nouveau groupe
								gt.Add(contacts.get(d));
							}
							else
							{
								d = groups_id.indexOf(id[i]);
								if (d >= 0)
								{ // il s'agit d'un groupe, on l'ajoute dans le nouveau groupe
									gt.Add(groups.get(d));
								}
								else
								{ // ni contact ni groupe -> erreur
									System.err.println("Error: addressbook: line "+count+": unknown reference \""+id[i]+"\"");
								}
							}
						}
						
						// on ajoute le nouveau groupe aux listes temporaires (s'il n'est pas vide)
						if (gt.ContactSize() > 0 || gt.GroupSize() > 0)
						{
							groups_id.add(field[0]);
							groups.add(gt);
						}
					}
				}
			}
			
			// on ajoute maintenant tous les contacts et groupes créés dans le groupe à renvoyer
			for (int i=0; i<contacts.size(); i++)
				all_contacts.Add(contacts.get(i));
			for (int i=0; i<groups.size(); i++)
				all_contacts.Add(groups.get(i));
			
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: addressbook: unable to read "+filename+" file");
		}
		
		return all_contacts;
	}
	
	public static boolean ValidName(String n)
	{
		if (n.startsWith(" ") || IllegalCharacter(n) || MatchKeyword(n))
			return false;
		else
			return true;
	}
	
	public static boolean ValidHost(String h)
	{
		if (h.matches("[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]*") ||
			h.compareTo("?") == 0 ||
			h.compareTo("localhost") == 0)
			return true;
		else
			return false;
	}
	
	public static boolean ValidPort(String p)
	{
		return p.matches("[0-9]*");
	}
	
	public static boolean IllegalCharacter(String s)
	{
		return (
			s.contains(",") ||
			ContainsDPOrSlash(s)
		);
	}
	
	public static boolean ContainsDPOrSlash(String s)
	{
		return (
			s.contains(":") ||
			s.contains("/")
		);
	}
	
	public static boolean MatchKeyword(String s)
	{
		return (
			s.compareTo("bye")			== 0 ||
			s.compareTo("end")			== 0 ||
			s.compareTo("stop")			== 0 ||
			s.compareTo("quit")			== 0 ||
			s.compareTo("q")			== 0 ||
			s.compareTo("exit")			== 0 ||
			s.compareTo("broadcast")	== 0 ||
			s.compareTo("bc")			== 0 ||
			s.compareTo("all")			== 0 ||
			s.compareTo("search")		== 0 ||
			s.compareTo("seek")			== 0 ||
			s.compareTo("who")			== 0 ||
			s.compareTo("wizz")			== 0 ||
			s.compareTo("help")			== 0 ||
			s.compareTo("add")			== 0 ||
			s.compareTo("del")			== 0 ||
			s.compareTo("delete")		== 0 ||
			s.compareTo("list")			== 0 ||
			s.compareTo("mod")			== 0 ||
			s.compareTo("modify")		== 0 ||
			s.compareTo("nick")		    == 0 ||
			s.compareTo("nickname")		== 0 ||
			s.compareTo("me")			== 0
		);
	}
	
	public static void WriteContacts(String filename, RootGroup rg)
	{
		Contact c, gc;
		Group g, gg;
		int i, j, sc, sg;
		
		try
		{
			FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

			bw.write("/** Contacts **/");
			bw.newLine();
			bw.newLine();
			
			for (i=0; i<rg.ContactSize(); i++)
			{
				c = rg.GetContact(i);
				
				bw.write(c.name+":"+c.ipAddress+","+c.port);
				bw.newLine();
			}
			
			bw.newLine();
			bw.write("/** Groups **/");
			bw.newLine();
			bw.newLine();
			
			for (i=0; i<rg.GroupSize(); i++)
			{
				g = rg.GetGroup(i);
				bw.write(g.name+":");
				
				sc = g.ContactSize();
				if (sc > 0)
				{
					for (j=0; j<sc-1; j++)
					{
						gc = g.GetContact(j);
						bw.write(gc.name+",");
					}
					gc = g.GetContact(j);
					bw.write(gc.name);
				}
				
				sg = g.GroupSize();
				if (sg > 0)
				{
					if (sc > 0)
						bw.write(",");
						
					for (j=0; j<sg-1; j++)
					{
						gg = g.GetGroup(j);
						bw.write(gg.name+",");
					}
					gg = g.GetGroup(j);
					bw.write(gg.name);
				}
				
				bw.newLine();
			}
			
            bw.flush();
		}
		catch (Exception e)
		{
			System.err.println("Error: addressbook: " + e.getMessage());
		}
	}
}
