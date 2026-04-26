package cn.iocoder.yudao.module.signin.service.facephotos;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceFeatureExtractClient;
import cn.iocoder.yudao.module.signin.service.persons.PersonsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacePhotoFeatureSyncServiceImplTest {

    @Mock
    private PersonsService personsService;
    @Mock
    private FacePhotosService facePhotosService;
    @Mock
    private FileService fileService;
    @Mock
    private FaceFeatureExtractClient faceFeatureExtractClient;

    @InjectMocks
    private FacePhotoFeatureSyncServiceImpl facePhotoFeatureSyncService;

    @Test
    void syncFeatureFromUpload_createPath_callsAlgorithmAndActivate() throws Exception {
        String studentNo = "9210";
        Long classId = 100L;
        String displayName = "张三";
        when(faceFeatureExtractClient.extractEmbedding(any(), anyString(), anyString()))
                .thenReturn(new float[]{0.1f, 0.2f});
        when(fileService.createFile(any(), anyString(), anyString(), anyString())).thenReturn("http://oss/1.jpg");
        when(facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(classId, studentNo)).thenReturn(null);
        when(facePhotosService.createFacePhotos(any(), anyString(), anyString(), anyInt())).thenReturn("2001");

        MockMultipartFile file = new MockMultipartFile(
                "file", "a.jpg", "image/jpeg", new byte[]{1, 2, 3});

        String photoId = facePhotoFeatureSyncService.syncFeatureFromUpload(studentNo, classId, displayName, null, null, file);

        assertEquals("2001", photoId);
        verify(faceFeatureExtractClient).extractEmbedding(any(), eq("a.jpg"), eq("image/jpeg"));
        verify(facePhotosService).createFacePhotos(any(), eq("http://oss/1.jpg"), eq("a.jpg"), anyInt());
        verify(personsService).ensurePersonAndActivate(eq(studentNo), eq(displayName));
    }

    @Test
    void syncFeatureFromUpload_nameOmitted_passesNullDisplayName() throws Exception {
        when(faceFeatureExtractClient.extractEmbedding(any(), anyString(), anyString()))
                .thenReturn(new float[]{0.2f});
        when(fileService.createFile(any(), anyString(), anyString(), anyString())).thenReturn("http://oss/y.jpg");
        when(facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(2L, "88")).thenReturn(null);
        when(facePhotosService.createFacePhotos(any(), anyString(), anyString(), anyInt())).thenReturn("7");

        MockMultipartFile file = new MockMultipartFile(
                "file", "a.jpg", "image/jpeg", new byte[]{1});

        facePhotoFeatureSyncService.syncFeatureFromUpload("88", 2L, null, null, null, file);

        verify(personsService).ensurePersonAndActivate(eq("88"), isNull());
    }

    @Test
    void syncFeatureFromUpload_updatePath_mismatchedPerson_throws() {
        String studentNo = "p-1";
        Long classId = 1L;
        FacePhotosDO other = FacePhotosDO.builder().photoId("ph").studentNo("other").build();
        when(facePhotosService.getFacePhotos("ph")).thenReturn(other);
        MockMultipartFile file = new MockMultipartFile(
                "file", "a.jpg", "image/jpeg", new byte[]{1, 2, 3});

        assertThrows(ServiceException.class,
                () -> facePhotoFeatureSyncService.syncFeatureFromUpload(studentNo, classId, "N", "ph", null, file));
        verify(faceFeatureExtractClient, never()).extractEmbedding(any(), anyString(), anyString());
    }

    @Test
    void syncFeatureFromUpload_upsertByClassAndStudent_updatesExisting() throws Exception {
        String studentNo = "9210";
        Long classId = 5L;
        String displayName = "李四";
        when(faceFeatureExtractClient.extractEmbedding(any(), anyString(), anyString()))
                .thenReturn(new float[]{0.3f});
        when(fileService.createFile(any(), anyString(), anyString(), anyString())).thenReturn("http://oss/2.jpg");
        FacePhotosDO existing = FacePhotosDO.builder().photoId("old-1").studentNo(studentNo).classId(classId).isPrimary(true).build();
        when(facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(classId, studentNo)).thenReturn(existing);

        MockMultipartFile file = new MockMultipartFile(
                "file", "b.jpg", "image/jpeg", new byte[]{9});

        String photoId = facePhotoFeatureSyncService.syncFeatureFromUpload(studentNo, classId, displayName, null, null, file);

        assertEquals("old-1", photoId);
        verify(facePhotosService).updateFacePhotos(any(), eq("http://oss/2.jpg"), eq("b.jpg"), anyInt());
        verify(facePhotosService, never()).createFacePhotos(any(), anyString(), anyString(), anyInt());
    }

}
