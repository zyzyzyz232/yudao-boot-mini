CREATE TABLE `teaching_video` (
  -- 1. 业务主键
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '视频编号',

  -- 2. 核心业务字段
  `title` varchar(100) NOT NULL COMMENT '视频标题',
  `description` varchar(500) DEFAULT NULL COMMENT '视频简介',
  `video_url` varchar(255) NOT NULL COMMENT '视频链接',
  `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图链接',
  `duration` int(11) DEFAULT '0' COMMENT '视频时长(秒)',
  
  -- 3. 归类与控制字段
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类编号',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '显示排序',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(0:开启 1:关闭)',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放次数',

  -- 4. 框架必备基础字段 (千万不要删除或修改这部分字段名)
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教学视频表';