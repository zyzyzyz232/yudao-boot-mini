package cn.iocoder.yudao.module.signin.controller.admin.record.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 人脸比对签到 Response VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceVerifyAndSignRespVO {

    @Schema(description = "相似度 [0..1]")
    private Float similarity;

    @Schema(description = "阈值")
    private Float threshold;

    @Schema(description = "是否判定为同人")
    private Boolean isSamePerson;

    @Schema(description = "是否已写入签到（已签到 status=1）")
    private Boolean signedIn;

}
