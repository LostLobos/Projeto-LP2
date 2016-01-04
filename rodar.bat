@ECHO OFF

echo.
echo.
echo Apagando todos os Arquivos .class ...

echo.
echo.

echo --------------------------------------------------------------
echo.
echo.

del /S *.class

echo.
echo.
echo --------------------------------------------------------------

echo.
echo.
echo Remocao Concluida!
echo.
echo.

echo Compilando Programa Java ...

echo.
echo.

echo --------------------------------------------------------------
echo.
echo.

javac Main.java

echo.
echo.
echo --------------------------------------------------------------

echo.
echo.
echo Compilacao Comcluida !
echo.
echo.

echo Executando Programa ...

echo.
echo.

echo --------------------------------------------------------------
echo.
echo.

SET mypath=%~dp0

java -cp ;"%mypath%mysql-connector-java-5.1.38-bin.jar" Main

echo.
echo.
echo --------------------------------------------------------------

echo.
echo.
echo Execucao Comcluida !
echo.
echo.