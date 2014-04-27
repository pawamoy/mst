MST (Messaging Service Transport)
=================================

TODO
----
 * Lors d'un envoi quelconque (message, diffusion, wizz), envoyer son propre port. Le destinataire cherche l'id dans ses contact, s'il ne connait pas cet id, il demande d'entrer un nom
 * Interpreter: autoriser les listes de contacts (séparateur ',' sans espace). Cela demande un traitement supplémentaire de Command.target dans les fonctions
 * Modifier l'utilisation des contacts/groupes courants: on veut pouvoir avoir des listes (et puis éviter le cast de RootGroup en Group)
 * Permettre de bloquer des identifiants / adresses
 * Message d'erreur et arrêt si port fourni déjà utilisé (fct run du make)
 * (Incrémenter le port tant quu'utilisé, afficher nouveau port choisi)
 * Fonction exit: penser à actualiser le fichier d'adresses (si on output dans l'ordre, les dépendances sont respectées) 
 * Supprimer groupes vides à l'arrêt
 * Finir l'interface graphique
 * Ecrire un rapport (diagrammes UML, etc...)
 * (Changer adresse d'un contact en '?' si erreur) 
 * (Ajouter un timeout pour la récupération d'une interface via rmi (ou lancer via un thread): on veut pas bloquer le prog)


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
 
 

