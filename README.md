MST (Messaging Service Transport)
=================================

TODO
----
 * Interpreter: autoriser les listes de contacts (séparateur ',' sans espace). Cela demande un traitement supplémentaire de Command.target dans les fonctions
 * Modifier l'utilisation des contacts/groupes courants: on veut pouvoir avoir des listes (et puis éviter le cast de RootGroup en Group)
 * Ajouter un timeout pour la récupération d'une interface via rmi (ou lancer via un thread): on veut pas bloquer le prog
 * Fonction exit: penser à actualiser le fichier d'adresses (si on output dans l'ordre, les dépendances sont respectées) 
 * Changer adresse d'un contact en '?' si erreur
 * Supprimer groupes vides ? (pendant l'exécution ? à l'arrêt ?)
 * Pouvoir récupérer l'adresse d'un contact inconnu s'il nous envoie un msg (wtf?) -> envoyer sa propre ip à chaque message ??? comment la récupérer localement ???
 * (Interface graphique)
 * Ecrire un rapport (diagrammes UML, etc...)
 * Est-il utile de conserver les adresses ip puisque le programme ne fonctionnera pas via internet ? revoir l'implémentation


Explication diagramme
---------------------
Le programme principal, MST, instancie un MSTClient et un MSTServeur, lancés en tant que threads.

Le serveur instancie une Communication contenant la référence au client local.
Cela permet à l'instance de Communication d'appeler des méthodes du client, comme le nécessite la diffusion.

AppData (en fait instanciée dans MST) n'est utilisée que par le client.
Elle contient le RootGroup contacts, contenant tous les contacts et tous les groupes (une seule instance possible).
La classe Group dérive de RootGroup, redéfinissant quelques méthodes (suppression d'éléments), et ajoutant d'autres méthodes. Cela permet une définition en arborescence de groupes et sous-groupes de contacts.

Chaque contact possède un nom (choisi par l'utilisateur local, aucune influence sur le programme),
une adresse ip connue (A.B.C.D) ou non (?), un port (par défaut 1099), et une interface de communication.

Cette instance de CommInterface sert au client local pour contacter les serveurs distants
(y compris le serveur local via l'attribut contact "me" de AppData).


Rendu
-----
 * A rendre avant le 28 avril
 * Rapport papier à mettre dans le casier de Sonntag
 * Rendre le code en même temps par mail (+ executable)
 * Fichiers dans un rep Nom1Nom2Nom3
 

Programme
---------
 * Compilation: ./make
 * Lancement: ./make run PSEUDO | [-h|--help]
 * usage make: make help|[mst]|run ...|clean
 * Pensez à alias m='./make' pour vous simplifier la vie
 
 
Déroulement
-----------
Au démarrage, tous les contacts/groupes sont lu dans le fichier appdata/addressbook par la classe AddressBook.
L'utilisateur peut alors tapper des commandes au clavier (String), qui sont envoyées
à l'Interpreter. L'Interpreter renvoie alors une Commande au client, que ce dernier va traiter
(en utilisant les interfaces de communication des contacts pour les requêtes distantes).
On envoie un message au(x) destinataire(s) courant(s) en tappant simplement du texte.
On précise le destinataire en précédant son nom par ':', exemple ":hollande salut mon gros :)"
Le destinataire peut être un contact ou un groupe, ou même une liste de contacts/groupes, séparés par ',' (fonctionnalité non implémentée).
On utilise une commande de l'application en la faisant précéder de ':', exemple ":broadcast Diiiiiiiiii...-FUSION !"


Remarque
--------
La communication via internet ne fonctionne PAS.
En réseau local cependant, les utilisateurs devront se différencier grâce au port.
Un port différent par personne devra être utilisé.


Commandes
---------
(+ bouton de l'interface)

 * Quitter:				bye = end = stop = quit = exit = q
 * Diffuser message:	broadcast = bc MSG
 * Chercher contact:	search = seek = who [NOM]
 * Wizz:				wizz [CTT/GRP]
 * Recharger adresses:	refresh
 * Aide:				help [CMD]
 * Ajouter contact:		add NOM [ADR] [PORT]
 * Supprimer contact:	delete = del [NOM/ADR]
 * Lister:				list [GRP]
 * Modifier contact:	modify = mod [CTT/GRP]


Syntaxe fichier de config
-------------------------
	LIST		->	ADDRESS '\n' LIST
				|	ADDRESS

	ADDRESS		->	IDENTIFIER ':' HOST

	IDENTIFIER	->	[a-zA-Z_]+

	HOST		->	Host
				|	'?'
				|	LIST_ID
				
	LIST_ID		->	IDENTIFIER ',' LIST_ID
				| IDENTIFIER

 * Modif manuelle autorisée
 * Mise à jour auto


Exemple fichier config
----------------------
	Benoit : 212.85.150.133,1099
	Jerome : ?
	Isaac : Benoit, Jerome


Syntaxe envoi de message
------------------------
	MESSAGE			->	String'\n'
					|	':' LIST_ID String'\n'
					|	COMMAND
					
	LIST_ID			->	IDENTIFIER','LIST_ID
					|	IDENTIFIER
					
	IDENTIFIER		->	[a-zA-Z_]+

	COMMAND			-> ':bye' ...


Exemple de message
------------------
	:Benoit Hello
	: Benoit,Jerome Quoi de 9 ?
	:Isaac Bye !
	A bientot.
	:bye

