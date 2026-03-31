package cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 训练项目执行状态新增/修改 Request VO")
@Data
public class ItemStatusSaveReqVO {

    @Schema(description = "状态记录编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2980")
    private Long id;

    @Schema(description = "所属训练项目编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11590")
    @NotNull(message = "所属训练项目编号不能为空")
    private Long itemId;

    @Schema(description = "训练用户编号", example = "19389")
    private Long userId;

    @Schema(description = "训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)不能为空")
    private Integer status;

    @Schema(description = "实际训练时长(秒)")
    private Integer actualDuration;

    @Schema(description = "教练或学员备注(如: 动作标准、体力不支等)", example = "你猜")
    private String remark;

}