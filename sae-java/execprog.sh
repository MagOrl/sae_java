#!/bin/bash
clear
javac -d bin/ src/*.java
java -ea -cp bin/:/usr/share/java/mariadb-java/mariadb-java-client.jar Executable