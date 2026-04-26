package cn.iocoder.yudao.module.signin.framework.facealgorithm;

import lombok.Data;

/**
 * Lite API POST /lite/verify 响应体
 */
@Data
public class LiteFaceVerifyResponse {

    private Float similarity;

    private Float threshold;

    private Boolean isSamePerson;

}
