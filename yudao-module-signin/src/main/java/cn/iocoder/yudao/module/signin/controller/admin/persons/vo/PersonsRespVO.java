package cn.iocoder.yudao.module.signin.controller.admin.persons.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 签到系统-人员基本信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PersonsRespVO {

    @Schema(description = "人员唯一标识(可使用UUID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5883")
    @ExcelProperty("人员唯一标识(可使用UUID)")
    private String personId;

    @Schema(description = "人员姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("人员姓名")
    private String name;

    @Schema(description = "所属部门/组织")
    @ExcelProperty("所属部门/组织")
    private String department;

    @Schema(description = "状态：1-正常, 0-禁用", example = "1")
    @ExcelProperty("状态：1-正常, 0-禁用")
    private Boolean status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}