package cn.iocoder.yudao.module.teaching.controller.admin.video;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.teaching.controller.admin.video.vo.*;
import cn.iocoder.yudao.module.teaching.dal.dataobject.video.VideoDO;
import cn.iocoder.yudao.module.teaching.service.video.VideoService;

@Tag(name = "管理后台 - 教学视频")
@RestController
@RequestMapping("/teaching/video")
@Validated
public class VideoController {

    @Resource
    private VideoService videoService;

    @PostMapping("/create")
    @Operation(summary = "创建教学视频")
    @PreAuthorize("@ss.hasPermission('teaching:video:create')")
    public CommonResult<Long> createVideo(@Valid @RequestBody VideoSaveReqVO createReqVO) {
        return success(videoService.createVideo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新教学视频")
    @PreAuthorize("@ss.hasPermission('teaching:video:update')")
    public CommonResult<Boolean> updateVideo(@Valid @RequestBody VideoSaveReqVO updateReqVO) {
        videoService.updateVideo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除教学视频")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('teaching:video:delete')")
    public CommonResult<Boolean> deleteVideo(@RequestParam("id") Long id) {
        videoService.deleteVideo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除教学视频")
                @PreAuthorize("@ss.hasPermission('teaching:video:delete')")
    public CommonResult<Boolean> deleteVideoList(@RequestParam("ids") List<Long> ids) {
        videoService.deleteVideoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得教学视频")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('teaching:video:query')")
    public CommonResult<VideoRespVO> getVideo(@RequestParam("id") Long id) {
        VideoDO video = videoService.getVideo(id);
        return success(BeanUtils.toBean(video, VideoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得教学视频分页")
    @PreAuthorize("@ss.hasPermission('teaching:video:query')")
    public CommonResult<PageResult<VideoRespVO>> getVideoPage(@Valid VideoPageReqVO pageReqVO) {
        PageResult<VideoDO> pageResult = videoService.getVideoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VideoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出教学视频 Excel")
    @PreAuthorize("@ss.hasPermission('teaching:video:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVideoExcel(@Valid VideoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VideoDO> list = videoService.getVideoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "教学视频.xls", "数据", VideoRespVO.class,
                        BeanUtils.toBean(list, VideoRespVO.class));
    }

}