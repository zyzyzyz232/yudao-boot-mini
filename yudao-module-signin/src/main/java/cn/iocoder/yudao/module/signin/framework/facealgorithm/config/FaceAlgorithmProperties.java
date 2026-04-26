package cn.iocoder.yudao.module.signin.framework.facealgorithm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * 签到人脸算法 Lite API 配置（{@code POST {baseUrl}/lite/extract}、{@code POST {baseUrl}/lite/verify}）
 */
@ConfigurationProperties(prefix = "yudao.signin.face-algorithm")
@Data
@Validated
public class FaceAlgorithmProperties {

    /**
     * 算法服务根地址，不含尾斜杠，例如 http://127.0.0.1:3000
     */
    private String baseUrl = "";

    @Min(1)
    private int connectTimeoutMillis = 5_000;

    @Min(1)
    private int readTimeoutMillis = 120_000;

    /**
     * 可选：完整 Authorization 头值（例如 Bearer xxx）
     */
    private String authorization = "";

}
