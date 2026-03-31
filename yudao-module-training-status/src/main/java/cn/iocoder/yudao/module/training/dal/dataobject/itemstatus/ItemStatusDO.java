package cn.iocoder.yudao.module.training.dal.dataobject.itemstatus;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 训练项目执行状态 DO
 *
 * @author 芋道源码
 */
@TableName("training_item_status")
@KeySequence("training_item_status_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemStatusDO extends BaseDO {

    /**
     * 状态记录编号
     */
    @TableId
    private Long id;
    /**
     * 所属训练项目编号
     */
    private Long itemId;
    /**
     * 训练用户编号
     */
    private Long userId;
    /**
     * 训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)
     */
    private Integer status;
    /**
     * 实际训练时长(秒)
     */
    private Integer actualDuration;
    /**
     * 教练或学员备注(如: 动作标准、体力不支等)
     */
    private String remark;


}