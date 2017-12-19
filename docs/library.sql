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

-- Create user for database access.

CREATE USER 'librarian'@'localhost' IDENTIFIED BY 'booksrfun451';

-- Dumping database structure for library
CREATE DATABASE IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `library`;

-- give new user access to database

GRANT ALL PRIVILEGES ON `library` to 'librarian'@'%' WITH GRANT OPTION;


-- Dumping structure for table library.books
CREATE TABLE IF NOT EXISTS `books` (
  `bookID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `edition` int(11) DEFAULT NULL,
  `publisher` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.books: ~2 rows (approximately)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` (`bookID`, `title`, `edition`, `publisher`, `price`, `pages`) VALUES
	(1, 'Bibliotheque', 1, 'France', 5.00, 3425),
	(2, 'The Story of the Rug', 45, 'The Dude', 49.95, 20);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Dumping structure for table library.records
CREATE TABLE IF NOT EXISTS `records` (
  `recordID` int(11) NOT NULL AUTO_INCREMENT,
  `bookID` int(11) DEFAULT NULL,
  `studentID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `checkout_date` timestamp NULL DEFAULT NULL,
  `return_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`recordID`),
  KEY `FK_RECORD_BOOKID` (`bookID`),
  KEY `FK_RECORD_STUDENTID` (`studentID`),
  KEY `FK_RECORD_USERID` (`userID`),
  CONSTRAINT `FK_RECORD_BOOKID` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`),
  CONSTRAINT `FK_RECORD_STUDENTID` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`),
  CONSTRAINT `FK_RECORD_USERID` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.records: ~2 rows (approximately)
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
INSERT INTO `records` (`recordID`, `bookID`, `studentID`, `userID`, `checkout_date`, `return_date`) VALUES
	(1, 1, 10, 1, '2017-11-18 22:14:06', '0000-00-00 00:00:00'),
	(2, 2, 12, 1, '2017-11-12 22:14:27', '2017-11-18 22:14:44');
/*!40000 ALTER TABLE `records` ENABLE KEYS */;

-- Dumping structure for table library.students
CREATE TABLE IF NOT EXISTS `students` (
  `studentID` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.students: ~3 rows (approximately)
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` (`studentID`, `first_name`, `last_name`, `email`, `phone`) VALUES
	(10, 'John', 'Smith', 'Smith', '102019293'),
	(12, 'bobb', 'bob', 'bob', 'bob'),
	(13, 'Prince', 'Charming', 'Private', 'Unlisted');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;

-- Dumping structure for table library.users
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(8) DEFAULT NULL COMMENT 'If we''re not doing real security, why do you need a secure password? :P',
  `security_question` int(11) DEFAULT NULL,
  `security_answer` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table library.users: ~0 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`userID`, `name`, `password`, `security_question`, `security_answer`) VALUES
	(1, 'Libra', 'rian', 1, '1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
