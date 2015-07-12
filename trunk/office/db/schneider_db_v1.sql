CREATE DATABASE  IF NOT EXISTS `schneider_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `schneider_db`;
-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: schneider_db
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.1

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
-- Table structure for table `subscriber`
--

DROP TABLE IF EXISTS `subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriber` (
  `subscriber_id` int(6) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `rf_id` varchar(45) DEFAULT NULL,
  `company_id` int(10) DEFAULT NULL,
  `company_name` varchar(45) DEFAULT NULL,
  `public_rfid` varchar(45) DEFAULT NULL,
  `denied_groups` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `credit_units` int(5) DEFAULT NULL,
  PRIMARY KEY (`subscriber_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (274774,'enabled','FFEEDDCCBBAA99',26070777,'PTT Public Company Ltd.','FFEEDDCCBBAA99',NULL,'user',NULL,NULL,NULL,0),(275314,'enabled','000000000000000000FFEEDDCCBBAA98',26070777,'PTT Public Company Ltd.','FFEEDDCCBBAA98',NULL,'user',NULL,NULL,NULL,0),(275315,'disabled','000000000000000000FFEEDDCCBBAA96',26070777,'PTT Public Company Ltd.','FFEEDDCCBBAA96',NULL,'user',NULL,NULL,NULL,0),(275316,'enabled','000000000000000000FFEEDDCCBBAA95',26070777,'PTT Public Company Ltd.','FFEEDDCCBBAA95',NULL,'user',NULL,NULL,NULL,0),(275344,'enabled','000000000000000000FFEEDDCCBBAA97',26070777,'PTT Public Company Ltd.','FFEEDDCCBBAA97',NULL,'user',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charge_log`
--

DROP TABLE IF EXISTS `charge_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charge_log` (
  `timestamp` decimal(13,0) NOT NULL,
  `start_time` decimal(13,0) DEFAULT NULL,
  `charge_order` varchar(45) DEFAULT NULL,
  `station_id` int(10) DEFAULT NULL,
  `charging_duration` int(4) DEFAULT NULL,
  `end_time` decimal(13,0) DEFAULT NULL,
  `subscriber_id` int(6) DEFAULT NULL,
  `charging_start_code` int(4) DEFAULT NULL,
  `contract_duration` varchar(45) DEFAULT NULL,
  `contract_power` int(4) DEFAULT NULL,
  `contract_type` int(4) DEFAULT NULL,
  `amount` int(4) DEFAULT NULL,
  `token_id` varchar(45) DEFAULT NULL,
  `vehicule_id` varchar(45) DEFAULT NULL,
  `energy_total` int(4) DEFAULT NULL,
  `charging_id` int(6) DEFAULT NULL,
  `contract_id` int(6) DEFAULT NULL,
  `charging_type` int(4) DEFAULT NULL,
  `charging_end_code` int(4) DEFAULT NULL,
  `charging_max_power` int(4) DEFAULT NULL,
  `outlet_id` int(6) DEFAULT NULL,
  `id` int(6) NOT NULL,
  `last_update` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`timestamp`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charge_log`
--

LOCK TABLES `charge_log` WRITE;
/*!40000 ALTER TABLE `charge_log` DISABLE KEYS */;
INSERT INTO `charge_log` VALUES (1330053644000,0,NULL,71889,3,0,NULL,0,NULL,6,0,0,'023CFE5F1FE07B9312E0F7FFFC00D99B',NULL,1852,7,0,2,1,0,11,72112,NULL),(1330058660000,0,NULL,71889,4,0,72309,0,NULL,6,0,0,'000000000000000007E00000CA1AE218',NULL,2579,11,0,2,1,0,21,72125,NULL),(1330144882000,0,NULL,71889,3,0,NULL,0,NULL,6,0,0,NULL,NULL,1697,16,0,2,1,0,21,72345,NULL),(1330160677000,0,NULL,71889,0,0,NULL,0,NULL,0,0,0,'00000000000000000000000000000000',NULL,0,0,0,0,0,0,0,72362,NULL),(1330318179000,0,NULL,71889,0,0,NULL,0,NULL,0,0,0,'00000000000000000000000000000000',NULL,0,0,0,0,0,0,0,72449,NULL),(1347083661000,1347083620000,NULL,71889,0,0,NULL,0,NULL,0,0,0,NULL,NULL,3,83,0,1,2,0,31,166688,NULL),(1347084481000,1347084446000,NULL,71889,0,0,NULL,0,NULL,0,0,0,NULL,NULL,2,103,0,1,2,0,31,166623,NULL),(1347260631000,0,NULL,71889,0,0,NULL,0,NULL,0,0,0,NULL,NULL,0,154,0,0,0,0,0,167596,NULL),(1347260643000,0,NULL,71889,0,0,NULL,0,NULL,0,0,0,NULL,NULL,0,156,0,0,0,0,0,167594,NULL),(1347260871000,0,NULL,71889,0,0,NULL,0,NULL,0,0,0,NULL,NULL,0,171,0,1,0,0,0,167673,NULL);
/*!40000 ALTER TABLE `charge_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriber_group`
--

DROP TABLE IF EXISTS `subscriber_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriber_group` (
  `subscriber_id` int(6) NOT NULL,
  `group` varchar(45) NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subscriber_id`,`group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber_group`
--

LOCK TABLES `subscriber_group` WRITE;
/*!40000 ALTER TABLE `subscriber_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriber_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminal`
--

DROP TABLE IF EXISTS `terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal` (
  `station_id` int(10) NOT NULL,
  `identifier` int(10) NOT NULL,
  `type` int(2) DEFAULT NULL,
  `version` int(4) DEFAULT NULL,
  `screen_version` varchar(45) DEFAULT NULL,
  `rfid_version` varchar(45) DEFAULT NULL,
  `id` int(5) DEFAULT NULL,
  `last_update` int(13) DEFAULT NULL,
  PRIMARY KEY (`station_id`,`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminal`
--

LOCK TABLES `terminal` WRITE;
/*!40000 ALTER TABLE `terminal` DISABLE KEYS */;
/*!40000 ALTER TABLE `terminal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `station_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `input` int(11) DEFAULT NULL,
  `default_code` int(11) DEFAULT NULL,
  `output` int(11) DEFAULT NULL,
  `power` int(11) DEFAULT NULL,
  `total_energy_counter` int(11) DEFAULT NULL,
  `energy_counter_co2` int(11) DEFAULT NULL,
  `energy_counter_green` int(11) DEFAULT NULL,
  `energy_counter_load_shedding` int(11) DEFAULT NULL,
  `current_i1` int(11) DEFAULT NULL,
  `current_i2` int(11) DEFAULT NULL,
  `current_i3` int(11) DEFAULT NULL,
  `uph1_voltage` int(11) DEFAULT NULL,
  `uph2_voltage` int(11) DEFAULT NULL,
  `uph3_voltage` int(11) DEFAULT NULL,
  `realpower` int(11) DEFAULT NULL,
  `reactive_power` int(11) DEFAULT NULL,
  `apparent_power` int(11) DEFAULT NULL,
  `power_factor` varchar(45) DEFAULT NULL,
  `current_dc` int(11) DEFAULT NULL,
  `dc_voltage` int(11) DEFAULT NULL,
  PRIMARY KEY (`station_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `station_id` int(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `company_id` int(4) DEFAULT NULL,
  `serial_number` varchar(45) DEFAULT NULL,
  `external_id` varchar(45) DEFAULT NULL,
  `device_external_id` varchar(45) DEFAULT NULL,
  `asset_id` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `last_communication_date` decimal(20,0) DEFAULT NULL,
  `group_id` int(4) DEFAULT NULL,
  `model_id` int(4) DEFAULT NULL,
  `device_id` int(4) DEFAULT NULL,
  PRIMARY KEY (`station_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES (71889,'WANG NOI',26070777,'353270040584791','M1','353270040584791','630063','14.288632','100.81762',1359158919656,26070778,24671126,0);
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outlet`
--

DROP TABLE IF EXISTS `outlet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outlet` (
  `station_id` int(10) NOT NULL,
  `identifier` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  `index` int(2) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `input` int(4) DEFAULT NULL,
  `config` int(4) DEFAULT NULL,
  `last_event_input` varchar(45) DEFAULT NULL,
  `last_event_output` varchar(45) DEFAULT NULL,
  `last_event_failure` varchar(45) DEFAULT NULL,
  `index_in_station` varchar(45) DEFAULT NULL,
  `settings` int(4) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `consumer` varchar(45) DEFAULT NULL,
  `contract_power` varchar(45) DEFAULT NULL,
  `default_code` int(2) DEFAULT NULL,
  `power_order` int(2) DEFAULT NULL,
  `power_config` int(2) DEFAULT NULL,
  `charging_counter` int(4) DEFAULT NULL,
  `total_energy_conter` int(4) DEFAULT NULL,
  `output` int(4) DEFAULT NULL,
  `charging_start_datetime` int(13) DEFAULT NULL,
  `charge_status` int(2) DEFAULT NULL,
  `cable_capacity` int(4) DEFAULT NULL,
  `change_status_date_time` int(13) DEFAULT NULL,
  `last_charge_duration` int(4) DEFAULT NULL,
  `bp` varchar(45) DEFAULT NULL,
  `id` int(6) DEFAULT NULL,
  `last_update` int(13) DEFAULT NULL,
  PRIMARY KEY (`station_id`,`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outlet`
--

LOCK TABLES `outlet` WRITE;
/*!40000 ALTER TABLE `outlet` DISABLE KEYS */;
/*!40000 ALTER TABLE `outlet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `idtest` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtest`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-01-27 23:58:39
