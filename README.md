MSN avec Java et RMI
====================

Rendu
------

 * A rendre avant le 21 avril
 * Rapport papier à mettre dans le casier de Sonntag
 * Rendre le code en même temps par mail (+ executable)
 * Fichiers dans un rep Nom1Nom2Nom3

Programme
------

 * Faire une option --help

Syntaxe fichier de config
------
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
------
	Benoit : 212.85.150.133,0x20000000,1
	Jerome : ?
	Isaac : Benoit, Jerome

Syntaxe envoi de message
------

	MESSAGE			->	':' String '\n'
					|	LIST_ID ':' String '\n'
					|	COMMAND
					
	LIST_ID			->	IDENTIFIER ',' LIST_ID
					|	IDENTIFIER
					
	IDENTIFIER		->	[a-zA-Z_]+

	COMMAND			-> 'bye'

Exemple de message
------

	Benoit : Hello
	Benoit, Jerome : Quoi de 9 ?
	Isaac : Bye !
	: A bientot.
	bye

