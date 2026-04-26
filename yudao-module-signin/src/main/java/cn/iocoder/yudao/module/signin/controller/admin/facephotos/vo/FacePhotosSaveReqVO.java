package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 签到系统-人脸照片特征新增/修改 Request VO")
@Data
public class FacePhotosSaveReqVO {

    @Schema(description = "照片主键 id；不传时若传了 teachingClassStudentId 则使用其字符串形式，否则自动生成 UUID。与 teaching_class_student.id 绑定时应与此一致", example = "5574")
    private String photoId;

    @Schema(description = "教学班级学员主键，与表 teaching_class_student.id 一致；传则作为 photoId/表 id 使用（与 photoId 同时传时以此为准）", example = "1001")
    private Long teachingClassStudentId;

    @Schema(description = "学员编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9210")
    @NotEmpty(message = "学员编号不能为空")
    private String studentNo;

    @Schema(description = "班级编号", example = "100")
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
