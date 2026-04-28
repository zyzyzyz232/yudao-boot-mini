package cn.iocoder.yudao.module.signin.dal.dataobject.facephotos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 签到系统-人脸照片特征 DO
 * <p>
 * 继承 {@link BaseDO}，使用 {@code deleted} 字段做逻辑删除（与芋道默认一致）。
 * 主键列名为 {@code id}，Java 属性为 {@code photoId}；可与 {@code teaching_class_student.id} 同值（逻辑关联）。
 * <p>
 * 标记 {@link TenantIgnore}：表含 {@code tenant_id} 且脚本默认 0 时，若实体未纳入租户字段填充，
 * MyBatis 租户插件仍会拼接当前登录租户，导致与库中 0 不一致而查无数据。
 *
 * @author 芋道源码
 */
@TenantIgnore
@TableName("signin_face_photos")
@KeySequence("signin_face_photos_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacePhotosDO extends BaseDO {

    /**
     * 表主键列 {@code id}（BIGINT）；可与教学侧 {@code teaching_class_student.id} 同值，或由服务生成雪花 ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long photoId;
    /**
     * 学员编号（表字段 student_no）
     */
    private String studentNo;
    /**
     * 班级编号（同一学员多班级时区分底库）
     */
    private Long classId;
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
    /**
     * 姓名
     */
    private String name;
    /**
     * 状态：1-正常, 0-禁用（表字段 status）
     */
    private Boolean status;

}
