import java.io.*;
import java.lang.String;
import java.util.ArrayList;

public abstract class AddressBook
{
	public static Group ReadContacts(String filename)
	{
		Group all_contacts = new Group("all");
		
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
				line = line.replaceAll(" ", "");
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
						Contact ct = new Contact(field[0], id[0]);
						
						// si on permet plusieurs "host" pour un contact
						// ajouter une méthode AddHost pour Contact
						/*
						for (int i=1; i<id.length; i++)
						{
							if ( ValidHost(id[i]) )
							{
								ct.AxddHost(id[i]);
							}
							else
							{
								System.err.println("Error: addressbook: line "+count+": \""+id[i]+"\" host is unvalid");
							}
						}
						*/
						
						// on ajoute le nouveau contact aux listes temporaires
						contacts_id.add(field[0]);
						contacts.add(ct);
					}
					else
					{ // on n'a pas trouvé d'host valide, on définit sûrement un groupe
						Group gt = new Group(field[0]);
						
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
									System.err.println("Error: addressbook: line "+count+": unknown reference \""+id[0]+"\"");
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
				
				count++;
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
			System.err.println("Error: " + e.getMessage());
		}
		
		return all_contacts;
	}
	
	public static boolean ValidName(String n)
	{
		if (n.contains(":") ||
			n.contains(",") ||
			n.contains("/"))
			return false;
		else
			return true;
	}
	
	public static boolean ValidHost(String h)
	{
		if (h.matches("[0-9]*.[0-9]*.[0-9]*.[0-9]*.") ||
			h.compareTo("?") == 0 ||
			h.matches("0x[0-9]*") ||
			h.matches("[0-9]*"))
			return true;
		else
			return false;
	}
}
