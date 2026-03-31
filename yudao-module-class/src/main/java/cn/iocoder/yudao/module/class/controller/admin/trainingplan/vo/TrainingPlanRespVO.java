package cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 课堂训练计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TrainingPlanRespVO {

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30346")
    @ExcelProperty("计划编号")
    private Long id;

    @Schema(description = "所属课堂编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2960")
    @ExcelProperty("所属课堂编号")
    private Long classId;

    @Schema(description = "计划标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计划标题")
    private String title;

    @Schema(description = "训练类型(如：步法、力量、多球、战术等)", example = "2")
    @ExcelProperty("训练类型(如：步法、力量、多球、战术等)")
    private String trainingType;

    @Schema(description = "详细训练内容与指标要求")
    @ExcelProperty("详细训练内容与指标要求")
    private String content;

    @Schema(description = "预计训练时长(分钟)")
    @ExcelProperty("预计训练时长(分钟)")
    private Integer duration;

    @Schema(description = "执行状态(0:未开始 1:进行中 2:已完成)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("执行状态(0:未开始 1:进行中 2:已完成)")
    private Integer status;

    @Schema(description = "排序(用于控制多个计划的执行顺序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序(用于控制多个计划的执行顺序)")
    private Integer sort;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}