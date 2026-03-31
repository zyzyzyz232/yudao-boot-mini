package cn.iocoder.yudao.module.teaching.dal.mysql.video;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.teaching.dal.dataobject.video.VideoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.teaching.controller.admin.video.vo.*;

/**
 * 教学视频 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface VideoMapper extends BaseMapperX<VideoDO> {

    default PageResult<VideoDO> selectPage(VideoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VideoDO>()
                .eqIfPresent(VideoDO::getTitle, reqVO.getTitle())
                .eqIfPresent(VideoDO::getDescription, reqVO.getDescription())
                .eqIfPresent(VideoDO::getVideoUrl, reqVO.getVideoUrl())
                .eqIfPresent(VideoDO::getCoverUrl, reqVO.getCoverUrl())
                .eqIfPresent(VideoDO::getDuration, reqVO.getDuration())
                .eqIfPresent(VideoDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(VideoDO::getSort, reqVO.getSort())
                .eqIfPresent(VideoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(VideoDO::getViewCount, reqVO.getViewCount())
                .betweenIfPresent(VideoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VideoDO::getId));
    }

}