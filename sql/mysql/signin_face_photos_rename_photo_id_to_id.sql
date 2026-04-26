-- 将主键列 photo_id 重命名为 id（与 FacePhotosDO @TableId(value = "id") 一致）
-- 若表中已是 id 列则勿执行。执行前请备份。

ALTER TABLE `signin_face_photos`
    CHANGE COLUMN `photo_id` `id` VARCHAR(50) NOT NULL COMMENT '照片唯一标识(可使用UUID)';
