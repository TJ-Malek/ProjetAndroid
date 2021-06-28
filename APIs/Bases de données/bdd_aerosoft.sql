-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 23 juin 2021 à 10:08
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `aerosoft`
--
DROP DATABASE IF EXISTS aerosoft;
CREATE DATABASE aerosoft;
-- --------------------------------------------------------

--
-- Structure de la table `aeroport`
--

CREATE TABLE `aeroport` (
  `IdAeroport` varchar(3) NOT NULL,
  `NomAeroport` varchar(50) NOT NULL,
  `NomVilleDesservie` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `aeroport`
--

INSERT INTO `aeroport` (`IdAeroport`, `NomAeroport`, `NomVilleDesservie`) VALUES
('BAS', 'Poretta', 'Bastia'),
('BLA', 'Blagnac', 'Toulouse'),
('BRI', 'Brive', 'Brive'),
('CDG', 'Roissy', 'Paris'),
('GRE', 'Saint Geoir', 'Grenoble'),
('LYS', 'Saint Exupery', 'Lyon'),
('NAN', 'Saint Herblain', 'Nantes'),
('NIC', 'Nice Cote D Azur', 'Nice'),
('ORL', 'Orly', 'Paris');

-- --------------------------------------------------------

--
-- Structure de la table `affectation`
--

CREATE TABLE `affectation` (
  `IdAffectation` varchar(30) NOT NULL,
  `NumVol` varchar(50) NOT NULL,
  `DateVol` date DEFAULT NULL,
  `AffectationCode` tinyint(1) DEFAULT NULL,
  `NumAvion` int(11) NOT NULL,
  `IdPilote` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `affectation`
--

INSERT INTO `affectation` (`IdAffectation`, `NumVol`, `DateVol`, `AffectationCode`, `NumAvion`, `IdPilote`) VALUES
('IT1002001-04-06', 'IT100', '2001-04-06', 1, 100, 1),
('IT1002001-04-07', 'IT100', '2001-04-07', 1, 101, 2),
('IT1012001-04-06', 'IT101', '2001-04-06', 1, 100, 2),
('IT1012001-04-07', 'IT101', '2001-04-07', 0, 103, 4),
('IT1022001-04-06', 'IT102', '2001-04-06', 0, 101, 1),
('IT1022001-04-07', 'IT102', '2001-04-07', 1, 102, 3),
('IT1032001-04-06', 'IT103', '2001-04-06', 1, 105, 3),
('IT1032001-04-07', 'IT103', '2001-04-07', 1, 104, 2),
('IT1042001-04-06', 'IT104', '2001-04-06', 1, 105, 3),
('IT1042001-04-07', 'IT104', '2001-04-07', 1, 107, 8),
('IT1052001-04-06', 'IT105', '2001-04-06', 1, 107, 7),
('IT1052001-04-07', 'IT105', '2001-04-07', 1, 106, 7),
('IT1062001-04-06', 'IT106', '2001-04-06', 1, 109, 8),
('IT1062001-04-07', 'IT106', '2001-04-07', 1, 104, 5),
('IT1072001-04-06', 'IT107', '2001-04-06', 1, 106, 9),
('IT1072001-04-07', 'IT107', '2001-04-07', 1, 103, 8),
('IT1082001-04-06', 'IT108', '2001-04-06', 1, 106, 9),
('IT1082001-04-07', 'IT108', '2001-04-07', 0, 106, 5),
('IT1092001-04-06', 'IT109', '2001-04-06', 0, 107, 7),
('IT1092001-04-07', 'IT109', '2001-04-07', 0, 105, 1),
('IT1102001-04-06', 'IT110', '2001-04-06', 0, 102, 2),
('IT1102001-04-07', 'IT110', '2001-04-07', 0, 104, 3),
('IT1112001-04-06', 'IT111', '2001-04-06', 0, 101, 4),
('IT1112001-04-07', 'IT111', '2001-04-07', 0, 100, 8);

-- --------------------------------------------------------

--
-- Structure de la table `avion`
--

CREATE TABLE `avion` (
  `NumAvion` int(11) NOT NULL,
  `TypeAvion` varchar(50) NOT NULL,
  `BaseAeroport` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `avion`
--

INSERT INTO `avion` (`NumAvion`, `TypeAvion`, `BaseAeroport`) VALUES
(100, 'A320', 'NIC'),
(101, 'B707', 'CDG'),
(102, 'A320', 'BLA'),
(103, 'DC10', 'BLA'),
(104, 'B747', 'ORL'),
(105, 'A320', 'GRE'),
(106, 'ATR42', 'CDG'),
(107, 'B727', 'LYS'),
(108, 'B727', 'NAN'),
(109, 'A340', 'BAS');

-- --------------------------------------------------------

--
-- Structure de la table `constructeur`
--

CREATE TABLE `constructeur` (
  `IdConstructeur` int(11) NOT NULL,
  `NomConstructeur` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `constructeur`
--

INSERT INTO `constructeur` (`IdConstructeur`, `NomConstructeur`) VALUES
(1, 'Aerospatiale'),
(2, 'Boeing'),
(3, 'Cessna'),
(4, 'Douglas');

-- --------------------------------------------------------

--
-- Structure de la table `detailavion`
--

CREATE TABLE `detailavion` (
  `TypeAvion` varchar(50) NOT NULL,
  `Capacite` int(11) NOT NULL,
  `IdConstructeur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `detailavion`
--

INSERT INTO `detailavion` (`TypeAvion`, `Capacite`, `IdConstructeur`) VALUES
('A320', 300, 1),
('A340', 350, 1),
('ATR42', 50, 1),
('B707', 250, 2),
('B727', 300, 2),
('B747', 400, 2),
('DC10', 200, 4);

-- --------------------------------------------------------

--
-- Structure de la table `pilote`
--

CREATE TABLE `pilote` (
  `IdPilote` int(11) NOT NULL,
  `NomPilote` varchar(50) NOT NULL,
  `PrenomPilote` varchar(50) NOT NULL,
  `Matricule` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `pilote`
--

INSERT INTO `pilote` (`IdPilote`, `NomPilote`, `PrenomPilote`, `Matricule`) VALUES
(1, 'BARBARO', 'EVA', '154MD'),
(2, 'ZELLAMA', 'FAYROUZ', '148OP'),
(3, 'DACRUZ', 'SANDRA', '789UY'),
(4, 'KHALFI', 'HEDIA', '421OP'),
(5, 'MONDON', 'NATHALIE', '419TY'),
(7, 'DAVID', 'XAVIER', '412OP'),
(8, 'JARRY', 'KEVIN', '789RE'),
(9, 'CONFRERE', 'ROMAIN', '148YT');

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `IdRole` varchar(20) NOT NULL,
  `RoleNom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`IdRole`, `RoleNom`) VALUES
('01011', 'Chargé Clientèle'),
('11111', 'Utilisateur'),
('44444', 'Technicien d\'exploitation'),
('55555', 'Administrateur');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `IdUtilisateur` varchar(20) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Mail` varchar(50) NOT NULL,
  `MotDePasse` varchar(50) NOT NULL,
  `Statut` tinyint(1) DEFAULT NULL,
  `IdRole` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`IdUtilisateur`, `Nom`, `Prenom`, `Mail`, `MotDePasse`, `Statut`, `IdRole`) VALUES
('14562148', 'Durand', 'Adèle', 'clientele@aerosoft.com', 'pass123', 1, '11111'),
('27895410', 'Thomas', 'Agnès ', 'technique@aerosoft.com', 'pass123', 1, '11111'),
('37845651', 'Bernard', 'Jonathan', 'admin@aerosoft.com', 'pass123', 1, '55555'),
('47894512', 'Martin', 'Louis', 'pilote@aerosoft.com', 'pass123', 1, '11111');

-- --------------------------------------------------------

--
-- Structure de la table `vol`
--

CREATE TABLE `vol` (
  `NumVol` varchar(50) NOT NULL,
  `AeroportDept` varchar(3) NOT NULL,
  `HDepart` time NOT NULL,
  `AeroportArr` varchar(3) NOT NULL,
  `HArrivee` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `vol`
--

INSERT INTO `vol` (`NumVol`, `AeroportDept`, `HDepart`, `AeroportArr`, `HArrivee`) VALUES
('IT100', 'NIC', '07:00:00', 'CDG', '09:00:00'),
('IT101', 'ORL', '11:00:00', 'BLA', '12:00:00'),
('IT102', 'CDG', '12:00:00', 'NIC', '14:00:00'),
('IT103', 'GRE', '09:00:00', 'BLA', '11:00:00'),
('IT104', 'BLA', '17:00:00', 'GRE', '19:00:00'),
('IT105', 'LYS', '06:00:00', 'ORL', '07:00:00'),
('IT106', 'BAS', '10:00:00', 'ORL', '13:00:00'),
('IT107', 'NIC', '07:00:00', 'BRI', '08:00:00'),
('IT108', 'BRI', '19:00:00', 'ORL', '20:00:00'),
('IT109', 'NIC', '18:00:00', 'ORL', '19:00:00'),
('IT110', 'ORL', '15:00:00', 'NIC', '16:00:00'),
('IT111', 'NIC', '17:00:00', 'NAN', '19:00:00');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `aeroport`
--
ALTER TABLE `aeroport`
  ADD PRIMARY KEY (`IdAeroport`);

--
-- Index pour la table `affectation`
--
ALTER TABLE `affectation`
  ADD PRIMARY KEY (`IdAffectation`),
  ADD KEY `NumVol` (`NumVol`),
  ADD KEY `NumAvion` (`NumAvion`),
  ADD KEY `IdPilote` (`IdPilote`);

--
-- Index pour la table `avion`
--
ALTER TABLE `avion`
  ADD PRIMARY KEY (`NumAvion`),
  ADD KEY `TypeAvion` (`TypeAvion`),
  ADD KEY `BaseAeroport` (`BaseAeroport`);

--
-- Index pour la table `constructeur`
--
ALTER TABLE `constructeur`
  ADD PRIMARY KEY (`IdConstructeur`);

--
-- Index pour la table `detailavion`
--
ALTER TABLE `detailavion`
  ADD PRIMARY KEY (`TypeAvion`),
  ADD KEY `IdConstructeur` (`IdConstructeur`);

--
-- Index pour la table `pilote`
--
ALTER TABLE `pilote`
  ADD PRIMARY KEY (`IdPilote`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`IdRole`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`IdUtilisateur`),
  ADD KEY `IdRole` (`IdRole`);

--
-- Index pour la table `vol`
--
ALTER TABLE `vol`
  ADD PRIMARY KEY (`NumVol`),
  ADD KEY `AeroportDept` (`AeroportDept`),
  ADD KEY `AeroportArr` (`AeroportArr`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avion`
--
ALTER TABLE `avion`
  MODIFY `NumAvion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- AUTO_INCREMENT pour la table `constructeur`
--
ALTER TABLE `constructeur`
  MODIFY `IdConstructeur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `pilote`
--
ALTER TABLE `pilote`
  MODIFY `IdPilote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `affectation`
--
ALTER TABLE `affectation`
  ADD CONSTRAINT `affectation_ibfk_1` FOREIGN KEY (`NumVol`) REFERENCES `vol` (`NumVol`) ON DELETE CASCADE,
  ADD CONSTRAINT `affectation_ibfk_2` FOREIGN KEY (`NumAvion`) REFERENCES `avion` (`NumAvion`) ON DELETE CASCADE,
  ADD CONSTRAINT `affectation_ibfk_3` FOREIGN KEY (`IdPilote`) REFERENCES `pilote` (`IdPilote`) ON DELETE CASCADE;

--
-- Contraintes pour la table `avion`
--
ALTER TABLE `avion`
  ADD CONSTRAINT `avion_ibfk_1` FOREIGN KEY (`TypeAvion`) REFERENCES `detailavion` (`TypeAvion`) ON DELETE CASCADE,
  ADD CONSTRAINT `avion_ibfk_2` FOREIGN KEY (`BaseAeroport`) REFERENCES `aeroport` (`IdAeroport`) ON DELETE SET NULL;

--
-- Contraintes pour la table `detailavion`
--
ALTER TABLE `detailavion`
  ADD CONSTRAINT `detailavion_ibfk_1` FOREIGN KEY (`IdConstructeur`) REFERENCES `constructeur` (`IdConstructeur`) ON DELETE CASCADE;

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`IdRole`) REFERENCES `roles` (`IdRole`) ON DELETE SET NULL;

--
-- Contraintes pour la table `vol`
--
ALTER TABLE `vol`
  ADD CONSTRAINT `vol_ibfk_1` FOREIGN KEY (`AeroportDept`) REFERENCES `aeroport` (`IdAeroport`) ON DELETE CASCADE,
  ADD CONSTRAINT `vol_ibfk_2` FOREIGN KEY (`AeroportArr`) REFERENCES `aeroport` (`IdAeroport`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
