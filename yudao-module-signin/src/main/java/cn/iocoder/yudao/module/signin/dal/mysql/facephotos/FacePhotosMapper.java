package cn.iocoder.yudao.module.signin.dal.mysql.facephotos;

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
                .eqIfPresent(FacePhotosDO::getStudentNo, reqVO.getStudentNo())
                .eqIfPresent(FacePhotosDO::getClassId, reqVO.getClassId())
                .eqIfPresent(FacePhotosDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(FacePhotosDO::getImageFormat, reqVO.getImageFormat())
                .eqIfPresent(FacePhotosDO::getImageSizeKb, reqVO.getImageSizeKb())
                .eqIfPresent(FacePhotosDO::getFaceVector, reqVO.getFaceVector())
                .eqIfPresent(FacePhotosDO::getQualityScore, reqVO.getQualityScore())
                .eqIfPresent(FacePhotosDO::getLivenessScore, reqVO.getLivenessScore())
                .eqIfPresent(FacePhotosDO::getIsPrimary, reqVO.getIsPrimary())
                .eqIfPresent(FacePhotosDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(FacePhotosDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FacePhotosDO::getPhotoId));
    }

    /**
     * 按学员编号选取一条用于更新：优先主图，否则按更新时间、主键 id 取最新一条。
     * <p>
     * 单次 {@code ORDER BY}，避免 {@code eq(isPrimary, true)} 在部分 JDBC/列类型下与库中主图标记比较不一致，导致两段查询均查不到。
     */
    default FacePhotosDO selectForUpdateByStudentNo(String studentNo) {
        return selectOne(new LambdaQueryWrapperX<FacePhotosDO>()
                .eq(FacePhotosDO::getStudentNo, studentNo)
                .last("ORDER BY (is_primary = 1) DESC, update_time DESC, id DESC LIMIT 1"));
    }

    /**
     * 按班级 + 学员编号选取一条用于更新：优先主图，否则按更新时间、主键 id 取最新一条。
     */
    default FacePhotosDO selectForUpdateByStudentNoAndClassId(Long classId, String studentNo) {
        return selectOne(new LambdaQueryWrapperX<FacePhotosDO>()
                .eq(FacePhotosDO::getStudentNo, studentNo)
                .eq(FacePhotosDO::getClassId, classId)
                .last("ORDER BY (is_primary = 1) DESC, update_time DESC, id DESC LIMIT 1"));
    }

}
