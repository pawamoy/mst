MST (Messaging Service Transport)
=================================

TODO
----
 * Lors d'un envoi quelconque (message, diffusion, wizz), envoyer son propre port. Le destinataire cherche l'id dans ses contact, s'il ne connait pas cet id, il demande d'entrer un nom
 * Permettre de bloquer des identifiants / adresses
 * Message d'erreur et arrêt si port fourni déjà utilisé (fct run du make)
 * (Incrémenter le port tant qu'utilisé, afficher nouveau port choisi)
 * Supprimer groupes vides à l'arrêt


Rendu
-----
 * A rendre avant le 28 avril
 * Rapport papier à mettre dans le casier de Sonntag
 * Rendre le code en même temps par mail (+ executable)
 * Fichiers dans un rep Nom1Nom2Nom3
 

Programme
---------
 * Compilation: ./make
 * Lancement: ./mst PSEUDO [PORT] | [-h|--help]
 * usage make: make help|[mst]|clean
 * Pensez à alias m='./make' pour vous simplifier la vie
 
 

