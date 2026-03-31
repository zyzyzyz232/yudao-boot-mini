package cn.iocoder.yudao.module.training.dal.mysql.itemstatus;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.training.dal.dataobject.itemstatus.ItemStatusDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo.*;

/**
 * 训练项目执行状态 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ItemStatusMapper extends BaseMapperX<ItemStatusDO> {

    default PageResult<ItemStatusDO> selectPage(ItemStatusPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ItemStatusDO>()
                .eqIfPresent(ItemStatusDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ItemStatusDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ItemStatusDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ItemStatusDO::getActualDuration, reqVO.getActualDuration())
                .eqIfPresent(ItemStatusDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ItemStatusDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemStatusDO::getId));
    }

}