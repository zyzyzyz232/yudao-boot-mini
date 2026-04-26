-- 在人脸照片菜单下增加「人脸特征同步」按钮权限（与 signin:face-photos:create 同级）
-- 执行前请确认库中已存在 permission = 'signin:face-photos:create' 的菜单行（自行配置签到模块菜单时）
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '人脸特征同步', 'signin:face-photos:sync-feature', 3, 8, `parent_id`, '', '', NULL, NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'
FROM `system_menu`
WHERE `permission` = 'signin:face-photos:create' AND `deleted` = b'0'
LIMIT 1;
