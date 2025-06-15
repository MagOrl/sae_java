#!/bin/bash
clear
javac -d bin/ src/*.java
<<<<<<< HEAD
java -ea -cp bin/:/usr/share/java/mariadb-java/mariadb-java-client-3.3.2.jar Executable$1
#java -ea -cp bin/:/usr/share/java/mariadb-java-client.jar Executable$1
=======
# java -cp bin/:/usr/share/java/mariadb-jdbc/mariadb-java-client.jar Executable
java -cp bin/:/usr/share/java/mariadb-java-client.jar Executable
>>>>>>> main
