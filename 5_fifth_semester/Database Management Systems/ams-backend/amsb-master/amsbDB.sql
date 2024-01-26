-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: ams
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flight_number` varchar(7) NOT NULL,
  `departure_airport` varchar(10) NOT NULL,
  `destination_airport` varchar(10) NOT NULL,
  `departure_time` datetime NOT NULL,
  `arrival_time` datetime NOT NULL,
  `gate_number` varchar(15) NOT NULL,
  `plane_registration` varchar(6) NOT NULL,
  `status` varchar(50) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_flight_number` (`flight_number`),
  KEY `plane_registration` (`plane_registration`),
  CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`plane_registration`) REFERENCES `plane` (`plane_registration`) ON DELETE SET DEFAULT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (1,'TK101','JFK','ADB','2023-11-01 09:30:00','2024-11-01 15:45:00','Gate A12','TC-JJA','onflight',1215.12),(2,'TK202','LHR','ADB','2023-11-06 14:00:00','2024-11-06 17:30:00','Gate B05','TC-LNB','onflight',1247.36),(3,'TK303','CDG','ADB','2023-11-11 07:45:00','2024-11-11 11:15:00','Gate C08','TC-LOA','onflight',1091.41),(4,'TK404','FRA','ADB','2023-11-16 16:15:00','2024-11-16 20:30:00','Gate D14','TC-ANA','onflight',1214.98),(5,'TK505','AMS','ADB','2023-11-21 11:00:00','2024-11-21 15:15:00','Gate E03','TC-LRA','onflight',1300.66),(6,'TK606','JFK','ADB','2023-12-01 10:30:00','2024-12-01 14:30:00','Gate F12','TC-ABC','onflight',1358.36),(7,'TK707','CDG','ADB','2023-12-05 13:15:00','2024-12-05 16:45:00','Gate B08','TC-XYZ','onflight',1389.82),(8,'TK808','LHR','ADB','2023-12-10 08:45:00','2024-12-10 12:30:00','Gate C15','TC-DEF','onflight',1374.04),(9,'TK909','AMS','ADB','2023-12-15 15:30:00','2024-12-15 19:15:00','Gate D22','TC-GHI','onflight',1200.74),(10,'TK1010','FRA','ADB','2023-12-20 12:00:00','2024-12-20 16:30:00','Gate E10','TC-JKL','onflight',1381.58),(11,'TK1111','JFK','ADB','2023-12-25 05:45:00','2024-12-25 11:00:00','Gate A15','TC-MNO','onflight',1305.66),(12,'TK1212','LHR','ADB','2023-12-30 15:30:00','2024-12-30 20:45:00','Gate B12','TC-PQR','onflight',1383.55),(13,'TK1313','CDG','ADB','2024-01-04 10:15:00','2024-01-04 14:45:00','Gate C18','TC-STU','onflight',1000.78),(14,'TK1414','FRA','ADB','2024-01-09 08:00:00','2024-01-09 13:30:00','Gate D25','TC-VWX','scheduled',1353.26),(15,'TK1515','AMS','ADB','2024-01-14 12:45:00','2024-01-14 17:15:00','Gate E15','TC-YZA','scheduled',1263.95),(16,'TK1616','JFK','ADB','2024-01-19 06:30:00','2024-01-19 12:45:00','Gate F22','TC-ZYX','scheduled',1259.98),(17,'TK2020','AMS','ADB','2024-01-24 13:15:00','2024-01-24 16:30:00','Gate G25','TC-SSD','scheduled',1008.03),(18,'TK2121','JFK','ADB','2024-01-29 15:45:00','2024-01-29 20:00:00','Gate H30','TC-FED','scheduled',1260.23),(19,'TK2222','FRA','ADB','2024-02-03 10:30:00','2024-02-03 15:45:00','Gate I35','TC-MON','scheduled',1277.04),(20,'TK2323','CDG','ADB','2024-02-08 08:15:00','2024-02-08 12:30:00','Gate J40','TC-PRQ','scheduled',1104.54),(21,'TK2424','LHR','ADB','2024-02-13 14:00:00','2024-02-13 18:15:00','Gate K45','TC-SUT','scheduled',1191.55),(22,'TK2525','AMS','ADB','2024-02-18 07:45:00','2024-02-18 12:00:00','Gate L50','TC-VXW','scheduled',1144.14),(23,'TK2626','FRA','ADB','2024-02-23 12:30:00','2024-02-23 17:00:00','Gate M55','TC-YZS','scheduled',1146.04),(24,'TK2727','JFK','ADB','2024-02-28 05:15:00','2024-02-28 10:30:00','Gate N60','TC-ACB','scheduled',1297.81),(25,'TK2828','CDG','ADB','2024-03-04 10:00:00','2024-03-04 15:15:00','Gate O65','TC-KEF','scheduled',1050.90),(26,'TK2929','LHR','ADB','2024-03-09 15:30:00','2024-03-09 20:45:00','Gate P70','TC-SHI','scheduled',1361.07),(27,'TK3030','AMS','ADB','2024-03-14 11:15:00','2024-03-14 15:30:00','Gate Q75','TC-KKL','scheduled',1152.66),(28,'TK3131','FRA','ADB','2024-03-19 06:00:00','2024-03-19 11:15:00','Gate R80','TC-MMO','scheduled',1180.10),(29,'TK3232','AMS','ADB','2024-03-24 13:45:00','2024-03-24 18:00:00','Gate S85','TC-CRR','scheduled',1442.51),(30,'TK3333','CDG','ADB','2024-03-29 08:30:00','2024-03-29 13:45:00','Gate T90','TC-UTU','scheduled',1172.25),(31,'TK3434','FRA','ADB','2024-04-03 14:15:00','2024-04-03 18:30:00','Gate U95','TC-PWX','scheduled',1033.73),(32,'TK3535','ADB','ADA','2024-04-08 16:00:00','2024-04-08 21:15:00','Gate V100','TC-BBC','scheduled',1151.89),(37,'TK4040','ADA','ADB','2024-05-03 09:45:00','2024-05-03 14:00:00','Gate AA125','TC-ERR','scheduled',1439.11),(38,'TK105','JFK','ADB','2023-11-01 15:30:00','2023-11-01 21:45:00','Gate A1','TC-YSF','completed',1215.12),(39,'TK3331','SFO','ADB','2024-08-01 12:00:00','2024-08-02 01:00:00','Gate A1','TC-SMA','scheduled',1401.00);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luggage`
--

DROP TABLE IF EXISTS `luggage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `luggage` (
  `luggage_id` varchar(12) NOT NULL,
  `baggage_allowance` float DEFAULT NULL,
  `weight` float NOT NULL,
  `piece` int NOT NULL,
  PRIMARY KEY (`luggage_id`),
  UNIQUE KEY `luggage_id` (`luggage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage`
--

LOCK TABLES `luggage` WRITE;
/*!40000 ALTER TABLE `luggage` DISABLE KEYS */;
INSERT INTO `luggage` VALUES ('098765432020',35,35.5,2),('098765432080',25,25.8,2),('098765432090',15,15.8,1),('098765432101',15,15.9,1),('098765432111',35,35.4,2),('098765432151',35,35.7,2),('098765432161',45,45.1,3),('098765432838',35,35.6,2),('098765432929',35,35.8,2),('109876543010',25,25.9,2),('109876543050',25,25.1,1),('109876543060',35,35.6,2),('109876543070',45,45.3,3),('109876543210',25,25.1,1),('109876543979',45,45.2,3),('109876543989',25,25.4,1),('202051003400',45,-1,-1),('210987654737',25,25.3,1),('210987654789',35,35.2,2),('210987654828',25,25.7,1),('210987654878',35,35.5,2),('210987654888',45,45.3,3),('210987654919',25,0,0),('210987654949',15,15.8,1),('210987654959',25,25.7,1),('210987654969',35,35.8,2),('235082310825',25,-1,-1),('276016440759',45,-1,-1),('321098765636',15,15.6,1),('321098765678',45,44.9,3),('321098765727',15,15.5,1),('321098765777',25,25.4,1),('321098765787',35,35.6,2),('321098765808',45,45.2,3),('321098765818',15,15.1,1),('321098765848',45,45.1,3),('321098765858',15,15.4,1),('321098765868',25,25.3,1),('384911568572',45,-1,-1),('432109876535',25,25.8,2),('432109876567',15,15.7,1),('432109876626',25,25.4,1),('432109876676',15,15.6,1),('432109876686',25,25.7,1),('432109876707',25,25.6,1),('432109876717',25,25.7,1),('432109876747',35,35.2,2),('432109876757',45,45.3,3),('432109876767',15,15.5,1),('435294567604',15,-1,-1),('448512958857',25,-1,-1),('494672440630',45,-1,-1),('513682869333',45,-1,-1),('521184581209',45,-1,-1),('541256977614',45,-1,-1),('543210987343',45,45.5,3),('543210987434',45,45.1,3),('543210987456',25,25.4,1),('543210987525',45,45.4,3),('543210987575',45,45.1,3),('543210987585',15,15.2,1),('543210987606',35,35.3,2),('543210987616',45,45.3,3),('543210987646',25,25.6,1),('543210987656',35,35.5,2),('543210987666',45,45.4,3),('595402327690',15,-1,-1),('654321098242',35,35.4,2),('654321098333',35,35.3,2),('654321098345',45,44.8,3),('654321098424',35,35.9,2),('654321098474',35,35.7,2),('654321098484',45,45.4,3),('654321098505',15,15.6,1),('654321098515',35,35.7,2),('654321098545',15,15.9,1),('654321098555',25,25.8,2),('654321098565',35,35.2,2),('663150083975',45,-1,-1),('677629774685',15,-1,-1),('684799173919',15,-1,-1),('687100075239',45,23.4,2),('687399358214',45,32.3,2),('705296022691',45,-1,-1),('751165338501',15,15.2,1),('765432109141',25,25.4,1),('765432109232',25,25.9,2),('765432109234',15,15.3,1),('765432109323',25,25.2,1),('765432109373',25,25.2,1),('765432109383',35,35.9,2),('765432109404',25,25.3,1),('765432109414',25,25.5,1),('765432109444',25,25.5,1),('765432109454',15,15.6,1),('765432109464',25,25.7,1),('876543210040',15,15.7,1),('876543210123',35,35.1,2),('876543210131',15,15.2,1),('876543210222',15,15.4,1),('876543210272',15,15.7,1),('876543210282',25,25.6,1),('876543210303',45,45,3),('876543210313',15,15.8,1),('876543210353',25,25.3,1),('876543210363',15,15.9,1),('914411008137',15,-1,-1),('946764819451',45,-1,-1),('977826109495',15,-1,-1),('987654321012',25,25.2,1),('987654321030',45,45.2,3),('987654321102',35,35,2),('987654321121',45,45.5,3),('987654321171',25,25.5,1),('987654321181',15,15.3,1),('987654321212',45,45.1,3),('987654321252',45,45.2,3),('987654321262',25,25.6,1),('987654321939',45,45.3,3);
/*!40000 ALTER TABLE `luggage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
  `national_id` varchar(11) NOT NULL,
  `pnr_no` varchar(8) NOT NULL,
  `flight_number` varchar(7) DEFAULT NULL,
  `baggage_allowance` float NOT NULL,
  `luggage_id` varchar(12) NOT NULL,
  `fare_type` varchar(20) NOT NULL,
  `seat` int DEFAULT NULL,
  `meal` tinyint(1) NOT NULL,
  `extra_luggage` tinyint(1) NOT NULL,
  `check_in` tinyint(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `gender` enum('Male','Female','Other') DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `cip_member` tinyint(1) NOT NULL,
  `vip_member` tinyint(1) NOT NULL,
  `disabled` tinyint(1) NOT NULL,
  `child` tinyint(1) NOT NULL,
  `buyer` int DEFAULT NULL,
  UNIQUE KEY `pnr_no` (`pnr_no`),
  UNIQUE KEY `luggage_id` (`luggage_id`),
  CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`luggage_id`) REFERENCES `luggage` (`luggage_id`) ON DELETE SET DEFAULT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES ('19219219234','45BHSM26','TK4040',45,'687100075239','Comfort',11,1,0,1,'Güney','Dinc','guneydinc@gmail.com','5465368978','Male','1995-01-01',1,0,0,0,4),('13265753454','4NYRPF89','TK4040',45,'687399358214','Comfort',13,1,0,1,'Sam','jackosn','samjackson@gmail.com','5231223865','Male','1995-01-01',1,0,0,0,1),('20220220220','5DU72IN3','TK4040',45,'946764819451','Comfort',12,1,0,0,'Berkay','Söğüt','berkaysogut@gmail.com','5365478978','Male','1995-01-15',1,0,0,0,1),('13429215454','70JLWZCJ','TK4040',45,'705296022691','Comfort',10,1,0,0,'Jack','Johson','jj@gmail.com','5231254872','Male','1995-01-01',1,0,0,0,1),('12345678901','ABC12345','TK101',25,'987654321012','Essentials',1,1,20,1,'John','Doe','johndoe@gmail.com','1234567890','Male','1980-05-15',0,0,0,0,1),('13265753454','BB6QH8N8','TK4040',15,'977826109495','Essentials',14,0,0,0,'Samiye','jackosn','samiyejackson@gmail.com','5223523865','Female','1995-01-01',0,0,0,0,1),('12345678910','BCD23410','TK101',25,'109876543010','Advantage',10,0,20,1,'Ethan','Fisher','ethanfisher@icloud.com','1234567890','Male','1995-07-05',0,0,0,0,1),('12345678909','BCD23419','TK101',25,'210987654919','Essentials',9,0,10,1,'Evelyn','Turner','evelynturner@gmail.com','1234567890','Female','1998-08-03',0,0,0,0,1),('12345678908','BCD23428','TK101',25,'210987654828','Essentials',8,0,20,1,'Owen','Hayes','owenhayes@outlook.com','1234567890','Male','1985-09-25',0,0,0,0,1),('12345678907','BCD23437','TK101',25,'210987654737','Essentials',7,1,20,0,'Evelyn','Watkins','evelynwatkins@outlook.com','1234567890','Female','1981-10-18',0,0,0,0,1),('12345678906','BCD23446','TK101',25,'543210987646','Essentials',6,0,20,1,'Elijah','Turner','elijah.turner@icloud.com','3210987654','Male','1998-09-15',0,0,0,0,1),('12345678905','BCD23455','TK101',25,'654321098555','Essentials',5,1,15,0,'Lily','Knight','lily.knight@icloud.com','3210987654','Female','1984-08-16',0,0,0,0,1),('12345678904','BCD23464','TK101',25,'765432109464','Essentials',4,0,15,1,'Lily','Reyes','lily.reyes@icloud.com','2109876543','Female','1980-05-18',0,0,0,0,1),('12345678903','BCD23473','TK101',25,'765432109373','Essentials',3,0,15,0,'Emma','Roberts','emma.roberts@gmail.com','4321098765','Female','1988-11-28',0,0,0,0,1),('12345678902','BCD23482','TK101',25,'876543210282','Essentials',2,0,15,1,'Liam','Clark','liam.clark@gmail.com','4321098765','Male','1999-02-23',0,0,0,0,1),('12345678900','BCD67891','TK101',15,'098765432101','Essentials',0,1,15,0,'Ella','Moore','ellamoore@gmail.com','1234567890','Female','1973-09-02',0,0,0,0,1),('23456789012','DEF67890','TK101',35,'876543210123','Advantage',15,0,10,1,'Jane','Smith','janesmith@outlook.com','2345678901','Female','1995-09-22',0,0,0,0,1),('28328328323','E2R9WHKX','TK4040',45,'663150083975','comfort',25,1,0,0,'Joseph','Gassallini','josephgassallini@gmail.com','5895985847','Male','2001-03-13',1,0,0,0,1),('23456789011','EFG34592','TK101',35,'987654321102','Advantage',14,0,20,1,'Liam','Davis','liamdavis@icloud.com','2345678901','Male','1987-07-28',0,0,0,0,1),('23456789021','EFG78911','TK101',35,'098765432111','Advantage',24,1,10,0,'Chloe','Brooks','chloebrooks@gmail.com','2345678901','Female','1989-08-23',0,0,0,0,1),('23456789020','EFG78920','TK101',35,'098765432020','Advantage',23,1,20,1,'Lincoln','Richardson','lincolnrichardson@outlook.com','2345678901','Male','1974-12-20',0,0,0,0,1),('23456789019','EFG78929','TK101',35,'098765432929','Advantage',22,1,10,0,'Mia','Newton','mianewton@gmail.com','2345678901','Female','1996-10-12',0,0,0,0,1),('23456789018','EFG78938','TK101',35,'098765432838','Advantage',21,0,10,1,'Carter','Peters','carterpeters@gmail.com','2345678901','Male','1996-03-23',0,0,0,0,1),('23456789017','EFG78947','TK101',35,'432109876747','Advantage',20,1,15,0,'Lily','Martin','lily.martin@gmail.com','2109876543','Female','1980-05-18',0,0,0,0,1),('23456789016','EFG78956','TK101',35,'543210987656','Advantage',19,0,20,1,'Mason','Fisher','mason.fisher@gmail.com','2109876543','Male','1990-04-29',0,0,0,0,1),('23456789015','EFG78965','TK101',35,'654321098565','Advantage',18,1,20,0,'Emma','Cooper','emma.cooper@gmail.com','1098765432','Female','1987-12-12',0,0,0,0,1),('23456789014','EFG78974','TK101',35,'654321098474','Advantage',17,1,20,1,'Lucas','Bennett','lucas.bennett@outlook.com','3210987654','Male','1993-08-15',0,0,0,0,1),('23456789013','EFG78983','TK101',35,'765432109383','Advantage',16,1,20,0,'Lily','Ward','lily.ward@outlook.com','3210987654','Female','1984-08-06',0,0,0,0,1),('34567890123','GHI34567','TK101',15,'765432109234','Essentials',27,1,15,0,'Michael','Johnson','michaeljohnson@icloud.com','3456789012','Male','1988-10-11',0,0,0,0,1),('34567890132','HIJ23412','TK4040',45,'987654321212','Essentials',36,0,15,1,'Lucas','Butler','lucasbutler@outlook.com','3456789012','Male','1984-01-19',0,0,0,0,1),('34567890131','HIJ23421','TK4040',45,'987654321121','Essentials',35,0,15,0,'Aria','Simpson','ariasimpson@gmail.com','3456789012','Female','1999-05-04',0,0,0,0,1),('34567890130','HIJ23430','TK4040',45,'987654321030','Essentials',34,0,15,1,'Austin','Warren','austinwarren@icloud.com','3456789012','Male','1980-03-17',0,0,0,0,1),('34567890129','HIJ23439','TK4040',45,'987654321939','Essentials',33,1,15,0,'Sofia','Stanley','sofiastanley@icloud.com','3456789012','Female','1987-09-08',0,0,0,0,1),('34567890128','HIJ23448','TK4040',45,'321098765848','Essentials',32,0,10,1,'Oliver','Miller','oliver.miller@outlook.com','1098765432','Male','1987-12-12',0,0,0,0,1),('34567890127','HIJ23457','TK4040',45,'432109876757','Essentials',31,1,10,0,'Lily','Harrison','lily.harrison@outlook.com','1098765432','Female','1987-12-12',0,0,0,0,1),('34567890126','HIJ23466','TK101',45,'543210987666','Essentials',30,0,10,1,'Ethan','Rogers','ethan.rogers@outlook.com','987654321','Male','1989-07-14',0,0,0,0,1),('34567890125','HIJ23475','TK101',45,'543210987575','Essentials',29,0,10,1,'Sophia','Ross','sophia.ross@icloud.com','2109876543','Female','1980-04-29',0,0,0,0,1),('34567890124','HIJ23484','TK101',45,'654321098484','Essentials',28,0,10,1,'Mason','Hughes','mason.hughes@icloud.com','2109876543','Male','1990-04-29',0,0,0,0,1),('34567890122','HIJ90193','TK101',45,'876543210303','Essentials',26,1,10,0,'Ava','Thomas','avathomas@gmail.com','3456789012','Female','1994-04-17',0,0,0,0,1),('45678901234','JKL90123','TK4040',45,'654321098345','Essentials',38,0,10,1,'Emily','Williams','emilywilliams@gmail.com','4567890123','Female','1992-05-07',0,0,0,0,1),('45678901233','KLM45694','TK4040',25,'765432109404','Essentials',37,1,20,1,'James','Wilson','jameswilson@outlook.com','4567890123','Male','1978-10-22',0,0,1,0,1),('45678901243','KLM78913','TK4040',15,'876543210313','Essentials',47,1,20,0,'Lily','Hall','lilyhall@gmail.com','4567890123','Female','1997-06-04',0,0,1,0,1),('45678901242','KLM78922','TK4040',15,'876543210222','Essentials',46,1,20,1,'Landon','Crawford','landoncrawford@icloud.com','4567890123','Male','1988-09-18',0,0,0,0,1),('45678901241','KLM78931','TK4040',15,'876543210131','Essentials',45,1,20,1,'Addison','Fleming','addisonfleming@outlook.com','4567890123','Female','1995-08-22',0,0,1,0,1),('45678901240','KLM78940','TK4040',15,'876543210040','Essentials',44,0,20,1,'Lincoln','Hughes','lincolnhughes@outlook.com','4567890123','Male','1992-01-26',0,0,0,0,1),('45678901239','KLM78949','TK4040',15,'210987654949','Essentials',43,1,15,0,'Sophie','Clark','sophie.clark@icloud.com','987654321','Female','1986-07-03',0,0,1,0,1),('45678901238','KLM78958','TK4040',15,'321098765858','Essentials',42,0,15,1,'Emma','Cooper','emma.cooper@icloud.com','987654321','Female','1989-07-14',0,0,0,0,1),('45678901237','KLM78967','TK4040',15,'432109876767','Essentials',41,1,15,0,'Sophie','Reyes','sophie.reyes@icloud.com','7654321098','Female','1988-01-27',0,0,0,0,1),('45678901236','KLM78976','TK4040',15,'432109876676','Essentials',40,0,15,0,'Ethan','Wilson','ethan.wilson@gmail.com','1098765432','Male','1987-12-12',0,0,0,0,1),('45678901235','KLM78985','TK4040',15,'543210987585','Essentials',39,1,15,0,'Emma','Reyes','emma.reyes@gmail.com','1098765432','Female','1980-05-18',0,0,0,0,1),('21321321321','M7WEHS2D','TK4040',25,'448512958857','Advantage',13,1,0,0,'Test','Testooğlu','test@gmail.com','5789654878','Male','2001-03-13',0,0,0,0,1),('56789012345','MNO45678','TK4040',25,'543210987456','Essentials',49,1,20,0,'David','Anderson','davidanderson@outlook.com','5678901234','Male','1975-03-30',0,0,0,0,1),('56789012353','NOP23423','TK4040',25,'765432109323','Essentials',57,0,15,0,'Ella','Stevens','ellastevens@gmail.com','5678901234','Female','1993-02-22',0,0,0,0,1),('56789012352','NOP23432','TK4040',25,'765432109232','Essentials',56,0,10,0,'Lucas','Black','lucasblack@gmail.com','5678901234','Male','1988-05-11',0,0,0,0,1),('56789012351','NOP23441','TK4040',25,'765432109141','Advantage',55,0,10,1,'Ava','Murray','avamurray@gmail.com','5678901234','Female','1986-04-02',0,0,0,0,1),('56789012350','NOP23450','TK4040',25,'109876543050','Essentials',54,0,20,1,'Ethan','Evans','ethan.evans@gmail.com','8765432101','Male','2001-04-14',0,0,0,0,1),('56789012349','NOP23459','TK4040',25,'210987654959','Essentials',53,1,20,0,'Ethan','Rogers','ethan.rogers@gmail.com','8765432101','Male','1982-05-02',0,0,0,0,1),('56789012348','NOP23468','TK4040',25,'321098765868','Advantage',52,0,20,1,'Mason','Hughes','mason.hughes@gmail.com','5432109876','Male','1997-03-05',0,0,0,0,1),('56789012347','NOP23477','TK4040',25,'321098765777','Essentials',51,1,20,1,'Amelia','Turner','amelia.turner@outlook.com','987654321','Female','1996-07-15',0,0,0,0,1),('56789012346','NOP23486','TK4040',25,'432109876686','Essentials',50,0,20,0,'Elijah','Hill','elijah.hill@outlook.com','987654321','Male','1987-12-12',0,0,0,0,1),('56789012344','NOP78905','TK4040',15,'654321098505','Essentials',48,0,15,0,'Emma','Bennett','emmabennett@gmail.com','5678901234','Female','1999-02-11',0,0,0,0,1),('67890123455','PQR23456','TK2222',35,'543210987606','Essentials',58,1,10,1,'Logan','Young','loganyoung@icloud.com','6789012345','Male','1986-11-06',0,0,0,0,1),('67890123456','PQR78901','TK2222',15,'432109876567','Essentials',59,0,15,1,'Sophia','Brown','sophiabrown@gmail.com','6789012345','Female','1982-08-18',0,0,0,0,1),('67890123465','PQR78915','TK2222',35,'654321098515','Essentials',68,0,15,0,'Aria','Campbell','ariacampbell@gmail.com','6789012345','Female','1996-07-14',0,0,0,0,1),('67890123464','PQR78924','TK2222',35,'654321098424','Essentials',67,1,10,1,'Milo','Adams','miloadams@outlook.com','6789012345','Male','1977-09-07',0,0,0,0,1),('67890123463','PQR78933','TK2222',35,'654321098333','Advantage',66,1,15,1,'Sophie','Hall','sophiehall@icloud.com','6789012345','Female','1973-12-04',0,0,0,0,1),('67890123462','PQR78942','TK2222',35,'654321098242','Essentials',65,1,15,0,'Owen','Holmes','owenholmes@icloud.com','6789012345','Male','1991-09-15',0,0,0,0,1),('67890123461','PQR78951','TK2222',35,'098765432151','Essentials',64,1,10,0,'Grace','Carter','grace.carter@outlook.com','7654321098','Female','1988-01-27',0,0,0,0,1),('67890123460','PQR78960','TK2222',35,'109876543060','Essentials',63,0,10,1,'Sophie','Evans','sophie.evans@outlook.com','7654321098','Female','1985-02-10',0,0,0,0,1),('67890123459','PQR78969','TK2222',35,'210987654969','Essentials',62,1,10,0,'Lily','Reyes','lily.reyes@outlook.com','1098765432','Female','1980-05-18',0,0,0,0,1),('67890123458','PQR78978','TK2222',35,'210987654878','Essentials',61,0,10,0,'Liam','Anderson','liam.anderson@icloud.com','8765432101','Male','1999-02-03',0,0,0,0,1),('67890123457','PQR78987','TK2222',35,'321098765787','Advantage',60,1,10,1,'Sophie','Scott','sophie.scott@icloud.com','8765432101','Female','1985-02-27',0,0,0,0,1),('78901234576','STU23416','TK2222',45,'543210987616','Comfort',79,1,10,1,'Mason','Cooper','masoncooper@outlook.com','7890123456','Male','1982-02-02',0,0,0,0,1),('78901234575','STU23425','TK2222',45,'543210987525','Comfort',78,0,20,0,'Luna','Hudson','lunahudson@gmail.com','7890123456','Female','1992-12-04',0,0,0,0,1),('78901234574','STU23434','TK2222',45,'543210987434','Comfort',77,0,20,0,'Grayson','Bishop','graysonbishop@outlook.com','7890123456','Male','1990-07-29',0,0,0,0,1),('78901234573','STU23443','TK2222',45,'543210987343','Comfort',76,0,20,1,'Emma','Bowman','emmabowman@outlook.com','7890123456','Female','1984-10-06',0,0,0,0,1),('78901234572','STU23452','TK2222',45,'987654321252','Comfort',75,0,15,1,'Aiden','Perry','aiden.perry@icloud.com','6543210987','Male','1994-09-09',0,0,0,0,1),('78901234567','STU23456','TK2222',45,'321098765678','Comfort',70,1,10,0,'William','Taylor','williamtaylor@icloud.com','7890123456','Male','1990-12-25',0,0,0,0,1),('78901234571','STU23461','TK2222',45,'098765432161','Comfort',74,1,15,0,'Noah','Cook','noah.cook@icloud.com','6543210987','Male','1988-01-27',0,0,0,0,1),('78901234570','STU23470','TK2222',45,'109876543070','Comfort',73,0,15,1,'Oliver','Miller','oliver.miller@gmail.com','987654321','Male','1994-09-09',0,0,0,0,1),('78901234569','STU23479','TK2222',45,'109876543979','Comfort',72,0,15,1,'Grace','Miller','grace.miller@gmail.com','7654321098','Female','1988-01-27',0,0,0,0,1),('78901234568','STU23488','TK2222',45,'210987654888','Comfort',71,0,15,0,'Oliver','White','oliver.white@gmail.com','7654321098','Male','1993-11-10',0,0,0,0,1),('78901234566','STU78907','TK2222',25,'432109876707','Essentials',69,0,20,0,'Grace','Hill','gracehill@gmail.com','7890123456','Female','1991-03-29',0,0,0,0,1),('19219219234','TQZ3IXJA','TK2222',45,'677629774685','Comfort',45,1,0,0,'Güney','Dinc','guneydinc@gmail.com','5465368978','Male','1995-01-01',1,0,0,0,4),('89012345677','VWX23458','TK2222',45,'321098765808','Comfort',80,1,10,1,'Jackson','Ward','jacksonward@outlook.com','8901234567','Male','1983-08-09',0,0,0,0,1),('89012345678','VWX78901','TK2222',35,'210987654789','Essentials',81,1,20,1,'Olivia','Miller','oliviamiller@gmail.com','8901234567','Female','1985-06-14',0,0,0,0,1),('89012345687','VWX78917','TK3131',25,'432109876717','Essentials',90,0,20,0,'Aubrey','Hughes','aubreyhughes@gmail.com','8901234567','Female','1993-09-25',0,0,0,0,1),('89012345686','VWX78926','TK3131',25,'432109876626','Essentials',89,1,10,1,'Hudson','Bryant','hudsonbryant@icloud.com','8901234567','Male','1986-06-30',0,0,0,0,1),('89012345685','VWX78935','TK3131',25,'432109876535','Essentials',88,1,10,1,'Harper','Morrison','harpermorrison@gmail.com','8901234567','Female','1985-02-16',0,0,0,0,1),('89012345684','VWX78944','TK3131',25,'765432109444','Essentials',87,0,15,1,'Mason','Ward','mason.ward@gmail.com','5432109876','Male','1997-03-05',0,0,0,0,1),('89012345683','VWX78953','TK3131',25,'876543210353','Essentials',86,1,20,0,'Amelia','Greene','amelia.greene@gmail.com','5432109876','Female','1981-06-26',0,0,0,0,1),('89012345682','VWX78962','TK3131',25,'987654321262','Essentials',85,0,20,1,'Amelia','Watson','amelia.watson@gmail.com','8765432101','Female','1982-05-02',0,0,0,0,1),('89012345681','VWX78971','TK2222',25,'987654321171','Essentials',84,1,20,1,'Sophie','Clark','sophie.clark@outlook.com','6543210987','Female','1992-10-03',0,0,0,0,1),('89012345680','VWX78980','TK2222',25,'098765432080','Essentials',83,1,20,1,'Aiden','Scott','aiden.scott@outlook.com','6543210987','Male','1986-10-22',0,0,0,0,1),('89012345679','VWX78989','TK2222',25,'109876543989','Essentials',82,1,20,1,'Chloe','Miller','chloe.miller@outlook.com','6543210987','Female','1996-07-15',0,0,0,0,1),('90123456798','YZA12318','TK3131',15,'321098765818','Essentials',99,1,15,1,'Levi','Reid','levireid@icloud.com','9012345678','Male','1987-06-13',0,0,0,0,1),('90123456797','YZA12327','TK3131',15,'321098765727','Essentials',98,0,15,0,'Nova','Keller','novakeller@gmail.com','9012345678','Female','1991-04-03',0,0,0,0,1),('90123456796','YZA12336','TK3131',15,'321098765636','Essentials',97,0,15,0,'Liam','Gilliam','liamgilliam@icloud.com','9012345678','Male','1998-01-05',0,0,0,0,1),('90123456789','YZA12345','TK3131',25,'109876543210','Essentials',92,0,10,1,'Aiden','Clark','aidenclark@outlook.com','9012345678','Male','1998-03-09',0,0,0,0,1),('90123456794','YZA12354','TK3131',15,'765432109454','Essentials',96,0,10,1,'Liam','Turner','liam.turner@outlook.com','4321098765','Male','1999-02-03',0,0,0,0,1),('90123456793','YZA12363','TK3131',15,'876543210363','Essentials',95,1,10,0,'Liam','Reed','liam.reed@outlook.com','3210987654','Male','1998-09-15',0,0,0,0,1),('90123456791','YZA12381','TK3131',15,'987654321181','Essentials',94,0,10,0,'Sophie','Lopez','sophie.lopez@icloud.com','5432109876','Female','1981-06-26',0,0,0,0,1),('90123456790','YZA12390','TK3131',15,'098765432090','Essentials',93,0,10,1,'Oliver','Clark','oliver.clark@icloud.com','5432109876','Male','1988-01-27',0,0,0,0,1),('90123456788','YZA78909','TK3131',15,'751165338501','Essentials',91,1,15,0,'Avery','Ross','averyross@gmail.com','9012345678','Female','1976-12-12',0,0,0,0,1);
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personnel` (
  `national_id` varchar(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `gender` varchar(5) NOT NULL,
  `birth_date` date NOT NULL,
  `password` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`national_id`),
  UNIQUE KEY `national_id` (`national_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnel`
--

LOCK TABLES `personnel` WRITE;
/*!40000 ALTER TABLE `personnel` DISABLE KEYS */;
INSERT INTO `personnel` VALUES ('00000000000','Semih','Utku','semih.utku@ams.com','0090000000000','Male','2000-10-10','123456','admin','General Menager'),('11111111111','Mert','Yılmaz','merty@ams.com','+90 537 777 77 77','Male','1994-01-02','123456','flight_planner','Senior Flight Planner ');
/*!40000 ALTER TABLE `personnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plane`
--

DROP TABLE IF EXISTS `plane`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plane` (
  `plane_registration` varchar(6) NOT NULL,
  `model` varchar(255) NOT NULL,
  `location` varchar(10) NOT NULL,
  `max_passengers` int NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`plane_registration`),
  UNIQUE KEY `plane_registration` (`plane_registration`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plane`
--

LOCK TABLES `plane` WRITE;
/*!40000 ALTER TABLE `plane` DISABLE KEYS */;
INSERT INTO `plane` VALUES ('TC-ABC','Boeing 777','ADB',270,1),('TC-ACB','Airbus A350','ADB',270,1),('TC-ANA','Airbus A350','ADB',270,1),('TC-BBC','Airbus A350','ADB',270,1),('TC-BRK','Boeing 777','ADB',270,1),('TC-CAN','Airbus A350','ADB',270,1),('TC-CLN','Airbus A350','ADB',270,0),('TC-CRR','Boeing 787','ADB',270,1),('TC-DEF','Boeing 787','ADB',270,1),('TC-DNC','Boeing 777','ADB',270,0),('TC-DNZ','Boeing 777','ADB',270,0),('TC-ERR','Boeing 787','ADB',270,1),('TC-EZG','Airbus A350','ADB',270,1),('TC-FED','Airbus A350','ADB',270,1),('TC-FLZ','Airbus A350','ADB',270,1),('TC-GHI','Boeing 787','ADB',270,1),('TC-GNS','Boeing 777','ADB',270,1),('TC-GNY','Airbus A350','ADB',270,1),('TC-HTY','Boeing 787','ADB',270,0),('TC-IZM','Airbus A350','ADB',270,0),('TC-JJA','Boeing 777','ADB',270,1),('TC-JKL','Airbus A350','ADB',270,1),('TC-JLK','Boeing 777','ADB',270,1),('TC-KAF','Boeing 787','ADB',270,1),('TC-KEF','Boeing 777','ADB',270,1),('TC-KKL','Boeing 737','ADB',270,1),('TC-KML','Airbus A350','ADB',270,1),('TC-LNB','Airbus A350','ADB',270,1),('TC-LOA','Boeing 777','ADB',270,1),('TC-LRA','Boeing 787','ADB',270,1),('TC-MMO','Airbus A350','ADB',270,1),('TC-MNO','Boeing 777','ADB',270,1),('TC-MON','Boeing 787','ADB',270,1),('TC-NNO','Airbus A350','ADB',270,1),('TC-PHI','Airbus A350','ADB',270,1),('TC-PQR','Airbus A350','ADB',270,1),('TC-PRQ','Airbus A350','ADB',270,1),('TC-PWX','Boeing 777','ADB',270,1),('TC-SGT','Boeing 787','ADB',270,0),('TC-SHI','Airbus A350','ADB',270,1),('TC-SMA','Airbus A350','ADB',270,1),('TC-SSD','Boeing 777','ADB',270,1),('TC-STU','Boeing 777','ADB',270,1),('TC-SUT','Boeing 777','ADB',270,1),('TC-ULS','Airbus A350','ADB',270,0),('TC-UTU','Airbus A350','ADB',270,1),('TC-VWX','Airbus A350','ADB',270,1),('TC-VXW','Airbus A350','ADB',270,1),('TC-XYZ','Airbus A350','ADB',270,1),('TC-YSF','Boeing 777','ADB',270,1),('TC-YZA','Boeing 787','ADB',270,1),('TC-YZS','Boeing 787','ADB',270,1),('TC-ZYX','Airbus A350','ADB',270,1);
/*!40000 ALTER TABLE `plane` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `gender` varchar(5) NOT NULL,
  `birth_date` date NOT NULL,
  `money` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Yusuf','Gassaloglu','y@ams.com','123456','05365460461','Male','2001-03-13',689276),(4,'Berkay','Dinc','b@ams.com','123456','05435895798','Male','2002-08-14',979304),(6,'Guney','Sogut','g@ams.com','123456','5354491746','Male','2001-01-05',999999),(7,'Tester','Tester','testuser@testuser.com','123456','+90 1548697859','Male','1990-01-01',5000);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-02 23:39:23
