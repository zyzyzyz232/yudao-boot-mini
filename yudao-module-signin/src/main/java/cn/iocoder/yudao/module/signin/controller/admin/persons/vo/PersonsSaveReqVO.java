package cn.iocoder.yudao.module.signin.controller.admin.persons.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 签到系统-人员基本信息新增/修改 Request VO")
@Data
public class PersonsSaveReqVO {

    @Schema(description = "人员唯一标识(可使用UUID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5883")
    private String personId;

    @Schema(description = "人员姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "人员姓名不能为空")
    private String name;

    @Schema(description = "所属部门/组织")
    private String department;

    @Schema(description = "状态：1-正常, 0-禁用", example = "1")
    private Boolean status;

}