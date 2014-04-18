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
		
		// instanciation des données de l'appli (+ lecture carnet d'adresses)
		AppData app = AppData.getInstance();
		app.me = new Contact(args[0], "localhost"/*, on peut ajouter le port ici */);
				
		// simple affichage des contacts et des groupes
		//~ RootGroup all = app.contacts;
		//~ 
		//~ int cs = all.ContactSize();
		//~ int gs = all.GroupSize();
		//~ 
		//~ System.out.println("");
		//~ 
		//~ System.out.println(cs+" contacts:");
		//~ for (int i=0; i<cs; i++)
		//~ {
			//~ System.out.println("\t"+all.GetContact(i).name+"\t("+all.GetContact(i).ipAddress+")");
		//~ }
		//~ 
		//~ System.out.println(gs+" groups:");
		//~ for (int i=0; i<gs; i++)
		//~ {
			//~ System.out.println("\t"+all.GetGroup(i).name);
		//~ }
		//~ 
		//~ System.out.println("");
		
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
		
		local_server = new MSTServer(app);
		local_client = new MSTClient(app);
		
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
