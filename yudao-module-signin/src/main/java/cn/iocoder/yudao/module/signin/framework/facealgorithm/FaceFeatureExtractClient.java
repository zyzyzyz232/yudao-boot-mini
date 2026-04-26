package cn.iocoder.yudao.module.signin.framework.facealgorithm;

/**
 * 调用人脸算法 Lite API，提取单脸 embedding。
 */
public interface FaceFeatureExtractClient {

    /**
     * @param fileContent   图片字节
     * @param filename      原始文件名（用于 multipart 文件名）
     * @param contentType   可为 null，multipart 可不传子 part Content-Type
     * @return 校验后的 embedding 与维度信息
     */
    float[] extractEmbedding(byte[] fileContent, String filename, String contentType);

}
