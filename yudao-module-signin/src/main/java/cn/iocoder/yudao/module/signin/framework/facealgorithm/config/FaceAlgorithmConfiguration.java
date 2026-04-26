package cn.iocoder.yudao.module.signin.framework.facealgorithm.config;

import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceFeatureExtractClient;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceFeatureExtractClientImpl;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceVerifyClient;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceVerifyClientImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FaceAlgorithmProperties.class)
public class FaceAlgorithmConfiguration {

    @Bean
    public FaceFeatureExtractClient faceFeatureExtractClient(FaceAlgorithmProperties properties) {
        return new FaceFeatureExtractClientImpl(properties);
    }

    @Bean
    public FaceVerifyClient faceVerifyClient(FaceAlgorithmProperties properties) {
        return new FaceVerifyClientImpl(properties);
    }

}
