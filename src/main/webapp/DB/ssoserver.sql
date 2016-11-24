/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : ssoserver

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2016-08-19 09:58:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userid` varchar(40) NOT NULL COMMENT '用户标识',
  `username` varchar(50) NOT NULL COMMENT '用户姓名',
  `useraccount` varchar(100) NOT NULL COMMENT '用户账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `smscode` varchar(6) DEFAULT NULL COMMENT ' 验证码',
  `regtime` datetime NOT NULL COMMENT '注册时间',
  `lastlogintime` datetime DEFAULT NULL COMMENT ' 最后登录时间',
  `logintoken` varchar(50) DEFAULT NULL COMMENT '自动登陆标识',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('24672905350610946', '张三', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18581343206', null, '2016-08-08 11:23:00', null, '');
