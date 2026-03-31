package cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 课堂训练计划新增/修改 Request VO")
@Data
public class TrainingPlanSaveReqVO {

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30346")
    private Long id;

    @Schema(description = "所属课堂编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2960")
    @NotNull(message = "所属课堂编号不能为空")
    private Long classId;

    @Schema(description = "计划标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计划标题不能为空")
    private String title;

    @Schema(description = "训练类型(如：步法、力量、多球、战术等)", example = "2")
    private String trainingType;

    @Schema(description = "详细训练内容与指标要求")
    private String content;

    @Schema(description = "预计训练时长(分钟)")
    private Integer duration;

    @Schema(description = "执行状态(0:未开始 1:进行中 2:已完成)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "执行状态(0:未开始 1:进行中 2:已完成)不能为空")
    private Integer status;

    @Schema(description = "排序(用于控制多个计划的执行顺序)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序(用于控制多个计划的执行顺序)不能为空")
    private Integer sort;

}