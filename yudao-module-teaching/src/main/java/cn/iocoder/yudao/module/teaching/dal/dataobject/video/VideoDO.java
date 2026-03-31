package cn.iocoder.yudao.module.teaching.dal.dataobject.video;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 教学视频 DO
 *
 * @author 芋道源码
 */
@TableName("teaching_video")
@KeySequence("teaching_video_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDO extends BaseDO {

    /**
     * 视频编号
     */
    @TableId
    private Long id;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频简介
     */
    private String description;
    /**
     * 视频链接
     */
    private String videoUrl;
    /**
     * 封面图链接
     */
    private String coverUrl;
    /**
     * 视频时长(秒)
     */
    private Integer duration;
    /**
     * 分类编号
     */
    private Long categoryId;
    /**
     * 显示排序
     */
    private Integer sort;
    /**
     * 状态(0:开启 1:关闭)
     */
    private Integer status;
    /**
     * 播放次数
     */
    private Integer viewCount;


}