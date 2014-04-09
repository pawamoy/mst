MST (Messaging Service Transport)
=================================

Rendu
-----

 * A rendre avant le 21 avril
 * Rapport papier à mettre dans le casier de Sonntag
 * Rendre le code en même temps par mail (+ executable)
 * Fichiers dans un rep Nom1Nom2Nom3
 

Programme
---------

 * Faire une option --help


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
	Benoit : 212.85.150.133,0x20000000,1
	Jerome : ?
	Isaac : Benoit, Jerome


Syntaxe envoi de message
------------------------
	MESSAGE			->	':' String '\n'
					|	LIST_ID ':' String '\n'
					|	COMMAND
					
	LIST_ID			->	IDENTIFIER ',' LIST_ID
					|	IDENTIFIER
					
	IDENTIFIER		->	[a-zA-Z_]+

	COMMAND			-> 'bye'


Exemple de message
------------------
	Benoit : Hello
	Benoit, Jerome : Quoi de 9 ?
	Isaac : Bye !
	: A bientot.
	bye


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

 * bye
 * broadcast
 * seekfriend
 * wizz
 * confrefresh
 * help
