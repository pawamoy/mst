Objectifs
=========
 * Mémoriser des groupes de contacts, un groupe pouvant contenir d'autres groupes
 * Architecture P2P, donc client et serveur en même temps
 * Distinguer les protagonistes sur un réseau local (localhost)
 * Interpréter des commandes tappées par l'utilisateur
 * Diffusion de messages
 * Envoi de fichier
 
 
Groupes de contacts
===================

Idées
-----
 * Un groupe contient des groupes et des contacts: utilisation d'ArrayList.
 * Permet de définir des arborescences de contacts.
 * Le fonctionnement objet de java avec les références permet une modification très simple des contacts et groupes, qui peuvent appartenir à plusieurs groupes en même temps.
 * Idée d'un groupe "racine" contenant tous les autres, par défaut nommé "all".

Problèmes & Solutions
---------------------
 * Les méthodes de suppression et d'accès aux contacts / groupes ne doivent pas être identiques entre le groupe racine et un groupe normal -> déclinaison en 2 classes: RootGroup, dont hérite Group.
 * Lire les contacts/groupes dans un fichier et assurer la cohérence -> classe abstraite "AddressBook" avec méthodes statiques
 
 
Architecture P2P
================

Idées
-----
 * 2 classes "Server" et "Client", lancées l'une après l'autre depuis la main class.
 * Classe Communication et son interface CommInterface pour l'appel de méthodes distantes via RMI.
 
Problèmes & Solutions
---------------------
 * Comment stocker les CommInterfaces pour chaque contact ? -> on l'ajoute comme attribut à la classe Contact (qui contient déjà l'adresse, le nom et le port).
 * Fonctionnement P2P: le "serveur" doit communiquer avec le client et vice-versa. Comment les relier ? -> ici le serveur ne sert qu'à faire un rebind sur l'objet Communication de l'utilisateur. C'est justement l'interface CommInterface qui doit pouvoir appeler des méthodes du client, et le client les méthodes des interfaces de ses contacts: on va stocker toutes les données de l'application (contacts, soi-même, destinataires courants, référence client) dans une instance unique de classe: AppData. En fournissant comme attribut cette référence à AppData aux interfaces et aux clients, ils peuvent communiquer sans problème.
 
 
Distinction des utilisateurs
============================

Idées
-----
 * En réseau local et avec RMI, c'est le port qui distingue les utilisateurs entre eux, le pseudo ne joue pas.
 
Problèmes & Solutions
---------------------
 * On ne peut utiliser 2x le même port -> avertir l'utilisateur au lancement si le port fourni est déjà utilisé.
 * Comment savoir quels ports sont libres ? A priori, il y en a beaucoup, donc on doit pouvoir en trouver un en quelques essais (on pourrait aussi automatiquement incrémenter le port jusqu'à en trouver un libre, et afficher le port trouvé au lancement de l'application)


Interprétation des commandes
============================

Idées
-----
 * L'application développée est un chat. La plupart du temps, on tappera donc des messages (bien plus souvent que des commandes). Nous allons donc changer le fonctionnement: on utilise ':' pour tapper une commande ou préciser un/des destinataire(s), et on écrit normalement pour envoyer un message au(x) destinataire(s) courant.
 
Mise en oeuvre
--------------
 * Classe abstraite "Interpreter" avec méthodes statiques.
 
 
Diffusion de messages
=====================

Idées
-----
 * A l'appel de la méthode Broadcast depuis sa propre interface de communication, rappeler la méthode Broadcast de son propre client.
 
Problèmes & Solutions
---------------------
 * Comment éviter les cycles ? Lors d'un envoi en diffusion, préciser son identifiant (ici le port) et un numéro de message (incrémenté à chaque nouvelle demande de diffusion), pour que l'interface des contacts puisse enregistrer dans une liste ce couple d'identifiant à la réception. A chaque réception de diffusion, l'interface vérifiera que le couple d'identifiant n'est pas déjà présent dans sa liste, afin de rappeler ou non la méthode Broadcast du client et afficher le message. Il est nécessaire d'utiliser ET le port, ET un numéro, car sinon il serait impossible de différencier les messages d'un même contacts, ou les contacts pour un même numéro de message.
 
 
Envoi de fichiers
=================

Idées
-----
 * Transformer le fichier en une longue chaîne style base64, le décoder et l'écrire sur le disque à la réception.
 
Problèmes & Solutions
---------------------
 * Longueur de chaîne limitée -> "splitter" la chaîne en sous-chaînes

Mise en oeuvre
--------------
 * Rien pour l'instant
 

A mettre dans le rapport
========================

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
