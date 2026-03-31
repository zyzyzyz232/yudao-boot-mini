package cn.iocoder.yudao.module.training.service.planitem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.training.controller.admin.planitem.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.planitem.PlanItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 训练项目 Service 接口
 *
 * @author 芋道源码
 */
public interface PlanItemService {

    /**
     * 创建训练项目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPlanItem(@Valid PlanItemSaveReqVO createReqVO);

    /**
     * 更新训练项目
     *
     * @param updateReqVO 更新信息
     */
    void updatePlanItem(@Valid PlanItemSaveReqVO updateReqVO);

    /**
     * 删除训练项目
     *
     * @param id 编号
     */
    void deletePlanItem(Long id);

    /**
    * 批量删除训练项目
    *
    * @param ids 编号
    */
    void deletePlanItemListByIds(List<Long> ids);

    /**
     * 获得训练项目
     *
     * @param id 编号
     * @return 训练项目
     */
    PlanItemDO getPlanItem(Long id);

    /**
     * 获得训练项目分页
     *
     * @param pageReqVO 分页查询
     * @return 训练项目分页
     */
    PageResult<PlanItemDO> getPlanItemPage(PlanItemPageReqVO pageReqVO);

}