package cn.iocoder.yudao.module.signin.service.facephotos;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.signin.dal.mysql.facephotos.FacePhotosMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.*;

/**
 * 签到系统-人脸照片特征 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FacePhotosServiceImpl implements FacePhotosService {

    @Resource
    private FacePhotosMapper facePhotosMapper;

    @Override
    public String createFacePhotos(FacePhotosSaveReqVO createReqVO) {
        // 插入
        FacePhotosDO facePhotos = BeanUtils.toBean(createReqVO, FacePhotosDO.class);
        facePhotosMapper.insert(facePhotos);

        // 返回
        return facePhotos.getId();
    }

    @Override
    public void updateFacePhotos(FacePhotosSaveReqVO updateReqVO) {
        // 校验存在
        validateFacePhotosExists(updateReqVO.getId());
        // 更新
        FacePhotosDO updateObj = BeanUtils.toBean(updateReqVO, FacePhotosDO.class);
        facePhotosMapper.updateById(updateObj);
    }

    @Override
    public void deleteFacePhotos(String id) {
        // 校验存在
        validateFacePhotosExists(id);
        // 删除
        facePhotosMapper.deleteById(id);
    }

    @Override
        public void deleteFacePhotosListByIds(List<String> ids) {
        // 删除
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