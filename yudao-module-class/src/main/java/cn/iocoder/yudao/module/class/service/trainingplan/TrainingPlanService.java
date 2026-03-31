package cn.iocoder.yudao.module.class.service.trainingplan;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo.*;
import cn.iocoder.yudao.module.class.dal.dataobject.trainingplan.TrainingPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 课堂训练计划 Service 接口
 *
 * @author 芋道源码
 */
public interface TrainingPlanService {

    /**
     * 创建课堂训练计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTrainingPlan(@Valid TrainingPlanSaveReqVO createReqVO);

    /**
     * 更新课堂训练计划
     *
     * @param updateReqVO 更新信息
     */
    void updateTrainingPlan(@Valid TrainingPlanSaveReqVO updateReqVO);

    /**
     * 删除课堂训练计划
     *
     * @param id 编号
     */
    void deleteTrainingPlan(Long id);

    /**
    * 批量删除课堂训练计划
    *
    * @param ids 编号
    */
    void deleteTrainingPlanListByIds(List<Long> ids);

    /**
     * 获得课堂训练计划
     *
     * @param id 编号
     * @return 课堂训练计划
     */
    TrainingPlanDO getTrainingPlan(Long id);

    /**
     * 获得课堂训练计划分页
     *
     * @param pageReqVO 分页查询
     * @return 课堂训练计划分页
     */
    PageResult<TrainingPlanDO> getTrainingPlanPage(TrainingPlanPageReqVO pageReqVO);

}