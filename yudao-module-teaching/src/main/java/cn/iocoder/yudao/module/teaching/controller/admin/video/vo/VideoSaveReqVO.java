package cn.iocoder.yudao.module.teaching.controller.admin.video.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 教学视频新增/修改 Request VO")
@Data
public class VideoSaveReqVO {

    @Schema(description = "视频编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1664")
    private Long id;

    @Schema(description = "视频标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "视频标题不能为空")
    private String title;

    @Schema(description = "视频简介", example = "你说的对")
    private String description;

    @Schema(description = "视频链接", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "视频链接不能为空")
    private String videoUrl;

    @Schema(description = "封面图链接", example = "https://www.iocoder.cn")
    private String coverUrl;

    @Schema(description = "视频时长(秒)")
    private Integer duration;

    @Schema(description = "分类编号", example = "21472")
    private Long categoryId;

    @Schema(description = "显示排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示排序不能为空")
    private Integer sort;

    @Schema(description = "状态(0:开启 1:关闭)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态(0:开启 1:关闭)不能为空")
    private Integer status;

    @Schema(description = "播放次数", requiredMode = Schema.RequiredMode.REQUIRED, example = "28379")
    @NotNull(message = "播放次数不能为空")
    private Integer viewCount;

}