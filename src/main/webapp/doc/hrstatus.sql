    Copyright (C) 2012  Filippe Costa Spolti

	This file is part of Hrstatus.

    Hrstatus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

-- MySQL dump 10.13  Distrib 5.6.19, for Linux (x86_64)
--
-- Host: localhost    Database: hrstatus
-- ------------------------------------------------------
-- Server version	5.5.41-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Configurations`
--

DROP TABLE IF EXISTS `Configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Configurations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dests` varchar(255) DEFAULT NULL,
  `difference` int(11) DEFAULT NULL,
  `jndiMail` varchar(255) DEFAULT NULL,
  `mailFrom` varchar(255) DEFAULT NULL,
  `sendNotification` bit(1) DEFAULT NULL,
  `ntpServer` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `updateNtpIsActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Configurations`
--

LOCK TABLES `Configurations` WRITE;
/*!40000 ALTER TABLE `Configurations` DISABLE KEYS */;
INSERT INTO `Configurations` VALUES (1,'example@hrstatus.com.br',50,'java:jboss/mail/TestGmail','hrstatus@hrstatus.com.br','\0','1.2.3.4','NO REPLY - Status Horario de Verao','\0');
/*!40000 ALTER TABLE `Configurations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PassExpire`
--

DROP TABLE IF EXISTS `PassExpire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PassExpire` (
  `username` varchar(255) NOT NULL,
  `changeTime` varchar(255) DEFAULT NULL,
  `expireTime` varchar(255) DEFAULT NULL,
  `newPwd` varchar(255) DEFAULT NULL,
  `oldPwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PassExpire`
--

LOCK TABLES `PassExpire` WRITE;
/*!40000 ALTER TABLE `PassExpire` DISABLE KEYS */;
/*!40000 ALTER TABLE `PassExpire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Servidores`
--

DROP TABLE IF EXISTS `Servidores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Servidores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SO` varchar(255) DEFAULT NULL,
  `clientTime` varchar(255) DEFAULT NULL,
  `difference` bigint(20) DEFAULT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `lastCheck` varchar(255) DEFAULT NULL,
  `logDir` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `serverTime` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `suCommand` varchar(255) DEFAULT NULL,
  `trClass` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `verify` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Servidores`
--

LOCK TABLES `Servidores` WRITE;
/*!40000 ALTER TABLE `Servidores` DISABLE KEYS */;
/*!40000 ALTER TABLE `Servidores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SERVER`
--

DROP TABLE IF EXISTS `USER_SERVER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_SERVER` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  KEY `FK13567237F672A072` (`username`),
  KEY `FK13567237D35D7D09` (`id`),
  CONSTRAINT `FK13567237D35D7D09` FOREIGN KEY (`id`) REFERENCES `Servidores` (`id`),
  CONSTRAINT `FK13567237F672A072` FOREIGN KEY (`username`) REFERENCES `Users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SERVER`
--

LOCK TABLES `USER_SERVER` WRITE;
/*!40000 ALTER TABLE `USER_SERVER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SERVER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `username` varchar(255) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `firstLogin` bit(1) DEFAULT NULL,
  `lastLogin` varchar(255) DEFAULT NULL,
  `mail` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('admin','ROLE_ADMIN',' ','\0','Sat Feb 07 01:32:35 BRST 2015','administrador@example.com','Administrador do Sistema','89794b621a313bb59eed0d9f0f4e8205');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancoDados`
--

DROP TABLE IF EXISTS `bancoDados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancoDados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Difference` bigint(20) DEFAULT NULL,
  `clientTime` varchar(255) DEFAULT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `instance` varchar(255) DEFAULT NULL,
  `db_name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `lastCheck` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `queryDate` varchar(255) DEFAULT NULL,
  `serverTime` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trClass` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `vendor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancoDados`
--

LOCK TABLES `bancoDados` WRITE;
/*!40000 ALTER TABLE `bancoDados` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancoDados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lockedResources`
--

DROP TABLE IF EXISTS `lockedResources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lockedResources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recurso` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lockedResources`
--

LOCK TABLES `lockedResources` WRITE;
/*!40000 ALTER TABLE `lockedResources` DISABLE KEYS */;
/*!40000 ALTER TABLE `lockedResources` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-07  1:34:47-- User Configurations
CREATE USER hrstatus@localhost IDENTIFIED BY 'P@ssw0rd';
GRANT CREATE, ALTER, SELECT,INSERT,UPDATE,DELETE ON hrstatus.* TO 'hrstatus'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
