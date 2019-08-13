 DROP TABLE IF EXISTS `db_sheet_group`;
CREATE TABLE `db_sheet_group`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `creator_cardid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者id',
  `sheet_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务标题',
  `sheet_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  `group_fileurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档请求路径',
  `group_filepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档存储路径',
  `group_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务说明文档存储文件名',
  `task_type` int(11) NULL DEFAULT 0 COMMENT '默认为零 任务类型 0-创建者1-接受任务  2-抄送任务',
  `task_creattime` datetime(0) NULL DEFAULT NULL COMMENT '任务创建时间',
  `group_status` int(11) NULL DEFAULT 0 COMMENT '正常0 结束1 默认0',
  `is_delete` int(1) NULL DEFAULT 0 COMMENT '是否被删除 0：否  1：是删除',
  `is_overdue` int(1) NULL DEFAULT 0 COMMENT '是否被逾期 0：否  1：是逾期',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for db_sheet_log
-- ----------------------------
DROP TABLE IF EXISTS `db_sheet_log`;
CREATE TABLE `db_sheet_log`  (
  `log_id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(255) NULL DEFAULT NULL COMMENT '任务组id',
  `log_time` datetime(0) NULL DEFAULT NULL COMMENT '上传任务进度时间',
  `log_cardid` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `log_type` int(11) DEFAULT NULL COMMENT '操作类型： 0-图片 1-文档 2-文字描述   3.创建任务（发布者）   4.结束任务（发布者） 5.撤回任务（发布者，可以带理由） 6.开始任务（执行者 ） 7.完成任务（执行者 ） 8.拒绝任务（执行者，可以带理由 ）',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件存储路径',
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件访问路径',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_zoom_path` varchar(255) DEFAULT NULL COMMENT '图片缩略图存储路径',
  `file_zoom_url` varchar(255) DEFAULT NULL COMMENT '图片缩略图访问路径',
  `file_tagging` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传标注',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for db_sheet_task
-- ----------------------------
DROP TABLE IF EXISTS `db_sheet_task`;
CREATE TABLE `db_sheet_task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_cardid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行人员id',
  `task_copierid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务抄送人员id',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '任务组id',
  `task_type` int(11) NULL DEFAULT NULL COMMENT '任务类型 0-创建者1-接受任务  2-抄送任务',
  `task_state` int(11) NULL DEFAULT 0 COMMENT '0未开始 1进行中 2拒绝 3已完成  4逾期',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;