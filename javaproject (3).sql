-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 01 avr. 2026 à 15:45
-- Version du serveur : 8.4.7
-- Version de PHP : 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `javaproject`
--

-- --------------------------------------------------------

--
-- Structure de la table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `film_id` int NOT NULL,
  `user_id` int NOT NULL,
  `tickets` int NOT NULL,
  `booking_date` date NOT NULL,
  `timeslot` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `student` tinyint(1) NOT NULL,
  `total_price` double NOT NULL,
  `payment_status` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `film_id` (`film_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `booking`
--

INSERT INTO `booking` (`id`, `film_id`, `user_id`, `tickets`, `booking_date`, `timeslot`, `student`, `total_price`, `payment_status`) VALUES
(12, 1, 2, 5, '2026-11-29', '21:00:00', 1, 50, 1),
(10, 1, 2, 2, '2026-07-05', '21:00:00', 1, 20, 1),
(11, 1, 2, 2, '2026-06-06', '06:06:00', 0, 24, 1),
(13, 1, 2, 3, '2026-11-29', '21:00:00', 1, 30, 1),
(15, 1, 5, 5, '2026-07-05', '21:00:00', 1, 50, 1),
(16, 1, 2, 4, '2026-11-29', '21:00:00', 1, 40, 1),
(18, 3, 2, 5, '2026-10-28', '13:00:00', 1, 37.5, 1),
(19, 3, 2, 5, '2026-10-28', '13:00:00', 0, 50, 1),
(23, 4, 2, 5, '2026-04-03', '18:00:00', 1, 35, 1);

-- --------------------------------------------------------

--
-- Structure de la table `movie`
--

DROP TABLE IF EXISTS `movie`;
CREATE TABLE IF NOT EXISTS `movie` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `moviename` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `genre` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `publicationdate` date NOT NULL,
  `urltrailer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `runningtime` int NOT NULL,
  `price` double NOT NULL,
  `discount` double NOT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`,`moviename`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `movie`
--

INSERT INTO `movie` (`ID`, `moviename`, `genre`, `publicationdate`, `urltrailer`, `runningtime`, `price`, `discount`, `picture`) VALUES
(1, 'titanic', 'romance', '1998-01-23', 'https://www.youtube.com/watch?v=LuPB43YSgCs', 194, 12, 10, 'C:\\Users\\gallo\\OneDrive\\Documents\\GitHub\\javaprojectmovieapplication\\JavaProject\\src\\images\\titanic.jpg'),
(3, 'Avengers', 'Science Fiction', '2012-12-01', 'https://www.youtube.com/watch?v=eOrNdBpGMv8', 143, 10, 7.5, 'C:\\Users\\gallo\\OneDrive\\Documents\\GitHub\\javaprojectmovieapplication\\JavaProject\\src\\images\\avengers.jpg'),
(4, 'The Dark Knight', 'Science fiction', '2008-10-25', 'https://www.youtube.com/watch?v=EXeTwQWrcwY', 152, 15, 7, 'C:\\Users\\gallo\\OneDrive\\Documents\\GitHub\\javaprojectmovieapplication\\JavaProject\\src\\images\\The_Dark_Knight_.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `card_holder` varchar(100) NOT NULL,
  `card_number` varchar(25) NOT NULL,
  `expiration_date` varchar(7) NOT NULL,
  `cvv` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `payment`
--

INSERT INTO `payment` (`id`, `card_holder`, `card_number`, `expiration_date`, `cvv`) VALUES
(1, 'Alice Martin', '0000-0000-0000', '04/27', '111'),
(2, 'Bruno Petit', '4444-3333-2222', '12/25', '456'),
(3, 'Carla Nunes', '1234-5678-9012', '09/29', '789');

-- --------------------------------------------------------

--
-- Structure de la table `showtime`
--

DROP TABLE IF EXISTS `showtime`;
CREATE TABLE IF NOT EXISTS `showtime` (
  `id` int NOT NULL AUTO_INCREMENT,
  `movie_id` int NOT NULL,
  `day` date NOT NULL,
  `schedule` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `showtime`
--

INSERT INTO `showtime` (`id`, `movie_id`, `day`, `schedule`) VALUES
(1, 1, '1999-05-30', '17:07:00'),
(4, 1, '2026-11-30', '21:00:00'),
(3, 1, '2026-07-05', '21:00:00'),
(5, 1, '2026-11-29', '21:00:00'),
(6, 1, '2026-11-28', '21:00:00'),
(7, 2, '2026-11-28', '21:00:00'),
(8, 2, '2026-11-27', '21:00:00'),
(9, 1, '2026-06-06', '06:06:00'),
(10, 3, '2026-10-28', '06:00:00'),
(11, 3, '2026-10-28', '13:00:00'),
(15, 4, '2026-04-02', '14:00:00'),
(14, 4, '2026-04-02', '10:06:00'),
(16, 4, '2026-04-02', '20:00:00'),
(17, 4, '2026-04-03', '19:00:00'),
(18, 4, '2026-04-03', '18:00:00'),
(19, 4, '2026-04-03', '16:00:00'),
(20, 4, '2026-04-04', '18:00:00'),
(21, 4, '2026-04-04', '17:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `statut` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`ID`, `email`, `password`, `statut`) VALUES
(1, 'lirone@gmail.com', 'lirone', 'employe'),
(2, 'abc@gmail.com', 'password', 'customer'),
(3, 'eddie@gmail.com', 'eddie', 'customer'),
(4, 'laura@gmail.com', 'laura', 'customer'),
(5, 'eddiegallois494@gmail.com', 'eddie', 'customer');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
