package cn.iocoder.yudao.module.signin.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * signin 模块错误码枚举
 * 错误码区间：待分配
 */
public interface ErrorCodeConstants {

    // ========== 签到相关 ==========
    ErrorCode SIGNIN_NOT_EXISTS = new ErrorCode(1_200_000_000, "签到记录不存在");

    // ========== 人员相关 ==========
    ErrorCode PERSONS_NOT_EXISTS = new ErrorCode(1_200_000_001, "人员信息不存在");

    // ========== 人脸照片相关 ==========
    ErrorCode FACE_PHOTOS_NOT_EXISTS = new ErrorCode(1_200_000_002, "人脸照片不存在");
    ErrorCode FACE_PHOTO_PERSON_MISMATCH = new ErrorCode(1_200_000_004, "人脸照片与学员编号不匹配");
    ErrorCode FACE_PHOTO_SYNC_INVALID_FILE = new ErrorCode(1_200_000_005, "人脸同步照片不合法");
    ErrorCode FACE_ALGORITHM_ERROR = new ErrorCode(1_200_000_006, "人脸算法服务调用失败：{}");
    ErrorCode FACE_ALGORITHM_NOT_CONFIGURED = new ErrorCode(1_200_000_007, "人脸算法服务未配置 base-url");

    ErrorCode RECORD_NOT_EXISTS = new ErrorCode(1_200_000_003, "签到记录不存在");

    // ========== 教学班级学员校验 ==========
    ErrorCode TEACHING_CLASS_STUDENT_API_NOT_CONFIGURED = new ErrorCode(1_200_000_008, "教学班级学员接口未配置 yudao.signin.teaching-api.base-url");
    ErrorCode TEACHING_CLASS_STUDENT_API_ERROR = new ErrorCode(1_200_000_009, "教学班级学员接口调用失败：{}");
    ErrorCode STUDENT_NOT_IN_CLASS = new ErrorCode(1_200_000_010, "学员不属于该班级");

}
