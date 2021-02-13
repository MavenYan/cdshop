/*
Source Server
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar_id` bigint DEFAULT NULL COMMENT '头像',
  `email` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` bigint DEFAULT NULL COMMENT '状态:1启用,0停用',
  `username` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `job`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职业',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `last_password_reset_time` datetime DEFAULT NULL COMMENT '最后修改密码的日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, 'cdshop@qq.com', 1, 'admin', '$2a$10$fP.426qKaTmix50Oln8L.uav55gELhAd0Eg66Av4oG86u8km7D/Ky', '管理员', '男', '18888888888', '', '2018-08-23 09:11:56', '2019-05-18 17:34:21');
INSERT INTO `sys_user` VALUES (2, NULL, 'test@yshopnet', 1, 'test', '$2a$10$HhxyGZy.ulf3RvAwaHUGb.k.2i9PBpv4YbLMJWp8pES7pPhTyRCF.', '测试', '男', '17777777777', '', '2018-12-27 20:05:26', '2019-04-01 09:15:24');
COMMIT;