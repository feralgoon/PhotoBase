-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for photobase
CREATE DATABASE IF NOT EXISTS `photobase` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `photobase`;

-- Dumping structure for table photobase.aperture
CREATE TABLE IF NOT EXISTS `aperture` (
  `ApertureId` int(11) NOT NULL AUTO_INCREMENT,
  `ApertureName` varchar(10) NOT NULL,
  PRIMARY KEY (`ApertureId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.aperture: ~8 rows (approximately)
/*!40000 ALTER TABLE `aperture` DISABLE KEYS */;
INSERT INTO `aperture` (`ApertureId`, `ApertureName`) VALUES
	(25, 'f/6.3'),
	(26, 'f/2.8'),
	(27, 'f/11.0'),
	(28, 'f/1.8'),
	(29, 'f/16.0'),
	(30, 'f/5.6'),
	(31, 'f/4.5'),
	(32, 'f/5.0'),
	(33, 'f/2.5'),
	(34, 'f/3.2');
/*!40000 ALTER TABLE `aperture` ENABLE KEYS */;

-- Dumping structure for table photobase.cameramodel
CREATE TABLE IF NOT EXISTS `cameramodel` (
  `CameraModelId` int(11) NOT NULL AUTO_INCREMENT,
  `ManufacturerId` int(11) NOT NULL,
  `CameraModelName` varchar(50) NOT NULL,
  PRIMARY KEY (`CameraModelId`),
  KEY `cameramanufacturer_cameramodel_fk` (`ManufacturerId`),
  CONSTRAINT `cameramanufacturer_cameramodel_fk` FOREIGN KEY (`ManufacturerId`) REFERENCES `manufacturer` (`ManufacturerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.cameramodel: ~7 rows (approximately)
/*!40000 ALTER TABLE `cameramodel` DISABLE KEYS */;
INSERT INTO `cameramodel` (`CameraModelId`, `ManufacturerId`, `CameraModelName`) VALUES
	(24, 15, 'NIKON D7100'),
	(25, 15, 'NIKON D3X'),
	(26, 16, 'Canon EOS 5D Mark III'),
	(27, 16, 'Canon EOS 5DS'),
	(28, 16, 'Canon EOS Rebel T6'),
	(29, 16, 'Canon EOS 80D'),
	(30, 16, 'Canon EOS REBEL T5');
/*!40000 ALTER TABLE `cameramodel` ENABLE KEYS */;

-- Dumping structure for table photobase.exposuretime
CREATE TABLE IF NOT EXISTS `exposuretime` (
  `ExposureTimeId` int(11) NOT NULL AUTO_INCREMENT,
  `ExposureLength` varchar(10) NOT NULL,
  PRIMARY KEY (`ExposureTimeId`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.exposuretime: ~14 rows (approximately)
/*!40000 ALTER TABLE `exposuretime` DISABLE KEYS */;
INSERT INTO `exposuretime` (`ExposureTimeId`, `ExposureLength`) VALUES
	(28, '1/250 sec'),
	(29, '1/800 sec'),
	(30, '1/320 sec'),
	(31, '0.01 sec'),
	(32, '0.1 sec'),
	(33, '3.2 sec'),
	(34, '1/125 sec'),
	(35, '1/1000 sec'),
	(36, '0.5 sec'),
	(37, '1/640 sec'),
	(38, '1/160 sec'),
	(39, '1/400 sec'),
	(40, '1/1250 sec'),
	(41, '1/200 sec'),
	(42, '1/3200 sec'),
	(43, '1/5000 sec'),
	(44, '1/500 sec');
/*!40000 ALTER TABLE `exposuretime` ENABLE KEYS */;

-- Dumping structure for table photobase.iso
CREATE TABLE IF NOT EXISTS `iso` (
  `IsoId` int(11) NOT NULL AUTO_INCREMENT,
  `IsoSpeed` varchar(10) NOT NULL,
  PRIMARY KEY (`IsoId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.iso: ~7 rows (approximately)
/*!40000 ALTER TABLE `iso` DISABLE KEYS */;
INSERT INTO `iso` (`IsoId`, `IsoSpeed`) VALUES
	(22, '3200'),
	(23, '200'),
	(24, '400'),
	(25, '100'),
	(26, '50'),
	(27, '160'),
	(28, '1600');
/*!40000 ALTER TABLE `iso` ENABLE KEYS */;

-- Dumping structure for table photobase.lensmodel
CREATE TABLE IF NOT EXISTS `lensmodel` (
  `LensModelId` int(11) NOT NULL AUTO_INCREMENT,
  `LensModelName` varchar(50) NOT NULL,
  PRIMARY KEY (`LensModelId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.lensmodel: ~14 rows (approximately)
/*!40000 ALTER TABLE `lensmodel` DISABLE KEYS */;
INSERT INTO `lensmodel` (`LensModelId`, `LensModelName`) VALUES
	(23, '100.0-400.0 mm f/5.0-6.3'),
	(24, '70.0-200.0 mm f/2.8'),
	(25, '150-500mm'),
	(26, 'Leica Leitz Elmarit-R 1:2.8 / 28mm'),
	(27, 'TS-E17mm f/4L'),
	(28, 'EF16-35mm f/4L IS USM'),
	(29, 'EF75-300mm f/4-5.6'),
	(30, 'EF-S18-55mm f/3.5-5.6 III'),
	(31, '24.0-70.0 mm f/2.8'),
	(32, 'Leica Summicron-R 1:2 / 50mm'),
	(33, 'Leica Elmarit-R 1:2.8 / 28mm'),
	(34, 'TAMRON SP 35mm F/1.8 Di VC USD F012'),
	(35, '85mm F1.4 DG HSM | Art 016'),
	(36, 'EF50mm f/1.8 II');
/*!40000 ALTER TABLE `lensmodel` ENABLE KEYS */;

-- Dumping structure for table photobase.manufacturer
CREATE TABLE IF NOT EXISTS `manufacturer` (
  `ManufacturerId` int(11) NOT NULL AUTO_INCREMENT,
  `ManufacturerName` varchar(25) NOT NULL,
  PRIMARY KEY (`ManufacturerId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.manufacturer: ~2 rows (approximately)
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
INSERT INTO `manufacturer` (`ManufacturerId`, `ManufacturerName`) VALUES
	(15, 'NIKON CORPORATION'),
	(16, 'Canon');
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;

-- Dumping structure for table photobase.photo
CREATE TABLE IF NOT EXISTS `photo` (
  `PhotoId` int(11) NOT NULL AUTO_INCREMENT,
  `PhotographerId` int(11) NOT NULL,
  `LensModelId` int(11) NOT NULL,
  `CameraModelId` int(11) NOT NULL,
  `IsoId` int(11) NOT NULL,
  `ApertureId` int(11) NOT NULL,
  `ExposureTimeId` int(11) NOT NULL,
  `DateTaken` date DEFAULT NULL,
  `TimeTaken` time DEFAULT NULL,
  PRIMARY KEY (`PhotoId`),
  KEY `exposuretime_photo_fk` (`ExposureTimeId`),
  KEY `aperture_photo_fk` (`ApertureId`),
  KEY `iso_photo_fk` (`IsoId`),
  KEY `photographer_photo_fk` (`PhotographerId`),
  KEY `lensmodel_photo_fk` (`LensModelId`),
  KEY `cameramodel_photo_fk` (`CameraModelId`),
  CONSTRAINT `aperture_photo_fk` FOREIGN KEY (`ApertureId`) REFERENCES `aperture` (`ApertureId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cameramodel_photo_fk` FOREIGN KEY (`CameraModelId`) REFERENCES `cameramodel` (`CameraModelId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exposuretime_photo_fk` FOREIGN KEY (`ExposureTimeId`) REFERENCES `exposuretime` (`ExposureTimeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `iso_photo_fk` FOREIGN KEY (`IsoId`) REFERENCES `iso` (`IsoId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lensmodel_photo_fk` FOREIGN KEY (`LensModelId`) REFERENCES `lensmodel` (`LensModelId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `photographer_photo_fk` FOREIGN KEY (`PhotographerId`) REFERENCES `photographer` (`PhotographerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.photo: ~17 rows (approximately)
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` (`PhotoId`, `PhotographerId`, `LensModelId`, `CameraModelId`, `IsoId`, `ApertureId`, `ExposureTimeId`, `DateTaken`, `TimeTaken`) VALUES
	(27, 22, 23, 24, 22, 25, 28, '2017-12-10', '11:35:42'),
	(28, 23, 24, 25, 23, 26, 29, '2018-05-18', '16:18:20'),
	(29, 24, 25, 26, 24, 27, 30, '2017-06-30', '13:18:10'),
	(30, 25, 26, 26, 25, 28, 31, '2017-10-19', '09:43:19'),
	(31, 26, 27, 27, 25, 27, 32, '2017-10-24', '10:51:51'),
	(32, 26, 28, 27, 25, 27, 33, '2018-01-24', '12:18:34'),
	(33, 27, 29, 28, 25, 29, 34, '2018-01-31', '19:49:31'),
	(34, 27, 30, 28, 25, 30, 35, '2018-03-02', '05:14:50'),
	(35, 26, 27, 27, 25, 27, 36, '2018-06-04', '14:15:27'),
	(36, 23, 31, 25, 23, 31, 37, '2018-05-11', '13:29:49'),
	(37, 25, 32, 26, 26, 28, 29, '2018-05-12', '12:31:42'),
	(38, 25, 33, 26, 27, 28, 38, '2018-05-21', '09:44:14'),
	(39, 22, 23, 24, 28, 25, 39, '2018-05-14', '11:53:18'),
	(40, 28, 34, 29, 25, 32, 40, '2018-05-19', '14:23:18'),
	(41, 28, 35, 29, 25, 32, 28, '2018-05-17', '10:13:52'),
	(42, 29, 36, 30, 24, 28, 41, '2018-04-10', '20:24:23'),
	(43, 29, 36, 30, 24, 28, 34, '2018-04-10', '20:32:47'),
	(44, 28, 34, 29, 25, 33, 42, '2018-05-19', '15:27:48'),
	(45, 28, 34, 29, 25, 28, 43, '2018-05-19', '14:50:38'),
	(46, 22, 24, 24, 24, 26, 28, '2018-07-01', '08:58:56'),
	(47, 28, 34, 29, 23, 34, 44, '2018-05-20', '12:55:05');
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;

-- Dumping structure for table photobase.photographer
CREATE TABLE IF NOT EXISTS `photographer` (
  `PhotographerId` int(11) NOT NULL AUTO_INCREMENT,
  `PhotographerName` varchar(50) NOT NULL,
  PRIMARY KEY (`PhotographerId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- Dumping data for table photobase.photographer: ~8 rows (approximately)
/*!40000 ALTER TABLE `photographer` DISABLE KEYS */;
INSERT INTO `photographer` (`PhotographerId`, `PhotographerName`) VALUES
	(22, 'Mathias Appel'),
	(23, 'Steven Shepard'),
	(24, 'Dave Steadman'),
	(25, 'Markus Spiske'),
	(26, 'Michael D Beckwith'),
	(27, 'ADRIANA L ROJAS'),
	(28, 'ARTEM BELIAIKIN'),
	(29, 'Joseph Teague');
/*!40000 ALTER TABLE `photographer` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
