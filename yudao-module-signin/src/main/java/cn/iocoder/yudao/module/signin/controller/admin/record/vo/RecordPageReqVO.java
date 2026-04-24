package cn.iocoder.yudao.module.signin.controller.admin.record.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到记录分页 Request VO")
@Data
public class RecordPageReqVO extends PageParam {

    @Schema(description = "课堂编号", example = "4049")
    private Long lessonId;

    @Schema(description = "学员编号", example = "abc123")
    private String personId;

    @Schema(description = "签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）", example = "1")
    private Integer status;

    @Schema(description = "实际签到时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}