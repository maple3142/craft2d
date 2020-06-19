#!/bin/bash

command_exists () {
    type "$1" &> /dev/null ;
}

shopt -s expand_aliases

if grep -q Microsoft /proc/version; then
  if ! command_exists java; then
    alias java=java.exe
  fi
  if ! command_exists javac; then
    alias javac=javac.exe
  fi
fi

cd src
find . -name '*.java' > sources.txt
javac -cp '../lib/*' -d '../dest' @sources.txt
rm sources.txt
cd ../dest
cp -r ../assets/* .
java --module-path ../lib --add-modules javafx.controls,javafx.media,com.google.gson,gson.extras net.maple3142.craft2d.Main
