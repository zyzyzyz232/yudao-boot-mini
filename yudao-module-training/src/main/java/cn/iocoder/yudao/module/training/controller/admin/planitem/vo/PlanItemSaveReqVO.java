package cn.iocoder.yudao.module.training.controller.admin.planitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 训练项目新增/修改 Request VO")
@Data
public class PlanItemSaveReqVO {

    @Schema(description = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15608")
    private Long id;

    @Schema(description = "所属训练计划编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22839")
    @NotNull(message = "所属训练计划编号不能为空")
    private Long planId;

    @Schema(description = "项目名称(如: 正手挑球练习)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "项目名称(如: 正手挑球练习)不能为空")
    private String title;

    @Schema(description = "说明文字(项目具体练什么、达标要求)")
    private String instruction;

    @Schema(description = "提示文字(如: 发力技巧、易错点提醒)")
    private String tips;

    @Schema(description = "项目时长(秒)")
    private Integer duration;

    @Schema(description = "视频访问链接(示范视频)", example = "https://www.iocoder.cn")
    private String videoUrl;

    @Schema(description = "图片访问链接(动作拆解图或战术图)", example = "https://www.iocoder.cn")
    private String imageUrl;

    @Schema(description = "执行顺序(数值越小越先执行)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "执行顺序(数值越小越先执行)不能为空")
    private Integer sort;

}