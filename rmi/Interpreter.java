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
				if (field.length > 1)			
					result = new Command(CommandType.WIZZ, field[1]);
				else
					result = new Command(CommandType.WIZZ);
			}
			else if (field[0].compareTo("me") == 0)
			{	// message à moi-même
				// arg1..N =  msg
				for (int i=1; i<field.length; i++)
					args = args.concat(field[i]+" ");
					
				result = new Command("me", args);
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
		String result = "";
		
		switch(c.type)
		{
			case MESSAGE:
				if ( !c.target.isEmpty() )
					result = result.concat(":"+c.target+" ");
				result = result.concat(c.args);
				break;
				
			case EXIT:
				result = "exit";
				break;
				
			case BROADCAST:
				result = result.concat(":broadcast "+c.args);
				break;
				
			case SEARCH:
				result = result.concat(":search "+c.target);
				if ( !c.args.isEmpty() )
					result = result.concat(" "+c.args);
				break;
				
			case WIZZ:
				result = ":wizz";
				if ( !c.target.isEmpty() )
					result = result.concat(" "+c.target);
				break;
				
			case REFRESH_CONFIG:
				result = ":refresh";
				break;
				
			case HELP:
				result = ":help";
				if ( !c.target.isEmpty() )
					result = result.concat(" "+c.target);
				break;
				
			case CONTACT:
			default:
				break;
		}
		
		return result;
	}
}

/*
 * TODO: autoriser les combinaison de commande:
 * :bc      [:wizz]    = :bc :wizz
 */
 
/*
 * TODO: permettre de bloquer des identifiants / adresses
 */
 
/*
 * TODO: ajouter une commande pour l'envoi de fichier (style :file FICHIER)
 */
