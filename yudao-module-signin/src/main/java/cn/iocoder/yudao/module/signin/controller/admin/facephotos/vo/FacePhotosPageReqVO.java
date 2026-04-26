package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 签到系统-人脸照片特征分页 Request VO")
@Data
public class FacePhotosPageReqVO extends PageParam {

    @Schema(description = "学员编号", example = "9210")
    private String studentNo;

    @Schema(description = "班级编号")
    private Long classId;

    @Schema(description = "照片在对象存储(OSS/S3)中的访问路径", example = "https://www.iocoder.cn")
    private String imageUrl;

    @Schema(description = "图片格式(jpg, png等)")
    private String imageFormat;

    @Schema(description = "图片大小(KB)")
    private Integer imageSizeKb;

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

    @Schema(description = "状态：1-正常, 0-禁用")
    private Boolean status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}