package cn.iocoder.yudao.module.signin.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 签到记录新增/修改 Request VO")
@Data
public class SigninRecordSaveReqVO {

    @Schema(description = "签到记录编号", example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "签到日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "签到日期不能为空")
    private LocalDate signinDate;

    @Schema(description = "签到类型（0：正常，1：补签，2：代签）", example = "0")
    private Integer signinType;

    @Schema(description = "备注")
    private String remark;

}
