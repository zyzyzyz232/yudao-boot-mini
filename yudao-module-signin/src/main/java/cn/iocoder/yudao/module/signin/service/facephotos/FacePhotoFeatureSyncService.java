package cn.iocoder.yudao.module.signin.service.facephotos;

import org.springframework.web.multipart.MultipartFile;

/**
 * 人脸照片上传 + 算法提特征 + 写入 signin_face_photos
 */
public interface FacePhotoFeatureSyncService {

    /**
     * 同步人脸特征：仅依赖 signin_face_photos，按 classId+studentNo upsert；
     * 上传 OSS、调 Lite /lite/extract、更新 face_vector。
     *
     * @param studentNo 学员编号（与 signin_face_photos.student_no 一致）
     * @param classId   班级编号
     * @param name      可选；写入人脸记录 name 字段
     * @param photoId   可选；为空则按 classId+studentNo upsert，否则更新该记录（须属于该学员且 classId 一致）
     * @param teachingClassStudentId 可选；新建且非空时作为人脸记录主键 id（与 teaching_class_student.id 对齐）
     * @param file     照片
     * @return 人脸记录 photoId
     */
    Long syncFeatureFromUpload(String studentNo, Long classId, String name, Long photoId,
                               Long teachingClassStudentId, MultipartFile file) throws Exception;

}
