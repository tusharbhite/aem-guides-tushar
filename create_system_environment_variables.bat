rem Run this file Before Starting an AEM instance as an administrator to create or update environment variable
rem Key value pairs as Environment variables and Ita Value

@echo off
setlocal

rem Declare the key value pairs here
set string=hiwww
set integer=2323
set multivalued="\"1\",\"2\",\"3\""

rem Use setx to set the variables as environment variables
setx foo %string% /m
setx bar %integer% /m
setx baz %multivalued% /m

endlocal
