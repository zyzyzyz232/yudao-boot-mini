package cn.iocoder.yudao.module.class.dal.mysql.trainingplan;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.class.dal.dataobject.trainingplan.TrainingPlanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo.*;

/**
 * 课堂训练计划 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TrainingPlanMapper extends BaseMapperX<TrainingPlanDO> {

    default PageResult<TrainingPlanDO> selectPage(TrainingPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrainingPlanDO>()
                .eqIfPresent(TrainingPlanDO::getClassId, reqVO.getClassId())
                .eqIfPresent(TrainingPlanDO::getTitle, reqVO.getTitle())
                .eqIfPresent(TrainingPlanDO::getTrainingType, reqVO.getTrainingType())
                .eqIfPresent(TrainingPlanDO::getContent, reqVO.getContent())
                .eqIfPresent(TrainingPlanDO::getDuration, reqVO.getDuration())
                .eqIfPresent(TrainingPlanDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrainingPlanDO::getSort, reqVO.getSort())
                .betweenIfPresent(TrainingPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrainingPlanDO::getId));
    }

}