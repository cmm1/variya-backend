/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.28 : Database - variya
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `security_menu` */

DROP TABLE IF EXISTS `security_menu`;

CREATE TABLE `security_menu` (
  `id` bigint NOT NULL COMMENT 'id',
  `parent_id` bigint DEFAULT NULL COMMENT '父id',
  `menu_name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `menu_path` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `menu_icon` varchar(255) DEFAULT NULL COMMENT '菜单icon',
  `menu_sort` varchar(255) DEFAULT NULL COMMENT '排序值',
  `deleted` int DEFAULT NULL COMMENT '是否删除 1:正常 0：已删除',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='菜单表';

/*Data for the table `security_menu` */

LOCK TABLES `security_menu` WRITE;

UNLOCK TABLES;

/*Table structure for table `security_role` */

DROP TABLE IF EXISTS `security_role`;

CREATE TABLE `security_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述',
  `deleted` int DEFAULT '1' COMMENT '是否删除 1:正常 0：已删除',
  `create_time` datetime DEFAULT NULL,
  `creater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `security_role` */

LOCK TABLES `security_role` WRITE;

insert  into `security_role`(`id`,`name`,`description`,`deleted`,`create_time`,`creater`,`update_time`,`updater`) values 
(1,'超级管理员','超级管理员',1,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `security_role_menu` */

DROP TABLE IF EXISTS `security_role_menu`;

CREATE TABLE `security_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint NOT NULL COMMENT '权限id',
  `menu_id` varchar(255) DEFAULT NULL COMMENT '菜单id',
  `deleted` int DEFAULT NULL COMMENT '是否删除 1:正常 0：已删除',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色菜单中间表;';

/*Data for the table `security_role_menu` */

LOCK TABLES `security_role_menu` WRITE;

UNLOCK TABLES;

/*Table structure for table `security_user` */

DROP TABLE IF EXISTS `security_user`;

CREATE TABLE `security_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称（姓名）',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号（手机号）',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `account_non_expired` tinyint(1) DEFAULT '1' COMMENT '是否过期',
  `account_non_locked` tinyint(1) DEFAULT '1' COMMENT '是否冻结',
  `credentials_non_expired` tinyint(1) DEFAULT '1' COMMENT '证书过期',
  `rush_count` int DEFAULT '0' COMMENT '加急次数，每天两次',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `status` int DEFAULT '0' COMMENT '0离线 1在线 2忙碌',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `security_user` */

LOCK TABLES `security_user` WRITE;

insert  into `security_user`(`id`,`nick_name`,`username`,`password`,`account_non_expired`,`account_non_locked`,`credentials_non_expired`,`rush_count`,`enabled`,`create_time`,`creater`,`update_time`,`updater`,`status`) values 
(1,'超级管理员','admin','$2a$10$SO7XrctCQ8dI4bUlJyu5ge/WnVkXH18bi1xKQLa91he5Y06YxCN1e',1,1,1,0,1,'2024-01-11 17:45:14','superAdmin',NULL,NULL,1);

UNLOCK TABLES;

/*Table structure for table `security_user_role` */

DROP TABLE IF EXISTS `security_user_role`;

CREATE TABLE `security_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `security_user_role` */

LOCK TABLES `security_user_role` WRITE;

insert  into `security_user_role`(`id`,`user_id`,`role_id`) values 
(1,1,1);

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
