package cn.iocoder.yudao.module.training.service.planitem;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.training.controller.admin.planitem.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.planitem.PlanItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.training.dal.mysql.planitem.PlanItemMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.training.enums.ErrorCodeConstants.*;

/**
 * 训练项目 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PlanItemServiceImpl implements PlanItemService {

    @Resource
    private PlanItemMapper planItemMapper;

    @Override
    public Long createPlanItem(PlanItemSaveReqVO createReqVO) {
        // 插入
        PlanItemDO planItem = BeanUtils.toBean(createReqVO, PlanItemDO.class);
        planItemMapper.insert(planItem);

        // 返回
        return planItem.getId();
    }

    @Override
    public void updatePlanItem(PlanItemSaveReqVO updateReqVO) {
        // 校验存在
        validatePlanItemExists(updateReqVO.getId());
        // 更新
        PlanItemDO updateObj = BeanUtils.toBean(updateReqVO, PlanItemDO.class);
        planItemMapper.updateById(updateObj);
    }

    @Override
    public void deletePlanItem(Long id) {
        // 校验存在
        validatePlanItemExists(id);
        // 删除
        planItemMapper.deleteById(id);
    }

    @Override
        public void deletePlanItemListByIds(List<Long> ids) {
        // 删除
        planItemMapper.deleteByIds(ids);
        }


    private void validatePlanItemExists(Long id) {
        if (planItemMapper.selectById(id) == null) {
            throw exception(PLAN_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public PlanItemDO getPlanItem(Long id) {
        return planItemMapper.selectById(id);
    }

    @Override
    public PageResult<PlanItemDO> getPlanItemPage(PlanItemPageReqVO pageReqVO) {
        return planItemMapper.selectPage(pageReqVO);
    }

}