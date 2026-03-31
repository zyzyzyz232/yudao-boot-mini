package cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 训练项目执行状态分页 Request VO")
@Data
public class ItemStatusPageReqVO extends PageParam {

    @Schema(description = "所属训练项目编号", example = "11590")
    private Long itemId;

    @Schema(description = "训练用户编号", example = "19389")
    private Long userId;

    @Schema(description = "训练状态(0:未开始 1:进行中 2:已完成 3:已放弃)", example = "2")
    private Integer status;

    @Schema(description = "实际训练时长(秒)")
    private Integer actualDuration;

    @Schema(description = "教练或学员备注(如: 动作标准、体力不支等)", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}