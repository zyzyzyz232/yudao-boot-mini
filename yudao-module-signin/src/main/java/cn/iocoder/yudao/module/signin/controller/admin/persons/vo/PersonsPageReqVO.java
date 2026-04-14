package cn.iocoder.yudao.module.signin.controller.admin.persons.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到系统-人员基本信息分页 Request VO")
@Data
public class PersonsPageReqVO extends PageParam {

    @Schema(description = "人员姓名", example = "李四")
    private String name;

    @Schema(description = "所属部门/组织")
    private String department;

    @Schema(description = "状态：1-正常, 0-禁用", example = "1")
    private Boolean status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}