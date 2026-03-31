package cn.iocoder.yudao.module.teaching.controller.admin.video.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 教学视频分页 Request VO")
@Data
public class VideoPageReqVO extends PageParam {

    @Schema(description = "视频标题")
    private String title;

    @Schema(description = "视频简介", example = "你说的对")
    private String description;

    @Schema(description = "视频链接", example = "https://www.iocoder.cn")
    private String videoUrl;

    @Schema(description = "封面图链接", example = "https://www.iocoder.cn")
    private String coverUrl;

    @Schema(description = "视频时长(秒)")
    private Integer duration;

    @Schema(description = "分类编号", example = "21472")
    private Long categoryId;

    @Schema(description = "显示排序")
    private Integer sort;

    @Schema(description = "状态(0:开启 1:关闭)", example = "1")
    private Integer status;

    @Schema(description = "播放次数", example = "28379")
    private Integer viewCount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}