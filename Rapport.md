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
 
