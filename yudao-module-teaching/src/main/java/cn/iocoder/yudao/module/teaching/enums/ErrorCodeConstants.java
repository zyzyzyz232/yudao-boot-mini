package cn.iocoder.yudao.module.teaching.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * teaching 模块错误码枚举
 */
public interface ErrorCodeConstants {

    // ========== 照片相关 ==========
    ErrorCode PHOTOS_NOT_EXISTS = new ErrorCode(1_210_000_000, "照片不存在");

    // ========== 教学视频相关 ==========
    ErrorCode VIDEO_NOT_EXISTS = new ErrorCode(1_210_000_001, "教学视频不存在");

}
