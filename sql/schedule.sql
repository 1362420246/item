 
DROP TABLE IF EXISTS `tb_schedule_group`;
CREATE TABLE `tb_schedule_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator_cardid` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '创建人身份证',
  `schedule_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '日程标题',
  `schedule_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '日程描述',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '结束时间',
  `is_repeat` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否重复  0：不重复  1：重复',
  `repeat_rules` int(1) NOT NULL DEFAULT 0 COMMENT '重复规则 0:不重复 1每天 2每周 3每月',
  `start_week` int(1) NULL DEFAULT NULL COMMENT '开始时间的星期',
  `end_week` int(1) NULL DEFAULT NULL COMMENT '结束时间的星期',
  `start_day_month` int(2) NULL DEFAULT NULL COMMENT '开始时间的日',
  `end_day_month` int(2) NULL DEFAULT NULL COMMENT '结束时间的日',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '日程组表' ROW_FORMAT = Compact;

 
DROP TABLE IF EXISTS `tb_schedule_info`;
CREATE TABLE `tb_schedule_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '日程组id',
  `creator_cardid` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '创建人身份证',
  `affiliated_cardid` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '关联人身份证',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1 表示删除，0 表示未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '日程表' ROW_FORMAT = Compact;
 
DROP TABLE IF EXISTS `tb_schedule_log`;
CREATE TABLE `tb_schedule_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '日程组id',
  `operation_cardid` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作日身份证',
  `log_type` int(1) NOT NULL COMMENT '1:开始  2:上传照片 3:上传附件 4:任务标注 5:删除 6:结束',
  `log_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作日志',
  `log_remarks` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '任务标注',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '文件存储路径（相对路径）',
  `file_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '文件访问路径（相对路径）',
  `file_zoom_path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片缩略图存储路径',
  `file_zoom_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片缩略图访问路径',
  `file_name` varchar(255) COLLATE utf8mb4 DEFAULT '' COMMENT '文件名称',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '日程日志表' ROW_FORMAT = Compact;
 
CREATE TABLE `tb_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `type` int(2) NOT NULL COMMENT '日志类型',
  `title` varchar(50) NOT NULL COMMENT '日志标题',
  `create_user` varchar(10) NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(50) NOT NULL COMMENT '操作者IP',
  `request_uri` varchar(50) NOT NULL COMMENT '请求uri',
  `params` text NOT NULL COMMENT '请求参数',
  `cost_time` int(11) NOT NULL COMMENT '执行时间',
  `return_msg` text NOT NULL,
  `user_agent` varchar(200) NULL DEFAULT NULL,
  `exception` text NULL COMMENT '异常信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志表' ROW_FORMAT = Compact;
