package cn.iocoder.yudao.module.signin.framework.facealgorithm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Lite API POST /lite/extract 响应体
 */
@Data
public class LiteFaceExtractResponse {

    private List<Double> embedding;

    @JsonProperty("embeddingSize")
    private Integer embeddingSize;

    @JsonProperty("detectedFaces")
    private Integer detectedFaces;

}
