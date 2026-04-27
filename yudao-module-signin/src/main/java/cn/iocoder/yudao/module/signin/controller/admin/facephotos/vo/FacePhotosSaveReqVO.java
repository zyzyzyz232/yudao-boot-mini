package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 签到系统-人脸照片特征新增/修改 Request VO")
@Data
public class FacePhotosSaveReqVO {

    @Schema(description = "照片主键 id（表 id）；不传时若传了 teachingClassStudentId 则使用其字符串形式，否则由服务生成雪花数字串（兼容 id 列为 BIGINT）", example = "5574")
    private String photoId;

    @Schema(description = "班级学员关联 ID（对应库表 class_student / teaching_class_student 等业务主键，仅用于落库关联主键 id，选传，不参与业务必填校验）", example = "1001")
    private Long teachingClassStudentId;

    @Schema(description = "学员编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9210")
    @NotEmpty(message = "学员编号不能为空")
    private String studentNo;

    @Schema(description = "班级编号（必填）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "班级编号不能为空")
    private Long classId;

    @Schema(description = "人脸特征向量数据(通常是一组浮点数数组)")
    private String faceVector;

    @Schema(description = "人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片")
    private BigDecimal qualityScore;

    @Schema(description = "活体检测得分(0.0000 - 1.0000)，防止照片攻击")
    private BigDecimal livenessScore;

    @Schema(description = "是否为主照片(1-是, 0-否)")
    private Boolean isPrimary;

    @Schema(description = "姓名")
    private String name;

}
