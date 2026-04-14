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

}
