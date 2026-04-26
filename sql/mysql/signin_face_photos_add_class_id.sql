-- 签到人脸底库增加班级维度，支持同一学员多班级独立底库
ALTER TABLE `signin_face_photos`
    ADD COLUMN `class_id` bigint DEFAULT NULL COMMENT '班级编号' AFTER `student_no`;

CREATE INDEX `idx_signin_face_photos_class_student` ON `signin_face_photos` (`class_id`, `student_no`);
