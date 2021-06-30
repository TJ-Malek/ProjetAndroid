DROP DATABASE IF EXISTS bdd_social_connect;
CREATE DATABASE bdd_social_connect;
USE bdd_social_connect;



CREATE TABLE ENTREPRISE(
   IdEntreprise BIGINT(20),
   Designation VARCHAR(50) NOT NULL,
   Logo VARCHAR(50) NOT NULL,
   Description VARCHAR(50) NOT NULL,
   url VARCHAR(100) NOT NULL,
   Statut boolean,
   PRIMARY KEY(IdEntreprise)
);

CREATE TABLE UTILISATEUR(
   IdUtilisateur BIGINT(20) ,
   NomUtilisateur VARCHAR(50) NOT NULL,
   MotDePasse VARCHAR(100) NOT NULL,
   Mail VARCHAR(50) NOT NULL,
   Role VARCHAR(50) NOT NULL,
   Statut boolean,
   PRIMARY KEY(IdUtilisateur)
);

CREATE TABLE MEMBRE(
   IdMembre BIGINT(20),
   Nom VARCHAR(20) NOT NULL,
   Prenom VARCHAR(20) NOT NULL,
   Photo VARCHAR(50) NOT NULL,
   Poste VARCHAR(20) NOT NULL,
   Grade VARCHAR(20) NOT NULL,
   Departement VARCHAR(20),
   Date_Embauche DATE NOT NULL,
   IdEntreprise BIGINT(20) NOT NULL,
   PRIMARY KEY(IdMembre),
   FOREIGN KEY(IdMembre) REFERENCES UTILISATEUR(IdUtilisateur),
   FOREIGN KEY(IdEntreprise) REFERENCES ENTREPRISE(IdEntreprise)
);

CREATE TABLE PUBLICATION(
   IdPublication BIGINT(20),
   Description VARCHAR(100),
   Image VARCHAR(50),
   Statut VARCHAR(50) NOT NULL,
   NombreLike INT,
   IdMembre BIGINT(20) NOT NULL,
   datePub DATETIME,
   videourl VARCHAR(100),
   fichierurl VARCHAR(100),
   PRIMARY KEY(IdPublication),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre)
);

CREATE TABLE COMMENTAIRE(
   IdCommentaire BIGINT(20),
   Description VARCHAR(100) NOT NULL,
   NombreLike INT,
   dateCom DateTime,
   IdPublication BIGINT(20) NOT NULL,
   IdMembre BIGINT(20) NOT NULL,
   PRIMARY KEY(IdCommentaire),
   FOREIGN KEY(IdPublication) REFERENCES PUBLICATION(IdPublication),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre)
);

CREATE TABLE IMAGE(
   IdImage BIGINT(20),
   Titre VARCHAR(50) NOT NULL,
   ImageUrl VARCHAR(50) NOT NULL,
   IdMembre BIGINT(20) NOT NULL,
   PRIMARY KEY(IdImage),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre)
);

CREATE TABLE LIKE_COMMENTAIRE(
   IdMembre BIGINT(20),
   IdCommentaire BIGINT(20),
   PRIMARY KEY(IdMembre, IdCommentaire),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre),
   FOREIGN KEY(IdCommentaire) REFERENCES COMMENTAIRE(IdCommentaire)
);

CREATE TABLE LIKE_PUBLICATION(
   IdMembre BIGINT(20),
   IdPublication BIGINT(20),
   PRIMARY KEY(IdMembre, IdPublication),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre),
   FOREIGN KEY(IdPublication) REFERENCES PUBLICATION(IdPublication)
);

CREATE TABLE COUPLE_AMIS(
   IdMembre BIGINT(20),
   IdMembre_1 BIGINT(20),
   PRIMARY KEY(IdMembre, IdMembre_1),
   FOREIGN KEY(IdMembre) REFERENCES MEMBRE(IdMembre),
   FOREIGN KEY(IdMembre_1) REFERENCES MEMBRE(IdMembre)
);

INSERT INTO `utilisateur` (`IdUtilisateur`, `NomUtilisateur`, `MotDePasse`, `Mail`, `Role`, `Statut`) VALUES
(1696278514562148, 'GoogleAdmin', '123', 'google@admin.com', 'admin', 1),
(1696278527895410, 'FnacAdmin', '123', 'fnac@admin.com', 'admin', 1),
(1696278537845651, 'AmazonAdmin', '123', 'amazon@admin.com', 'admin', 1),
(1696278547894512, 'GoogleUser', '123', 'google@user.com', 'user', 1),
(1696278547894513, 'FnacUser', '123', 'fnac@user.com', 'user', 1),
(1696278547894514, 'AmazonUser', '123', 'amazon@user.com', 'user', 1),
(1697740830758427, 'a', '123', 'olivierosoufafpa@gmail.com', 'superadmin', 1);

INSERT INTO `entreprise` (`identreprise`, `designation`, `logo`, `description`, `url`, `statut`) VALUES
(1696278527184852, 'Google', 'LogoGoogle.jpg', 'Je suis Google', 'monReseau/1696278527184852', 1),
(1696278531096786, 'Fnac', 'LogoFnac.png', 'Je suis la Fnac', 'monReseau/1696278531096786', 1),
(1696278577788060, 'Amazon', 'LogoAmazon.png', 'Je suis Amazon', 'monReseau/1696278577788060', 1);

INSERT INTO `membre` (`IdMembre`, `nom`, `prenom`, `photo`, `poste`, `grade`, `departement`, `date_embauche`, `IdEntreprise`) VALUES
(1696278514562148, 'Google', 'Jean', 'maPhotoJean.jpg', 'Superviseur', 'Manager', 'Developpement', '2019-02-15',1696278527184852),
(1696278527895410, 'Fnac', 'Luiza', 'maPhotoLuiza.jpg', 'Superviseur', 'Manager', 'Communication', '2017-05-17', 1696278531096786),
(1696278537845651, 'Amazon', 'Nathalie', 'maPhotoNath.jpg', 'Superviseur', 'Manager', 'Relation Presse', '2015-07-02',1696278577788060),
(1696278547894512, 'Doe', 'Jane', 'maPhotoJane.jpg', 'Developpeur', 'Employe', 'Developpement', '2020-07-25',1696278527184852),
(1696278547894513, 'Onyme', 'Anne', 'maPhotoAnne.jpg', 'Community manager', 'Employe', 'Communication', '2021-01-05',1696278531096786),
(1696278547894514, 'Drake', 'Nathan', 'maPhotoNate.jpg', 'Assistant', 'Employe', 'Communication', '2019-11-01',1696278577788060);

INSERT INTO `couple_amis` (`idMembre`, `idMembre_1`) VALUES
(1696278514562148, 1696278547894512),
(1696278527895410, 1696278547894513),
(1696278537845651, 1696278547894514),
(1696278547894512, 1696278514562148),
(1696278547894513, 1696278527895410),
(1696278547894514, 1696278537845651);

INSERT INTO `publication` (`idpublication`, `description`, `image`, `Nombrelike`, `statut`, `IdMembre`, `datePub`, `videourl`, `fichierurl`) VALUES
(1696278514560951, 'descriptionOnyme', 'uneImageOnyme.jpg', 1, 'public', 1696278547894513, '2021-02-17 11:09:47', NULL, NULL),
(1696278514561268, 'descriptionFnac', 'uneImageFnac.jpg', 0, 'public', 1696278527895410, '2021-02-08 13:09:47', NULL, NULL),
(1696278514561638, 'descriptionDrake', 'uneImageDrake.jpg', 0, 'public', 1696278547894514, '2021-04-05 15:09:47', NULL, NULL),
(1696278514567029, 'descriptionDoe', 'uneImageDoe.jpg', 1, 'amis', 1696278547894512, '2021-04-07 15:09:47', NULL, NULL),
(1696278514567744, 'descriptionGoogle', 'uneImageGoogle.jpg', 1, 'public', 1696278514562148, '2021-04-01 14:17:07', NULL, NULL),
(1696278514568239, 'descriptionAmazon', 'uneImageAmazon.jpg', 2, 'public', 1696278537845651, '2021-04-14 00:00:00', NULL, NULL),
(1697475944022720, 'test pdf', NULL, 1, 'public', 1696278527895410, '2021-04-19 15:33:30', NULL, '607d86aa87ed8.pdf'),
(1697476033531564, 'test video', '', 1, 'public', 1696278527895410, '2021-04-19 15:34:55', '607d86ffe4ac3.mp4', NULL);

INSERT INTO `like_publication` (`IdMembre`, `idpublication`) VALUES
(1696278514562148, 1696278514567029),
(1696278514562148, 1696278514567744),
(1696278527895410, 1696278514560951),
(1696278537845651, 1696278514568239),
(1696278547894514, 1696278514568239);

INSERT INTO `commentaire` (`idcommentaire`, `description`, `Nombrelike`, `idpublication`, `IdMembre`, `dateCom`) VALUES
(1696278514562205, 'descriptionAmazon', 0, 1696278514561638, 1696278537845651, '2021-04-14 15:59:18'),
(1696278514565563, 'descriptionGoogle', 0, 1696278514561268, 1696278514562148, '2021-04-13 15:59:18'),
(1696278514565607, 'descriptionDoe', 2, 1696278514560951, 1696278547894512, '2021-04-12 15:59:18'),
(1696278514567789, 'comGoogle', 1, 1696278514567744, 1696278547894512, '2021-04-12 15:59:18'),
(1697542221288553, 'Belle photo', 0, 1696278514567029, 1696278514562148, '2021-04-20 09:06:57');

--

INSERT INTO `like_commentaire` (`IdMembre`, `idcommentaire`) VALUES
(1696278514562148, 1696278514565607),
(1696278514562148, 1696278514567789),
(1696278547894512, 1696278514565607);


INSERT INTO `image` (`idimage`, `titre`, `imageurl`, `IdMembre`) VALUES
(1696278514561349, 'MonImageDrake', 'MonImageDrake.jpg', 1696278547894514),
(1696278514565298, 'MonImageDoe', 'MonImageDoe.jpg', 1696278547894512),
(1696278514567560, 'MonImageFnac', 'MonImageFnac.jpg', 1696278527895410),
(1697541346958714, 'image du soir', '607e7a8c9020f.png', 1696278514562148),
(1697541410902537, 'Okapi', '607e7ab98e49e.jpg', 1696278514562148);


