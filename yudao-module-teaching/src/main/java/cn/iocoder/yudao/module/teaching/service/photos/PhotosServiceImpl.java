package cn.iocoder.yudao.module.teaching.service.photos;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.teaching.controller.admin.photos.vo.*;
import cn.iocoder.yudao.module.teaching.dal.dataobject.photos.PhotosDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.teaching.dal.mysql.photos.PhotosMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.teaching.enums.ErrorCodeConstants.*;

/**
 * 人脸照片及特征数据 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PhotosServiceImpl implements PhotosService {

    @Resource
    private PhotosMapper photosMapper;

    @Override
    public String createPhotos(PhotosSaveReqVO createReqVO) {
        // 插入
        PhotosDO photos = BeanUtils.toBean(createReqVO, PhotosDO.class);
        photosMapper.insert(photos);

        // 返回
        return photos.getPhotoId();
    }

    @Override
    public void updatePhotos(PhotosSaveReqVO updateReqVO) {
        // 校验存在
        validatePhotosExists(updateReqVO.getPhotoId());
        // 更新
        PhotosDO updateObj = BeanUtils.toBean(updateReqVO, PhotosDO.class);
        photosMapper.updateById(updateObj);
    }

    @Override
    public void deletePhotos(String id) {
        // 校验存在
        validatePhotosExists(id);
        // 删除
        photosMapper.deleteById(id);
    }

    @Override
        public void deletePhotosListByIds(List<String> ids) {
        // 删除
        photosMapper.deleteByIds(ids);
        }


    private void validatePhotosExists(String id) {
        if (photosMapper.selectById(id) == null) {
            throw exception(PHOTOS_NOT_EXISTS);
        }
    }

    @Override
    public PhotosDO getPhotos(String id) {
        return photosMapper.selectById(id);
    }

    @Override
    public PageResult<PhotosDO> getPhotosPage(PhotosPageReqVO pageReqVO) {
        return photosMapper.selectPage(pageReqVO);
    }

}