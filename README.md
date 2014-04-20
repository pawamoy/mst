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



Rendu
-----

 * A rendre avant le 28 avril
 * Rapport papier à mettre dans le casier de Sonntag
 * Rendre le code en même temps par mail (+ executable)
 * Fichiers dans un rep Nom1Nom2Nom3
 

Programme
---------

 * Compilation: ./make
 * Lancement: ./make run
 * usage make: make help|[mst]|run|clean
 * Pensez à alias m='./make' pour vous simplifier la vie
 
 
Déroulement
-----------
On lance l'appli avec un identifiant.
On entre des contacts (groupe) via une fenêtre graphique, ou bien via le fichier de config que l'on relit via une commande de l'appli.
Si on reçoit un message d'un destinataire inconnu, on ajoute son adresse dans la table et on demande un identifiant.


Commandes
---------
(+ bouton de l'interface)

 * Quitter:				bye = end = stop = quit = exit = q
 * Diffuser message:	broadcast = bc = all MSG
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

