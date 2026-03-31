package cn.iocoder.yudao.module.training.controller.admin.planitem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 训练项目分页 Request VO")
@Data
public class PlanItemPageReqVO extends PageParam {

    @Schema(description = "所属训练计划编号", example = "22839")
    private Long planId;

    @Schema(description = "项目名称(如: 正手挑球练习)")
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

    @Schema(description = "执行顺序(数值越小越先执行)")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}