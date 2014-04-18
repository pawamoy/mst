public class MST
{
	public static MSTServer local_server;
	public static MSTClient local_client;
	
	public static void main(String[] args)
	{
		// vérification arguments
		if (args.length < 1)
		{
			System.err.println("Error: you must specify your name");
			System.exit(-1);
		}
		
		// vérification nom fourni (pas de , / ou :)
		if ( !AddressBook.ValidName(args[0]) )
		{
			System.err.println("Error: your id must not contains any of these: ':,/'");
			System.exit(-1);
		}
		
		// lecture du carnet d'adresses
		Group all = AddressBook.ReadContacts("appdata/addressbook");
		// ajout de soi-même ?
		all.Add(new Contact(args[0], "//localhost"));
		
		// simple affichage des contacts et des groupes
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
			
			// contenu après suppression
			System.out.println("");
			System.out.println("Contenu Isaac après suppression:");
			for (int i=0; i<isaac.ContactSize(); i++)
				System.out.println("\t"+isaac.GetContact(i).name);
				
			System.out.println("Contenu Jacob.Isaac après suppression:");
			for (int i=0; i<jacobisaac.ContactSize(); i++)
				System.out.println("\t"+jacobisaac.GetContact(i).name);
		*/
		/** fin test répercussion ***************************/
		/****************************************************/
		
		// lancement des threads client et server
		int sleep_msec = 1000;
		
		local_server = new MSTServer();
		local_client = new MSTClient();
		
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
		
		// trouver un moyen d'arrêter le server lorsque le client s'arrête
		// (d'ailleurs le client ne s'arrête pas en sortant de la boucle, wtf...)
		//~ try
		//~ {
			//~ local_client.join();
			//~ local_server.join(1000);
		//~ }
		//~ catch (InterruptedException ie)
		//~ {
			//~ Thread.currentThread().interrupt();
		//~ }
	}
}
