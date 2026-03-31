package cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 训练项目执行状态 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ItemStatusRespVO {

    @Schema(description = "状态记录编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2980")
    @ExcelProperty("状态记录编号")
    private Long id;

    @Schema(description = "所属训练项目编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11590")
    @ExcelProperty("所属训练项目编号")
    private Long itemId;

    @Schema(description = "训练用户编号", example = "19389")
    @ExcelProperty("训练用户编号")
    private Long userId;

    @Schema(description = "训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)")
    private Integer status;

    @Schema(description = "实际训练时长(秒)")
    @ExcelProperty("实际训练时长(秒)")
    private Integer actualDuration;

    @Schema(description = "教练或学员备注(如: 动作标准、体力不支等)", example = "你猜")
    @ExcelProperty("教练或学员备注(如: 动作标准、体力不支等)")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}