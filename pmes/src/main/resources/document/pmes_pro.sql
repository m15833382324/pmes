/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50536
Source Host           : 127.0.0.1:3306
Source Database       : pmes_pro

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2018-04-20 19:49:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_auth_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_history`;
CREATE TABLE `t_auth_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `authtime` char(19) DEFAULT NULL,
  `authtype` varchar(8) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `btid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_history
-- ----------------------------

-- ----------------------------
-- Table structure for `t_bug_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `t_bug_ticket`;
CREATE TABLE `t_bug_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` char(1) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `happentime` char(19) DEFAULT NULL,
  `createtime` char(19) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `procid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`userid`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bug_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(32) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '故障管理', null, '0');
INSERT INTO `t_menu` VALUES ('2', '故障查询', null, '0');
INSERT INTO `t_menu` VALUES ('3', '故障统计', null, '0');
INSERT INTO `t_menu` VALUES ('4', '组织机构', null, '0');
INSERT INTO `t_menu` VALUES ('5', '权限管理', null, '0');
INSERT INTO `t_menu` VALUES ('6', '流程管理', null, '0');
INSERT INTO `t_menu` VALUES ('7', '通知管理', null, '0');
INSERT INTO `t_menu` VALUES ('8', '日志管理', null, '0');
INSERT INTO `t_menu` VALUES ('9', '系统设置', null, '0');
INSERT INTO `t_menu` VALUES ('10', '故障单维护', '/html/bugticket/index.html', '1');
INSERT INTO `t_menu` VALUES ('11', '待审故障单', '/html/bugticket/authIndex.html', '1');
INSERT INTO `t_menu` VALUES ('12', '已审故障单', '/html/bugticket/authedIndex.html', '1');
INSERT INTO `t_menu` VALUES ('13', '归档故障单', '/html/bugticket/finishedIndex.html', '1');
INSERT INTO `t_menu` VALUES ('14', '简单查询', null, '2');
INSERT INTO `t_menu` VALUES ('15', '高级查询', null, '2');
INSERT INTO `t_menu` VALUES ('31', '统计分析', null, '3');
INSERT INTO `t_menu` VALUES ('41', '机构维护', null, '4');
INSERT INTO `t_menu` VALUES ('42', '员工维护', null, '4');
INSERT INTO `t_menu` VALUES ('51', '许可维护', null, '5');
INSERT INTO `t_menu` VALUES ('52', '角色维护', null, '5');
INSERT INTO `t_menu` VALUES ('53', '用户维护', '/html/user/index.html', '5');
INSERT INTO `t_menu` VALUES ('54', '在线用户', null, '5');
INSERT INTO `t_menu` VALUES ('61', '流程维护', '/html/process/index.html', '6');
INSERT INTO `t_menu` VALUES ('62', '挂起流程', null, '6');
INSERT INTO `t_menu` VALUES ('63', '流程监控', '/html/process/activiIndex.html', '6');
INSERT INTO `t_menu` VALUES ('64', '设置优先级', null, '6');
INSERT INTO `t_menu` VALUES ('65', '催办流程', null, '6');
INSERT INTO `t_menu` VALUES ('71', '公告管理', null, '7');
INSERT INTO `t_menu` VALUES ('81', '操作日志', '/html/log/index.html', '8');
INSERT INTO `t_menu` VALUES ('82', '安全日志', null, '8');
INSERT INTO `t_menu` VALUES ('91', '故障类型', null, '9');
INSERT INTO `t_menu` VALUES ('92', '故障局点', null, '9');

-- ----------------------------
-- Table structure for `t_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_log`;
CREATE TABLE `t_operate_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户登录以及操作日志',
  `userid` int(11) DEFAULT NULL,
  `operatenode` varchar(140) DEFAULT NULL,
  `operatedate` varchar(140) DEFAULT NULL,
  `operatemodel` varchar(140) DEFAULT NULL,
  `operatetype` varchar(32) DEFAULT NULL,
  `operateip` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(32) NOT NULL,
  `loginpswd` varchar(32) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `level` varchar(32) DEFAULT NULL COMMENT '�����ֶ�ȡֵ��Χ��: ְԱ, �鳤, ����',
  `createtime` char(19) DEFAULT NULL,
  `mid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_user_loginname` (`loginname`),
  KEY `FK_Reference_1` (`mid`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`mid`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '张三', 'mail', '管理员', '2018-04-19 13:53:27', null);
INSERT INTO `t_user` VALUES ('2', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '王五', 'lhl2284@qq.com', '经理', '2018-04-19 13:53:27', null);
INSERT INTO `t_user` VALUES ('3', 'zhaoliu', 'e10adc3949ba59abbe56e057f20f883e', '赵六', 'chen_2284@163.com', '组长', '2018-04-19 14:24:32', '2');
INSERT INTO `t_user` VALUES ('4', 'qianqi', 'e10adc3949ba59abbe56e057f20f883e', '钱七', 'xiao_22847@163.com', '职员', '2018-04-19 14:35:57', '3');
INSERT INTO `t_user` VALUES ('5', 'zhouba', 'e10adc3949ba59abbe56e057f20f883e', '周八', 'chen_2284@163.com', '职员', '2018-04-19 17:34:50', '3');
