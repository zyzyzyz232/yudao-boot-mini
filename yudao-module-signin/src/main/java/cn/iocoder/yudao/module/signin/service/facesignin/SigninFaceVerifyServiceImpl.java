package cn.iocoder.yudao.module.signin.service.facesignin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.http.HttpUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.signin.controller.admin.record.vo.FaceVerifyAndSignRespVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceVerifyClient;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.LiteFaceVerifyResponse;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotosService;
import cn.iocoder.yudao.module.signin.service.record.SigninRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.invalidParamException;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.*;

@Service
public class SigninFaceVerifyServiceImpl implements SigninFaceVerifyService {

    private static final Set<String> ALLOWED_IMAGE_TYPES;

    static {
        Set<String> s = new HashSet<>();
        s.add("image/jpeg");
        s.add("image/jpg");
        s.add("image/png");
        s.add("image/webp");
        s.add("image/pjpeg");
        s.add("image/x-png");
        ALLOWED_IMAGE_TYPES = Collections.unmodifiableSet(s);
    }

    @Resource
    private FacePhotosService facePhotosService;
    @Resource
    private FileService fileService;
    @Resource
    private FaceVerifyClient faceVerifyClient;
    @Resource
    private SigninRecordService signinRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FaceVerifyAndSignRespVO verifyFaceAndSignIn(Long lessonId, String personId, Long photoId,
                                                       MultipartFile compareImage) throws Exception {
        if (lessonId == null) {
            throw invalidParamException("课堂编号不能为空");
        }
        if (StrUtil.isBlank(personId)) {
            throw invalidParamException("学员编号不能为空");
        }
        if (photoId == null) {
            throw invalidParamException("照片ID不能为空");
        }
        if (compareImage == null || compareImage.isEmpty()) {
            throw exception(FACE_PHOTO_SYNC_INVALID_FILE);
        }
        validateCompareImage(compareImage);

        FacePhotosDO face = facePhotosService.getFacePhotos(photoId);
        if (face == null) {
            throw exception(FACE_PHOTOS_NOT_EXISTS);
        }
        if (!personId.equals(face.getStudentNo())) {
            throw exception(FACE_PHOTO_PERSON_MISMATCH);
        }
        if (StrUtil.isBlank(face.getImageUrl())) {
            throw invalidParamException("底图地址为空");
        }

        byte[] sourceBytes = fileService.getFileContentByUrl(face.getImageUrl());
        if (sourceBytes == null || sourceBytes.length == 0) {
            throw exception(FACE_ALGORITHM_ERROR, "底图读取失败");
        }
        byte[] targetBytes = IoUtil.readBytes(compareImage.getInputStream());

        String sourceName = FileUtil.getName(HttpUtils.removeUrlQuery(face.getImageUrl()));
        if (StrUtil.isBlank(sourceName)) {
            sourceName = "source.jpg";
        }
        String sourceMime = mimeFromFormat(face.getImageFormat());

        LiteFaceVerifyResponse algo = faceVerifyClient.verify(
                sourceBytes, sourceName, sourceMime,
                targetBytes, compareImage.getOriginalFilename(), compareImage.getContentType());

        boolean signedIn = Boolean.TRUE.equals(algo.getIsSamePerson());
        if (signedIn) {
            signinRecordService.markSignedInByLessonAndPerson(lessonId, personId);
        }

        return FaceVerifyAndSignRespVO.builder()
                .similarity(algo.getSimilarity())
                .threshold(algo.getThreshold())
                .isSamePerson(algo.getIsSamePerson())
                .signedIn(signedIn)
                .build();
    }

    private static void validateCompareImage(MultipartFile file) {
        String ct = file.getContentType();
        if (StrUtil.isNotBlank(ct)) {
            String lower = ct.toLowerCase(Locale.ROOT);
            if (!ALLOWED_IMAGE_TYPES.contains(lower)) {
                throw exception(FACE_PHOTO_SYNC_INVALID_FILE);
            }
        }
    }

    private static String mimeFromFormat(String imageFormat) {
        if (StrUtil.isBlank(imageFormat)) {
            return null;
        }
        String ext = imageFormat.toLowerCase(Locale.ROOT);
        switch (ext) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "webp":
                return "image/webp";
            default:
                return null;
        }
    }

}
