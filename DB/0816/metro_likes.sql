-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: metro
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `board_id` int DEFAULT NULL,
  `member_username` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5cq36196j3ww17d7r95qdm4td` (`board_id`),
  KEY `FKnhtl4nomqixqaa74g0goqnfun` (`member_username`),
  CONSTRAINT `FK5cq36196j3ww17d7r95qdm4td` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`),
  CONSTRAINT `FKnhtl4nomqixqaa74g0goqnfun` FOREIGN KEY (`member_username`) REFERENCES `member` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (18,105,'member'),(19,105,'asdf'),(20,106,'asdf'),(21,195,'asdf'),(26,84,'asdf'),(28,81,'asdf'),(30,180,'asdf'),(47,135,'ㅋㅌㅊㅍ'),(49,105,'ㅋㅌㅊㅍ'),(50,195,'ㅋㅌㅊㅍ'),(52,37,'ㅋㅌㅊㅍ'),(53,64,'ㅋㅌㅊㅍ'),(54,84,'ㅋㅌㅊㅍ'),(55,66,'ㅋㅌㅊㅍ'),(56,77,'asdf'),(57,53,'asdf'),(58,68,'asdf'),(60,67,'asdf'),(61,190,'asdf'),(62,33,'asdf'),(63,34,'asdf'),(64,35,'asdf'),(65,36,'asdf'),(66,10,'asdf'),(67,11,'asdf'),(68,12,'asdf'),(69,17,'asdf'),(70,18,'asdf'),(71,19,'asdf'),(72,20,'asdf'),(73,79,'ㅂㅈㄷㄱ'),(74,80,'ㅂㅈㄷㄱ'),(75,117,'ㅂㅈㄷㄱ'),(78,121,'ㅂㅈㄷㄱ'),(79,123,'ㅂㅈㄷㄱ'),(80,129,'ㅂㅈㄷㄱ'),(81,118,'ㅂㅈㄷㄱ'),(82,207,'star'),(83,206,'star'),(84,205,'star'),(85,188,'abc'),(86,83,'abc'),(87,41,'manager'),(88,60,'manager'),(89,184,'manager'),(90,190,'manager'),(91,181,'manager'),(92,98,'abc'),(94,319,'ㅂㅈㄷㄱ'),(95,241,'asdf');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-16 18:02:27
