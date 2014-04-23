/*
 * MSTguiApp.java
 */

package mstgui;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class MSTguiApp extends SingleFrameApplication {

    public static MSTServer local_server;
    public static MSTClient local_client;    

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new MSTguiView(this));
    }
    
    protected static void Loop(String[] args)
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
            local_server = new MSTServer(local_client);

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

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
        
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MSTguiApp
     */
    public static MSTguiApp getApplication() {
        return Application.getInstance(MSTguiApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MSTguiApp.class, args);
        //MSTguiApp.Loop(args);
    }
}
