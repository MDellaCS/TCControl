CREATE DATABASE DB_TCControl;
USE DB_TCControl;
CREATE TABLE Aluno
(
   id int auto_increment primary key,
   nome varchar (100),
   cpf varchar (11),
   rg varchar (12),
   curso varchar (150),
   ra varchar (13),
   semestre varchar (2)
);
CREATE TABLE Orientador
(
   id int auto_increment primary key,
   nome varchar (100),
   cpf varchar (11),
   rg varchar (12),
   curso varchar (150),
   alunos varchar (255)
);
/*CREATE TABLE Orienta
(
   idAluno int,
   idOrientador int,
   PRIMARY KEY
   (
      idAluno,
      idOrientador
   ),
   FOREIGN KEY (idAluno) REFERENCES Aluno (id),
   FOREIGN KEY (idOrientador) REFERENCES Orientador (id)
);
*/ CREATE TABLE TCC
(
   id int auto_increment primary key,
   nome varchar (150),
   aprovado varchar (20)
);
CREATE TABLE Banca
(
   id int auto_increment primary key,
   data varchar (30),
   alunos varchar (255),
   orientadores varchar (255)
);
CREATE TABLE PropostaTCC
(
   id int auto_increment primary key,
   alunos varchar (255),
   orientador varchar (100),
   tema varchar (100),
   tipo varchar (100)
);