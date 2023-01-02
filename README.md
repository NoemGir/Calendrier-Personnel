# Projet
Projet Java / Ocaml - Ingénierie des logiciels et utilisabilité 1 - Licence informatique
                                                                                      GIREAUD
                                                                                      Noemie
                                                                                      [INXIL11A31]
/!\Attention !! Enclancher les methodes de la classe Scenarios (java) vont surcharger toutes les données enregistrées, donc il faut penser à sauvegarder les données autres part si elles sont importantes et les remettre à leur place a la fin de l'utilisation de la classe Scenarios /!\


Pour enclancher le logiciel principal, il faut run la classe 'Main' sur Eclipse. Pour enclancher les scenarios tests, il faut run la classe 'Scenarios' (dossier test).
La mise a niveau des données ( exécutés par Ocaml ) n'est possible que sur Linux, cependant la persistance des données reste possible sur
n'importe quel systeme d'exploitaton.	Une option "Guide" est présente dans le Menu principal pour exploquer le fonctionnement du logiciel.

Tout le projet se trouve dans le dossier "Projet", les choses importantes à localiser sont :

Le fichier 'class diagram.png' (racine), qui contient un diagramme de classe simplifié du code contenue dans le dossier 'main' du dossier 'src' (racine).
Le script bash 'ExecuterOcaml.sh' (racine) est exécuté  par Java a chaque lancement du code, pour lancer le code Ocaml.
Le dossier 'src' (racine) ( contenant les dossiers 'main' et 'test' ) qui stockent le code Java.
Le dossier 'ocaml' (racine) ( contenant entre autre les dossiers 'donnees' et 'src' ).
Le dossiers 'donnees' (ocaml) stock tous les fichiers servant a la persistance, crées et modifiés en Java et Ocaml.
Le dossier 'src' (ocaml) contient le code Ocaml ( regarder principalement le fichier 'main.ml' ).
                       		       
                                                                                        https://youtu.be/kPt9MKkJrKw


