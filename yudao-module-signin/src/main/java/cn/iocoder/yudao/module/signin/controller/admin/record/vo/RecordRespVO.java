package cn.iocoder.yudao.module.signin.controller.admin.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 签到记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11459")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "课堂编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4049")
    @ExcelProperty("课堂编号")
    private Long lessonId;

    @Schema(description = "学员编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "abc123")
    @ExcelProperty("学员编号")
    private String personId;

    @Schema(description = "签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）")
    private Integer status;

    @Schema(description = "实际签到时间")
    @ExcelProperty("实际签到时间")
    private LocalDateTime signTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
