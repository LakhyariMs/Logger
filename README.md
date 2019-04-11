# Logger
Ce logger développé permet d’afficher sur la console ou d’enregistrer dans un fichier des messages horodatés avec le nom de la classe selon le type de gravité du log en console. Pour son utilisation, il suffit d'importer la librairie JAR du Logger et utiliser ses méthodes .
	
exemple d’affichage :
		[INFO] [15/12/2018 13:10:45:305] (nom de class) <message>
  
Le logger peut-être configurer pour consigner uniquement les éléments d’une gravité spécifiée ou supérieure, tout en ignorant les éléments de moindre importance.
              NB : Le niveau de gravité est par défaut sur TRACE au départ (niveau 1) .
              
Le logger permet aussi d'enregistrer les messages de logs dans un fichier “. txt”, par défaut le fichier généré sera stocké dans le fichier du projet Eclipse, sinon il y a la possibilité d’indiquer le chemin ou sera ce dernier enregistré dans le paramètre de la méthode ‘ setFileLogger ’ ou de modifier la valeur de l’attribut ‘ loggerFilePath ’  sur le fichier logger.properties .
              NB : Le nom du fichier généré est par défaut ‘logFile.txt’ .
              
Ainsi il y a 3 modes d'affichage de logs (configurable depuis le fichier .properties ou le prog. ) :
CONSOLE :  afficher les logs seulement sur la console d'Eclipse
  - FILE : enregistrer les logs directement sur le fichier sans les afficher sur la console.
  - ALL : les logs seront affichés sur la console et enregistrer sur le fichier 
  - NB : Le mode d’affichage par défaut et ‘ALL’.
