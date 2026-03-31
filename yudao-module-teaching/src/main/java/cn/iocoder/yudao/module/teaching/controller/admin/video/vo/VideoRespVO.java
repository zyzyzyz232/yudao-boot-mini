package cn.iocoder.yudao.module.teaching.controller.admin.video.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 教学视频 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VideoRespVO {

    @Schema(description = "视频编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1664")
    @ExcelProperty("视频编号")
    private Long id;

    @Schema(description = "视频标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("视频标题")
    private String title;

    @Schema(description = "视频简介", example = "你说的对")
    @ExcelProperty("视频简介")
    private String description;

    @Schema(description = "视频链接", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("视频链接")
    private String videoUrl;

    @Schema(description = "封面图链接", example = "https://www.iocoder.cn")
    @ExcelProperty("封面图链接")
    private String coverUrl;

    @Schema(description = "视频时长(秒)")
    @ExcelProperty("视频时长(秒)")
    private Integer duration;

    @Schema(description = "分类编号", example = "21472")
    @ExcelProperty("分类编号")
    private Long categoryId;

    @Schema(description = "显示排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示排序")
    private Integer sort;

    @Schema(description = "状态(0:开启 1:关闭)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态(0:开启 1:关闭)")
    private Integer status;

    @Schema(description = "播放次数", requiredMode = Schema.RequiredMode.REQUIRED, example = "28379")
    @ExcelProperty("播放次数")
    private Integer viewCount;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}