package cn.iocoder.yudao.module.training.service.itemstatus;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.itemstatus.ItemStatusDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.training.dal.mysql.itemstatus.ItemStatusMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.training.enums.ErrorCodeConstants.*;

/**
 * 训练项目执行状态 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ItemStatusServiceImpl implements ItemStatusService {

    @Resource
    private ItemStatusMapper itemStatusMapper;

    @Override
    public Long createItemStatus(ItemStatusSaveReqVO createReqVO) {
        // 插入
        ItemStatusDO itemStatus = BeanUtils.toBean(createReqVO, ItemStatusDO.class);
        itemStatusMapper.insert(itemStatus);

        // 返回
        return itemStatus.getId();
    }

    @Override
    public void updateItemStatus(ItemStatusSaveReqVO updateReqVO) {
        // 校验存在
        validateItemStatusExists(updateReqVO.getId());
        // 更新
        ItemStatusDO updateObj = BeanUtils.toBean(updateReqVO, ItemStatusDO.class);
        itemStatusMapper.updateById(updateObj);
    }

    @Override
    public void deleteItemStatus(Long id) {
        // 校验存在
        validateItemStatusExists(id);
        // 删除
        itemStatusMapper.deleteById(id);
    }

    @Override
        public void deleteItemStatusListByIds(List<Long> ids) {
        // 删除
        itemStatusMapper.deleteByIds(ids);
        }


    private void validateItemStatusExists(Long id) {
        if (itemStatusMapper.selectById(id) == null) {
            throw exception(ITEM_STATUS_NOT_EXISTS);
        }
    }

    @Override
    public ItemStatusDO getItemStatus(Long id) {
        return itemStatusMapper.selectById(id);
    }

    @Override
    public PageResult<ItemStatusDO> getItemStatusPage(ItemStatusPageReqVO pageReqVO) {
        return itemStatusMapper.selectPage(pageReqVO);
    }

}