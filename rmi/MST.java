public class MST
{
	public static MSTServer local_server;
	public static MSTClient local_client;
	
	public static void main(String[] args)
	{
		int user_port = -1;
		
		// vérification arguments
		if (args.length == 0)
		{
			System.err.println("usage: java MST <PSEUDO> [PORT]");
			System.exit(-1);
		}
		else
		{
			if (args[0].compareTo("--help") == 0 ||
				args[0].compareTo("-h") == 0)
			{
				Help();
				System.exit(0);
			}
			// vérification nom fourni (pas de , / ou :)
			else if ( !AddressBook.ValidName(args[0]) )
			{
				System.err.println("Error: your name must not contain any of these character : , /");
				System.exit(-1);
			}
			
			// vérification port fourni (que des chiffres)
			if (args.length == 2)
			{
				if ( !AddressBook.ValidPort(args[1]) )
				{
					System.err.println("Error: port has to be an integer");
					System.exit(-1);
				}
				
				user_port = Integer.parseInt(args[1]);
			}
		}
		
		// instanciation des données de l'appli (+ lecture carnet d'adresses)
		AppData app = AppData.getInstance();
		if (user_port != -1)
			app.me = new Contact(args[0], "localhost", user_port);
		else	
			app.me = new Contact(args[0], "localhost");
				
		// simple affichage des contacts et des groupes
		/*
		RootGroup all = app.contacts;
		
		int cs = all.ContactSize();
		int gs = all.GroupSize();
		
		System.out.println("");
		
		System.out.println(cs+" contacts:");
		for (int i=0; i<cs; i++)
		{
			System.out.println("\t"+all.GetContact(i).name+"\t("+all.GetContact(i).ipAddress+")");
		}
		
		System.out.println(gs+" groups:");
		for (int i=0; i<gs; i++)
		{
			System.out.println("\t"+all.GetGroup(i).name);
		}
		
		System.out.println("");
		*/
		
		/** test répercussions des modifications de groupes */
		/****************************************************/
		/*
			Group isaac = all.GetGroup("Isaac");
			Contact jerome = isaac.GetContact("Jerome");
			Group jacob = all.GetGroup("Jacob");
			Group jacobisaac = jacob.GetGroup("Isaac");
			
			// contenu avant suppression
			System.out.println("");
			System.out.println("Contenu Isaac avant suppression:");
			for (int i=0; i<isaac.ContactSize(); i++)
				System.out.println("\t"+isaac.GetContact(i).name);
				
			System.out.println("Contenu Jacob.Isaac avant suppression:");
			for (int i=0; i<jacobisaac.ContactSize(); i++)
				System.out.println("\t"+jacobisaac.GetContact(i).name);
				
			// suppression d'un contact de Isaac (Jerome)
			System.out.println("Suppression de Jerome dans Isaac");
			isaac.Del(jerome);
			
			// suppression d'un contact racine (Benoit)
			System.out.println("Suppression de Benoit");
			all.Del(all.GetContact("Benoit"));
			
			// contenu après suppression
			System.out.println("");
			System.out.println("Contenu Isaac après suppression:");
			for (int i=0; i<isaac.ContactSize(); i++)
				System.out.println("\t"+isaac.GetContact(i).name);
				
			System.out.println("Contenu Jacob.Isaac après suppression:");
			for (int i=0; i<jacobisaac.ContactSize(); i++)
				System.out.println("\t"+jacobisaac.GetContact(i).name);
			
			System.out.println("");
		*/
		/** fin test répercussion ***************************/
		/****************************************************/
		
		// lancement des threads client et server
		int sleep_msec = 1000;
		
		local_client = new MSTClient(app);
		local_server = new MSTServer(app, local_client);
		
		local_server.start();
		System.out.println("Local server will be ready in "+sleep_msec/1000+"second(s)...");
		
		try
		{
			Thread.sleep(sleep_msec);
		}
		catch (InterruptedException ie)
		{
			Thread.currentThread().interrupt();
		}
		
		System.out.println("Starting local client");
		local_client.start();
		
		try
		{
			local_client.join();
		}
		catch (InterruptedException ie) { }
		
		System.exit(0);
	}
	
	public static void Help()
	{
		System.out.println("SYNOPSIS\n\tjava MST <PSEUDO> [PORT]");
		System.out.println("\nDESCRIPTION\n\tMST (Message Super Top) est un programme de messagerie instantanée, ou Chat.");
		System.out.println("\tIl fonctionne en P2P, en utilisant le protocole RMI.");
		System.out.println("\tIl permet de discuter avec un ou plusieurs contacts (groupes) en même temps,");
		System.out.println("\tet également de \"diffuser\" des messages (envoi à tous les contacts, qui eux-mêmes");
		System.out.println("\trenvoient à tous leurs contacts, etc...)");
		System.out.println("\tTous les contacts sont lus et sauvegardés dans le fichier addressbook du dossier appdata.");
		System.out.println("\nCOMMANDES\n\tToute commande doit être précédée de ':'");
		System.out.println("\t.-----------------------------------------------.");
		System.out.println("\t|      ACTION        |      COMMANDE(S)         |");
		System.out.println("\t|--------------------|--------------------------|");
		System.out.println("\t|       Aide         | help [CMD]               |");
		System.out.println("\t|      Quitter       | bye,end,stop,quit,q,exit |");
		System.out.println("\t|   Joindre contact  | NOM_CONTACT MSG          |");
		System.out.println("\t|  Chercher contact  | search,seek,who [NOM]    |");
		System.out.println("\t|  Ajouter contact   | add NOM [ADR] [PORT]     |");
		System.out.println("\t| Supprimer contact  | delete,del [NOM/ADR]     |");
		System.out.println("\t|  Modifier contact  | modify,mod [CTT/GRP]     |");
		System.out.println("\t|   Lister contacts  | list [GRP]               |");
		System.out.println("\t|       Wizz         | wizz [CTT/GRP]           |");
		System.out.println("\t|  Diffuser message  | broadcast,bc MSG         |");
		System.out.println("\t| Recharger adresses | refresh                  |");
		System.out.println("\t°-----------------------------------------------°");
		System.out.println("\n\tUn espace (et un seul) peut suivre les ':'.");
		System.out.println("\tSi vous entrez 2 espaces ou plus, il s'agira alors d'un message");
		System.out.println("\tà destination du contact/groupe courant. Si vous n'avez joint");
		System.out.println("\taucun contact jusqu'alors, rien ne se produira.");
		System.out.println("\nCARNET D'ADRESSES\n\tLe carnet d'adresses (fichier addressbook dans appdata)");
		System.out.println("\tpeut être modifié manuellement pendant l'exécution du programme.");
		System.out.println("\tVous devrez alors utiliser la commande 'refresh' pour recharger");
		System.out.println("\tles modifications apportées. La syntaxe du carnet d'adresses suit");
		System.out.println("\tquelques règles: un nom de groupe ou de contact ne doit pas contenir");
		System.out.println("\tde '/', ',' ou ':', et ne peux commencer par un espace ni en contenir.");
		System.out.println("\tL'application utilise des mots-clé réservé pour les commandes, mots-clé");
		System.out.println("\tque vous ne pourrez utiliser pour les noms de contacts/groupes.");
		System.out.println("\tIl s'agit en outre des commandes citées précédemment, ainsi que des mots");
		System.out.println("\t'all' et 'me', qui désignent respectivement tous vos contacts et vous-même.");
		System.out.println("\tVous pouvez utiliser sauter des lignes et des commentaires en commençant");
		System.out.println("\tune ligne avec un slash (/).");
	}
}
