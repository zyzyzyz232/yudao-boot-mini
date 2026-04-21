package cn.iocoder.yudao.module.signin.service.facephotos;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosPageReqVO;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.dal.mysql.facephotos.FacePhotosMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_PHOTOS_NOT_EXISTS;

/**
 * 签到系统-人脸照片特征 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class FacePhotosServiceImpl implements FacePhotosService {

    @Resource
    private FacePhotosMapper facePhotosMapper;

    @Override
    public String createFacePhotos(@Valid FacePhotosSaveReqVO createReqVO,
                                   String imageUrl, String fileName, int sizeKb) {
        // 若未传 photoId，自动生成
        if (StrUtil.isBlank(createReqVO.getPhotoId())) {
            createReqVO.setPhotoId(IdUtil.fastSimpleUUID());
        }

        // 组装 DO，imageUrl 由 Controller 通过 FileService 上传后传入
        FacePhotosDO facePhotos = BeanUtils.toBean(createReqVO, FacePhotosDO.class);
        facePhotos.setImageUrl(imageUrl);
        facePhotos.setImageFormat(FileUtil.extName(fileName));
        facePhotos.setImageSizeKb(sizeKb);
        facePhotosMapper.insert(facePhotos);

        return facePhotos.getPhotoId();
    }

    @Override
    public void updateFacePhotos(@Valid FacePhotosSaveReqVO updateReqVO,
                                 String imageUrl, String fileName, Integer sizeKb) {
        validateFacePhotosExists(updateReqVO.getPhotoId());
        FacePhotosDO updateObj = BeanUtils.toBean(updateReqVO, FacePhotosDO.class);
        // 若传了新文件，更新照片相关字段
        if (imageUrl != null) {
            updateObj.setImageUrl(imageUrl);
            updateObj.setImageFormat(FileUtil.extName(fileName));
            updateObj.setImageSizeKb(sizeKb);
        }
        facePhotosMapper.updateById(updateObj);
    }

    @Override
    public void deleteFacePhotos(String id) {
        validateFacePhotosExists(id);
        facePhotosMapper.deleteById(id);
    }

    @Override
    public void deleteFacePhotosListByIds(List<String> ids) {
        facePhotosMapper.deleteByIds(ids);
    }

    private void validateFacePhotosExists(String id) {
        if (facePhotosMapper.selectById(id) == null) {
            throw exception(FACE_PHOTOS_NOT_EXISTS);
        }
    }

    @Override
    public FacePhotosDO getFacePhotos(String id) {
        return facePhotosMapper.selectById(id);
    }

    @Override
    public PageResult<FacePhotosDO> getFacePhotosPage(FacePhotosPageReqVO pageReqVO) {
        return facePhotosMapper.selectPage(pageReqVO);
    }

}
