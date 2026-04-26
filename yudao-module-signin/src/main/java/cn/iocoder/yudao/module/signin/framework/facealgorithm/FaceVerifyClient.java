package cn.iocoder.yudao.module.signin.framework.facealgorithm;

/**
 * 调用人脸算法 Lite API，比对两张人脸图。
 */
public interface FaceVerifyClient {

    /**
     * @param sourceContent     底图（库中人像）字节
     * @param sourceFilename      multipart 文件名
     * @param sourceContentType   可为 null
     * @param targetContent     现场图字节
     * @param targetFilename      multipart 文件名
     * @param targetContentType   可为 null
     */
    LiteFaceVerifyResponse verify(byte[] sourceContent, String sourceFilename, String sourceContentType,
                                  byte[] targetContent, String targetFilename, String targetContentType);

}
