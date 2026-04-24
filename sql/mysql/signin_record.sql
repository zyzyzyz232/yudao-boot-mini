CREATE TABLE `signin_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lesson_id` bigint NOT NULL COMMENT '课堂编号',
  `person_id` bigint NOT NULL COMMENT '学员编号',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）',
  `sign_time` datetime DEFAULT NULL COMMENT '实际签到时间',
  
  -- 芋道系统 (Yudao) 标准 BaseDO 审计字段与多租户字段
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  
  PRIMARY KEY (`id`) USING BTREE,
  -- 针对高频查询建立索引
  KEY `idx_lesson_id` (`lesson_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签到记录表';