CREATE TABLE `training_plan_item` (
  -- 1. 业务主键
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目编号',

  -- 2. 核心关联字段
  `plan_id` bigint(20) NOT NULL COMMENT '所属训练计划编号',

  -- 3. 业务信息字段
  `title` varchar(100) NOT NULL COMMENT '项目名称(如: 正手挑球练习)',
  `instruction` varchar(1000) DEFAULT NULL COMMENT '说明文字(项目具体练什么、达标要求)',
  `tips` varchar(500) DEFAULT NULL COMMENT '提示文字(如: 发力技巧、易错点提醒)',
  `duration` int(11) DEFAULT '0' COMMENT '项目时长(秒)',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频访问链接(示范视频)',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片访问链接(动作拆解图或战术图)',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '执行顺序(数值越小越先执行)',

  -- 4. 框架必备基础字段 (严禁修改)
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',

  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`) COMMENT '训练计划编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='训练项目表';