package cn.iocoder.yudao.module.training.dal.mysql.planitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.training.dal.dataobject.planitem.PlanItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.training.controller.admin.planitem.vo.*;

/**
 * 训练项目 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PlanItemMapper extends BaseMapperX<PlanItemDO> {

    default PageResult<PlanItemDO> selectPage(PlanItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PlanItemDO>()
                .eqIfPresent(PlanItemDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(PlanItemDO::getTitle, reqVO.getTitle())
                .eqIfPresent(PlanItemDO::getInstruction, reqVO.getInstruction())
                .eqIfPresent(PlanItemDO::getTips, reqVO.getTips())
                .eqIfPresent(PlanItemDO::getDuration, reqVO.getDuration())
                .eqIfPresent(PlanItemDO::getVideoUrl, reqVO.getVideoUrl())
                .eqIfPresent(PlanItemDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(PlanItemDO::getSort, reqVO.getSort())
                .betweenIfPresent(PlanItemDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanItemDO::getId));
    }

}