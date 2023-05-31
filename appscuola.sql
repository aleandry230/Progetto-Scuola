-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 31, 2023 alle 10:22
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appscuola`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `account`
--

INSERT INTO `account` (`id`, `name`, `surname`, `email`, `password`, `class_id`) VALUES
(1, 'Andrea', 'Braia', 'andrix.braia@gmail.com', 'Gemellini04', 1),
(2, 'Annamaria', 'Pedelini', 'test@gmail.com', '1234', NULL),
(3, 'Simona', 'Toma', 'test1@gmail.com', '1234', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `classe`
--

CREATE TABLE `classe` (
  `id` int(11) NOT NULL,
  `class_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `classe`
--

INSERT INTO `classe` (`id`, `class_name`) VALUES
(1, '4Q'),
(2, '5Q'),
(3, '4H'),
(4, '4U'),
(5, '5H');

-- --------------------------------------------------------

--
-- Struttura della tabella `classi_insegnanti`
--

CREATE TABLE `classi_insegnanti` (
  `id` int(11) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `class_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `classi_insegnanti`
--

INSERT INTO `classi_insegnanti` (`id`, `teacher_id`, `class_id`) VALUES
(1, '1', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnanti`
--

CREATE TABLE `insegnanti` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `insegnanti`
--

INSERT INTO `insegnanti` (`id`, `name`, `surname`, `email`, `password`) VALUES
(1, 'Annamaria\n', 'Pedelini', 'test@gmail.com', '1234');

-- --------------------------------------------------------

--
-- Struttura della tabella `voti`
--

CREATE TABLE `voti` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `vote` float NOT NULL,
  `subject` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `voti`
--

INSERT INTO `voti` (`id`, `student_id`, `vote`, `subject`) VALUES
(1, 1, 8, 'Matematica');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `classe`
--
ALTER TABLE `classe`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `classi_insegnanti`
--
ALTER TABLE `classi_insegnanti`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `insegnanti`
--
ALTER TABLE `insegnanti`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `voti`
--
ALTER TABLE `voti`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `classe`
--
ALTER TABLE `classe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `classi_insegnanti`
--
ALTER TABLE `classi_insegnanti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `insegnanti`
--
ALTER TABLE `insegnanti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `voti`
--
ALTER TABLE `voti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
