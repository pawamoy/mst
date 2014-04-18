MST (Messaging Service Transport)
=================================

TODO
----

 * Ajouter la lecture des commandes add, delete, list, modify (contacts)
 * Ajouter un timeout pour la récupération d'une interface via rmi (ou lancer via un thread): on veut pas bloquer le prog
 * Fonction exit: penser à actualiser le fichier d'adresses (si on output dans l'ordre, les dépendances sont respectées)
 * AddressBook: autoriser les commentaires avec '/'
 * Interpreter: autoriser les listes de contacts (séparateur ',' sans espace). Cela demande un traitement supplémentaire de Command.target dans les fonctions
 * Implémentation des fonctions demandées (voire paragraphe Commandes)
 * (Interface graphique)
 * Ecrire un rapport (diagrammes UML, etc...)
 * Faire une option --help
 * Changer adresse d'un contact en '?' si erreur
 * Supprimer groupes vides ? (pendant l'exécution ? à l'arrêt ?)
 * A l'ajout de contacts/groupes, vérifier qu'on leur donne pas un mot clé comme nom (me/all/quit/etc...), et qu'ils commencent pas par un espace
 * Pouvoir récupérer l'adresse d'un contact inconnu s'il nous envoie un msg (wtf?)

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


Déroulement
-----------
On lance l'appli avec un identifiant.
On entre des contacts (groupe) via une fenêtre graphique, ou bien via le fichier de config que l'on relit via une commande de l'appli.
Si on reçoit un message d'un destinataire inconnu, on ajoute son adresse dans la table et on demande un identifiant.

Fonctions diverses:

 * Chercher un contact parmi les listes de tous les amis
 * Diffusion de message: envoi à tous les amis, qui envoient à tous leurs amis, ... (bool à true si déjà reçu pour éviter les cycles)

Commandes
---------
(+ bouton de l'interface)

 * bye = end = stop = quit = exit
 * broadcast = bc = all MSG
 * search = seek = who [NOM]
 * wizz [CTT/GRP]
 * refresh
 * help [CMD]
 * add NOM [ADR] [PORT]
 * delete = del [NOM/ADR]
 * list [GRP]
 * modify = mod [CTT/GRP]
