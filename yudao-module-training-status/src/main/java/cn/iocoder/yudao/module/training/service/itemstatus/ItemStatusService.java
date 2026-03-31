package cn.iocoder.yudao.module.training.service.itemstatus;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.itemstatus.ItemStatusDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 训练项目执行状态 Service 接口
 *
 * @author 芋道源码
 */
public interface ItemStatusService {

    /**
     * 创建训练项目执行状态
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createItemStatus(@Valid ItemStatusSaveReqVO createReqVO);

    /**
     * 更新训练项目执行状态
     *
     * @param updateReqVO 更新信息
     */
    void updateItemStatus(@Valid ItemStatusSaveReqVO updateReqVO);

    /**
     * 删除训练项目执行状态
     *
     * @param id 编号
     */
    void deleteItemStatus(Long id);

    /**
    * 批量删除训练项目执行状态
    *
    * @param ids 编号
    */
    void deleteItemStatusListByIds(List<Long> ids);

    /**
     * 获得训练项目执行状态
     *
     * @param id 编号
     * @return 训练项目执行状态
     */
    ItemStatusDO getItemStatus(Long id);

    /**
     * 获得训练项目执行状态分页
     *
     * @param pageReqVO 分页查询
     * @return 训练项目执行状态分页
     */
    PageResult<ItemStatusDO> getItemStatusPage(ItemStatusPageReqVO pageReqVO);

}