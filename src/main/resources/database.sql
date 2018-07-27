drop database if exists job_manager;

create database job_manager DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use job_manager;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `crontab`;
CREATE TABLE `crontab` (
  `job_id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `descript` varchar(300) CHARACTER SET utf8 DEFAULT NULL,
  `performdate` timestamp NULL DEFAULT NULL,
  `cron_exp` varchar(32) CHARACTER SET utf8 NOT NULL,
  `url` varchar(500) CHARACTER SET utf8 NOT NULL,
  `status` smallint(6) DEFAULT '1' COMMENT '0 启用 1停用',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `unique_url` (`url`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `login_ip` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0' COMMENT '用户状态 0正常 1 禁止 2 在线',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `job_manager`.`sys_user` (`user_id`, `user_name`, `password`, `login_ip`, `email`, `create_time`, `update_time`, `status`) VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', NULL, 'admin@admin.com', '2018-07-24 16:51:43', '2018-07-24 16:51:45', '0');


