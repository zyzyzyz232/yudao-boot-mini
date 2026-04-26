package cn.iocoder.yudao.module.signin.service.facephotos;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceFeatureExtractClient;
import cn.iocoder.yudao.module.signin.service.persons.PersonsService;
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
public class FacePhotoFeatureSyncServiceImpl implements FacePhotoFeatureSyncService {

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
    private PersonsService personsService;
    @Resource
    private FacePhotosService facePhotosService;
    @Resource
    private FileService fileService;
    @Resource
    private FaceFeatureExtractClient faceFeatureExtractClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String syncFeatureFromUpload(String studentNo, Long classId, String name, String photoId,
                                        Long teachingClassStudentId, MultipartFile file) throws Exception {
        if (StrUtil.isBlank(studentNo)) {
            throw invalidParamException("学员编号不能为空");
        }
        if (classId == null) {
            throw invalidParamException("班级编号不能为空");
        }
        if (file == null || file.isEmpty()) {
            throw exception(FACE_PHOTO_SYNC_INVALID_FILE);
        }
        validateFile(file);

        String trimmedStudentNo = StrUtil.trim(studentNo);
        String displayName = StrUtil.isNotBlank(name) ? StrUtil.trim(name) : null;

        FacePhotosDO existingForUpdate = null;
        if (StrUtil.isNotBlank(photoId)) {
            existingForUpdate = facePhotosService.getFacePhotos(photoId);
            if (existingForUpdate == null) {
                throw exception(FACE_PHOTOS_NOT_EXISTS);
            }
            if (!trimmedStudentNo.equals(existingForUpdate.getStudentNo())) {
                throw exception(FACE_PHOTO_PERSON_MISMATCH);
            }
            if (existingForUpdate.getClassId() != null && !existingForUpdate.getClassId().equals(classId)) {
                throw exception(FACE_PHOTO_PERSON_MISMATCH);
            }
        }

        byte[] content = IoUtil.readBytes(file.getInputStream());
        float[] embedding = faceFeatureExtractClient.extractEmbedding(
                content, file.getOriginalFilename(), file.getContentType());
        String faceVectorJson = JsonUtils.toJsonString(embedding);

        String imageUrl = fileService.createFile(content, file.getOriginalFilename(),
                "signin/face-photos", file.getContentType());
        int sizeKb = (int) (file.getSize() / 1024);

        String resultPhotoId;
        if (StrUtil.isNotBlank(photoId)) {
            FacePhotosSaveReqVO updateReq = new FacePhotosSaveReqVO();
            updateReq.setPhotoId(photoId);
            updateReq.setStudentNo(trimmedStudentNo);
            updateReq.setClassId(classId);
            updateReq.setFaceVector(faceVectorJson);
            updateReq.setName(displayName);
            updateReq.setIsPrimary(existingForUpdate.getIsPrimary());
            facePhotosService.updateFacePhotos(updateReq, imageUrl, file.getOriginalFilename(), sizeKb);
            resultPhotoId = photoId;
        } else {
            FacePhotosDO byClassStudent = facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(classId, trimmedStudentNo);
            if (byClassStudent != null) {
                FacePhotosSaveReqVO updateReq = new FacePhotosSaveReqVO();
                updateReq.setPhotoId(byClassStudent.getPhotoId());
                updateReq.setStudentNo(trimmedStudentNo);
                updateReq.setClassId(classId);
                updateReq.setFaceVector(faceVectorJson);
                updateReq.setName(displayName);
                updateReq.setIsPrimary(byClassStudent.getIsPrimary());
                facePhotosService.updateFacePhotos(updateReq, imageUrl, file.getOriginalFilename(), sizeKb);
                resultPhotoId = byClassStudent.getPhotoId();
            } else {
                FacePhotosSaveReqVO createReq = new FacePhotosSaveReqVO();
                createReq.setStudentNo(trimmedStudentNo);
                createReq.setClassId(classId);
                createReq.setTeachingClassStudentId(teachingClassStudentId);
                createReq.setFaceVector(faceVectorJson);
                createReq.setName(displayName);
                resultPhotoId = facePhotosService.createFacePhotos(createReq, imageUrl,
                        file.getOriginalFilename(), sizeKb);
            }
        }

        personsService.ensurePersonAndActivate(trimmedStudentNo, displayName);
        return resultPhotoId;
    }

    private void validateFile(MultipartFile file) {
        String ct = file.getContentType();
        if (StrUtil.isNotBlank(ct)) {
            String lower = ct.toLowerCase(Locale.ROOT);
            if (!ALLOWED_IMAGE_TYPES.contains(lower)) {
                throw exception(FACE_PHOTO_SYNC_INVALID_FILE);
            }
        }
    }

}
