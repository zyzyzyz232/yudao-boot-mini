package cn.iocoder.yudao.module.signin.controller.admin.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 签到记录新增/修改 Request VO")
@Data
public class RecordSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11459")
    private Long id;

    @Schema(description = "课堂编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4049")
    @NotNull(message = "课堂编号不能为空")
    private Long lessonId;

    @Schema(description = "学员编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "abc123")
    @NotEmpty(message = "学员编号不能为空")
    private String personId;

    @Schema(description = "签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）不能为空")
    private Integer status;

    @Schema(description = "实际签到时间")
    private LocalDateTime signTime;

}