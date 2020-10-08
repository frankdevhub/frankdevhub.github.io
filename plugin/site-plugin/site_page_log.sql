/*
Navicat MySQL Data Transfer

Source Server         : 39.98.246.50
Source Server Version : 50645
Source Host           : 39.98.246.50:3306
Source Database       : frankdevhub_site_log

Target Server Type    : MYSQL
Target Server Version : 50645
File Encoding         : 65001

Date: 2020-03-06 22:13:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for site_page_log
-- ----------------------------
DROP TABLE IF EXISTS `site_page_log`;
CREATE TABLE `site_page_log` (
  `id` bigint(20) NOT NULL,
  `date` bigint(20) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL,
  `mac_address` varchar(50) DEFAULT NULL,
  `lat` varchar(10) DEFAULT NULL,
  `lng` varchar(10) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
