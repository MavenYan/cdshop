/*
Source Server
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar_id` bigint DEFAULT NULL COMMENT '头像',
  `email` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_generate_ci DEFAULT NULL COMMENT '邮箱',
  `enable` bigint DEFAULT NULL COMMENT '状态:1启用,0停用',
  `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_generate_ci DEFAULT NULL COMMENT '密码',
  `username` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_generate_ci DEFAULT NULL COMMENT '用户名',
  `dept_id` bigint DEFAULT NULL COMMENT '部门名称'
)