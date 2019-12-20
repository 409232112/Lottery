/*
Navicat MySQL Data Transfer

Source Server         : yuanyuanlian
Source Server Version : 50646
Source Host           : www.yuanyuanlian.cn:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-12-19 15:41:12
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
INSERT INTO `sys_model` VALUES ('16865d5cbf8d464eb5a69f36f4f62703', '抽奖详情', 'LotteryMgr/LotteryQuery.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '2', '', '2019-12-03 16:13:15', '52d995e1502e42a9bf117d458917f67a', '2019-12-04 17:17:25', '1');
INSERT INTO `sys_model` VALUES ('2', '用户管理', 'SystemMgr/UserMgr.html', 'icon-standard-user', '2', '1', '1', '', '2019-11-30 07:29:15', '', '2019-11-30 07:29:27', '');
INSERT INTO `sys_model` VALUES ('3', '模块信息维护', 'SystemMgr/ModelMgr.html', 'icon-standard-brick-edit', '2', '1', '2', '', '2019-11-30 07:32:41', '', '2019-11-30 07:32:46', '');
INSERT INTO `sys_model` VALUES ('4', '用户模块管理', 'SystemMgr/UserModelMgr.html', 'icon-standard-brick-link', '2', '1', '3', '', '2019-11-04 22:59:22', null, null, null);
INSERT INTO `sys_model` VALUES ('827e77322d6140e38825ef7e1e37017a', '抽奖内定', 'LotteryMgr/LotteryDecide.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '3', '', '2019-11-30 00:16:06', '52d995e1502e42a9bf117d458917f67a', '2019-12-03 20:43:23', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('a0812a81bf7f4f8da52c66a3f86862cc', '抽奖设置', 'LotteryMgr/LotteryMgr.html', 'icon-standard-medal-gold-1', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '1', '', '2019-11-30 00:14:30', '52d995e1502e42a9bf117d458917f67a', '2019-11-30 15:09:55', '52d995e1502e42a9bf117d458917f67a');
INSERT INTO `sys_model` VALUES ('dd68a6b1942b4562a814fa9c03e165f8', '抽奖背景设置', 'LotteryMgr/LotteryImg.html', 'icon-standard-image', '2', 'eb52d5b9c53d44d1806a54cef77799d1', '4', '', '2019-12-05 17:09:12', '1', null, null);
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
INSERT INTO `sys_user_model` VALUES ('1', 'dd68a6b1942b4562a814fa9c03e165f8');
INSERT INTO `sys_user_model` VALUES ('1', '1');
INSERT INTO `sys_user_model` VALUES ('1', '2');
INSERT INTO `sys_user_model` VALUES ('1', '3');
INSERT INTO `sys_user_model` VALUES ('1', '4');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', 'eb52d5b9c53d44d1806a54cef77799d1');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', 'a0812a81bf7f4f8da52c66a3f86862cc');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', '16865d5cbf8d464eb5a69f36f4f62703');
INSERT INTO `sys_user_model` VALUES ('1dee7a8183d14864ab5e88d7d07a451d', 'dd68a6b1942b4562a814fa9c03e165f8');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', 'eb52d5b9c53d44d1806a54cef77799d1');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', 'a0812a81bf7f4f8da52c66a3f86862cc');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', '16865d5cbf8d464eb5a69f36f4f62703');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', '827e77322d6140e38825ef7e1e37017a');
INSERT INTO `sys_user_model` VALUES ('52d995e1502e42a9bf117d458917f67a', 'dd68a6b1942b4562a814fa9c03e165f8');

-- ----------------------------
-- Table structure for t_file_img
-- ----------------------------
DROP TABLE IF EXISTS `t_file_img`;
CREATE TABLE `t_file_img` (
  `id` varchar(32) NOT NULL,
  `comments` varchar(64) DEFAULT NULL,
  `file_name` varchar(128) DEFAULT NULL,
  `file_path` varchar(128) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `is_use` varchar(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file_img
-- ----------------------------
INSERT INTO `t_file_img` VALUES ('03f575fcc0c149f7b73ca3dcb1765bed', '', 'WechatIMG192.png', 'acd282246c3345a59ceb87f9856407cc.png', '2019-12-06 11:54:59', '1');
INSERT INTO `t_file_img` VALUES ('70aabc375d8f4c9d8ddb74fa92a74fbd', '鼠年背景', '2020.jpg', '2b9a63470a994978ace37a315a752212.jpg', '2019-12-06 09:43:54', '0');
INSERT INTO `t_file_img` VALUES ('773a9dedfd47448599d2c0ba560ddefe', '原始背景图', 'WechatIMG191.png', 'd2a49e45765d4754b1b6bc9409c8c14e.png', '2019-12-06 07:37:50', '0');

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
INSERT INTO `t_lottery` VALUES ('873cd1c0c6bb45f2b9fa1e98e2b422e2', '24小时物流年会盛典', '2020-01-10', '成都市武侯区宽亭酒楼3楼', '19', '52d995e1502e42a9bf117d458917f67a', '2019-12-05 14:46:41', 'no_start');

-- ----------------------------
-- Table structure for t_lottery_user
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_user`;
CREATE TABLE `t_lottery_user` (
  `id` varchar(32) NOT NULL,
  `t_lottery_id` varchar(32) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `lottery_num` int(8) DEFAULT NULL,
  `t_prize_id` varchar(32) DEFAULT NULL COMMENT '中奖的ID',
  `decide_t_prize_id` varchar(32) DEFAULT NULL COMMENT '内定的奖品ID',
  `is_black` varchar(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_lottery_id` (`t_lottery_id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery_user
-- ----------------------------
INSERT INTO `t_lottery_user` VALUES ('1b2f6f03085a48b69ab36fc4d2c875b1', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '张波', '1', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('3328929ae78643b1aea648093d7f2408', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '马会', '9', null, 'b5c67c3133f64349b43b25b320d20015', '0');
INSERT INTO `t_lottery_user` VALUES ('3aa14d39852c44f4974575723988177a', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '谢婷', '8', null, 'b5c67c3133f64349b43b25b320d20015', '0');
INSERT INTO `t_lottery_user` VALUES ('3cbd47d25ea346e698a86ddb5af60e04', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '谢琴', '18', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('5647ff8b4d1b41d88a25ebdb3437ee4d', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '李奕杉', '14', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('6491f94547c34fddb8dcfe43d3ac652d', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '杨丽娜', '16', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('6c017596338d4991aeee8606c0a30c94', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '罗建', '11', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('7e9e8bda205840af8ff2e46f7a58f603', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '程华蓉', '7', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('7fce73b104cb4e7aaf71c2835961be3c', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '赵泽燕', '15', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('8a935b70c5524c6a9bb85485a172f524', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '蔡雪', '6', null, 'b5c67c3133f64349b43b25b320d20015', '0');
INSERT INTO `t_lottery_user` VALUES ('8f904f9116c248a0a206972025fc9e6c', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '胡森', '10', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('b361c320743943baa1f11ab7cc9e8e21', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '王媛媛', '2', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('c2b96d173f19462eb5a5c0388c5aaf86', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '辜杰', '12', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('d233b1af33604ebd9d3c4db8019ae398', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '朱洪籼', '5', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('d2741739b95b4a1d9e4e4f2f1f3fc437', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '彭磊', '4', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('dd80347233dd47a7a792e68bc82bcd59', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '程华玉', '19', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('e47f4ae7e6f44a66b33e743b5f2670bb', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '邓荣云', '3', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('eb0ab2e0e6b34ee5b69cd6ac852e1f32', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '朱倩', '17', null, null, '0');
INSERT INTO `t_lottery_user` VALUES ('f74280d27ea54a4998e81fa61a0e6ccd', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '袁芳', '13', null, null, '0');

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
INSERT INTO `t_prize` VALUES ('2dd207da8657b844a689f388d373091d', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '二等奖', '3', '冰箱2台 ipad1台', null, null, '2');
INSERT INTO `t_prize` VALUES ('81d157fbc8215c4d47c870cc36d7fd45', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '特等奖', '1', '奔驰C级轿车', null, null, '4');
INSERT INTO `t_prize` VALUES ('b5c67c3133f64349b43b25b320d20015', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '一等奖', '1', '华为P30 Pro', null, null, '3');
INSERT INTO `t_prize` VALUES ('c80aab624c803448c74bace481ff5971', '873cd1c0c6bb45f2b9fa1e98e2b422e2', '三等奖', '15', '家用微波炉15台', null, null, '1');
