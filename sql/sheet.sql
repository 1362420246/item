DROP TABLE IF EXISTS `db_sheet_log`;
CREATE TABLE `db_sheet_log`  (
  `log_id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(1) NULL DEFAULT NULL COMMENT '任务组id',
  `log_time` date NOT NULL COMMENT '上传任务进度时间',
  `log_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传任务进度图片路径、文档路径、文字描述',
  `log_cardid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `log_type` int(255) NULL DEFAULT NULL COMMENT '上传类型 0-图片 1-文档 2-文字描述',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `db_sheet_group`;
CREATE TABLE `db_sheet_group`  (
  `group_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `creator_cardid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者id',
  `sheet_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务标题',
  `sheet_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `db_sheet_task`;
CREATE TABLE `db_sheet_task`  (
  `task_id` int(255) NOT NULL AUTO_INCREMENT,
  `task_cardid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务关联人员id',
  `group_id` int(1) NULL DEFAULT NULL COMMENT '任务组id',
  `task_type` int(10) NULL DEFAULT NULL COMMENT '任务类型 0-发布任务  1-接受任务  2-抄送任务',
  `task_state` int(10) NULL DEFAULT NULL COMMENT '  0-任务已开始 1- 任务已超时 2-任务已拒绝',
  `task_main` int(10) NULL DEFAULT NULL COMMENT '任务主从关系 0-任务接受者 1-任务发布者 2-抄送者',
  `task_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档',
  `task_endtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
