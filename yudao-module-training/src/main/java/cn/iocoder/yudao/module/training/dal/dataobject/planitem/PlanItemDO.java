package cn.iocoder.yudao.module.training.dal.dataobject.planitem;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 训练项目 DO
 *
 * @author 芋道源码
 */
@TableName("training_plan_item")
@KeySequence("training_plan_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanItemDO extends BaseDO {

    /**
     * 项目编号
     */
    @TableId
    private Long id;
    /**
     * 所属训练计划编号
     */
    private Long planId;
    /**
     * 项目名称(如: 正手挑球练习)
     */
    private String title;
    /**
     * 说明文字(项目具体练什么、达标要求)
     */
    private String instruction;
    /**
     * 提示文字(如: 发力技巧、易错点提醒)
     */
    private String tips;
    /**
     * 项目时长(秒)
     */
    private Integer duration;
    /**
     * 视频访问链接(示范视频)
     */
    private String videoUrl;
    /**
     * 图片访问链接(动作拆解图或战术图)
     */
    private String imageUrl;
    /**
     * 执行顺序(数值越小越先执行)
     */
    private Integer sort;


}