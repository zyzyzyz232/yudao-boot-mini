package cn.iocoder.yudao.module.signin.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到记录分页 Request VO")
@Data
public class SigninRecordPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "1")
    private Long userId;

    @Schema(description = "签到日期")
    private LocalDate signinDate;

    @Schema(description = "签到类型（0：正常，1：补签，2：代签）", example = "0")
    private Integer signinType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
