#!/bin/bash
clear
javac -d bin/ src/*.java
java -cp bin/:/usr/share/java/mariadb-jdbc/mariadb-java-client.jar Executable