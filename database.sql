-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 07, 2020 at 07:32 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `redakcija`
--

-- --------------------------------------------------------

--
-- Table structure for table `autori`
--

CREATE TABLE `autori` (
  `autor_id` int(11) NOT NULL,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) NOT NULL,
  `adresa` varchar(255) NOT NULL,
  `institucija` varchar(355) NOT NULL,
  `telefon` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `autori` (`autor_id`, `ime`, `prezime`, `adresa`, `institucija`, `telefon`, `email`) VALUES
(1, 'Pero', 'Peric', 'Svetog Save bb', 'Institucija', '+387 XX XXX XXX', 'pero@peric.com');

-- --------------------------------------------------------

--
-- Table structure for table `casopisi`
--

CREATE TABLE `casopisi` (
  `casopis_id` int(11) NOT NULL,
  `naziv` varchar(255) NOT NULL,
  `broj_izlazaka` int(11) NOT NULL,
  `godina_osnivanja` int(11) NOT NULL,
  `urednik` varchar(255) NOT NULL,
  `broj_osoba_redakcije` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `casopisi` (`casopis_id`, `naziv`, `broj_izlazaka`, `godina_osnivanja`, `urednik`, `broj_osoba_redakcije`) VALUES
(1, 'Casopis 1', 50, 2010, 'Jovo Jovic', 40);

-- --------------------------------------------------------

--
-- Table structure for table `radovi`
--

CREATE TABLE `radovi` (
  `rad_id` int(11) NOT NULL,
  `naziv` varchar(255) NOT NULL,
  `casopis_id` int(11) NOT NULL,
  `datum_prispjeca` DATE NOT NULL,
  `rb_godine` int(11) DEFAULT NULL,
  `rb_sveske` int(11) DEFAULT NULL,
  `pocetna_strana` int(11) DEFAULT NULL,
  `krajnja_Strana` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `radovi` (`rad_id`, `naziv`, `casopis_id`, `datum_prispjeca`, `rb_godine`, `rb_sveske`, `pocetna_strana`, `krajnja_Strana`) VALUES
(1, 'Rad 1', 1, '2020-01-01', 20, 1, 5, 20);

-- --------------------------------------------------------

--
-- Table structure for table `recezenti`
--

CREATE TABLE `recezenti` (
  `recezent_id` int(11) NOT NULL,
  `ime` varchar(255) NOT NULL,
  `prezime` varchar(255) NOT NULL,
  `adresa` varchar(355) NOT NULL,
  `telefon` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recezenti`
--

INSERT INTO `recezenti` (`recezent_id`, `ime`, `prezime`, `adresa`, `telefon`, `email`) VALUES
(1, 'Pero', 'Peric', 'Svetog Save bb', '+387 XX XXX XXX', 'pero@peric.com');

-- --------------------------------------------------------

--
-- Table structure for table `recezenti`
--

CREATE TABLE `rad_recezent` (
  `rad_recezent_id` int(11) NOT NULL,
  `rad_id` int(11) NOT NULL,
  `recezent_id` int(11) NOT NULL,
  `odluka` int(11) NOT NULL,
  `datum_slanja_recenzije` DATE NOT NULL,
  `datum_dobijanja_recenzije` DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `rad_recezent` (`rad_recezent_id`, `rad_id`, `recezent_id`, `odluka`, `datum_slanja_recenzije`, `datum_dobijanja_recenzije`) VALUES
(1, 1, 1, 1, '2020-02-02', '2020-02-05');

CREATE TABLE `autori_radovi` (
  `autori_radovi_id` int(11) NOT NULL,
  `rad_id` int(11) NOT NULL,
  `autor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `autori_radovi` (`autori_radovi_id`, `rad_id`, `autor_id`) VALUES
(1, 1, 1);
--
-- Indexes for dumped tables
--

--
-- Indexes for table `autori`
--
ALTER TABLE `autori`
  ADD PRIMARY KEY (`autor_id`);

--
-- Indexes for table `casopisi`
--
ALTER TABLE `casopisi`
  ADD PRIMARY KEY (`casopis_id`);

--
-- Indexes for table `radovi`
--
ALTER TABLE `radovi`
  ADD PRIMARY KEY (`rad_id`),
  ADD KEY `fk_casopis` (`casopis_id`);

--
-- Indexes for table `recezenti`
--
ALTER TABLE `recezenti`
  ADD PRIMARY KEY (`recezent_id`);

--
-- Indexes for table `rad_recezent`
--
ALTER TABLE `rad_recezent`
  ADD PRIMARY KEY (`rad_recezent_id`),
  ADD KEY `fk_rad` (`rad_id`),
  ADD KEY `fk_recezent` (`recezent_id`);

ALTER TABLE `autori_radovi`
  ADD PRIMARY KEY (`autori_radovi_id`),
  ADD KEY `fk_autor_rad` (`rad_id`),
  ADD KEY `fk_autor` (`autor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `autori`
--
ALTER TABLE `autori`
  MODIFY `autor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `casopisi`
--
ALTER TABLE `casopisi`
  MODIFY `casopis_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `radovi`
--
ALTER TABLE `radovi`
  MODIFY `rad_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `recezenti`
--
ALTER TABLE `recezenti`
  MODIFY `recezent_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `rad_recezent`
--
ALTER TABLE `rad_recezent`
  MODIFY `rad_recezent_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `autori_radovi`
  MODIFY `autori_radovi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `radovi`
--
ALTER TABLE `radovi`
  ADD CONSTRAINT `fk_casopis` FOREIGN KEY (`casopis_id`) REFERENCES `casopisi` (`casopis_id`) ON UPDATE CASCADE;


ALTER TABLE `rad_recezent`
  ADD CONSTRAINT `fk_rad` FOREIGN KEY (`rad_id`) REFERENCES `radovi` (`rad_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_recezent` FOREIGN KEY (`recezent_id`) REFERENCES `recezenti` (`recezent_id`) ON UPDATE CASCADE;

ALTER TABLE `autori_radovi`
  ADD CONSTRAINT `fk_autor_rad` FOREIGN KEY (`rad_id`) REFERENCES `radovi` (`rad_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_autor` FOREIGN KEY (`autor_id`) REFERENCES `autori` (`autor_id`) ON UPDATE CASCADE;
COMMIT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
