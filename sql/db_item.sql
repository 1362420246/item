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

DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(36) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` int(11) NOT NULL COMMENT '父菜单',
  `level` int(11) NOT NULL COMMENT '菜单层级',
  `paths` varchar(255) NOT NULL DEFAULT '' COMMENT '路径',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='菜单表';

DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(36) NOT NULL DEFAULT '' COMMENT '角色名称',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='角色表';

DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
)  ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='角色菜单表';

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(36) NOT NULL DEFAULT '' COMMENT '登录名',
  `nick_name` varchar(36) NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(36) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(40) NOT NULL DEFAULT '' COMMENT 'shiro加密盐',
  `is_lock` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否锁定',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='用户表';

DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
)  ENGINE=InnoDB ROW_FORMAT=COMPACT COMMENT='用户角色表';


