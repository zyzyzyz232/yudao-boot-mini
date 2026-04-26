-- 将 signin_face_photos.person_id 重命名为 student_no（已有库升级用；新建库请直接使用 face_compare_photo.sql 等建表脚本）
-- 执行前请备份数据；若列名已是 student_no 则勿重复执行

ALTER TABLE `signin_face_photos`
    CHANGE COLUMN `person_id` `student_no` VARCHAR(50) NOT NULL COMMENT '学员编号';

-- 重建索引（MySQL 5.7+：CHANGE 会保留原索引名或按需调整）
-- 若原索引名为 idx_person_id，可改为：
-- ALTER TABLE `signin_face_photos` DROP INDEX `idx_person_id`, ADD INDEX `idx_student_no` (`student_no`);
