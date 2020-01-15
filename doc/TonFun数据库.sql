/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.43.155
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 192.168.43.155:3306
 Source Schema         : tonfun

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 29/10/2019 11:06:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_calendar
-- ----------------------------
DROP TABLE IF EXISTS `sys_calendar`;
CREATE TABLE `sys_calendar`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日历ID',
  `holiday_id` bigint(20) NULL DEFAULT NULL COMMENT '标签ID',
  `time_start` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `time_end` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日历' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ids',
  `simple_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (24, 0, '[0],', '总公司', '总公司', '', NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (25, 24, '[0],[24],', '开发部', '开发部', '', NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (26, 24, '[0],[24],', '运营部', '运营部', '', NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (27, 24, '[0],[24],', '战略部', '战略部', '', NULL, 5, NULL, '2019-09-20 15:03:25', NULL, 1);
INSERT INTO `sys_dept` VALUES (29, 26, '[0],[24],[26],', '人事部', '人事部', '', NULL, 7, '2019-09-20 15:02:50', '2019-09-23 17:37:47', 1, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '所属字典类型的id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级代码id',
  `parent_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所有上级id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'ENABLE' COMMENT '状态（字典）',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字典的描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '基础字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典类型id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典类型编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典类型名称',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字典描述',
  `system_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否是系统字典，Y-是，N-否',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'ENABLE' COMMENT '状态(字典)',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `create_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `sys_enclosure`;
CREATE TABLE `sys_enclosure`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件原名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件地址',
  `pdf_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'PDF文件地址',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `for_surface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键表',
  `for_id` bigint(20) NULL DEFAULT NULL COMMENT '外键ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 250 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_enclosure
-- ----------------------------
INSERT INTO `sys_enclosure` VALUES (242, '新建稿2', 'temp/20191010/8b48ba1afccf42a8b57e159412a5cd47新建稿2.pptx', 'temp/20191010/8b48ba1afccf42a8b57e159412a5cd47新建稿2.pdf', 'pptx', 3457924, NULL, NULL, '2019-10-10 18:21:33', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (243, '新建稿1', 'temp/20191010/d05eaf3410834836ba580ccfe6a59ea0新建稿1.ppt', 'temp/20191010/d05eaf3410834836ba580ccfe6a59ea0新建稿1.pdf', 'ppt', 2270720, NULL, NULL, '2019-10-10 18:21:33', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (244, '工作流数据库', 'temp/20191010/cf59c77c59194250a60acf15cd100a43工作流数据库.txt', 'temp/20191010/cf59c77c59194250a60acf15cd100a43工作流数据库.pdf', 'txt', 1129, NULL, NULL, '2019-10-10 18:29:41', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (245, '1b8f6581d72fa55da119b7d444444255', 'temp/20191010/16b094e5d501471f8b3316c52f09fa721b8f6581d72fa55da119b7d444444255.jpg', 'temp/20191010/16b094e5d501471f8b3316c52f09fa721b8f6581d72fa55da119b7d444444255.pdf', 'jpg', 221170, NULL, NULL, '2019-10-10 18:29:41', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (246, '新建稿2', 'temp/20191010/d91e394ff75f4106acf68c8c74dd992f新建稿2.pptx', 'temp/20191010/d91e394ff75f4106acf68c8c74dd992f新建稿2.pdf', 'pptx', 3457924, NULL, NULL, '2019-10-10 18:30:00', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (247, '错误代码', 'temp/20191010/0c06047dd9e04243b57fae612fc59691错误代码.xlsx', 'temp/20191010/0c06047dd9e04243b57fae612fc59691错误代码.pdf', 'xlsx', 10927, NULL, NULL, '2019-10-10 18:30:01', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (248, '图标建议-思维指南-已转档', 'temp/20191010/32ab5b88840f4ff8bf018809797386c2图标建议-思维指南-已转档.pdf', 'temp/20191010/32ab5b88840f4ff8bf018809797386c2图标建议-思维指南-已转档.pdf', 'pdf', 144434, NULL, NULL, '2019-10-10 18:30:01', 1, '同丰');
INSERT INTO `sys_enclosure` VALUES (249, '库存管理及基础管理', 'temp/20191010/b3f62adfff7546ba80161e1c560c5ec0库存管理及基础管理.docx', 'temp/20191010/b3f62adfff7546ba80161e1c560c5ec0库存管理及基础管理.pdf', 'docx', 13583, NULL, NULL, '2019-10-10 18:30:05', 1, '同丰');

-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '假日名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '假日' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_holiday
-- ----------------------------
INSERT INTO `sys_holiday` VALUES (1, '国庆节', 100, '2019-10-17 15:58:24', '2019-10-17 15:58:54', 1, 1);
INSERT INTO `sys_holiday` VALUES (2, '元旦', 10, '2019-10-17 15:58:32', '2019-10-17 15:58:58', 1, 1);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日志名称',
  `user_Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '管理员id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否执行成功',
  `message` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '具体消息',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (21, '登录日志', '同丰', '2019-10-17 15:40:48', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (22, '登录日志', '同丰', '2019-10-17 15:52:23', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (23, '退出日志', '同丰', '2019-10-17 15:52:37', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (24, '登录日志', '同丰', '2019-10-17 15:52:40', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (25, '登录日志', '同丰', '2019-10-17 15:54:17', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (26, '登录日志', '同丰', '2019-10-17 15:57:26', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (27, '登录日志', '同丰', '2019-10-17 15:57:59', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (28, '登录日志', '同丰', '2019-10-21 09:33:30', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (29, '登录日志', '同丰', '2019-10-21 10:16:19', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (30, '退出日志', '同丰', '2019-10-21 10:16:49', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (31, '登录日志', '同丰', '2019-10-21 10:16:53', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (32, '登录日志', '同丰', '2019-10-21 10:20:00', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (33, '登录日志', '同丰', '2019-10-21 10:25:15', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (34, '登录日志', '同丰', '2019-10-21 10:26:51', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (35, '登录日志', '同丰', '2019-10-21 10:31:01', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (36, '登录日志', '同丰', '2019-10-21 10:31:48', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (37, '登录日志', '同丰', '2019-10-21 10:39:42', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (38, '登录日志', '同丰', '2019-10-21 11:01:20', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (39, '登录日志', '同丰', '2019-10-21 11:07:03', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (40, '登录日志', '同丰', '2019-10-21 11:14:31', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (41, '登录日志', '同丰', '2019-10-21 11:16:59', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (42, '登录日志', '同丰', '2019-10-21 11:20:33', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (43, '登录日志', '同丰', '2019-10-21 11:35:06', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (44, '登录日志', '同丰', '2019-10-21 11:36:58', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (45, '登录日志', '同丰', '2019-10-21 13:19:48', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (46, '登录日志', '同丰', '2019-10-21 13:22:59', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (47, '登录日志', '同丰', '2019-10-21 13:24:03', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (48, '登录日志', '同丰', '2019-10-21 13:24:58', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (49, '登录日志', '同丰', '2019-10-21 13:27:22', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (50, '登录日志', '同丰', '2019-10-21 13:34:27', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (51, '登录日志', '同丰', '2019-10-21 13:38:36', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (52, '登录日志', '同丰', '2019-10-21 13:40:01', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (53, '登录日志', '同丰', '2019-10-21 13:49:15', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (54, '登录日志', '同丰', '2019-10-21 13:53:11', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (55, '登录日志', '同丰', '2019-10-21 13:56:05', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (56, '登录日志', '同丰', '2019-10-21 13:58:46', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (57, '登录日志', '同丰', '2019-10-21 14:01:19', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (58, '登录日志', '同丰', '2019-10-21 14:01:55', '成功', NULL, '127.0.0.1');
INSERT INTO `sys_login_log` VALUES (59, '登录日志', '同丰', '2019-10-21 15:13:33', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (60, '登录日志', '同丰', '2019-10-22 15:17:21', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (61, '登录日志', '同丰', '2019-10-22 15:52:36', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (62, '登录日志', '同丰', '2019-10-22 15:54:41', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (63, '登录日志', '同丰', '2019-10-22 16:08:38', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (64, '登录日志', '同丰', '2019-10-22 16:09:37', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (65, '登录日志', '同丰', '2019-10-22 16:11:06', '成功', NULL, '127.0.0.1');
INSERT INTO `sys_login_log` VALUES (66, '登录日志', '同丰', '2019-10-22 16:12:25', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (67, '登录日志', '同丰', '2019-10-22 16:13:57', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (68, '登录日志', '同丰', '2019-10-22 16:16:33', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (69, '登录失败日志', NULL, '2019-10-22 16:17:18', '成功', '帐号：tonfun账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (70, '登录日志', '同丰', '2019-10-22 16:17:23', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (71, '登录日志', '同丰', '2019-10-22 16:18:21', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (72, '登录日志', '同丰', '2019-10-22 16:20:32', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (73, '登录日志', '同丰', '2019-10-22 16:24:38', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (74, '退出日志', '同丰', '2019-10-22 16:25:56', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (75, '登录失败日志', NULL, '2019-10-22 16:26:00', '成功', '帐号：test1账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (76, '登录失败日志', NULL, '2019-10-22 16:26:05', '成功', '帐号：test1账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (77, '登录日志', '55555', '2019-10-22 16:26:23', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (78, '退出日志', '55555', '2019-10-22 16:26:28', '成功', 'test1退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (79, '登录日志', '同丰', '2019-10-22 16:26:32', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (80, '退出日志', '同丰', '2019-10-22 16:27:53', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (81, '登录日志', '同丰', '2019-10-22 16:29:13', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (82, '退出日志', '同丰', '2019-10-22 16:30:15', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (83, '登录日志', '同丰', '2019-10-22 16:32:38', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (84, '退出日志', '同丰', '2019-10-22 16:32:49', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (85, '登录日志', 'ceshi123456', '2019-10-22 16:32:56', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (86, '登录日志', '同丰', '2019-10-22 16:33:05', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (87, '退出日志', '同丰', '2019-10-22 16:33:59', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (88, '登录日志', 'ceshi123456', '2019-10-22 16:34:03', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (89, '登录日志', 'ceshi123456', '2019-10-22 16:40:33', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (90, '登录日志', 'ceshi123456', '2019-10-22 16:48:57', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (91, '登录日志', 'ceshi123456', '2019-10-22 16:51:18', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (92, '退出日志', 'ceshi123456', '2019-10-22 16:51:51', '成功', 'ceshi123456退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (93, '登录失败日志', NULL, '2019-10-22 16:51:55', '成功', '帐号：1111账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (94, '登录失败日志', NULL, '2019-10-22 16:52:10', '成功', '帐号：1111账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (95, '登录日志', '1111', '2019-10-22 16:52:26', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (96, '退出日志', '1111', '2019-10-22 16:53:50', '成功', '1111退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (97, '登录日志', '1111', '2019-10-22 16:53:55', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (98, '退出日志', '1111', '2019-10-22 16:54:50', '成功', '1111退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (99, '登录日志', '同丰', '2019-10-22 16:54:55', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (100, '登录日志', '同丰', '2019-10-23 10:14:35', '成功', NULL, '127.0.0.1');
INSERT INTO `sys_login_log` VALUES (101, '登录日志', '同丰', '2019-10-23 10:18:44', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (102, '登录日志', '同丰', '2019-10-23 10:21:06', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (103, '退出日志', '同丰', '2019-10-23 10:21:52', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (104, '登录日志', '1111', '2019-10-23 10:21:57', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (105, '退出日志', '1111', '2019-10-23 10:22:09', '成功', '1111退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (106, '登录日志', 'ceshi123456', '2019-10-23 10:22:12', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (107, '登录日志', 'ceshi123456', '2019-10-23 10:45:36', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (108, '登录日志', 'ceshi123456', '2019-10-23 10:47:27', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (109, '登录日志', 'ceshi123456', '2019-10-23 10:57:57', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (110, '登录日志', '同丰', '2019-10-29 11:04:40', '成功', NULL, '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (111, '退出日志', '同丰', '2019-10-29 11:05:46', '成功', 'tonfun退出登录', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_login_log` VALUES (112, '登录日志', '同丰', '2019-10-29 11:05:47', '成功', NULL, '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `sort` int(65) NULL DEFAULT NULL COMMENT '排序',
  `levels` int(65) NULL DEFAULT NULL COMMENT '层级',
  `menu_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是菜单',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'ENABLE' COMMENT '状态',
  `new_page_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打开新页面的标识',
  `open_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打开',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 245 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'system', '0', '[0],', '系统管理', 'layui-icon-set-sm', '#', 10, 1, 'Y', NULL, 'ENABLE', NULL, '1', NULL, '2019-09-23 18:25:14', NULL, 1);
INSERT INTO `sys_menu` VALUES (2, 'user', 'system', '[0],[system],', '用户', '', '/user', 1, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (3, 'user_add', 'user', '[0],[system],[mgr],', '添加用户', NULL, '/user/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (4, 'user_delete', 'user', '[0],[system],[mgr],', '删除用户', NULL, '/user/delete', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, 'user_update', 'user', '[0],[system],[mgr],', '修改用户', NULL, '/user/update', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (6, 'user_reset', 'user', '[0],[system],[mgr],', '重置密码', NULL, '/user/reset', 4, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, 'user_setRole', 'user', '[0],[system],[mgr],', '分配角色', NULL, '/user/setRole', 7, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (8, 'role', 'system', '[0],[system],', '角色', '', '/role', 10, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, '2019-09-23 18:25:55', NULL, 1);
INSERT INTO `sys_menu` VALUES (9, 'role_add', 'role', '[0],[system],[role],', '添加角色', NULL, '/role/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 'role_delete', 'role', '[0],[system],[role],', '删除角色', NULL, '/role/delete', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 'role_update', 'role', '[0],[system],[role],', '修改角色', NULL, '/role/update', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', NULL, '/role/setAuthority', 4, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 'dept', 'system', '[0],[system],', '部门', '', '/dept', 20, 2, 'Y', NULL, 'ENABLE', NULL, NULL, NULL, '2019-09-23 18:26:01', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', NULL, '/dept/add', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', NULL, '/dept/delete', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (16, 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', NULL, '/dept/update', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 'menu', 'system', '[0],[system],', '菜单', '', '/menu', 40, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, '2019-09-23 18:26:13', NULL, 1);
INSERT INTO `sys_menu` VALUES (18, 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', NULL, '/menu/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 'menu_update', 'menu', '[0],[system],[menu],', '修改菜单', NULL, '/menu/update', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (20, 'menu_delete', 'menu', '[0],[system],[menu],', '删除菜单', NULL, '/menu/delete', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (21, 'dev_tools', '0', '[0],', '开发工具', 'layui-icon-app', '#', 1, 1, 'Y', NULL, 'ENABLE', NULL, NULL, NULL, '2019-09-23 18:25:09', NULL, 1);
INSERT INTO `sys_menu` VALUES (22, 'console', 'dev_tools', '[0],[dev_tools],', '项目介绍', '', '/system/console', 1, 2, 'Y', NULL, 'ENABLE', NULL, NULL, NULL, '2019-09-23 18:25:28', NULL, 1);
INSERT INTO `sys_menu` VALUES (23, 'code', 'dev_tools', '[0],[dev_tools],', '生成代码', '', '/code', 10, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, '2019-09-23 18:25:34', NULL, 1);
INSERT INTO `sys_menu` VALUES (221, 'position', 'system', '[0],[system],', '岗位', '', '/position', 30, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, '2019-09-23 18:26:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (222, 'position_list', 'position', '[0],[system],[position],', '岗位列表', '', '/position/list', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (223, 'position_add', 'position', '[0],[system],[position],', '岗位添加', '', '/position/add', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (224, 'position_update', 'position', '[0],[system],[position],', '岗位更新', '', '/position/update', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (225, 'position_delete', 'position', '[0],[system],[position],', '岗位删除', '', '/position/delete', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (226, 'position_detail', 'position', '[0],[system],[position],', '岗位详情', '', '/position/detail', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (227, 'position_dataAuthority', 'position', '[0],[system],[position],', '数据权限', '', '/position/dataAuthority', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (233, 'enclosure', 'system', '[0],[system],', '附件', '', '/enclosure', 99, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (234, 'enclosure_list', 'enclosure', '[0],[system],[enclosure],', '附件列表', '', '/enclosure/list', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (235, 'enclosure_add', 'enclosure', '[0],[system],[enclosure],', '附件添加', '', '/enclosure/add', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (236, 'enclosure_update', 'enclosure', '[0],[system],[enclosure],', '附件更新', '', '/enclosure/update', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (237, 'enclosure_delete', 'enclosure', '[0],[system],[enclosure],', '附件删除', '', '/enclosure/delete', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (238, 'enclosure_detail', 'enclosure', '[0],[system],[enclosure],', '附件详情', '', '/enclosure/detail', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (239, 'holiday', 'system', '[0],[system],', '假日', '', '/holiday', 99, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (240, 'holiday_list', 'holiday', '[0],[system],[holiday],', '假日列表', '', '/holiday/list', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (241, 'holiday_add', 'holiday', '[0],[system],[holiday],', '假日添加', '', '/holiday/add', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (242, 'holiday_update', 'holiday', '[0],[system],[holiday],', '假日更新', '', '/holiday/update', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (243, 'holiday_delete', 'holiday', '[0],[system],[holiday],', '假日删除', '', '/holiday/delete', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (244, 'holiday_detail', 'holiday', '[0],[system],[holiday],', '假日详情', '', '/holiday/detail', 99, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (245, 'loginLog', 'system', '[0],[system],', '历史', NULL, '/loginLog', NULL, 2, 'Y', NULL, 'ENABLE', NULL, NULL, '2019-10-29 11:05:14', NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (246, 'loginLog_list', 'loginLog', '[0],[system],[loginLog],', '历史列表', NULL, '/loginLog/list', NULL, 3, 'N', NULL, 'ENABLE', NULL, NULL, '2019-10-29 11:05:34', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
  `sort` int(255) NULL DEFAULT NULL COMMENT '顺序',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建id',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES (1, '总经理', 'CEO', 1, 'ENABLE', '总经理', 1, '同丰', '2019-09-20 09:56:11', '2');
INSERT INTO `sys_position` VALUES (2, '技术总裁', 'CTO', 2, 'ENABLE', '技术总经理', 1, '同丰', '2019-09-20 09:58:41', '3');

-- ----------------------------
-- Table structure for sys_position_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_position_dept`;
CREATE TABLE `sys_position_dept`  (
  `position_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`position_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位和部门关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position_dept
-- ----------------------------
INSERT INTO `sys_position_dept` VALUES (1, 0);
INSERT INTO `sys_position_dept` VALUES (1, 24);
INSERT INTO `sys_position_dept` VALUES (1, 27);
INSERT INTO `sys_position_dept` VALUES (2, 26);

-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 566 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES (559, 1, 5);
INSERT INTO `sys_relation` VALUES (560, 2, 5);
INSERT INTO `sys_relation` VALUES (561, 3, 5);
INSERT INTO `sys_relation` VALUES (562, 4, 5);
INSERT INTO `sys_relation` VALUES (563, 5, 5);
INSERT INTO `sys_relation` VALUES (564, 6, 5);
INSERT INTO `sys_relation` VALUES (565, 7, 5);
INSERT INTO `sys_relation` VALUES (566, 1, 1);
INSERT INTO `sys_relation` VALUES (567, 2, 1);
INSERT INTO `sys_relation` VALUES (568, 3, 1);
INSERT INTO `sys_relation` VALUES (569, 4, 1);
INSERT INTO `sys_relation` VALUES (570, 5, 1);
INSERT INTO `sys_relation` VALUES (571, 6, 1);
INSERT INTO `sys_relation` VALUES (572, 7, 1);
INSERT INTO `sys_relation` VALUES (573, 8, 1);
INSERT INTO `sys_relation` VALUES (574, 9, 1);
INSERT INTO `sys_relation` VALUES (575, 10, 1);
INSERT INTO `sys_relation` VALUES (576, 11, 1);
INSERT INTO `sys_relation` VALUES (577, 12, 1);
INSERT INTO `sys_relation` VALUES (578, 13, 1);
INSERT INTO `sys_relation` VALUES (579, 14, 1);
INSERT INTO `sys_relation` VALUES (580, 15, 1);
INSERT INTO `sys_relation` VALUES (581, 16, 1);
INSERT INTO `sys_relation` VALUES (582, 17, 1);
INSERT INTO `sys_relation` VALUES (583, 18, 1);
INSERT INTO `sys_relation` VALUES (584, 19, 1);
INSERT INTO `sys_relation` VALUES (585, 20, 1);
INSERT INTO `sys_relation` VALUES (586, 221, 1);
INSERT INTO `sys_relation` VALUES (587, 222, 1);
INSERT INTO `sys_relation` VALUES (588, 223, 1);
INSERT INTO `sys_relation` VALUES (589, 224, 1);
INSERT INTO `sys_relation` VALUES (590, 225, 1);
INSERT INTO `sys_relation` VALUES (591, 226, 1);
INSERT INTO `sys_relation` VALUES (592, 227, 1);
INSERT INTO `sys_relation` VALUES (593, 233, 1);
INSERT INTO `sys_relation` VALUES (594, 234, 1);
INSERT INTO `sys_relation` VALUES (595, 235, 1);
INSERT INTO `sys_relation` VALUES (596, 236, 1);
INSERT INTO `sys_relation` VALUES (597, 237, 1);
INSERT INTO `sys_relation` VALUES (598, 238, 1);
INSERT INTO `sys_relation` VALUES (599, 239, 1);
INSERT INTO `sys_relation` VALUES (600, 240, 1);
INSERT INTO `sys_relation` VALUES (601, 241, 1);
INSERT INTO `sys_relation` VALUES (602, 242, 1);
INSERT INTO `sys_relation` VALUES (603, 243, 1);
INSERT INTO `sys_relation` VALUES (604, 244, 1);
INSERT INTO `sys_relation` VALUES (605, 245, 1);
INSERT INTO `sys_relation` VALUES (606, 246, 1);
INSERT INTO `sys_relation` VALUES (607, 21, 1);
INSERT INTO `sys_relation` VALUES (608, 22, 1);
INSERT INTO `sys_relation` VALUES (609, 23, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '別名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '序号',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 0, '超级管理员', 'administrator', 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (2, 1, '临时', 'temp', 2, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别(字典)',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id(多个逗号隔开)',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `position_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位id(多个逗号隔开)',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态(字典)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '/static/common/images/portrait/f418f68e-540e-4f24-a019-029d526f7df8.jpeg', 'tonfun', 'f96aa420921cbb361c98b523f9b160aa', 'q6taw', '同丰', '2019-08-09 00:00:00', 'M', 'tofun@tofun1.com', '18888888888', '1,5', 24, NULL, 'ENABLE', '2016-01-29 08:49:53', NULL, '2019-07-01 14:04:25', 24, 25);
INSERT INTO `sys_user` VALUES (2, '/static/common/images/head.png', '1111', 'f96aa420921cbb361c98b523f9b160aa', 'q6taw', '1111', '2019-07-01 00:00:00', 'M', '1111@qq.com', '1111', '5', 29, '2', 'ENABLE', '2019-07-03 14:08:09', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (3, '/static/common/images/head.png', '222222', 'de2f556debb441822eda5950b290bf0f', 'xoes8', '222222', '2019-07-03 00:00:00', 'M', '222222@qq.com', '', '1,5', 24, NULL, 'ENABLE', '2019-07-03 14:08:55', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (4, '/static/common/images/head.png', 'zhaoliu', '64915245986aad1462ecc66ee3a3d9cd', 'h7hik', 'zhaoliu', '2019-08-01 00:00:00', 'M', 'qq@qq.com', '15851213213', '5', NULL, NULL, 'ENABLE', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (6, '/common/images/head.png', '1111111', 'fc81862f5c8774443ce274f43acbf39e', '91hqj', '1111111', '2019-09-20 00:00:00', 'M', '111@qqq.com', '1111', NULL, 24, '1', 'DELETED', '2019-09-20 11:28:33', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (7, '/common/images/head.png', '22222', '7850a53ac4f2ceb9e15003b3ce5f0621', 'lkwcg', '22222', '2019-09-20 00:00:00', 'M', '2222@qq.com', '22222', NULL, 24, '2,1', 'ENABLE', '2019-09-20 11:29:47', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (8, '/common/images/head.png', 'jiufeng', '94a73e4161871ba950d74e74cfab1640', 'e8x6g', '玖冯', '2019-09-23 00:00:00', 'M', '111@qq.com', '12424242424', '5', 25, '1', 'ENABLE', '2019-09-23 16:39:42', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (9, '/static/common/images/portrait/f418f68e-540e-4f24-a019-029d526f7df8.jpeg', 'tonfun1', 'f96aa420921cbb361c98b523f9b160aa', 'q6taw', '同丰1', '2019-08-09 00:00:00', 'M', 'tofun@tofun1.com', '18888888887', '1,5', 24, NULL, 'ENABLE', '2016-01-29 08:49:53', NULL, '2019-07-01 14:04:25', 24, 25);
INSERT INTO `sys_user` VALUES (10, '/static/common/images/head.png', 'test1', 'f96aa420921cbb361c98b523f9b160aa', 'q6taw', '55555', '2019-07-01 00:00:00', 'M', '1111@qq.com', '111111', '5', 26, '2', 'ENABLE', '2019-07-03 14:08:09', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (11, '/static/common/images/head.png', '44444', 'de2f556debb441822eda5950b290bf0f', 'xoes8', '44444', '2019-07-03 00:00:00', 'M', '222222@qq.com', '', '1,5', 24, NULL, 'ENABLE', '2019-07-03 14:08:55', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (12, '/static/common/images/head.png', 'zhaoliu1', '64915245986aad1462ecc66ee3a3d9cd', 'h7hik', 'zhaoliu', '2019-08-01 00:00:00', 'M', 'qq@qq.com', '15851213212', '5', NULL, NULL, 'ENABLE', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (13, '/common/images/head.png', '33333', 'fc81862f5c8774443ce274f43acbf39e', '91hqj', '33333', '2019-09-20 00:00:00', 'M', '111@qqq.com', '11111', NULL, 24, '1', 'ENABLE', '2019-09-20 11:28:33', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (14, '/common/images/head.png', '66666', '7850a53ac4f2ceb9e15003b3ce5f0621', 'lkwcg', '66666', '2019-09-20 00:00:00', 'M', '2222@qq.com', '222221', NULL, 24, '2,1', 'ENABLE', '2019-09-20 11:29:47', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (15, '/common/images/head.png', 'jiufeng1', '94a73e4161871ba950d74e74cfab1640', 'e8x6g', '玖冯', '2019-09-23 00:00:00', 'M', '111@qq.com', '12424242423', '5', 25, '1', 'ENABLE', '2019-09-23 16:39:42', 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (16, '/common/images/head.png', 'ceshi123456', 'ffa526b73679320e917d8fa3c66dff39', 'yxeco', 'ceshi123456', '2019-10-22 00:00:00', 'M', 'ceshi123456@qq.com', '', '5', 26, '2', 'ENABLE', '2019-10-22 16:29:51', 1, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
