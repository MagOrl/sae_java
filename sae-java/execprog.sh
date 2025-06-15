#!/bin/bash
clear
javadoc -d doc -charset utf8 -noqualifier all src/*.java
javac -d bin/ src/*.java
java -cp bin/:/usr/share/java/mariadb-java-client.jar Executable