package cn.iocoder.yudao.module.signin.service.facesignin;

import cn.iocoder.yudao.module.signin.controller.admin.record.vo.FaceVerifyAndSignRespVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 人脸比对并更新签到状态
 */
public interface SigninFaceVerifyService {

    /**
     * 使用库中人脸与现场照片调用算法比对；若判定同人则更新对应课堂、学员的签到记录为已签到。
     *
     * @param lessonId     课堂编号
     * @param personId     学员编号
     * @param photoId      人脸照片记录 ID
     * @param compareImage 现场拍摄的人脸图
     */
    FaceVerifyAndSignRespVO verifyFaceAndSignIn(Long lessonId, String personId, String photoId,
                                                MultipartFile compareImage) throws Exception;

}
