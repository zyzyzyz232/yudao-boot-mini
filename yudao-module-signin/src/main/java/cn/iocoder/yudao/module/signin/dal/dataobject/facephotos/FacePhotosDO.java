package cn.iocoder.yudao.module.signin.dal.dataobject.facephotos;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到系统-人脸照片特征 DO
 *
 * @author 芋道源码
 */
@TableName("signin_face_photos")
@KeySequence("signin_face_photos_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacePhotosDO extends BaseDO {

    /**
     * 照片唯一标识(可使用UUID)
     */
    @TableId(type = IdType.INPUT)
    private String photoId;
    /**
     * 关联的人员ID
     */
    private String personId;
    /**
     * 照片在对象存储(OSS/S3)中的访问路径
     */
    private String imageUrl;
    /**
     * 图片格式(jpg, png等)
     */
    private String imageFormat;
    /**
     * 图片大小(KB)
     */
    private Integer imageSizeKb;
    /**
     * 人脸特征向量数据(通常是一组浮点数数组)
     */
    private String faceVector;
    /**
     * 人脸质量得分(0.0000 - 1.0000)，用于过滤模糊/遮挡照片
     */
    private BigDecimal qualityScore;
    /**
     * 活体检测得分(0.0000 - 1.0000)，防止照片攻击
     */
    private BigDecimal livenessScore;
    /**
     * 是否为主照片(1-是, 0-否)
     */
    private Boolean isPrimary;


}