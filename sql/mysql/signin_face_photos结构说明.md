对应库表实际名：**`signin_face_photos`**（下列字段语义与之一致）。

**主键 `id`**：建表脚本为 `VARCHAR(50)`；若线上仍为 `BIGINT`，勿写入含字母的 UUID（会报 `Data truncated for column 'id'`）。后端在未传 `photoId` / `teachingClassStudentId` 时使用**雪花数字串**作为主键，与 `BIGINT`、`VARCHAR` 均兼容；需存 UUID 时请把列类型改为 `VARCHAR(50)`（见 `face_compare_photo.sql`）。

**管理后台接口约定**：`student_no`、`class_id` 在分页、导出及创建/同步等多数字段接口中为**必填**；创建人脸记录时 **照片文件 `file` 可为空**（无文件则 `image_url` 置空串等占位，可仅写 `face_vector` 等）。`class_student_id`（接口参数名常见为 `teachingClassStudentId`）仅用于与其它表主键对齐写入本表主键 `id`，**选传**，不参与业务侧必填校验。

---

## 学生人脸信息表（示例表名：`student_face_info`）

| 字段名            | 数据类型   | 说明（建议补充）                     |
|-------------------|------------|--------------------------------------|
| id                | bigint     | 主键，自增ID                         |
| name              | varchar    | 学生姓名                             |
| student_no        | varchar    | 学号（原先的person_id）               |
| image_url         | varchar    | 人脸图片存储路径/URL                 |
| image_format      | varchar    | 图片格式（如 jpg、png）              |
| image_size_kb     | int        | 图片大小（单位：KB）                 |
| face_vector       | json       | 人脸特征向量（JSON 数组存储）        |
| quality_score     | decimal    | 图片质量分                           |
| liveness_score   | decimal    | 活体检测得分                         |
| is_primary        | tinyint    | 是否为主照片（0：否，1：是）         |
| creator           | varchar    | 创建人                               |
| create_time       | datetime   | 创建时间                             |
| updater           | varchar    | 更新人                               |
| update_time       | datetime   | 更新时间                             |
| status            | bit        | 状态（0：禁用，1：启用）             |
| tenant_id         | bigint     | 租户ID（多租户场景）                 |
| deleted           | bit        | 逻辑删除标识（0：未删除，1：已删除） |
| class_student_id  | bigint     | 班级学生关联ID                       |
| class_id          | bigint     | 班级ID                               |