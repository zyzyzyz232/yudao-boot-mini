package cn.iocoder.yudao.module.teaching.controller.admin.photos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 人脸照片及特征数据新增/修改 Request VO")
@Data
public class PhotosSaveReqVO {

    @Schema(description = "照片唯一标识(可使用UUID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "32749")
    private String photoId;

    @Schema(description = "关联的人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5639")
    @NotEmpty(message = "关联的人员ID不能为空")
    private String personId;

    @Schema(description = "照片在对象存储(OSS/S3)中的访问路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "照片在对象存储(OSS/S3)中的访问路径不能为空")
    private String imageUrl;

    @Schema(description = "图片格式(jpg, png等)")
    private String imageFormat;

    @Schema(description = "图片大小(KB)")
    private Integer imageSizeKb;

    @Schema(description = "人脸特征向量数据(通常是一组浮点数数组)，PostgreSQL推荐用 vector 类型")
    private String faceVector;

    @Schema(description = "人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片")
    private BigDecimal qualityScore;

    @Schema(description = "活体检测得分(0.0000 - 1.0000)，防止照片攻击")
    private BigDecimal livenessScore;

    @Schema(description = "是否为该人员的主参考底库照片：1-是，0-否")
    private Boolean isPrimary;

    @Schema(description = "照片来源：manual_upload, camera_device, api_sync等")
    private String captureSource;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

}