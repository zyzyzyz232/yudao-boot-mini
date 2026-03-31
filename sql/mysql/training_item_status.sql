CREATE TABLE `training_item_status` (
  -- 1. 业务主键
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '状态记录编号',

  -- 2. 核心关联字段
  `item_id` bigint(20) NOT NULL COMMENT '所属训练项目编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '训练用户编号',

  -- 3. 业务状态字段
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)',
  `actual_duration` int(11) DEFAULT '0' COMMENT '实际训练时长(秒)',
  `remark` varchar(500) DEFAULT NULL COMMENT '教练或学员备注(如: 动作标准、体力不支等)',

  -- 4. 框架必备基础字段 (严禁修改)
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',

  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`item_id`) COMMENT '训练项目编号索引',
  KEY `idx_user_id` (`user_id`) COMMENT '训练用户编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='训练项目执行状态表';