DROP DATABASE IF EXISTS bdd_aerosoft;
CREATE DATABASE bdd_aerosoft;
USE bdd_aerosoft;




CREATE TABLE ROLE(
   IdRole INT,
   RoleNom VARCHAR(20) NOT NULL,
   PRIMARY KEY(IdRole)
);

CREATE TABLE CONSTRUCTEUR(
   IdConstructeur INT,
   NomConstructeur VARCHAR(50) NOT NULL,
   PRIMARY KEY(IdConstructeur)
);

CREATE TABLE DETAILAVION(
   TypeAvion VARCHAR(50),
   Capacite SMALLINT NOT NULL,
   IdConstructeur INT NOT NULL,
   PRIMARY KEY(TypeAvion),
   FOREIGN KEY(IdConstructeur) REFERENCES CONSTRUCTEUR(IdConstructeur)
);

CREATE TABLE AEROPORT(
   IdAeroport VARCHAR(3),
   NomAeroport VARCHAR(20) NOT NULL,
   NomVilleDesservie VARCHAR(20) NOT NULL,
   PRIMARY KEY(IdAeroport)
);

CREATE TABLE VOL(
   NumVol VARCHAR(5),
   Harrivée TIME NOT NULL,
   Hdepart TIME NOT NULL,
   IdAeroport VARCHAR(3) NOT NULL,
   IdAeroport_1 VARCHAR(3) NOT NULL,
   PRIMARY KEY(NumVol),
   FOREIGN KEY(IdAeroport) REFERENCES AEROPORT(IdAeroport),
   FOREIGN KEY(IdAeroport_1) REFERENCES AEROPORT(IdAeroport)
);

CREATE TABLE PILOTE(
   IdPilote INT,
   NomPilote VARCHAR(50) NOT NULL,
   PrenomPilote VARCHAR(50) NOT NULL,
   Matricule VARCHAR(50) NOT NULL,
   PRIMARY KEY(IdPilote),
   UNIQUE(Matricule)
);

CREATE TABLE UTILISATEUR(
   IdUtilisateur INT,
   Nom VARCHAR(50) NOT NULL,
   Prenom VARCHAR(50) NOT NULL,
   Mail VARCHAR(50) NOT NULL,
   MotDePasse VARCHAR(50) NOT NULL,
   Statut Boolean NOT NULL,
   IdRole INT NOT NULL,
   PRIMARY KEY(IdUtilisateur),
   UNIQUE(Mail),
   FOREIGN KEY(IdRole) REFERENCES ROLE(IdRole)
);

CREATE TABLE AVION(
   NumAvion INT,
   IdAeroport VARCHAR(3) NOT NULL,
   TypeAvion VARCHAR(50) NOT NULL,
   PRIMARY KEY(NumAvion),
   FOREIGN KEY(IdAeroport) REFERENCES AEROPORT(IdAeroport),
   FOREIGN KEY(TypeAvion) REFERENCES DETAILAVION(TypeAvion)
);

CREATE TABLE AFFECTATION(
   IdAffectation INT,
   DateVol DATE NOT NULL,
   AffectationCode boolean,
   NumAvion INT NOT NULL,
   IdPilote INT NOT NULL,
   NumVol VARCHAR(5) NOT NULL,
   PRIMARY KEY(IdAffectation),
   FOREIGN KEY(NumAvion) REFERENCES AVION(NumAvion),
   FOREIGN KEY(IdPilote) REFERENCES PILOTE(IdPilote),
   FOREIGN KEY(NumVol) REFERENCES VOL(NumVol)
);

