 
DROP TABLE IF EXISTS `db_sheet_group`;
CREATE TABLE `db_sheet_group`  (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `creator_cardid` char(20)  NOT NULL COMMENT '创建者id',
  `sheet_title` varchar(255)  NULL DEFAULT NULL COMMENT '任务标题',
  `sheet_describe` varchar(255)  NULL DEFAULT NULL COMMENT '任务描述',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  `group_fileurl` varchar(255)  NULL DEFAULT NULL COMMENT '任务说明文档请求路径',
  `group_filepath` varchar(255)  NULL DEFAULT NULL COMMENT '任务说明文档存储路径',
  `group_filename` varchar(255)  NULL DEFAULT NULL COMMENT '任务说明文档存储文件名',
  `task_type` int(11) NULL DEFAULT 0 COMMENT '默认为零 任务类型 0-创建者1-接受任务  2-抄送任务',
  `task_creattime` datetime(0) NULL DEFAULT NULL COMMENT '任务创建时间',
  `group_status` int(11) NULL DEFAULT 0 COMMENT '正常0 结束1 默认0',
  `is_delete` int(1) NULL DEFAULT 0 COMMENT '是否被删除 0：否  1：是删除',
  `is_overdue` int(1) NULL DEFAULT 0 COMMENT '是否被逾期 0：否  1：是逾期',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  ROW_FORMAT = Compact  COMMENT = '任务组表' ;

 
DROP TABLE IF EXISTS `db_sheet_log`;
CREATE TABLE `db_sheet_log`  (
  `log_id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(255) NULL DEFAULT NULL COMMENT '任务组id',
  `log_time` datetime(0) NULL DEFAULT NULL COMMENT '上传任务进度时间',
  `log_cardid` char(255)  NULL DEFAULT NULL COMMENT '上传人',
  `log_type` int(11) NULL DEFAULT NULL COMMENT '操作类型： 0-图片 1-文档 2-文字描述   3.创建任务（发布者）   4.结束任务（发布者） 5.撤回任务（发布者，可以带理由） 6.开始任务（执行者 ） 7.完成任务（执行者 ） 8.拒绝任务（执行者，可以带理由 ） 9修改任务（发布者）',
  `file_path` varchar(255)  NULL DEFAULT NULL COMMENT '文件存储路径',
  `file_url` varchar(255)  NULL DEFAULT NULL COMMENT '文件访问路径',
  `file_name` varchar(255)  NULL DEFAULT NULL COMMENT '文件名称',
  `file_zoom_path` varchar(255)  NULL DEFAULT NULL COMMENT '图片缩略图存储路径',
  `file_zoom_url` varchar(255)  NULL DEFAULT NULL COMMENT '图片缩略图访问路径',
  `file_tagging` varchar(255)  NULL DEFAULT NULL COMMENT '上传标注',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  ROW_FORMAT = Compact  COMMENT = '任务日志表';

 
DROP TABLE IF EXISTS `db_sheet_task`;
CREATE TABLE `db_sheet_task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_cardid` char(20)  NULL DEFAULT NULL COMMENT '任务执行人员id',
  `task_copierid` char(20)  NULL DEFAULT NULL COMMENT '任务抄送人员id',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '任务组id',
  `task_type` int(11) NULL DEFAULT NULL COMMENT '任务类型 0-创建者1-接受任务  2-抄送任务',
  `task_state` int(11) NULL DEFAULT 0 COMMENT '0未开始 1进行中 2拒绝 3已完成',
  `task_endtime` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  ROW_FORMAT = Compact  COMMENT = '任务表';

 
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `type` int(2) NOT NULL COMMENT '日志类型',
  `title` varchar(50)  NOT NULL COMMENT '日志标题',
  `create_user` varchar(10)  NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(50)  NOT NULL COMMENT '操作者IP',
  `request_uri` varchar(50)  NOT NULL COMMENT '请求uri',
  `params` text  NOT NULL COMMENT '请求参数',
  `cost_time` int(11) NOT NULL COMMENT '执行时间',
  `return_msg` text  NOT NULL,
  `user_agent` varchar(200)  NULL DEFAULT NULL,
  `exception` text  NULL COMMENT '异常信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  COMMENT = '日志表' ROW_FORMAT = Compact;
 
DROP TABLE IF EXISTS `tb_schedule_group`;
CREATE TABLE `tb_schedule_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator_cardid` char(18)  NOT NULL DEFAULT '' COMMENT '创建人身份证',
  `schedule_title` varchar(20)  NOT NULL DEFAULT '' COMMENT '日程标题',
  `schedule_describe` varchar(255)  NULL DEFAULT '' COMMENT '日程描述',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '日程组表' ROW_FORMAT = Compact;

 
DROP TABLE IF EXISTS `tb_schedule_info`;
CREATE TABLE `tb_schedule_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '日程组id',
  `creator_cardid` char(18)  NOT NULL DEFAULT '' COMMENT '创建人身份证',
  `affiliated_cardid` char(18)  NOT NULL COMMENT '关联人身份证',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1 表示删除，0 表示未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '日程表' ROW_FORMAT = Compact;

 
DROP TABLE IF EXISTS `tb_schedule_log`;
CREATE TABLE `tb_schedule_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '日程组id',
  `operation_cardid` char(18)  NOT NULL DEFAULT '' COMMENT '操作日身份证',
  `log_type` int(1) NOT NULL COMMENT '1:开始  2:上传照片 3:上传附件 4:任务标注 5:删除 6:结束',
  `log_details` varchar(255)  NOT NULL DEFAULT '' COMMENT '操作日志',
  `log_remarks` varchar(255)  NULL DEFAULT '' COMMENT '任务标注',
  `file_path` varchar(255)  NULL DEFAULT '' COMMENT '文件存储路径（相对路径）',
  `file_uri` varchar(255)  NULL DEFAULT '' COMMENT '文件访问路径（相对路径）',
  `file_name` varchar(255)  NULL DEFAULT NULL COMMENT '文件名称',
  `file_zoom_path` varchar(255)  NULL DEFAULT NULL COMMENT '图片缩略图存储路径',
  `file_zoom_url` varchar(255)  NULL DEFAULT NULL COMMENT '图片缩略图访问路径',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '日程日志表' ROW_FORMAT = Compact;

 
