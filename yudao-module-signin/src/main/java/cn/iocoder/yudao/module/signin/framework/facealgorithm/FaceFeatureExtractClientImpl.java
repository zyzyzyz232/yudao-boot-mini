package cn.iocoder.yudao.module.signin.framework.facealgorithm;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.config.FaceAlgorithmProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_ALGORITHM_ERROR;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_ALGORITHM_NOT_CONFIGURED;

@Slf4j
public class FaceFeatureExtractClientImpl implements FaceFeatureExtractClient {

    private final FaceAlgorithmProperties properties;
    private final RestTemplate restTemplate;

    public FaceFeatureExtractClientImpl(FaceAlgorithmProperties properties) {
        this.properties = properties;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.getConnectTimeoutMillis());
        factory.setReadTimeout(properties.getReadTimeoutMillis());
        this.restTemplate = new RestTemplate(factory);
    }

    @Override
    public float[] extractEmbedding(byte[] fileContent, String filename, String contentType) {
        if (StrUtil.isBlank(properties.getBaseUrl())) {
            throw exception(FACE_ALGORITHM_NOT_CONFIGURED);
        }
        if (fileContent == null || fileContent.length == 0) {
            throw exception(FACE_ALGORITHM_ERROR, "empty image");
        }

        String url = StrUtil.removeSuffix(properties.getBaseUrl().trim(), "/") + "/lite/extract";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (StrUtil.isNotBlank(properties.getAuthorization())) {
            headers.set(HttpHeaders.AUTHORIZATION, properties.getAuthorization());
        }

        String safeName = StrUtil.blankToDefault(filename, "face.jpg");
        ByteArrayResource resource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return safeName;
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (RestClientException e) {
            log.warn("[faceAlgorithm] extract request failed: {}", e.getMessage());
            throw exception(FACE_ALGORITHM_ERROR, e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("[faceAlgorithm] bad status {} bodySnippet={}",
                    response.getStatusCode(), bodySnippet(response.getBody()));
            throw exception(FACE_ALGORITHM_ERROR, "http " + response.getStatusCode().value());
        }

        LiteFaceExtractResponse parsed;
        try {
            parsed = JsonUtils.parseObject(response.getBody(), LiteFaceExtractResponse.class);
        } catch (RuntimeException e) {
            log.warn("[faceAlgorithm] json parse failed bodySnippet={}", bodySnippet(response.getBody()));
            throw exception(FACE_ALGORITHM_ERROR, "invalid json");
        }

        if (parsed == null || parsed.getEmbedding() == null || parsed.getEmbedding().isEmpty()) {
            throw exception(FACE_ALGORITHM_ERROR, "missing embedding");
        }
        if (parsed.getDetectedFaces() == null || parsed.getDetectedFaces() != 1) {
            throw exception(FACE_ALGORITHM_ERROR, "detectedFaces must be 1");
        }

        List<Double> list = parsed.getEmbedding();
        float[] floats = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Double v = list.get(i);
            floats[i] = v == null ? 0F : v.floatValue();
        }

        if (parsed.getEmbeddingSize() != null && parsed.getEmbeddingSize() != floats.length) {
            log.warn("[faceAlgorithm] embeddingSize {} != length {}", parsed.getEmbeddingSize(), floats.length);
        }

        return floats;
    }

    private static String bodySnippet(String body) {
        if (body == null) {
            return "";
        }
        return body.length() > 200 ? body.substring(0, 200) + "..." : body;
    }

}
