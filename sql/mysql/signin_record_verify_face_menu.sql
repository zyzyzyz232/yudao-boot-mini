-- 在签到记录菜单下增加「人脸比对签到」按钮权限
-- 执行前请确认库中已存在 permission = 'signin:record:query' 的菜单行（与项目菜单配置一致）
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '人脸比对签到', 'signin:record:verify-face', 3, 9, `parent_id`, '', '', NULL, NULL, 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'
FROM `system_menu`
WHERE `permission` = 'signin:record:query' AND `deleted` = b'0'
LIMIT 1;
