package cn.iocoder.yudao.module.signin.dal.mysql.facephotos;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.*;

/**
 * 签到系统-人脸照片特征 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FacePhotosMapper extends BaseMapperX<FacePhotosDO> {

    default PageResult<FacePhotosDO> selectPage(FacePhotosPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FacePhotosDO>()
                .eqIfPresent(FacePhotosDO::getPersonId, reqVO.getPersonId())
                .eqIfPresent(FacePhotosDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(FacePhotosDO::getImageFormat, reqVO.getImageFormat())
                .eqIfPresent(FacePhotosDO::getImageSizeKb, reqVO.getImageSizeKb())
                .eqIfPresent(FacePhotosDO::getFaceVector, reqVO.getFaceVector())
                .eqIfPresent(FacePhotosDO::getQualityScore, reqVO.getQualityScore())
                .eqIfPresent(FacePhotosDO::getLivenessScore, reqVO.getLivenessScore())
                .eqIfPresent(FacePhotosDO::getIsPrimary, reqVO.getIsPrimary())
                .betweenIfPresent(FacePhotosDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FacePhotosDO::getPhotoId));
    }

}