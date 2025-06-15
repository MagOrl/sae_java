#!/bin/bash
clear
javac -d bin/ src/*.java
java -cp bin/:/usr/share/java/mariadb-java/mariadb-java-client-3.3.2.jar Executable
#java -cp bin/:/usr/share/java/mariadb-jdbc/mariadb-java-client.jar Executable