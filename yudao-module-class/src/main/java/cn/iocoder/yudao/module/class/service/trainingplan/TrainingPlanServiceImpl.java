package cn.iocoder.yudao.module.class.service.trainingplan;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo.*;
import cn.iocoder.yudao.module.class.dal.dataobject.trainingplan.TrainingPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.class.dal.mysql.trainingplan.TrainingPlanMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.class.enums.ErrorCodeConstants.*;

/**
 * 课堂训练计划 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrainingPlanServiceImpl implements TrainingPlanService {

    @Resource
    private TrainingPlanMapper trainingPlanMapper;

    @Override
    public Long createTrainingPlan(TrainingPlanSaveReqVO createReqVO) {
        // 插入
        TrainingPlanDO trainingPlan = BeanUtils.toBean(createReqVO, TrainingPlanDO.class);
        trainingPlanMapper.insert(trainingPlan);

        // 返回
        return trainingPlan.getId();
    }

    @Override
    public void updateTrainingPlan(TrainingPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateTrainingPlanExists(updateReqVO.getId());
        // 更新
        TrainingPlanDO updateObj = BeanUtils.toBean(updateReqVO, TrainingPlanDO.class);
        trainingPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteTrainingPlan(Long id) {
        // 校验存在
        validateTrainingPlanExists(id);
        // 删除
        trainingPlanMapper.deleteById(id);
    }

    @Override
        public void deleteTrainingPlanListByIds(List<Long> ids) {
        // 删除
        trainingPlanMapper.deleteByIds(ids);
        }


    private void validateTrainingPlanExists(Long id) {
        if (trainingPlanMapper.selectById(id) == null) {
            throw exception(TRAINING_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public TrainingPlanDO getTrainingPlan(Long id) {
        return trainingPlanMapper.selectById(id);
    }

    @Override
    public PageResult<TrainingPlanDO> getTrainingPlanPage(TrainingPlanPageReqVO pageReqVO) {
        return trainingPlanMapper.selectPage(pageReqVO);
    }

}