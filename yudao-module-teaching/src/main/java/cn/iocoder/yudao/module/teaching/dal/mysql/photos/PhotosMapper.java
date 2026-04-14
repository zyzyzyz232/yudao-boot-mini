package cn.iocoder.yudao.module.teaching.dal.mysql.photos;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.teaching.dal.dataobject.photos.PhotosDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.teaching.controller.admin.photos.vo.*;

/**
 * 人脸照片及特征数据 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PhotosMapper extends BaseMapperX<PhotosDO> {

    default PageResult<PhotosDO> selectPage(PhotosPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PhotosDO>()
                .eqIfPresent(PhotosDO::getPersonId, reqVO.getPersonId())
                .eqIfPresent(PhotosDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(PhotosDO::getImageFormat, reqVO.getImageFormat())
                .eqIfPresent(PhotosDO::getImageSizeKb, reqVO.getImageSizeKb())
                .eqIfPresent(PhotosDO::getFaceVector, reqVO.getFaceVector())
                .eqIfPresent(PhotosDO::getQualityScore, reqVO.getQualityScore())
                .eqIfPresent(PhotosDO::getLivenessScore, reqVO.getLivenessScore())
                .eqIfPresent(PhotosDO::getIsPrimary, reqVO.getIsPrimary())
                .eqIfPresent(PhotosDO::getCaptureSource, reqVO.getCaptureSource())
                .eqIfPresent(PhotosDO::getCreatedAt, reqVO.getCreatedAt())
                .orderByDesc(PhotosDO::getPhotoId));
    }

}