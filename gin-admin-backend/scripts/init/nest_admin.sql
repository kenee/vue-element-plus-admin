-- MySQL dump 10.13  Distrib 5.7.44, for osx10.17 (x86_64)
--
-- Host: 127.0.0.1    Database: nest_admin
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `sys_card_example`
--

DROP TABLE IF EXISTS `sys_card_example`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_card_example` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `desc` varchar(500) DEFAULT NULL,
  `logo` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_card_example`
--

LOCK TABLES `sys_card_example` WRITE;
/*!40000 ALTER TABLE `sys_card_example` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_card_example` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_department`
--

DROP TABLE IF EXISTS `sys_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_department` (
  `id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sort` int NOT NULL DEFAULT '0',
  `status` tinyint NOT NULL DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_department`
--

LOCK TABLES `sys_department` WRITE;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` VALUES ('0172a365-d286-4cf7-aa4a-e2d957137065','销售部',5,0,'销售部的备注信息','2025-11-21 15:44:24.858000','2025-11-21 15:44:24.858000',NULL),('08be4ed3-261f-4605-b8d2-813197bd35d7','运营部',3,1,'运营部的备注信息','2025-11-21 15:44:24.777000','2025-11-21 15:44:24.777000',NULL),('10369cdc-90a7-48bf-8175-c282165169be','销售部',5,1,'销售部的备注信息','2025-11-24 22:02:27.124000','2025-11-24 22:02:27.124000',NULL),('11442ee9-e8f9-441d-a527-81e787a689a9','销售部',5,1,'销售部的备注信息','2025-11-24 22:46:00.940000','2025-11-24 22:46:00.940000',NULL),('1209130b-1857-429f-bbfe-e6ff3259e0d1','销售部',5,1,'销售部的备注信息','2025-11-24 22:02:27.043000','2025-11-24 22:02:27.043000',NULL),('1546569d-6c94-4248-8c3d-36cb529f9bf2','销售部',5,1,'销售部的备注信息','2025-11-24 22:46:00.677000','2025-11-24 22:46:00.677000',NULL),('19edc896-eb34-4d57-9062-f6cee98d3af5','产品部',2,1,'产品部的备注信息','2025-11-24 22:46:00.904000','2025-11-24 22:46:00.904000',NULL),('1b0e572f-4785-483e-af08-a49bc9afc3c7','客服部',6,1,'客服部的备注信息','2025-11-24 22:46:00.953000','2025-11-24 22:46:00.953000',NULL),('1cdc72c9-e982-42f2-8899-d0c1724cf826','客服部',6,1,'客服部的备注信息','2025-11-21 15:44:24.932000','2025-11-21 15:44:24.932000',NULL),('1d8cb4d1-62e9-4bad-bbbc-6df1837abcf6','客服部',6,1,'客服部的备注信息','2025-11-24 22:02:27.057000','2025-11-24 22:02:27.057000',NULL),('26aef64d-469e-4eb0-8468-8fbdd2663791','销售部',5,1,'销售部的备注信息','2025-11-21 15:44:24.796000','2025-11-21 15:44:24.796000',NULL),('276a58da-206c-4d97-8ba0-af4c4039624e','运营部',3,1,'运营部的备注信息','2025-11-24 22:46:00.454000','2025-11-24 22:46:00.454000',NULL),('28c98a04-d4f9-4215-b77b-2a91fd9389ef','市场部',4,1,'市场部的备注信息','2025-11-24 22:02:27.315000','2025-11-24 22:02:27.315000',NULL),('2a1714f8-0da7-4a18-9a30-90e0355b73b3','研发部',1,1,'研发部的备注信息','2025-11-21 15:44:24.949000','2025-11-21 15:44:24.949000',NULL),('2b6ed3ab-eaa9-4ee9-ab58-b5f01dfa333c','上海分公司',3,1,'上海分公司的备注信息','2025-11-21 15:44:24.814000','2025-11-21 15:44:24.814000',NULL),('331db986-bd6e-4941-940b-7a486046dc28','销售部',5,1,'销售部的备注信息','2025-11-24 22:46:00.769000','2025-11-24 22:46:00.769000',NULL),('374f2db2-48b7-4843-852e-bc198723b104','市场部',4,0,'市场部的备注信息','2025-11-21 15:44:24.849000','2025-11-21 15:44:24.849000',NULL),('3c603f54-c547-43ac-9c88-365a1bbcc772','研发部',1,1,'研发部的备注信息','2025-11-24 22:02:27.262000','2025-11-24 22:02:27.262000',NULL),('3f6e28e3-8d35-4625-b45b-195618ef82d2','产品部',2,1,'产品部的备注信息','2025-11-24 23:25:32.180000','2025-11-24 23:25:32.180000',NULL),('40edb5a1-288f-4119-bcaf-cb34e6a631c7','产品部',2,1,'产品部的备注信息','2025-11-24 22:02:27.085000','2025-11-24 22:02:27.085000',NULL),('4126632a-5cdc-45b9-aac3-dc831e24abca','销售部',5,1,'销售部的备注信息','2025-11-21 15:44:24.986000','2025-11-21 15:44:24.986000',NULL),('416da196-46e0-40d2-a457-0d925ade0ca7','深圳分公司',5,1,'深圳分公司的备注信息','2025-11-21 15:44:24.942000','2025-11-21 15:44:24.942000',NULL),('452a63db-a0bf-41f5-a482-185aa298a851','产品部',2,1,'产品部的备注信息','2025-11-24 23:25:32.528000','2025-11-24 23:25:32.528000',NULL),('4a58ce50-a590-4dd9-97b6-98dfe017376f','市场部',4,1,'市场部的备注信息','2025-11-24 22:46:00.759000','2025-11-24 22:46:00.759000',NULL),('4b6dbd25-fa36-40e3-b0fd-e5d4cf4c7b95','研发部',1,1,'研发部的备注信息','2025-11-24 23:25:32.066000','2025-11-24 23:25:32.066000',NULL),('4ce1ea53-ae71-4757-b93b-25434da2853f','产品部',2,1,'产品部的备注信息','2025-11-24 23:25:32.298000','2025-11-24 23:25:32.298000',NULL),('4ee34f80-c602-4a46-9360-4eba429b3c9f','市场部',4,0,'市场部的备注信息','2025-11-21 15:44:24.785000','2025-11-21 15:44:24.785000',NULL),('5150065b-a322-486e-9115-69bba1d0fde5','产品部',2,1,'产品部的备注信息','2025-11-24 22:02:27.175000','2025-11-24 22:02:27.175000',NULL),('531dcc6b-7bec-4463-a830-62d77845d983','客服部',6,1,'客服部的备注信息','2025-11-24 23:25:32.258000','2025-11-24 23:25:32.258000',NULL),('538c0eab-94f8-4b5c-b41f-3d25516750ef','研发部',1,1,'研发部的备注信息','2025-11-24 23:25:32.279000','2025-11-24 23:25:32.279000',NULL),('55490553-fec3-4a0b-9cff-4c2e53f278a6','销售部',5,1,'销售部的备注信息','2025-11-24 23:25:32.128000','2025-11-24 23:25:32.128000',NULL),('56609e19-6ee2-47b1-9eef-786096053adc','运营部',3,1,'运营部的备注信息','2025-11-24 23:25:32.198000','2025-11-24 23:25:32.198000',NULL),('5b4a4b91-b083-4eed-bd67-a4b9a44fb944','研发部',1,1,'研发部的备注信息','2025-11-24 22:46:00.800000','2025-11-24 22:46:00.800000',NULL),('5b70ee7a-ab49-47c6-bc34-c17148b2e417','客服部',6,1,'客服部的备注信息','2025-11-24 23:25:32.145000','2025-11-24 23:25:32.145000',NULL),('5f01aaa0-1bb1-4fbf-a3bd-319aa542b31b','销售部',5,1,'销售部的备注信息','2025-11-21 15:44:24.923000','2025-11-21 15:44:24.923000',NULL),('5f13e930-e5f9-47a8-9dfb-95876b732ea1','销售部',5,1,'销售部的备注信息','2025-11-24 22:46:00.862000','2025-11-24 22:46:00.862000',NULL),('639efec5-7bd0-4d7a-bbd4-d91be4c47a94','市场部',4,1,'市场部的备注信息','2025-11-24 23:25:32.113000','2025-11-24 23:25:32.113000',NULL),('645e55b5-da19-4610-9eab-a771622b7b74','销售部',5,1,'销售部的备注信息','2025-11-21 15:44:24.725000','2025-11-21 15:44:24.725000',NULL),('65e5995a-af4a-4e4d-a420-882251a6f3db','运营部',3,1,'运营部的备注信息','2025-11-24 22:02:27.187000','2025-11-24 22:02:27.187000',NULL),('66dac947-8890-48e5-a325-7b6f82293cb4','市场部',4,1,'市场部的备注信息','2025-11-24 22:46:00.477000','2025-11-24 22:46:00.477000',NULL),('6734a20d-8d13-4377-a50c-c6462afc8f1d','市场部',4,1,'市场部的备注信息','2025-11-24 22:02:27.030000','2025-11-24 22:02:27.030000',NULL),('6820cc08-70bf-4ad1-9ed7-0b62e61a24c9','市场部',4,1,'市场部的备注信息','2025-11-24 22:02:27.112000','2025-11-24 22:02:27.112000',NULL),('70ccbb9c-5913-402d-94ad-f5155b35446d','市场部',4,1,'市场部的备注信息','2025-11-24 23:25:32.447000','2025-11-24 23:25:32.447000',NULL),('710e644b-b68c-4a3a-ac2e-dbc92f82994f','客服部',6,1,'客服部的备注信息','2025-11-24 22:02:27.136000','2025-11-24 22:02:27.136000',NULL),('72591191-e705-4fb4-9444-8d4f8c5ee88d','客服部',6,1,'客服部的备注信息','2025-11-24 22:02:27.239000','2025-11-24 22:02:27.239000',NULL),('74ff751f-720d-429d-9261-2cce0fa2fbd0','运营部',3,1,'运营部的备注信息','2025-11-24 22:46:00.831000','2025-11-24 22:46:00.831000',NULL),('7595c9ef-a509-4626-a154-0ecf1a3ad6af','产品部',2,1,'产品部的备注信息','2025-11-24 23:25:32.409000','2025-11-24 23:25:32.409000',NULL),('762b4a21-7f91-45e3-8416-30366ac68dff','研发部',1,0,'研发部的备注信息','2025-11-21 15:44:24.884000','2025-11-21 15:44:24.884000',NULL),('7654d74d-a582-44f8-8171-7fcd507ab39b','产品部',2,1,'产品部的备注信息','2025-11-24 22:46:00.736000','2025-11-24 22:46:00.736000',NULL),('77e503a3-a196-49c1-8ab8-79612b8221f7','市场部',4,1,'市场部的备注信息','2025-11-24 22:46:00.845000','2025-11-24 22:46:00.845000',NULL),('78f17c82-2b3e-46a6-b238-5fee061b5e42','客服部',6,1,'客服部的备注信息','2025-11-24 23:25:32.612000','2025-11-24 23:25:32.612000',NULL),('79925c8a-7ac4-434e-827b-066a602cd5ae','福州分公司',4,1,'福州分公司的备注信息','2025-11-21 15:44:24.876000','2025-11-21 15:44:24.876000',NULL),('7aa8771d-733e-4775-aed5-4a098d73b3ca','销售部',5,0,'销售部的备注信息','2025-11-24 13:28:41.591000','2025-11-24 13:28:41.591000',NULL),('7c6802ce-73d0-4c92-9bae-a9e45d3a67d7','运营部',3,1,'运营部的备注信息','2025-11-24 22:46:00.645000','2025-11-24 22:46:00.645000',NULL),('7ec144dd-090f-4aef-a990-616b6a9c2a1f','市场部',4,1,'市场部的备注信息','2025-11-24 23:25:32.217000','2025-11-24 23:25:32.217000',NULL),('7fbdebda-f73b-47f8-8fc1-7f58e829b631','运营部',3,1,'运营部的备注信息','2025-11-24 22:46:00.916000','2025-11-24 22:46:00.916000',NULL),('7fccf3b7-772a-4d9e-9503-204a5ffcd7a5','运营部',3,1,'运营部的备注信息','2025-11-24 22:46:00.748000','2025-11-24 22:46:00.748000',NULL),('812e77cc-d874-447e-b0f0-0d9d09672e1e','运营部',3,1,'运营部的备注信息','2025-11-24 22:02:27.099000','2025-11-24 22:02:27.099000',NULL),('832b76b2-74c3-471a-b066-75a93d40dcf8','产品部',2,1,'产品部的备注信息','2025-11-24 22:02:27.279000','2025-11-24 22:02:27.279000',NULL),('859754ee-f2b4-4bb3-86e1-c8244a25af6f','市场部',4,1,'市场部的备注信息','2025-11-24 23:25:32.572000','2025-11-24 23:25:32.572000',NULL),('86c2e295-ed7e-4f07-9eee-d8daf50a57a5','市场部',4,1,'市场部的备注信息','2025-11-24 13:28:41.580000','2025-11-24 13:28:41.580000',NULL),('8958fa99-2f29-4b6f-922a-d5cf702c8508','客服部',6,1,'客服部的备注信息','2025-11-24 23:25:32.371000','2025-11-24 23:25:32.371000',NULL),('8efa3fab-075b-453b-a03a-dc58923528e5','厦门总公司',1,1,'厦门总公司的备注信息','2025-11-24 13:28:41.387000','2025-11-24 13:28:41.387000',NULL),('8f54bc29-13ae-4d7d-a077-c6e02b11c895','研发部',1,1,'研发部的备注信息','2025-11-24 23:25:32.515000','2025-11-24 23:25:32.515000',NULL),('9011151a-f4d2-4e46-b568-ca59201dc5c3','rerer',0,1,'kkk','2025-11-22 05:37:55.793000','2025-11-22 05:37:55.793000',NULL),('933f2f83-aacc-4125-9e32-30bd6e190e4e','销售部',5,1,'销售部的备注信息','2025-11-24 22:02:27.221000','2025-11-24 22:02:27.221000',NULL),('98a743fa-5acb-4915-84ea-26cdcad957dd','市场部',4,0,'市场部的备注信息','2025-11-21 15:44:24.913000','2025-11-21 15:44:24.913000',NULL),('9e8ae5b5-4c82-4ec8-9368-1c39e8e2b291','研发部',1,1,'研发部的备注信息','2025-11-24 22:02:27.073000','2025-11-24 22:02:27.073000',NULL),('9f4c569d-176b-49fe-8e9f-f8d9ff3b8987','产品部',2,1,'产品部的备注信息','2025-11-24 22:46:00.815000','2025-11-24 22:46:00.815000',NULL),('a2dd79be-1f06-445d-9078-db07e932aa6e','运营部',3,1,'运营部的备注信息','2025-11-21 15:44:24.901000','2025-11-21 15:44:24.901000',NULL),('a2e0f784-6a5c-4eba-a24f-039cf3fdce3e','客服部',6,0,'客服部的备注信息','2025-11-24 13:28:41.608000','2025-11-24 13:28:41.608000',NULL),('a48c4c47-f957-48a5-b4f6-2716306aad0e','客服部',6,1,'客服部的备注信息','2025-11-24 22:02:27.353000','2025-11-24 22:02:27.353000',NULL),('a5eff538-6e1e-45ef-8945-8f9d1d941e9e','产品部',2,1,'产品部的备注信息','2025-11-24 22:02:27.006000','2025-11-24 22:02:27.006000',NULL),('a771ce58-a932-4b9b-a597-5de33804482e','研发部',1,1,'研发部的备注信息','2025-11-24 23:25:32.166000','2025-11-24 23:25:32.166000',NULL),('a786d92a-6b90-4757-9606-008acaa5fc75','客服部',6,1,'客服部的备注信息','2025-11-24 22:46:00.556000','2025-11-24 22:46:00.556000',NULL),('a83ea399-a7b3-4cde-a413-24d63a12ca56','客服部',6,1,'客服部的备注信息','2025-11-24 22:46:00.783000','2025-11-24 22:46:00.783000',NULL),('a8404d77-bfcb-46ba-8c97-8d5f98539cfa','研发部',1,1,'研发部的备注信息','2025-11-24 22:02:26.991000','2025-11-24 22:02:26.991000',NULL),('a9db5d34-8da0-42f8-9078-6a91f0e9a306','销售部',5,1,'销售部的备注信息','2025-11-24 23:25:32.234000','2025-11-24 23:25:32.234000',NULL),('ad045da2-870d-4673-baaf-1830508f1dcc','运营部',3,0,'运营部的备注信息','2025-11-24 13:28:41.542000','2025-11-24 13:28:41.542000',NULL),('ad72a219-cb59-4a6f-a179-5aeea691290a','销售部',5,1,'销售部的备注信息','2025-11-24 23:25:32.472000','2025-11-24 23:25:32.472000',NULL),('adc04a8e-7278-4a19-998c-f4f340bc361b','产品部',2,0,'产品部的备注信息','2025-11-21 15:44:24.893000','2025-11-21 15:44:24.893000',NULL),('aed145ab-366b-4196-86cd-f34164c6d1f3','市场部',4,1,'市场部的备注信息','2025-11-24 23:25:32.332000','2025-11-24 23:25:32.332000',NULL),('b16f8d1a-27ab-456b-a7a1-397263181d94','北京分公司',2,1,'北京分公司的备注信息','2025-11-21 15:44:24.748000','2025-11-21 15:44:24.748000',NULL),('b26ff103-3edd-4670-88e2-7fd33ab2647d','运营部',3,1,'运营部的备注信息','2025-11-24 23:25:32.096000','2025-11-24 23:25:32.096000',NULL),('b2ee4f8d-4a9a-4f8c-b547-aed6205126e6','客服部',6,1,'客服部的备注信息','2025-11-21 15:44:24.995000','2025-11-21 15:44:24.995000',NULL),('b30b1ea0-b593-4a1c-9f8d-c86951ef1f75','产品部',2,1,'产品部的备注信息','2025-11-24 22:46:00.437000','2025-11-24 22:46:00.437000',NULL),('b5447fd6-98f0-405c-9467-e6ca92d03883','市场部',4,1,'市场部的备注信息','2025-11-24 22:02:27.206000','2025-11-24 22:02:27.206000',NULL),('b668ef8c-1a60-4715-9be4-10568dcf1ca1','运营部',3,1,'运营部的备注信息','2025-11-21 15:44:24.840000','2025-11-21 15:44:24.840000',NULL),('b7673f96-37a6-44cd-ae5c-1de53471ab61','市场部',4,1,'市场部的备注信息','2025-11-21 15:44:24.978000','2025-11-21 15:44:24.978000',NULL),('bab290a5-da89-4f46-a770-fce2dcc078c3','运营部',3,1,'运营部的备注信息','2025-11-24 22:02:27.297000','2025-11-24 22:02:27.297000',NULL),('bae72184-721a-4c42-9ba2-9e998a9bb6bf','运营部',3,1,'运营部的备注信息','2025-11-24 23:25:32.314000','2025-11-24 23:25:32.314000',NULL),('bd786224-91a1-4eae-8eee-e61e5989727e','gggg',3,1,'运营部的备注信息','2025-11-21 15:44:24.701000','2025-11-22 05:37:23.000000',NULL),('c1beaa28-3cf8-4dbb-91bb-46a3be44fe71','客服部',6,1,'客服部的备注信息','2025-11-24 23:25:32.490000','2025-11-24 23:25:32.490000',NULL),('c3f74b6a-c22b-4ecb-b779-9eade4bce29c','研发部',1,1,'研发部的备注信息','2025-11-24 22:02:27.161000','2025-11-24 22:02:27.161000',NULL),('c4616fa4-e344-45e0-b5ad-8577686f25e7','研发部',1,1,'研发部的备注信息','2025-11-24 22:46:00.425000','2025-11-24 22:46:00.425000',NULL),('c5c849e7-cd37-4571-9396-8776c3ee65f5','市场部',4,1,'市场部的备注信息','2025-11-24 22:46:00.928000','2025-11-24 22:46:00.928000',NULL),('c66d4076-9955-4256-9a60-571b7be816d9','运营部',3,1,'运营部的备注信息','2025-11-24 22:02:27.019000','2025-11-24 22:02:27.019000',NULL),('c84b2221-d6d2-40ac-bdab-3134419547b0','aafsdfd',0,1,NULL,'2025-11-22 05:37:38.909000','2025-11-22 05:37:38.909000',NULL),('c8fb36bb-6397-4185-87f9-a61c63c162ea','运营部',3,1,'运营部的备注信息','2025-11-24 23:25:32.546000','2025-11-24 23:25:32.546000',NULL),('ca7081ce-6a2a-4f71-9b93-901ccac33219','市场部',4,1,'市场部的备注信息','2025-11-24 22:46:00.660000','2025-11-24 22:46:00.660000',NULL),('ca801cfc-ea89-40be-b967-002c3e5dc419','客服部',6,1,'客服部的备注信息','2025-11-24 22:46:00.694000','2025-11-24 22:46:00.694000',NULL),('cd1d81b7-7149-4c07-a100-fa9f9deb429a','研发部',1,1,'22','2025-11-21 15:44:24.759000','2025-11-21 17:26:28.000000',NULL),('ced65c01-19ed-43a9-a226-ed9660f00846','市场部',4,1,'市场部的备注信息','2025-11-21 15:44:24.711000','2025-11-21 15:44:24.711000',NULL),('d001b576-3b4d-45ea-b104-d8a69dae0da7','产品部',2,1,'产品部的备注信息','2025-11-24 23:25:32.082000','2025-11-24 23:25:32.082000',NULL),('d16653f3-1186-4158-b40a-1cdb18b97df3','研发部',1,0,'研发部的备注信息','2025-11-24 13:28:41.426000','2025-11-24 13:28:41.426000',NULL),('d1dfc526-91eb-47bd-9205-d00f06790ea2','研发部',1,1,'研发部的备注信息','2025-11-24 22:46:00.600000','2025-11-24 22:46:00.600000',NULL),('d388ffdc-d681-4620-89a7-53626ab3d988','销售部',5,1,'销售部的备注信息','2025-11-24 22:46:00.524000','2025-11-24 22:46:00.524000',NULL),('d45731d9-d19a-4ff3-b293-15656e8818d3','客服部',6,1,'客服部的备注信息','2025-11-24 22:46:00.874000','2025-11-24 22:46:00.874000',NULL),('d9f75dfe-834d-4ad7-8e59-6d361eeab6ec','研发部',1,1,'研发部的备注信息','2025-11-24 22:46:00.892000','2025-11-24 22:46:00.892000',NULL),('dbca4721-f416-4592-af42-97a6558a9366','产品部',2,1,'产品部的备注信息','2025-11-21 15:44:24.960000','2025-11-21 15:44:24.960000',NULL),('e2987afd-d91d-4f88-b47a-09b8b6666238','研发部',1,1,'研发部的备注信息','2025-11-24 23:25:32.388000','2025-11-24 23:25:32.388000',NULL),('e4318a71-0335-41f7-88d1-e9bc37ee74b5','产品部',2,0,'产品部的备注信息','2025-11-24 13:28:41.440000','2025-11-24 13:28:41.440000',NULL),('e6444a94-eb69-4504-9ec3-00cfe2702904','运营部',3,1,'运营部的备注信息','2025-11-21 15:44:24.967000','2025-11-21 15:44:24.967000',NULL),('e768bccc-d938-479f-94ff-2ede641c8aa6','产品部',2,1,'产品部的备注信息','2025-11-24 22:46:00.629000','2025-11-24 22:46:00.629000',NULL),('e8381c4f-3958-45ae-87f5-e1308d85c1e4','客服部',6,1,'客服部的备注信息','2025-11-21 15:44:24.867000','2025-11-21 15:44:24.867000',NULL),('ea08d3e0-38a8-48b1-8029-da21cbeeb4f0','运营部',3,1,'运营部的备注信息','2025-11-24 23:25:32.429000','2025-11-24 23:25:32.429000',NULL),('ea76986c-29d8-4dde-b0db-e3bccbe9c3eb','产品部',2,0,'产品部的备注信息','2025-11-21 15:44:24.767000','2025-11-21 15:44:24.767000',NULL),('ec8331ea-68a1-44ec-a1c2-4f193de3de48','研发部',1,1,'研发部的备注信息','2025-11-21 15:44:24.823000','2025-11-21 15:44:24.823000',NULL),('f0b60887-d56c-4c29-9c35-a1cbc3218fe9','产品部',2,0,'产品部的备注信息','2025-11-21 15:44:24.831000','2025-11-21 15:44:24.831000',NULL),('f183c6b4-b594-40b1-88bb-1d35bfb0d1f3','研发部',1,1,'研发部的备注信息','2025-11-24 22:46:00.716000','2025-11-24 22:46:00.716000',NULL),('f1d1653d-3f19-45ac-a4a8-7a9af3e3659c','销售部',5,1,'销售部的备注信息','2025-11-24 22:02:27.335000','2025-11-24 22:02:27.335000',NULL),('f32ac747-e64d-4d94-959f-1fee52167860','客服部',6,0,'客服部的备注信息','2025-11-21 15:44:24.804000','2025-11-21 15:44:24.804000',NULL),('f6f3fc7d-5aeb-428b-a82f-cc8f12b07ac6','客服部',6,0,'客服部的备注信息','2025-11-21 15:44:24.736000','2025-11-21 15:44:24.736000',NULL),('fde3dffb-cf32-48de-b84e-894c84a9dabd','销售部',5,1,'销售部的备注信息','2025-11-24 23:25:32.594000','2025-11-24 23:25:32.594000',NULL),('ffadcff5-1f54-4c59-9c67-16a51c203c00','销售部',5,1,'销售部的备注信息','2025-11-24 23:25:32.352000','2025-11-24 23:25:32.352000',NULL);
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary`
--

DROP TABLE IF EXISTS `sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dictionary` (
  `id` varchar(36) NOT NULL,
  `dict_name` varchar(100) NOT NULL,
  `dict_code` varchar(100) NOT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_d30469028ed2d2d05d3c28a3b4` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary`
--

LOCK TABLES `sys_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` VALUES ('7dbf9454-1152-4a16-b3bc-385b1ad5f323','重要性','importance',1,'重要性字典：0-普通，1-良好，2-重要','2025-11-21 15:44:25.216131','2025-11-21 15:44:25.216131');
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary_item`
--

DROP TABLE IF EXISTS `sys_dictionary_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dictionary_item` (
  `id` varchar(36) NOT NULL,
  `dict_id` varchar(255) NOT NULL,
  `label` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  `sort` int NOT NULL DEFAULT '0',
  `status` tinyint NOT NULL DEFAULT '1',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `FK_5b6cf2c827deb65c9566aa112c0` (`dict_id`),
  CONSTRAINT `FK_5b6cf2c827deb65c9566aa112c0` FOREIGN KEY (`dict_id`) REFERENCES `sys_dictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary_item`
--

LOCK TABLES `sys_dictionary_item` WRITE;
/*!40000 ALTER TABLE `sys_dictionary_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dictionary_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `redirect` varchar(255) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `meta` json DEFAULT NULL COMMENT 'Menu meta information (title, icon, alwaysShow, noCache, etc.)',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '0: Directory, 1: Menu, 2: Button',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '0: disabled, 1: enabled',
  `permission` varchar(100) DEFAULT NULL,
  `sort` int NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('061670ed-bf41-4316-9f8b-1ac2146cb8dc','menu2','views/Level/Menu2',NULL,'菜单2','Menu2Demo',NULL,'{\"title\": \"菜单2\"}',1,1,NULL,1,'2025-11-25 01:23:19.571484','2025-11-25 01:23:19.571484','0d72f6a3-6dae-4127-a2a1-5ccd23ce8388'),('0d72f6a3-6dae-4127-a2a1-5ccd23ce8388','/level','#','/level/menu1/menu1-1/menu1-1-1','菜单','Level','vi-carbon:skill-level-advanced','{\"icon\": \"vi-carbon:skill-level-advanced\", \"title\": \"菜单\"}',0,1,NULL,0,'2025-11-25 01:23:19.502407','2025-11-25 01:23:19.502407',NULL),('2d832c74-2b2c-4ca6-814e-3059469e1a69','example-edit','views/Example/Page/ExampleEdit',NULL,'综合示例-编辑','ExampleEdit',NULL,'{\"title\": \"综合示例-编辑\", \"hidden\": true, \"noCache\": true, \"activeMenu\": \"/example/example-page\", \"noTagsView\": true, \"showMainRoute\": true}',1,1,NULL,1,'2025-11-25 01:23:19.650081','2025-11-25 01:23:19.650081','52581b91-f225-435b-a334-d344c4f641b6'),('34dd195f-8282-4ca8-af3b-e76d23f50a2e','menu1','##','/level/menu1/menu1-1/menu1-1-1','菜单1','Menu1',NULL,'{\"title\": \"菜单1\"}',0,1,NULL,1,'2025-11-25 01:23:19.513859','2025-11-25 01:23:19.513859','0d72f6a3-6dae-4127-a2a1-5ccd23ce8388'),('3b8d6ba0-4d55-4bb5-b4d2-b24d45e70c2a','role','views/Authorization/Role/Role',NULL,'角色管理','Role',NULL,'{\"title\": \"角色管理\"}',1,1,'add,edit,delete',1,'2025-11-25 01:23:19.458681','2025-11-25 01:23:19.458681','d06cdc74-8a1a-45d3-82e6-1e8972baa7a1'),('3bbba10f-c9eb-4432-b255-cf3512fae25e','example-page','views/Example/Page/ExamplePage',NULL,'综合示例-页面','ExamplePage',NULL,'{\"title\": \"综合示例-页面\"}',1,1,'add,edit,delete,view',1,'2025-11-25 01:23:19.616945','2025-11-25 01:23:19.616945','52581b91-f225-435b-a334-d344c4f641b6'),('52581b91-f225-435b-a334-d344c4f641b6','/example','#','/example/example-dialog','综合示例','Example','vi-ep:management','{\"icon\": \"vi-ep:management\", \"title\": \"综合示例\", \"alwaysShow\": true}',0,1,NULL,0,'2025-11-25 01:23:19.587149','2025-11-25 01:23:19.587149',NULL),('6cc595c7-3725-43a6-973f-0e9dceac18b1','https://element-plus-admin-doc.cn/',NULL,NULL,'文档','DocumentLink',NULL,'{\"title\": \"文档\"}',1,1,NULL,1,'2025-11-25 01:23:19.487561','2025-11-25 01:23:19.487561','f9646034-77d9-4899-97c6-c976169df79e'),('77c49072-aa81-4b91-8c0d-0d0c26d06234','user','views/Authorization/User/User',NULL,'用户管理','User',NULL,'{\"title\": \"用户管理\"}',1,1,'add,edit,delete',1,'2025-11-25 01:23:19.426830','2025-11-25 01:23:19.426830','d06cdc74-8a1a-45d3-82e6-1e8972baa7a1'),('8b0813ae-370b-48fc-8797-de29dd69f7bf','menu1-1','##','/level/menu1/menu1-1/menu1-1-1','菜单1-1','Menu11',NULL,'{\"title\": \"菜单1-1\", \"alwaysShow\": true}',0,1,NULL,2,'2025-11-25 01:23:19.525180','2025-11-25 01:23:19.525180','34dd195f-8282-4ca8-af3b-e76d23f50a2e'),('974e86c7-9372-4154-a526-e3e57d8c4707','department','views/Authorization/Department/Department',NULL,'部门管理','Department',NULL,'{\"title\": \"部门管理\"}',1,1,'add,edit,delete',1,'2025-11-25 01:23:19.413940','2025-11-25 01:23:19.413940','d06cdc74-8a1a-45d3-82e6-1e8972baa7a1'),('a1f089ed-8c2f-4767-a781-73dc33be673a','/dashboard','#','/dashboard/analysis','首页','Dashboard','vi-ant-design:dashboard-filled','{\"icon\": \"vi-ant-design:dashboard-filled\", \"title\": \"首页\", \"alwaysShow\": true}',0,1,NULL,0,'2025-11-25 01:23:19.352649','2025-11-25 01:23:19.352649',NULL),('a45b58f5-efac-4d97-92d2-b3963b71e1e3','example-detail','views/Example/Page/ExampleDetail',NULL,'综合示例-详情','ExampleDetail',NULL,'{\"title\": \"综合示例-详情\", \"hidden\": true, \"noCache\": true, \"activeMenu\": \"/example/example-page\", \"noTagsView\": true, \"showMainRoute\": true}',1,1,NULL,1,'2025-11-25 01:23:19.664093','2025-11-25 01:23:19.664093','52581b91-f225-435b-a334-d344c4f641b6'),('a95bacc2-08a2-481c-9bdd-2ea2d4dcc629','example-dialog','views/Example/Dialog/ExampleDialog',NULL,'综合示例-弹窗','ExampleDialog',NULL,'{\"title\": \"综合示例-弹窗\"}',1,1,'add,edit,delete,view',1,'2025-11-25 01:23:19.604375','2025-11-25 01:23:19.604375','52581b91-f225-435b-a334-d344c4f641b6'),('ae0e8325-0556-4b1e-9b2a-e8a516cb0e46','menu','views/Authorization/Menu/Menu',NULL,'菜单管理','Menu',NULL,'{\"title\": \"菜单管理\"}',1,1,'add,edit,delete',1,'2025-11-25 01:23:19.441119','2025-11-25 01:23:19.441119','d06cdc74-8a1a-45d3-82e6-1e8972baa7a1'),('b0c5e5f5-c3ea-4621-a7e1-f732da32b1c0','menu1-1-1','views/Level/Menu111',NULL,'菜单1-1-1','Menu111',NULL,'{\"title\": \"菜单1-1-1\"}',1,1,NULL,3,'2025-11-25 01:23:19.538721','2025-11-25 01:23:19.538721','8b0813ae-370b-48fc-8797-de29dd69f7bf'),('ba9071ad-7ab4-45bd-8617-243cb0c3ef89','analysis','views/Dashboard/Analysis',NULL,'分析页','Analysis',NULL,'{\"title\": \"分析页\", \"noCache\": true, \"permission\": [\"add\", \"edit\"]}',1,1,'add,edit',1,'2025-11-25 01:23:19.370481','2025-11-25 01:23:19.370481','a1f089ed-8c2f-4767-a781-73dc33be673a'),('d06cdc74-8a1a-45d3-82e6-1e8972baa7a1','/authorization','#','/authorization/user','权限管理','Authorization','vi-eos-icons:role-binding','{\"icon\": \"vi-eos-icons:role-binding\", \"title\": \"权限管理\", \"alwaysShow\": true}',0,1,NULL,0,'2025-11-25 01:23:19.402416','2025-11-25 01:23:19.402416',NULL),('d5eccd2f-97e8-47be-9a3e-dd406126e424','workplace','views/Dashboard/Workplace',NULL,'工作台','Workplace',NULL,'{\"title\": \"工作台\", \"noCache\": true}',1,1,'add,edit,delete',1,'2025-11-25 01:23:19.385953','2025-11-25 01:23:19.385953','a1f089ed-8c2f-4767-a781-73dc33be673a'),('e83999a5-d973-4147-9b0e-2d30691485f9','menu1-2','views/Level/Menu12',NULL,'菜单1-2','Menu12',NULL,'{\"title\": \"菜单1-2\"}',1,1,NULL,2,'2025-11-25 01:23:19.559503','2025-11-25 01:23:19.559503','34dd195f-8282-4ca8-af3b-e76d23f50a2e'),('ec73410f-8032-4926-a94c-cc156e6481d6','example-add','views/Example/Page/ExampleAdd',NULL,'综合示例-新增','ExampleAdd',NULL,'{\"title\": \"综合示例-新增\", \"hidden\": true, \"noCache\": true, \"activeMenu\": \"/example/example-page\", \"noTagsView\": true, \"showMainRoute\": true}',1,1,NULL,1,'2025-11-25 01:23:19.633568','2025-11-25 01:23:19.633568','52581b91-f225-435b-a334-d344c4f641b6'),('f9646034-77d9-4899-97c6-c976169df79e','/external-link','#',NULL,'文档','ExternalLink','vi-clarity:document-solid','{\"icon\": \"vi-clarity:document-solid\", \"title\": \"文档\"}',0,1,NULL,0,'2025-11-25 01:23:19.472963','2025-11-25 01:23:19.472963',NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_value` varchar(50) NOT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_e5e7bf40bc3991b1cca723e6a4` (`role_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('38dcfde2-2a6e-4b8a-a7de-8005b6516c65','test','test',1,NULL,'2025-11-23 12:48:34.061000','2025-11-23 12:48:34.061000'),('708eb5de-2e9e-413c-8239-5c976c5984fb','General User','user',1,'Standard user with limited access','2025-11-21 15:44:24.656000','2025-11-21 15:44:24.656000'),('71e252a3-1372-4d26-bffb-28be0243abe4','Super Admin','admin',1,'Super Administrator with full access','2025-11-21 15:44:24.634000','2025-11-24 23:25:32.860000');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `IDX_b65fa84413c357d7282153b4a8` (`role_id`),
  KEY `IDX_543ffcaa38d767909d9022f252` (`menu_id`),
  CONSTRAINT `FK_543ffcaa38d767909d9022f2522` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_b65fa84413c357d7282153b4a88` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('71e252a3-1372-4d26-bffb-28be0243abe4','061670ed-bf41-4316-9f8b-1ac2146cb8dc'),('71e252a3-1372-4d26-bffb-28be0243abe4','0d72f6a3-6dae-4127-a2a1-5ccd23ce8388'),('71e252a3-1372-4d26-bffb-28be0243abe4','2d832c74-2b2c-4ca6-814e-3059469e1a69'),('71e252a3-1372-4d26-bffb-28be0243abe4','34dd195f-8282-4ca8-af3b-e76d23f50a2e'),('71e252a3-1372-4d26-bffb-28be0243abe4','3b8d6ba0-4d55-4bb5-b4d2-b24d45e70c2a'),('71e252a3-1372-4d26-bffb-28be0243abe4','3bbba10f-c9eb-4432-b255-cf3512fae25e'),('71e252a3-1372-4d26-bffb-28be0243abe4','52581b91-f225-435b-a334-d344c4f641b6'),('71e252a3-1372-4d26-bffb-28be0243abe4','6cc595c7-3725-43a6-973f-0e9dceac18b1'),('71e252a3-1372-4d26-bffb-28be0243abe4','77c49072-aa81-4b91-8c0d-0d0c26d06234'),('71e252a3-1372-4d26-bffb-28be0243abe4','8b0813ae-370b-48fc-8797-de29dd69f7bf'),('71e252a3-1372-4d26-bffb-28be0243abe4','974e86c7-9372-4154-a526-e3e57d8c4707'),('71e252a3-1372-4d26-bffb-28be0243abe4','a1f089ed-8c2f-4767-a781-73dc33be673a'),('71e252a3-1372-4d26-bffb-28be0243abe4','a45b58f5-efac-4d97-92d2-b3963b71e1e3'),('71e252a3-1372-4d26-bffb-28be0243abe4','a95bacc2-08a2-481c-9bdd-2ea2d4dcc629'),('71e252a3-1372-4d26-bffb-28be0243abe4','ae0e8325-0556-4b1e-9b2a-e8a516cb0e46'),('71e252a3-1372-4d26-bffb-28be0243abe4','b0c5e5f5-c3ea-4621-a7e1-f732da32b1c0'),('71e252a3-1372-4d26-bffb-28be0243abe4','ba9071ad-7ab4-45bd-8617-243cb0c3ef89'),('71e252a3-1372-4d26-bffb-28be0243abe4','d06cdc74-8a1a-45d3-82e6-1e8972baa7a1'),('71e252a3-1372-4d26-bffb-28be0243abe4','d5eccd2f-97e8-47be-9a3e-dd406126e424'),('71e252a3-1372-4d26-bffb-28be0243abe4','e83999a5-d973-4147-9b0e-2d30691485f9'),('71e252a3-1372-4d26-bffb-28be0243abe4','ec73410f-8032-4926-a94c-cc156e6481d6'),('71e252a3-1372-4d26-bffb-28be0243abe4','f9646034-77d9-4899-97c6-c976169df79e');
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_table_example`
--

DROP TABLE IF EXISTS `sys_table_example`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_table_example` (
  `id` varchar(36) NOT NULL,
  `author` varchar(100) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text,
  `importance` int NOT NULL DEFAULT '1',
  `display_time` datetime NOT NULL,
  `pageviews` int NOT NULL DEFAULT '0',
  `image_uri` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `parentId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_87203206551e0d05259ed74655d` (`parentId`),
  CONSTRAINT `FK_87203206551e0d05259ed74655d` FOREIGN KEY (`parentId`) REFERENCES `sys_table_example` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_table_example`
--

LOCK TABLES `sys_table_example` WRITE;
/*!40000 ALTER TABLE `sys_table_example` DISABLE KEYS */;
INSERT INTO `sys_table_example` VALUES ('c57d72b4-dbac-4f6d-84c8-172415b2446d','212','121','<p>21212</p>',2,'2025-11-22 01:17:51',2,NULL,'2025-11-21 17:22:54.804916','2025-11-21 17:22:54.804916',NULL),('f06ad793-d8e5-4575-9466-d3ba7ddf74c3','2423','3223423','<p>wrfsfs</p>',1,'2025-11-22 01:23:06',2,NULL,'2025-11-21 17:23:12.657511','2025-11-21 17:23:12.657511',NULL);
/*!40000 ALTER TABLE `sys_table_example` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_table_example_closure`
--

DROP TABLE IF EXISTS `sys_table_example_closure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_table_example_closure` (
  `id_ancestor` varchar(255) NOT NULL,
  `id_descendant` varchar(255) NOT NULL,
  PRIMARY KEY (`id_ancestor`,`id_descendant`),
  KEY `IDX_17215fd960ba45e28436556b39` (`id_ancestor`),
  KEY `IDX_66d1493577f7576528afb3cb0c` (`id_descendant`),
  CONSTRAINT `FK_17215fd960ba45e28436556b39e` FOREIGN KEY (`id_ancestor`) REFERENCES `sys_table_example` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_66d1493577f7576528afb3cb0cf` FOREIGN KEY (`id_descendant`) REFERENCES `sys_table_example` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_table_example_closure`
--

LOCK TABLES `sys_table_example_closure` WRITE;
/*!40000 ALTER TABLE `sys_table_example_closure` DISABLE KEYS */;
INSERT INTO `sys_table_example_closure` VALUES ('c57d72b4-dbac-4f6d-84c8-172415b2446d','c57d72b4-dbac-4f6d-84c8-172415b2446d'),('f06ad793-d8e5-4575-9466-d3ba7ddf74c3','f06ad793-d8e5-4575-9466-d3ba7ddf74c3');
/*!40000 ALTER TABLE `sys_table_example_closure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `dept_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_9e7164b2f1ea1348bc0eb0a7da` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('07e4fef3-d835-4a87-8d9f-b03682267543','23432423','$2b$10$yW5P.a6umEjmwWHe5wlbeOJdQbNYoNVD0YZd.T7d1ES.aHfdOow.y',NULL,'11',1,'2025-11-22 05:38:50.822000','2025-11-22 05:51:15.000000',NULL),('b4fb3e28-629f-41f4-8356-7f93ea40348c','admin','$2b$10$umt6sD.saACeHgoa7NxxeexxEWyemBTOWUEXd3yoA2GKBNfoLtlIe','Admin','admin@example.com',1,'2025-11-21 15:44:25.308000','2025-11-24 23:25:32.882000',NULL),('d0c2c3e4-9498-4f09-bb02-7cae807293db','gggg','$2b$10$/3qabaK1y.bdTFS6i7a9w.WAhodtlzszhw9Cfkbd5.KkZMeLW0U1y',NULL,NULL,1,'2025-11-21 17:31:12.945000','2025-11-22 05:45:13.000000',NULL),('fda35615-98fa-4d16-ba33-df7c0c377c24','rrre','$2b$10$L8QHslmkvmKJoa4YUIdqMOVdTDoBZTydZZBLyDjh6EiYVcagsZnhm',NULL,NULL,1,'2025-11-22 05:38:33.809000','2025-11-22 05:51:50.000000',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `IDX_71b4edf9aedbd3e5707156e80a` (`user_id`),
  KEY `IDX_e8300bfcf561ed417f5f02c677` (`role_id`),
  CONSTRAINT `FK_71b4edf9aedbd3e5707156e80a2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_e8300bfcf561ed417f5f02c6776` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('b4fb3e28-629f-41f4-8356-7f93ea40348c','71e252a3-1372-4d26-bffb-28be0243abe4');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'nest_admin'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-25  9:25:37
