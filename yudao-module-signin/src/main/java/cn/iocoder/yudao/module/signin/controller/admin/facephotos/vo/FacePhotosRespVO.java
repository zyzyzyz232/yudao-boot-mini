package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 签到系统-人脸照片特征 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FacePhotosRespVO {

    @Schema(description = "照片唯一标识(可使用UUID)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5574")
    @ExcelProperty("照片唯一标识(可使用UUID)")
    private String photoId;

    @Schema(description = "关联的人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9210")
    @ExcelProperty("关联的人员ID")
    private String personId;

    @Schema(description = "照片在对象存储(OSS/S3)中的访问路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("照片在对象存储(OSS/S3)中的访问路径")
    private String imageUrl;

    @Schema(description = "图片格式(jpg, png等)")
    @ExcelProperty("图片格式(jpg, png等)")
    private String imageFormat;

    @Schema(description = "图片大小(KB)")
    @ExcelProperty("图片大小(KB)")
    private Integer imageSizeKb;

    @Schema(description = "人脸特征向量数据(通常是一组浮点数数组)")
    @ExcelProperty("人脸特征向量数据(通常是一组浮点数数组)")
    private String faceVector;

    @Schema(description = "人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片")
    @ExcelProperty("人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片")
    private BigDecimal qualityScore;

    @Schema(description = "活体检测得分(0.0000 - 1.0000)，防止照片攻击")
    @ExcelProperty("活体检测得分(0.0000 - 1.0000)，防止照片攻击")
    private BigDecimal livenessScore;

    @Schema(description = "是否为主照片(1-是, 0-否)")
    @ExcelProperty("是否为主照片(1-是, 0-否)")
    private Boolean isPrimary;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}