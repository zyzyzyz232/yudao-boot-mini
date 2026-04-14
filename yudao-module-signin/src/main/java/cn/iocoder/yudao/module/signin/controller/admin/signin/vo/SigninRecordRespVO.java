package cn.iocoder.yudao.module.signin.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 签到记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SigninRecordRespVO {

    @Schema(description = "签到记录编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("签到记录编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "签到日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("签到日期")
    private LocalDate signinDate;

    @Schema(description = "签到类型（0：正常，1：补签，2：代签）", example = "0")
    @ExcelProperty("签到类型")
    private Integer signinType;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
