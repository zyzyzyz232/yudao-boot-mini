package cn.iocoder.yudao.module.signin.service.facephotos;

import org.springframework.web.multipart.MultipartFile;

/**
 * 人脸照片上传 + 算法提特征 + 写库 + 人员激活编排
 */
public interface FacePhotoFeatureSyncService {

    /**
     * 同步人脸特征：仅依赖 signin_face_photos（逻辑即 photos）按 classId+studentNo upsert；
     * 上传 OSS、调 Lite /lite/extract、更新 face_vector、人员 status=已激活。
     *
     * @param studentNo 学员编号（与 signin_face_photos.student_no、人员 person_id 取值一致）
     * @param classId   班级编号
     * @param name      可选；写入底库/人员表
     * @param photoId   可选；为空则按 classId+studentNo upsert，否则更新该记录（须属于该学员且 classId 一致）
     * @param teachingClassStudentId 可选；新建且非空时作为人脸记录主键 id（与 teaching_class_student.id 对齐）
     * @param file     照片
     * @return 人脸记录 photoId
     */
    String syncFeatureFromUpload(String studentNo, Long classId, String name, String photoId,
                                 Long teachingClassStudentId, MultipartFile file) throws Exception;

}
