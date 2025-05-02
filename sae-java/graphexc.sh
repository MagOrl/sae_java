#!/bin/bash
javac -d bin --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls ihm/*.java
java -cp bin --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Executable