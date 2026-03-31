package cn.iocoder.yudao.module.class.dal.dataobject.trainingplan;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 课堂训练计划 DO
 *
 * @author 芋道源码
 */
@TableName("class_training_plan")
@KeySequence("class_training_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanDO extends BaseDO {

    /**
     * 计划编号
     */
    @TableId
    private Long id;
    /**
     * 所属课堂编号
     */
    private Long classId;
    /**
     * 计划标题
     */
    private String title;
    /**
     * 训练类型(如：步法、力量、多球、战术等)
     */
    private String trainingType;
    /**
     * 详细训练内容与指标要求
     */
    private String content;
    /**
     * 预计训练时长(分钟)
     */
    private Integer duration;
    /**
     * 执行状态(0:未开始 1:进行中 2:已完成)
     */
    private Integer status;
    /**
     * 排序(用于控制多个计划的执行顺序)
     */
    private Integer sort;


}