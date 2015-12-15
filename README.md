# Projeto-LP2
Projeto para a Disciplina LP2 em Java.

Todas as Informações Pertinentes ao Projeto por  aqui.

## Programas para Download ( Importantes )

* Programa do Git: https://git-for-windows.github.io/
* NotePad++ ("IDE"): https://notepad-plus-plus.org/download/v6.8.8.html

## Informações Importantes

* Compilar com JAVAC utilizando o Driver MySQL

```
java -cp ;"C:\Users\Igor\Desktop\Faculdade\LP2\Projeto\Projeto-LP2\mysql-connector-java-5.1.38-bin.jar" Main
```

## Instalar Java-SDK no Windows

Java SE-JDK 8u66, baixar versão x86 ou x64 de acordo com seu sistema.

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?ssSourceSiteId=otnpt

### Utilizando o comando JAVAC no Prompt de Comando

* Siga apenas o primeiro bloco do Link abaixo e o segundo ponto.

Siga os Passos descritos nesse Link, após instalar o Java SE-JDK 8u66 para poder utilizar o Java Compiler no CMD.

Link: http://introcs.cs.princeton.edu/java/15inout/windows-cmd.html

## Tabelas Banco de Dados

### Tabela Usuarios

```

CREATE TABLE `projetolp2`.`Usuarios` ( `id_usuario` INT NOT NULL AUTO_INCREMENT , `Usuario` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL , `Senha` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL , `Nome` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ) ENGINE = InnoDB;

```

### Tabela Chat
