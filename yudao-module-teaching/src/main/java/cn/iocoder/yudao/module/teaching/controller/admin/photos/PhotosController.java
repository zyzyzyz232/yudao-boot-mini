package cn.iocoder.yudao.module.teaching.controller.admin.photos;

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

import cn.iocoder.yudao.module.teaching.controller.admin.photos.vo.*;
import cn.iocoder.yudao.module.teaching.dal.dataobject.photos.PhotosDO;
import cn.iocoder.yudao.module.teaching.service.photos.PhotosService;

@Tag(name = "管理后台 - 人脸照片及特征数据")
@RestController
@RequestMapping("/teaching/photos")
@Validated
public class PhotosController {

    @Resource
    private PhotosService photosService;

    @PostMapping("/create")
    @Operation(summary = "创建人脸照片及特征数据")
    @PreAuthorize("@ss.hasPermission('teaching:photos:create')")
    public CommonResult<String> createPhotos(@Valid @RequestBody PhotosSaveReqVO createReqVO) {
        return success(photosService.createPhotos(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新人脸照片及特征数据")
    @PreAuthorize("@ss.hasPermission('teaching:photos:update')")
    public CommonResult<Boolean> updatePhotos(@Valid @RequestBody PhotosSaveReqVO updateReqVO) {
        photosService.updatePhotos(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除人脸照片及特征数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('teaching:photos:delete')")
    public CommonResult<Boolean> deletePhotos(@RequestParam("id") String id) {
        photosService.deletePhotos(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除人脸照片及特征数据")
                @PreAuthorize("@ss.hasPermission('teaching:photos:delete')")
    public CommonResult<Boolean> deletePhotosList(@RequestParam("ids") List<String> ids) {
        photosService.deletePhotosListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得人脸照片及特征数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('teaching:photos:query')")
    public CommonResult<PhotosRespVO> getPhotos(@RequestParam("id") String id) {
        PhotosDO photos = photosService.getPhotos(id);
        return success(BeanUtils.toBean(photos, PhotosRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得人脸照片及特征数据分页")
    @PreAuthorize("@ss.hasPermission('teaching:photos:query')")
    public CommonResult<PageResult<PhotosRespVO>> getPhotosPage(@Valid PhotosPageReqVO pageReqVO) {
        PageResult<PhotosDO> pageResult = photosService.getPhotosPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PhotosRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出人脸照片及特征数据 Excel")
    @PreAuthorize("@ss.hasPermission('teaching:photos:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPhotosExcel(@Valid PhotosPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PhotosDO> list = photosService.getPhotosPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "人脸照片及特征数据.xls", "数据", PhotosRespVO.class,
                        BeanUtils.toBean(list, PhotosRespVO.class));
    }

}