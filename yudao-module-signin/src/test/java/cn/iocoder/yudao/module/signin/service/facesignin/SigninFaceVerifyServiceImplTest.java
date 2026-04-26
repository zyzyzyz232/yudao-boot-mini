package cn.iocoder.yudao.module.signin.service.facesignin;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.signin.controller.admin.record.vo.FaceVerifyAndSignRespVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.FaceVerifyClient;
import cn.iocoder.yudao.module.signin.framework.facealgorithm.LiteFaceVerifyResponse;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotosService;
import cn.iocoder.yudao.module.signin.service.record.SigninRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SigninFaceVerifyServiceImplTest {

    @Mock
    private FacePhotosService facePhotosService;
    @Mock
    private FileService fileService;
    @Mock
    private FaceVerifyClient faceVerifyClient;
    @Mock
    private SigninRecordService signinRecordService;

    @InjectMocks
    private SigninFaceVerifyServiceImpl signinFaceVerifyService;

    @Test
    void verify_samePerson_marksSignedIn() throws Exception {
        when(facePhotosService.getFacePhotos("ph")).thenReturn(FacePhotosDO.builder()
                .photoId("ph")
                .studentNo("p1")
                .imageUrl("http://local/file.jpg")
                .imageFormat("jpg")
                .build());
        when(fileService.getFileContentByUrl("http://local/file.jpg")).thenReturn(new byte[]{1, 2});

        LiteFaceVerifyResponse algo = new LiteFaceVerifyResponse();
        algo.setSimilarity(0.95F);
        algo.setThreshold(0.7F);
        algo.setIsSamePerson(true);
        when(faceVerifyClient.verify(any(), anyString(), any(), any(), anyString(), any())).thenReturn(algo);

        MockMultipartFile compare = new MockMultipartFile(
                "compareImage", "b.jpg", "image/jpeg", new byte[]{3, 4, 5});

        FaceVerifyAndSignRespVO resp = signinFaceVerifyService.verifyFaceAndSignIn(100L, "p1", "ph", compare);

        assertTrue(resp.getIsSamePerson());
        assertTrue(resp.getSignedIn());
        verify(signinRecordService).markSignedInByLessonAndPerson(100L, "p1");
    }

    @Test
    void verify_notSamePerson_doesNotUpdateRecord() throws Exception {
        when(facePhotosService.getFacePhotos("ph")).thenReturn(FacePhotosDO.builder()
                .photoId("ph")
                .studentNo("p1")
                .imageUrl("http://local/file.jpg")
                .imageFormat("png")
                .build());
        when(fileService.getFileContentByUrl("http://local/file.jpg")).thenReturn(new byte[]{1});

        LiteFaceVerifyResponse algo = new LiteFaceVerifyResponse();
        algo.setSimilarity(0.2F);
        algo.setThreshold(0.7F);
        algo.setIsSamePerson(false);
        when(faceVerifyClient.verify(any(), anyString(), any(), any(), anyString(), any())).thenReturn(algo);

        MockMultipartFile compare = new MockMultipartFile(
                "compareImage", "b.png", "image/png", new byte[]{9});

        FaceVerifyAndSignRespVO resp = signinFaceVerifyService.verifyFaceAndSignIn(100L, "p1", "ph", compare);

        assertFalse(resp.getIsSamePerson());
        assertFalse(resp.getSignedIn());
        verify(signinRecordService, never()).markSignedInByLessonAndPerson(any(), anyString());
    }

    @Test
    void verify_faceNotExists_throws() {
        when(facePhotosService.getFacePhotos("missing")).thenReturn(null);
        MockMultipartFile compare = new MockMultipartFile(
                "compareImage", "b.jpg", "image/jpeg", new byte[]{1});

        assertThrows(ServiceException.class,
                () -> signinFaceVerifyService.verifyFaceAndSignIn(1L, "p1", "missing", compare));
        verify(faceVerifyClient, never()).verify(any(), anyString(), any(), any(), anyString(), any());
    }

    @Test
    void verify_personMismatch_throws() throws Exception {
        when(facePhotosService.getFacePhotos("ph")).thenReturn(FacePhotosDO.builder()
                .photoId("ph")
                .studentNo("other")
                .imageUrl("http://x")
                .build());
        MockMultipartFile compare = new MockMultipartFile(
                "compareImage", "b.jpg", "image/jpeg", new byte[]{1});

        assertThrows(ServiceException.class,
                () -> signinFaceVerifyService.verifyFaceAndSignIn(1L, "p1", "ph", compare));
        verify(fileService, never()).getFileContentByUrl(anyString());
    }

}
