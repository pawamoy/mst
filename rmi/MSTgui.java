import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MSTgui 
{
	public static MSTServer local_server;
	public static MSTClient local_client;
    
    public static MSTMainFrame mainframe;
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() 
    {
        mainframe = new MSTMainFrame();
 
        //Display the window.
        mainframe.pack();
        mainframe.setVisible(true);
    }
    
    private static void InitMST(String[] args)
    {
        createAndShowGUI();
        
		int user_port = -1;
		
		// vérification arguments
		if (args.length == 0)
		{
			mainframe.Print("usage: java MST <PSEUDO> [PORT]", "error");
			System.exit(-1);
		}
		else
		{
			if (args[0].compareTo("--help") == 0 ||
				args[0].compareTo("-h") == 0)
			{
				//Help();
				System.exit(0);
			}
			// vérification nom fourni (pas de , / ou :)
			else if ( !AddressBook.ValidName(args[0]) )
			{
				mainframe.Print("Error: your name must not contain any of these character : , /", "error");
				System.exit(-1);
			}
			
			// vérification port fourni (que des chiffres)
			if (args.length == 2)
			{
				
				if ( !AddressBook.ValidPort(args[1]) )
				{
					mainframe.Print("Error: port has to be an integer", "error");
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
		local_server = new MSTServer(local_client);
		
		local_server.start();
        mainframe.Print("Local server will be ready in "+sleep_msec/1000+"second(s)...", "info");
		
		try
		{
			Thread.sleep(sleep_msec);
		}
		catch (InterruptedException ie)
		{
			Thread.currentThread().interrupt();
		}
		
        
		mainframe.Print("Starting local client", "info");
		local_client.start();
		
		try
		{
			local_client.join();
		}
		catch (InterruptedException ie) { }
    }
 
    public static void main(final String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //~ javax.swing.SwingUtilities.invokeLater(new Runnable() {
            //~ public void run() {
                //~ InitMST(args);
            //~ }
        //~ });
        
        InitMST(args);
    }
}
