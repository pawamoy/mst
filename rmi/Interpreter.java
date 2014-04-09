public abstract class Interpreter
{
	public static Command StringToCommand(String s)
	{
		// si la commande commence par ':'
		
			// analyser le premier mot (séparateur = espace, le mot peut être collé aux ':')
			
				// comparer aux mots-clés de l'appli
				
					// search = seek = who
						// arg1 = nom à chercher
						// arg2...N = message si contact trouvé
					/* note: la recherche devant renvoyer un résultat,
					 * si les amis sont très nombreux (et récursivement)
					 * il faudrait alors lancer cette recherche dans un thread
					 * pour ne pas bloquer tout le programme
					 */
						
					// bye = exit = end = stop
					// broadcast = bc = all
						// arg1...N = message à diffuser
						
					// help
					// refresh
					// wizz
						// arg1 = nom du contact
						// arg2...N = message si contact trouvé
					
				// sinon comparer aux noms de groupes
					// actualiser contact courant
					// si args, envoyer msg
				
				// sinon comparer aux noms de contacts
					// actualiser contact courant
					// si args, envoyer msg
				
				// sinon équivalent à search / seek / who
		
		// sinon
			
			// il s'agit d'un message à destination du contact courant
			// s'il n'y a pas de contact courant, erreur
			
		return null;
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
