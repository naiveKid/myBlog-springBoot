create database if not exists MyBlog;
use MyBlog;

-- ----------------------------
-- Table structure for `aboutme`
-- ----------------------------
DROP TABLE IF EXISTS `aboutme`;
CREATE TABLE `aboutme` (
  `aboutMeId` int(11) NOT NULL AUTO_INCREMENT,
  `realName` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `nowAddress` varchar(255) DEFAULT NULL,
  `likeBook` varchar(255) DEFAULT NULL,
  `likeMusic` varchar(255) DEFAULT NULL,
  `essayId` int(11) DEFAULT NULL,
  `pictureId` int(11) DEFAULT NULL,
  PRIMARY KEY (`aboutMeId`),
  UNIQUE KEY `aboutMeId` (`aboutMeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of aboutme
-- ----------------------------
INSERT INTO `aboutme` VALUES ('1', '张三丰', '四川成都郫县', '四川成都郫县团结', '《大主宰》', '烧猪PDD', '1', '0');

-- ----------------------------
-- Table structure for `essay`
-- ----------------------------
DROP TABLE IF EXISTS `essay`;
CREATE TABLE `essay` (
  `essayId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `doTime` varchar(50) NOT NULL,
  `content` mediumtext NOT NULL,
  `pictureId` int(11) DEFAULT NULL,
  `essayType` varchar(255) DEFAULT NULL,
  `clickNum` int(11) DEFAULT NULL,
  `showLevel` int(11) DEFAULT NULL,
  PRIMARY KEY (`essayId`),
  UNIQUE KEY `essayId` (`essayId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of essay
-- ----------------------------
INSERT INTO `essay` VALUES ('1', '这只是一个小小的测试而已。', '2017年06月03日22时56分44秒', '不要紧张，很刺激的啊。', '0', 'aboutMe', '0', '0');

-- ----------------------------
-- Table structure for `mood`
-- ----------------------------
DROP TABLE IF EXISTS `mood`;
CREATE TABLE `mood` (
  `moodId` int(11) NOT NULL AUTO_INCREMENT,
  `doTime` varchar(50) NOT NULL,
  `content` mediumtext NOT NULL,
  `pictureId` int(11) DEFAULT NULL,
  PRIMARY KEY (`moodId`),
  UNIQUE KEY `moodId` (`moodId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT,
  `doUserId` int(11) NOT NULL,
  `content` mediumtext NOT NULL,
  PRIMARY KEY (`noticeId`),
  UNIQUE KEY `noticeId` (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `pickredpacket`
-- ----------------------------
DROP TABLE IF EXISTS `pickredpacket`;
CREATE TABLE `pickredpacket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sumMoney` decimal(16,2) NOT NULL,
  `doTime` varchar(255) NOT NULL,
  `number` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `restNumber` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pickredpacket
-- ----------------------------

-- ----------------------------
-- Table structure for `picture`
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `pictureId` int(11) NOT NULL AUTO_INCREMENT,
  `pictureName` varchar(255) NOT NULL,
  `pictureTitle` varchar(255) DEFAULT NULL,
  `pictureContent` varchar(255) DEFAULT NULL,
  `pictureType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pictureId`),
  UNIQUE KEY `pictureId` (`pictureId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES ('1', '/static/images/a1.jpg', '', '', 'index');
INSERT INTO `picture` VALUES ('2', '/static/images/a2.jpg', '', '', 'index');
INSERT INTO `picture` VALUES ('3', '/static/images/a3.jpg', '', '', 'index');
INSERT INTO `picture` VALUES ('4', '/static/images/a4.jpg', '', '', 'index');

-- ----------------------------
-- Table structure for `redpacket`
-- ----------------------------
DROP TABLE IF EXISTS `redpacket`;
CREATE TABLE `redpacket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `packetId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `money` decimal(16,2) NOT NULL,
  `doTime` varchar(255) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of redpacket
-- ----------------------------

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `isAdmin` int(2) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', 'zhangsan', 'f51703256a38e6bab3d9410a070c32ea', '1');
INSERT INTO `userinfo` VALUES ('2', 'lisi', 'f51703256a38e6bab3d9410a070c32ea', '1');
INSERT INTO `userinfo` VALUES ('3', 'zhaoliu', 'f51703256a38e6bab3d9410a070c32ea', '0');