package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 签到系统-人脸照片特征新增/修改 Request VO")
@Data
public class FacePhotosSaveReqVO {

    @Schema(description = "照片唯一标识(可使用UUID)，不传则自动生成", example = "5574")
    private String photoId;

    @Schema(description = "关联的人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9210")
    @NotEmpty(message = "关联的人员ID不能为空")
    private String personId;

    @Schema(description = "人脸特征向量数据(通常是一组浮点数数组)")
    private String faceVector;

    @Schema(description = "人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片")
    private BigDecimal qualityScore;

    @Schema(description = "活体检测得分(0.0000 - 1.0000)，防止照片攻击")
    private BigDecimal livenessScore;

    @Schema(description = "是否为主照片(1-是, 0-否)")
    private Boolean isPrimary;

}
