package cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 课堂训练计划分页 Request VO")
@Data
public class TrainingPlanPageReqVO extends PageParam {

    @Schema(description = "所属课堂编号", example = "2960")
    private Long classId;

    @Schema(description = "计划标题")
    private String title;

    @Schema(description = "训练类型(如：步法、力量、多球、战术等)", example = "2")
    private String trainingType;

    @Schema(description = "详细训练内容与指标要求")
    private String content;

    @Schema(description = "预计训练时长(分钟)")
    private Integer duration;

    @Schema(description = "执行状态(0:未开始 1:进行中 2:已完成)", example = "1")
    private Integer status;

    @Schema(description = "排序(用于控制多个计划的执行顺序)")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}