-- ==========================================
-- 1. 创建签到人员基本信息表
-- ==========================================
CREATE TABLE `signin_persons` (
    `person_id` VARCHAR(50) NOT NULL COMMENT '人员唯一标识(可使用UUID)',
    `name` VARCHAR(100) NOT NULL COMMENT '人员姓名',
    `department` VARCHAR(100) DEFAULT NULL COMMENT '所属部门/组织',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：1-正常, 0-禁用',
    
    -- ▼▼▼ 芋道(Yudao)框架要求的基础通用字段 ▼▼▼
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(0-未删除，1-已删除)',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    -- ▲▲▲ 芋道(Yudao)框架要求的基础通用字段 ▲▲▲
    
    PRIMARY KEY (`person_id`),
    INDEX `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到系统-人员基本信息表';


-- ==========================================
-- 2. 创建签到人脸照片与特征数据表
-- ==========================================
CREATE TABLE `signin_face_photos` (
    `id` VARCHAR(50) NOT NULL COMMENT '主键；可与 teaching_class_student.id 同值（逻辑关联，VARCHAR 存数字串）；未绑定时仍可用 UUID',
    `student_no` VARCHAR(50) NOT NULL COMMENT '学员编号',
    `class_id` bigint DEFAULT NULL COMMENT '班级编号（同一学员多班级时区分底库）',
    
    -- 图像存储信息
    `image_url` VARCHAR(500) NOT NULL COMMENT '照片在对象存储(OSS/S3)中的访问路径',
    `image_format` VARCHAR(10) DEFAULT 'jpg' COMMENT '图片格式(jpg, png等)',
    `image_size_kb` INT DEFAULT 0 COMMENT '图片大小(KB)',
    
    -- 人脸识别核心数据
    `face_vector` JSON DEFAULT NULL COMMENT '人脸特征向量数据(通常是一组浮点数数组)',
    `quality_score` DECIMAL(5,4) DEFAULT NULL COMMENT '人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片',
    `liveness_score` DECIMAL(5,4) DEFAULT NULL COMMENT '活体检测得分(0.0000 - 1.0000)，防止照片攻击',
    
    -- 业务逻辑字段
    `is_primary` TINYINT(1) DEFAULT 0 COMMENT '是否为主照片(1-是, 0-否)',
    `name` varchar(100) DEFAULT NULL COMMENT '姓名',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：1-正常, 0-禁用',
    
    -- ▼▼▼ 芋道(Yudao)框架要求的基础通用字段 ▼▼▼
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(0-未删除，1-已删除)',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    -- ▲▲▲ 芋道(Yudao)框架要求的基础通用字段 ▲▲▲
    
    PRIMARY KEY (`id`),
    INDEX `idx_student_no` (`student_no`),
    KEY `idx_signin_face_photos_class_student` (`class_id`, `student_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到系统-人脸照片特征表';