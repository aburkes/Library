-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               10.0.31-MariaDB-0ubuntu0.16.04.2 - Ubuntu 16.04
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for library
CREATE DATABASE IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `library`;

-- Dumping structure for table library.books
CREATE TABLE IF NOT EXISTS `books` (
  `bookID` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `edition` int(11) DEFAULT NULL,
  `publisher` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.books: ~0 rows (approximately)
DELETE FROM `books`;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Dumping structure for table library.records
CREATE TABLE IF NOT EXISTS `records` (
  `recordID` int(11) NOT NULL,
  `bookID` int(11) DEFAULT NULL,
  `studentID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `checkout_date` datetime DEFAULT NULL,
  `return_date` datetime NOT NULL,
  PRIMARY KEY (`recordID`),
  KEY `FK_RECORD_BOOKID` (`bookID`),
  KEY `FK_RECORD_STUDENTID` (`studentID`),
  KEY `FK_RECORD_USERID` (`userID`),
  CONSTRAINT `FK_RECORD_BOOKID` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`),
  CONSTRAINT `FK_RECORD_STUDENTID` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`),
  CONSTRAINT `FK_RECORD_USERID` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.records: ~0 rows (approximately)
DELETE FROM `records`;
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
/*!40000 ALTER TABLE `records` ENABLE KEYS */;

-- Dumping structure for table library.students
CREATE TABLE IF NOT EXISTS `students` (
  `studentID` int(11) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.students: ~0 rows (approximately)
DELETE FROM `students`;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
/*!40000 ALTER TABLE `students` ENABLE KEYS */;

-- Dumping structure for table library.users
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(8) DEFAULT NULL COMMENT 'If we''re not doing real security, why do you need a secure password? :P',
  `security_question` int(11) DEFAULT NULL,
  `security_anwer` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.users: ~0 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
