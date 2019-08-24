DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `type` int(2) DEFAULT NULL COMMENT '日志类型',
  `title` varchar(50) DEFAULT NULL COMMENT '日志标题',
  `create_user` varchar(10) DEFAULT '0' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(50) DEFAULT NULL COMMENT '操作者IP',
  `request_uri` varchar(50) DEFAULT NULL COMMENT '请求uri',
  `params` text COMMENT '请求参数',
  `cost_time` int(11) DEFAULT NULL COMMENT '执行时间',
  `return_msg` text COMMENT '返回结果',
  `user_agent` varchar(200) DEFAULT NULL,
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='日志表';