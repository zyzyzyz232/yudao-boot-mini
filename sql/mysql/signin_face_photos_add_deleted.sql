-- 为 signin_face_photos 增加逻辑删除列（与 FacePhotosDO / BaseDO 一致）
-- 若列已存在请勿重复执行。执行前请备份。

ALTER TABLE `signin_face_photos`
    ADD COLUMN `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(0-未删除，1-已删除)';
