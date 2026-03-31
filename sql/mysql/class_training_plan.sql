CREATE TABLE `class_training_plan` (
  -- 1. 业务主键
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '计划编号',

  -- 2. 核心业务字段
  `class_id` bigint(20) NOT NULL COMMENT '所属课堂编号',
  `title` varchar(100) NOT NULL COMMENT '计划标题',
  `training_type` varchar(50) DEFAULT NULL COMMENT '训练类型(如：步法、力量、多球、战术等)',
  `content` varchar(2000) DEFAULT NULL COMMENT '详细训练内容与指标要求',
  `duration` int(11) DEFAULT '0' COMMENT '预计训练时长(分钟)',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行状态(0:未开始 1:进行中 2:已完成)',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序(用于控制多个计划的执行顺序)',

  -- 3. 框架必备基础字段 (严禁修改)
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',

  PRIMARY KEY (`id`),
  KEY `idx_class_id` (`class_id`) COMMENT '课堂编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课堂训练计划表';