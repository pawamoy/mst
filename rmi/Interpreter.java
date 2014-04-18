public abstract class Interpreter
{
	public static Command StringToCommand(String s)
	{
		Command result;
		String field[];
		String args = "";
		// si la commande commence par ':'
		if (s.startsWith(":"))
		{
			// analyser le premier mot (séparateur = espace, le mot peut être collé aux ':')
			field = s.substring(1).split(" ");
			// comparer aux mots-clés de l'appli
			if (field[0].compareTo("search") == 0 ||
				field[0].compareTo("seek") == 0 ||
				field[0].compareTo("who") == 0)
			{	// search = seek = who
				// arg1 = nom à chercher
				// arg2...N = message si contact trouvé
				if (field.length > 2)
				{
					for (int i=2; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(CommandType.SEARCH, field[1], args);
				}
				else if (field.length > 1)
				{
					result = new Command(CommandType.SEARCH, field[1]);
				}
				else
				{
					System.err.println("Error: interpreter: command search needs a contact name");
					result = null;
				}
			}
			else if (field[0].compareTo("bye") == 0 ||
				field[0].compareTo("exit") == 0 ||
				field[0].compareTo("end") == 0 ||
				field[0].compareTo("stop") == 0)
			{	// bye = exit = end = stop
				result = new Command(CommandType.EXIT);
			}
			else if (field[0].compareTo("broadcast") == 0 ||
				field[0].compareTo("bc") == 0 ||
				field[0].compareTo("all") == 0)
			{	// broadcast = bc = all
				// arg1...N = message à diffuser
				for (int i=1; i<field.length; i++)
					args = args.concat(field[i]+" ");
					
				result = new Command(CommandType.BROADCAST, "", args);
			}
			else if (field[0].compareTo("help") == 0)
			{	// help
				if (field.length > 1)
					result = new Command(CommandType.HELP, field[1]);
				else
					result = new Command(CommandType.HELP);
			}
			else if (field[0].compareTo("refresh") == 0)
			{	// refresh
				result = new Command(CommandType.REFRESH_CONFIG);
			}
			else if (field[0].compareTo("refresh") == 0)
			{	// wizz
				// arg1 = nom du contact
				// arg2...N = message si contact trouvé
				if (field.length > 1)
				{
					for (int i=2; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(CommandType.WIZZ, field[1], args);
				}
				else
				{
					result = new Command(CommandType.WIZZ);
				}
			}
			else
			{	// message pour contact / groupe
				if (field.length > 1)
				{
					for (int i=1; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(field[0], args);
				}
				else
				{
					System.err.println("Error: interpreter: message function needs text to be send");
					result = null;
				}
			}
		}
		else
		{
			result = new Command("", s);
		}
			
		return result;
	}
	
	public static String CommandToString(Command c)
	{
		return "";
	}
}

/*
 * TODO: autoriser les combinaison de commande:
 * :wizz    [:CONTACT] [msg]
 * :CONTACT [:wizz]    [msg]
 * :wizz    [:bc]      [msg]
 * :bc      [:wizz]    [msg]
 */
 
/*
 * TODO: faire une liste de tous les usages possibles
 * (toutes les commandes et combinaisons de commandes possibles)
 */
 
/*
 * NOTE: l'identifiant 'all' est réservé par l'application
 * il désigne TOUS les contacts
 * c'est en fait le groupe racine contenant tous les groupes et contacts
 * de l'utilisateur
 */
 
/*
 * TODO: permettre de bloquer des identifiants / adresses
 */
 
/*
 * TODO: ajouter une commande pour l'envoi de fichier (style :file FICHIER)
 */
