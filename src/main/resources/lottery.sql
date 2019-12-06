/*
Navicat MySQL Data Transfer

Source Server         : yuanyuanlian
Source Server Version : 50646
Source Host           : www.yuanyuanlian.cn:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-12-04 15:03:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_model`;
CREATE TABLE `sys_model` (
  `id` varchar(32) NOT NULL,
  `text` varchar(64) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `iconCls` varchar(64) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT '0',
  `seq` int(11) DEFAULT NULL,
  `comments` text,
  `created_time` datetime DEFAULT NULL,
  `created_user_id` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_model
-- ----------------------------
INSERT INTO `sys_model` VALUES ('1', '系统管理', '', 'icon-standard-cog', '1', '0', '2', '', null, null, '2019-11-21 10:51:58', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('16865d5cbf8d464eb5a69f36f4f62703', '中奖查看', 'LotteryMgr/LotteryQuery.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '2', '', '2019-12-03 16:13:15', '52d995e1502e42a9bf117d458917f67a', '2019-12-03 20:44:24', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('2', '用户管理', 'SystemMgr/UserMgr.html', 'icon-standard-user', '2', '1', '1', '', '2019-11-30 07:29:15', '', '2019-11-30 07:29:27', '');
INSERT INTO `sys_model` VALUES ('3', '模块信息维护', 'SystemMgr/ModelMgr.html', 'icon-standard-brick-edit', '2', '1', '2', '', '2019-11-30 07:32:41', '', '2019-11-30 07:32:46', '');
INSERT INTO `sys_model` VALUES ('4', '用户模块管理', 'SystemMgr/UserModelMgr.html', 'icon-standard-brick-link', '2', '1', '3', '', '2019-11-04 22:59:22', null, null, null);
INSERT INTO `sys_model` VALUES ('827e77322d6140e38825ef7e1e37017a', '抽奖内定', 'LotteryMgr/LotteryDecide.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '3', '', '2019-11-30 00:16:06', '52d995e1502e42a9bf117d458917f67a', '2019-12-03 20:43:23', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('a0812a81bf7f4f8da52c66a3f86862cc', '抽奖设置', 'LotteryMgr/LotteryMgr.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '1', '', '2019-11-30 00:14:30', '52d995e1502e42a9bf117d458917f67a', '2019-11-30 15:09:55', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('eb52d5b9c53d44d1806a54cef77799d1', '抽奖管理', '', 'icon-standard-medal-gold-1', '1', '0', '1', '', '2019-11-30 00:13:08', '52d995e1502e42a9bf117d458917f67a', null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT '密码MD5',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'superadmin', 'superadmin', '17c4520f6cfd1ab53d8745e84681eb49');
INSERT INTO `sys_user` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', '成都24小时物流有限公司', 'cd24', '8ddcff3a80f4189ca1c9d4d902c3c909');
INSERT INTO `sys_user` VALUES ('52d995e1502e42a9bf117d458917f67a', '管理员', 'admin', '21232f297a57a5a743894a0e4a801fc3');

-- ----------------------------
-- Table structure for sys_user_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_model`;
CREATE TABLE `sys_user_model` (
  `user_id` varchar(32) DEFAULT NULL,
  `model_id` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_model
-- ----------------------------
INSERT INTO `sys_user_model` VALUES ('1', 'eb52d5b9c53d44d1806a54cef77799d1');
INSERT INTO `sys_user_model` VALUES ('1', 'a0812a81bf7f4f8da52c66a3f86862cc');
INSERT INTO `sys_user_model` VALUES ('1', '16865d5cbf8d464eb5a69f36f4f62703');
INSERT INTO `sys_user_model` VALUES ('1', '827e77322d6140e38825ef7e1e37017a');
INSERT INTO `sys_user_model` VALUES ('1', '1');
INSERT INTO `sys_user_model` VALUES ('1', '2');
INSERT INTO `sys_user_model` VALUES ('1', '3');
INSERT INTO `sys_user_model` VALUES ('1', '4');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', 'eb52d5b9c53d44d1806a54cef77799d1');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', 'a0812a81bf7f4f8da52c66a3f86862cc');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', '16865d5cbf8d464eb5a69f36f4f62703');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', '827e77322d6140e38825ef7e1e37017a');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', 'eb52d5b9c53d44d1806a54cef77799d1');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', 'a0812a81bf7f4f8da52c66a3f86862cc');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', '16865d5cbf8d464eb5a69f36f4f62703');

-- ----------------------------
-- Table structure for t_lottery
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery`;
CREATE TABLE `t_lottery` (
  `id` varchar(32) NOT NULL,
  `lottery_name` varchar(64) DEFAULT NULL,
  `lottery_date` date DEFAULT NULL,
  `lottery_address` varchar(256) DEFAULT NULL,
  `total_join` int(11) DEFAULT '0',
  `created_user_id` varchar(32) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `current_prize_id` varchar(32) DEFAULT 'no_start',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery
-- ----------------------------

-- ----------------------------
-- Table structure for t_lottery_user
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_user`;
CREATE TABLE `t_lottery_user` (
  `id` varchar(32) NOT NULL,
  `t_lottery_id` varchar(32) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `lottery_num` varchar(8) DEFAULT NULL,
  `t_prize_id` varchar(32) DEFAULT NULL COMMENT '中奖的ID',
  `decide_t_prize_id` varchar(32) DEFAULT NULL COMMENT '内定的奖品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_prize
-- ----------------------------
DROP TABLE IF EXISTS `t_prize`;
CREATE TABLE `t_prize` (
  `id` varchar(32) NOT NULL,
  `t_lottery_id` varchar(32) DEFAULT NULL,
  `prize_name` varchar(32) DEFAULT NULL,
  `prize_count` int(11) DEFAULT NULL,
  `award` varchar(128) DEFAULT NULL,
  `created_user_id` varchar(32) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `seq` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_prize
-- ----------------------------
