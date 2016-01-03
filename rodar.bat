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

java -cp ;"C:\Users\dvanc\Documents\GitHub\Projeto-LP2\mysql-connector-java-5.1.38-bin.jar" Main

echo.
echo.
echo --------------------------------------------------------------

echo.
echo.
echo Execucao Comcluida !
echo.
echo.