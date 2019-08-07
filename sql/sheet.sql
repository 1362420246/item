DROP TABLE IF EXISTS `db_sheet_log`;
CREATE TABLE `db_sheet_log`  (
  `log_id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(255) NULL DEFAULT NULL COMMENT '任务组id',
  `log_time` date NULL DEFAULT NULL COMMENT '上传任务进度时间',
  `log_cardid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `log_type` int(11) NULL DEFAULT NULL COMMENT '上传类型 0-图片 1-文档 2-文字描述',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件存储路径',
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件访问路径',
  `file_tagging` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传标注',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `db_sheet_group`;
CREATE TABLE `db_sheet_group`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `creator_cardid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者id',
  `sheet_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务标题',
  `sheet_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  `group_fileurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档请求路径',
  `group_filepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档存储路径',
  `task_type` int(11) NULL DEFAULT NULL COMMENT '默认为零',
  `task_creattime` datetime(0) NULL DEFAULT NULL COMMENT '任务创建时间',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `db_sheet_task`;
CREATE TABLE `db_sheet_task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_cardid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行人员id',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '任务组id',
  `task_type` int(10) NULL DEFAULT NULL COMMENT '任务类型 0-接受任务  1-抄送任务',
  `task_state` int(11) NULL DEFAULT NULL COMMENT '  0-任务未开始1-任务已开始  2-任务已拒绝',
  `task_copierid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务抄送人员id',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

