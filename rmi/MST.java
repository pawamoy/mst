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
		
		// lancement des threads client et server
		int sleep_msec = 1000;
		
		local_server = new MSTServer(args[0]);
		local_client = new MSTClient(args[0]);
		
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
