#########################		README			#########################

Projet présenté & application produite par
	DEFAYE 	Johan
	FOURMOND	Jérôme
en Master 1 - Informatique
dans le cadre de l'Unité d'Enseignement
	Modèle des graphes et de l'intelligence artificielle
dirigée par
	DUVAL Béatrice.
	
Le programme est fourni sous deux formes :
	- code source
	- archive java exécutable
accompagné de script bash nécessaire pour la compilation,
l'attribution des droits à l'exécution et l'exécution.
	
#########################		CODE SOURCE		##########################

Le répertoire "source" comporte différents fichiers :
	- deux scripts : ai-foil & setup
	- deux répertoires :libraries & src
		- libraries : contient l'archive java exécutable tirée de weka
		- src : contient les fichiers java (code-source) du programme
				  manipulable et modifiable

Les fichiers se manipulent de la façon suivante :

	----------	COMPILATION	----------
	
	1. Attribuer les droits au fichier setup
	2. Exécuter le fichier setup
		-> crée le répertoire "bin"
		-> compile le contenu du répertoire "src" à l'aide de la librairie
				weka situé dans le répertoire "libraries"
		-> crée les fichiers de compilation dans bin
		-> génère la javadoc
		-> attribue les droits au script ai-foil
		
	----------	EXECUTION	----------
	
	1. Exécuter le fichier ai-foil
		-> le programme se lance

#########################		PROGRAMME		##########################

Le répértoire "programme" contient différents fichiers :
	- deux scripts :	ai-foil-jar & setup_jar
	- une archive java exécutable (jar)

Pour pouvoir exécuter l'archive java exécutable il est possible de :

	----------	ATTRIBUTION	----------
	
	1. Attribuer les droits au fichier setup_jar
	2. Executer le fichier setup_jar
		-> attribue les droits d'exécution au fichier AI-FOIL.jar
		-> attribue les droits d'exécution au fichier ai-foil-jar
	
	----------	EXECUTION	----------
	
	1. Executer le fichier ai-foil-jar
		-> le programme se lance
	
ATTENTION : l'exécution du programme est tout à fait possible sans 
	l'utilisation des scripts.

#########################		ARGUMENTS		##########################

	Le programme lance l'interface graphique d'accueil à l'exécution du
programme sans arguments.

	Différents arguments peuvent être passés au programme (dans les
scripts d'exécution par exemple).

	- [ path-to-file ] : chemin vers le fichier traité par le programme
		-> si précisé uniquement le programme s'exécute directement sur
			le fichier dans l'interface graphique
		-> peut être précédé de l'argument "-nointerface 
	- "-nointerface"	: associé au chemin du fichier, le programme
			s'exécute dans la console uniquement.
		-> peut être précédé de l'argument "-out=..."
	- "-out=[ path-to-file ]" : chemin vers le fichier qui sera rempli
			avec le tableau d'instance du fichier et les règles de chaque
			attribut de la classe
