@echo off

set DIR=example-data
set A=%DIR%/in-verbs.txt
set B=%DIR%/in-nouns+adjectives.txt
set C=%DIR%/db-verbs.txt
set D=%DIR%/db-nouns.txt
set E=%DIR%/db-adjectives.txt
set F=%DIR%/out.txt

gradlew.bat run -PappArgs="['%A%', '%B%', '%C%', '%D%', '%E%', '%F%']"
