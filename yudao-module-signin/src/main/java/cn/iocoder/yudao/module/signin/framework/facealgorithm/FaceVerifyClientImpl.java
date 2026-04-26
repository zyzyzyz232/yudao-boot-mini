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

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_ALGORITHM_ERROR;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_ALGORITHM_NOT_CONFIGURED;

@Slf4j
public class FaceVerifyClientImpl implements FaceVerifyClient {

    private final FaceAlgorithmProperties properties;
    private final RestTemplate restTemplate;

    public FaceVerifyClientImpl(FaceAlgorithmProperties properties) {
        this.properties = properties;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.getConnectTimeoutMillis());
        factory.setReadTimeout(properties.getReadTimeoutMillis());
        this.restTemplate = new RestTemplate(factory);
    }

    @Override
    public LiteFaceVerifyResponse verify(byte[] sourceContent, String sourceFilename, String sourceContentType,
                                         byte[] targetContent, String targetFilename, String targetContentType) {
        if (StrUtil.isBlank(properties.getBaseUrl())) {
            throw exception(FACE_ALGORITHM_NOT_CONFIGURED);
        }
        if (sourceContent == null || sourceContent.length == 0 || targetContent == null || targetContent.length == 0) {
            throw exception(FACE_ALGORITHM_ERROR, "empty image");
        }

        String url = StrUtil.removeSuffix(properties.getBaseUrl().trim(), "/") + "/lite/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (StrUtil.isNotBlank(properties.getAuthorization())) {
            headers.set(HttpHeaders.AUTHORIZATION, properties.getAuthorization());
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("source_image", toResource(sourceContent, sourceFilename));
        body.add("target_image", toResource(targetContent, targetFilename));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (RestClientException e) {
            log.warn("[faceAlgorithm] verify request failed: {}", e.getMessage());
            throw exception(FACE_ALGORITHM_ERROR, e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("[faceAlgorithm] verify bad status {} bodySnippet={}",
                    response.getStatusCode(), bodySnippet(response.getBody()));
            throw exception(FACE_ALGORITHM_ERROR, "http " + response.getStatusCode().value());
        }

        LiteFaceVerifyResponse parsed;
        try {
            parsed = JsonUtils.parseObject(response.getBody(), LiteFaceVerifyResponse.class);
        } catch (RuntimeException e) {
            log.warn("[faceAlgorithm] verify json parse failed bodySnippet={}", bodySnippet(response.getBody()));
            throw exception(FACE_ALGORITHM_ERROR, "invalid json");
        }

        if (parsed == null || parsed.getSimilarity() == null || parsed.getIsSamePerson() == null) {
            throw exception(FACE_ALGORITHM_ERROR, "missing similarity or isSamePerson");
        }

        return parsed;
    }

    private static ByteArrayResource toResource(byte[] content, String filename) {
        String safeName = StrUtil.blankToDefault(filename, "face.jpg");
        return new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return safeName;
            }
        };
    }

    private static String bodySnippet(String body) {
        if (body == null) {
            return "";
        }
        return body.length() > 200 ? body.substring(0, 200) + "..." : body;
    }

}
