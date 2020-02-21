# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 114.55.66.195    Database: equipment_management
# ------------------------------------------------------
# Server version 5.5.62-log

DROP DATABASE IF EXISTS `equipment_management`;
CREATE DATABASE `equipment_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `equipment_management`;

#
# Source for table brand
#

DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Dumping data for table brand
#

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'333');
INSERT INTO `brand` VALUES (3,'adin');
INSERT INTO `brand` VALUES (4,'sdasd');
INSERT INTO `brand` VALUES (5,'21312');
INSERT INTO `brand` VALUES (6,'555');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table circulate
#

DROP TABLE IF EXISTS `circulate`;
CREATE TABLE `circulate` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `department` varchar(255) NOT NULL DEFAULT '',
  `time` varchar(255) DEFAULT NULL,
  `type` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Dumping data for table circulate
#

LOCK TABLES `circulate` WRITE;
/*!40000 ALTER TABLE `circulate` DISABLE KEYS */;
INSERT INTO `circulate` VALUES (3,12,'123456','jj','2019-12-22 15:08:34','1');
INSERT INTO `circulate` VALUES (4,321312,'333123','331221','2020-01-04 21:33:45','0');
INSERT INTO `circulate` VALUES (5,312231,'33123','331231333','2020-01-04 21:49:01','1');
INSERT INTO `circulate` VALUES (6,12321312,'Jiahai Zhou','','2020-01-04 23:00:45','1');
INSERT INTO `circulate` VALUES (7,213213,'Jiahai Zhou','','2020-01-04 23:00:58','1');
/*!40000 ALTER TABLE `circulate` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table equipment
#

DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `brand_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table equipment
#

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,'1',1);
INSERT INTO `equipment` VALUES (2,'3213',3);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table repair
#

DROP TABLE IF EXISTS `repair`;
CREATE TABLE `repair` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `phone` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) DEFAULT '',
  `reason` varchar(255) NOT NULL DEFAULT '',
  `equipment_id` int(11) NOT NULL DEFAULT '0',
  `level` varchar(255) DEFAULT '',
  `remark` varchar(255) DEFAULT '',
  `application_time` varchar(255) DEFAULT NULL,
  `repair_time` varchar(255) DEFAULT NULL,
  `sent_time` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

#
# Dumping data for table repair
#

LOCK TABLES `repair` WRITE;
/*!40000 ALTER TABLE `repair` DISABLE KEYS */;
INSERT INTO `repair` VALUES (8,'Jiahai Zhou','13138100103','312321','3213213',213213,'high','[报修]312321','2020-01-04 20:14:27','2020-01-04 23:06:13','2020-01-04 23:07:12','4');
INSERT INTO `repair` VALUES (9,'Jiahai Zhou','13138100103','3123','444',12321312,'high','[报修]21321312','2020-01-04 20:26:57',NULL,'2020-01-04 23:03:51','3');
INSERT INTO `repair` VALUES (10,'21312321','3123','312312','3123',12312321,'high','[报修]31231','2020-01-04 20:36:31',NULL,NULL,'0');
INSERT INTO `repair` VALUES (11,'21312321','3123','312312','3123',12312321,'high','[报修]31231','2020-01-04 20:37:14',NULL,NULL,'0');
INSERT INTO `repair` VALUES (12,'312312','12312','3123','12312',3123,'high','[报修]123123','2020-01-04 20:37:31',NULL,NULL,'0');
INSERT INTO `repair` VALUES (13,'Jiahai Zhou','13138100103','3123123','312312',21312,'high','[报修]312312','2020-01-04 20:39:19',NULL,NULL,'0');
INSERT INTO `repair` VALUES (14,'312312','2312312','31','3123',3123,'high','[报修]1212312','2020-01-04 20:40:54',NULL,NULL,'0');
INSERT INTO `repair` VALUES (15,'3123213','21312','3123','12312',3123213,'high','[报修]12321','2020-01-04 20:43:10',NULL,NULL,'0');
INSERT INTO `repair` VALUES (16,'321321','321321321','312321','3123123',12312312,'high','[报修]32321','2020-01-04 20:43:32',NULL,NULL,'0');
INSERT INTO `repair` VALUES (17,'213213','21312','3213','2132',32131,'high','[报修]321','2020-01-04 20:43:55',NULL,NULL,'0');
INSERT INTO `repair` VALUES (18,'312','13138100103','无','3123',21321,'high','[报修]321321','2020-01-04 20:44:38',NULL,NULL,'0');
INSERT INTO `repair` VALUES (19,'Jiahai Zhou','13138100103','无','3123',21312321,'high','[报修]1323','2020-01-04 20:45:35',NULL,NULL,'0');
INSERT INTO `repair` VALUES (20,'3','13138100103','无','213213',21321,'high','[报修]312321','2020-01-04 20:46:23',NULL,NULL,'0');
INSERT INTO `repair` VALUES (21,'Jiahai Zhou','13138100103','无','3123',21321,'high','[报修]2121','2020-01-04 20:47:00',NULL,NULL,'0');
INSERT INTO `repair` VALUES (22,'Jiahai Zhou','13138100103','无','1213',111,'normal','[报修]112','2020-01-04 20:56:16',NULL,NULL,'0');
/*!40000 ALTER TABLE `repair` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table resource
#

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(32) DEFAULT NULL COMMENT '资源父ID',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `perms` varchar(64) DEFAULT NULL COMMENT '权限标识符',
  `type` char(1) NOT NULL COMMENT '类型：0：目录，1：菜单，2：按钮',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Dumping data for table resource
#

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,0,'系统管理','sys','0');
INSERT INTO `resource` VALUES (2,1,'用户管理','sys:users','1');
INSERT INTO `resource` VALUES (3,1,'角色管理','sys:roles','1');
INSERT INTO `resource` VALUES (4,1,'资源管理','sys:roles','1');
INSERT INTO `resource` VALUES (5,1,'在线管理','sys:online','1');
INSERT INTO `resource` VALUES (6,1,'设备管理','sys:equipment','1');
INSERT INTO `resource` VALUES (7,1,'类型管理','sys:brand','1');
INSERT INTO `resource` VALUES (8,1,'报修管理','sys:repair','1');
INSERT INTO `resource` VALUES (9,2,'添加用户','sys:user:insert','2');
INSERT INTO `resource` VALUES (10,2,'删除用户','sys:user:delete','2');
INSERT INTO `resource` VALUES (11,2,'修改用户','sys:user:update','2');
INSERT INTO `resource` VALUES (12,2,'查询用户','sys:user:list','2');
INSERT INTO `resource` VALUES (13,2,'用户详情','sys:user:detail','2');
INSERT INTO `resource` VALUES (14,3,'添加角色','sys:role:insert','2');
INSERT INTO `resource` VALUES (15,3,'删除角色','sys:role:delete','2');
INSERT INTO `resource` VALUES (16,3,'修改角色','sys:role:update','2');
INSERT INTO `resource` VALUES (17,3,'查询角色','sys:role:list','2');
INSERT INTO `resource` VALUES (18,3,'角色详情','sys:role:detail','2');
INSERT INTO `resource` VALUES (19,4,'添加资源','sys:resource:insert','2');
INSERT INTO `resource` VALUES (20,4,'删除资源','sys:resource:delete','2');
INSERT INTO `resource` VALUES (21,4,'修改资源','sys:resource:update','2');
INSERT INTO `resource` VALUES (22,4,'查询资源','sys:resource:list','2');
INSERT INTO `resource` VALUES (23,4,'资源详情','sys:resource:detail','2');
INSERT INTO `resource` VALUES (24,5,'踢出用户','sys:online:delete','2');
INSERT INTO `resource` VALUES (25,1,'出入库管理','sys:circulate','1');
INSERT INTO `resource` VALUES (26,8,'报修申请','sys:repair:apply','2');
INSERT INTO `resource` VALUES (28,8,'报修修改','sys:repair:update','2');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table role
#

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `grade` tinyint(2) NOT NULL DEFAULT '0' COMMENT '角色等级',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Dumping data for table role
#

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (7,'超级管理员',1,'超级管理员,全站管理');
INSERT INTO `role` VALUES (8,'管理员',5,'管理员,全站管理');
INSERT INTO `role` VALUES (9,'游客',10,'游客');
INSERT INTO `role` VALUES (16,'观察员',9,'试试');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table role_resource
#

DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(32) NOT NULL COMMENT '角色id',
  `resource_id` int(32) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Dumping data for table role_resource
#

LOCK TABLES `role_resource` WRITE;
/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;
INSERT INTO `role_resource` VALUES (92,7,1);
INSERT INTO `role_resource` VALUES (93,7,2);
INSERT INTO `role_resource` VALUES (94,7,3);
INSERT INTO `role_resource` VALUES (95,7,4);
INSERT INTO `role_resource` VALUES (96,7,9);
INSERT INTO `role_resource` VALUES (97,7,10);
INSERT INTO `role_resource` VALUES (98,7,11);
INSERT INTO `role_resource` VALUES (99,7,12);
INSERT INTO `role_resource` VALUES (100,7,13);
INSERT INTO `role_resource` VALUES (101,7,14);
INSERT INTO `role_resource` VALUES (102,7,15);
INSERT INTO `role_resource` VALUES (103,7,16);
INSERT INTO `role_resource` VALUES (104,7,17);
INSERT INTO `role_resource` VALUES (105,7,18);
INSERT INTO `role_resource` VALUES (106,7,19);
INSERT INTO `role_resource` VALUES (107,7,20);
INSERT INTO `role_resource` VALUES (108,7,21);
INSERT INTO `role_resource` VALUES (109,7,22);
INSERT INTO `role_resource` VALUES (110,7,23);
INSERT INTO `role_resource` VALUES (111,8,1);
INSERT INTO `role_resource` VALUES (112,8,2);
INSERT INTO `role_resource` VALUES (113,8,3);
INSERT INTO `role_resource` VALUES (114,8,4);
INSERT INTO `role_resource` VALUES (115,8,9);
INSERT INTO `role_resource` VALUES (116,8,10);
INSERT INTO `role_resource` VALUES (117,8,11);
INSERT INTO `role_resource` VALUES (118,8,13);
INSERT INTO `role_resource` VALUES (119,8,14);
INSERT INTO `role_resource` VALUES (120,8,15);
INSERT INTO `role_resource` VALUES (121,8,16);
INSERT INTO `role_resource` VALUES (122,8,17);
INSERT INTO `role_resource` VALUES (123,8,18);
INSERT INTO `role_resource` VALUES (124,8,20);
INSERT INTO `role_resource` VALUES (125,8,21);
INSERT INTO `role_resource` VALUES (126,8,23);
INSERT INTO `role_resource` VALUES (127,8,19);
INSERT INTO `role_resource` VALUES (128,16,2);
INSERT INTO `role_resource` VALUES (129,16,1);
INSERT INTO `role_resource` VALUES (130,16,3);
INSERT INTO `role_resource` VALUES (131,16,5);
INSERT INTO `role_resource` VALUES (132,16,12);
INSERT INTO `role_resource` VALUES (133,16,17);
INSERT INTO `role_resource` VALUES (134,16,4);
INSERT INTO `role_resource` VALUES (135,16,22);
INSERT INTO `role_resource` VALUES (136,7,25);
INSERT INTO `role_resource` VALUES (137,7,26);
INSERT INTO `role_resource` VALUES (138,9,1);
/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
  `account` varchar(64) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(128) NOT NULL DEFAULT '' COMMENT '盐',
  `forbidden` char(1) NOT NULL DEFAULT '' COMMENT '是否禁用 0：否；1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Dumping data for table user
#

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'admin222','admin2','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (6,'admin','admin','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (16,'admin','admin5','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (18,'admin','admin3','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (19,'admin','admin4','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (20,'admin','admin66','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (22,'admin','admin6','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (23,'admin','2','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (24,'admin','2222222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (25,'admin','admin2222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (26,'admin','222222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (27,'admin','222222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (28,'admin','22222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (29,'admin','222','d3ce866c2a856303a182c9e595d498f5','8e7386142591cfa96ef7c293b5dfe72c','0');
INSERT INTO `user` VALUES (30,'addd','111','149fa287450eab4aad2e0b5fe53b6ab7','d17b3c809b569a0de23f3c2a7d4bb1cd','0');
INSERT INTO `user` VALUES (31,'试试哦','33333','400b06394c3e368c44e42e2376060534','223be5b13e81115c29c2ba107b62af30','0');
INSERT INTO `user` VALUES (32,'撒大声地','aaaa','980d9b08b386afd5130f7a132cea85e2','2c2cbcd7de7057f1a849baac5e31ccf3','0');
INSERT INTO `user` VALUES (33,'admin','dadas','c93a6929787a1a78d0eec1608cb256fc','76830144b6e4df658213f205da3f0d62','0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user_role
#

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(32) NOT NULL COMMENT '用户ID',
  `role_id` int(32) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Dumping data for table user_role
#

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (58,4,7);
INSERT INTO `user_role` VALUES (59,4,8);
INSERT INTO `user_role` VALUES (61,6,8);
INSERT INTO `user_role` VALUES (62,6,7);
INSERT INTO `user_role` VALUES (63,30,9);
INSERT INTO `user_role` VALUES (64,31,9);
INSERT INTO `user_role` VALUES (65,32,9);
INSERT INTO `user_role` VALUES (66,33,9);
INSERT INTO `user_role` VALUES (67,29,9);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
