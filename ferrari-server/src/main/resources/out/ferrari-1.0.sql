CREATE TABLE `t_tesla_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(64) DEFAULT NULL COMMENT '功能action code',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '功能action名称',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_action_code` (`menu_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

CREATE TABLE `t_tesla_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_code` varchar(64) DEFAULT NULL,
  `role_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `t_tesla_role_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_r_a_inx` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `t_tesla_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `nick_name` varchar(64) DEFAULT '' COMMENT '用户昵称',
  `mobile` varchar(16) NOT NULL COMMENT '用户手机号',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '登陆密码',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `header_url` varchar(256) DEFAULT NULL COMMENT '头像',
  `signature` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `is_active` int(11) NOT NULL DEFAULT '1' COMMENT '用户是否被激活   0:未激活    1:已激活',
  `version` int(10) NOT NULL DEFAULT '1' COMMENT '乐观锁版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile_un_inx` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';

CREATE TABLE `t_tesla_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_h_u_r` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;