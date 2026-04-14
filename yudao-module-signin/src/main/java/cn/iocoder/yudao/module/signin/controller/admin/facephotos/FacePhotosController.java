package cn.iocoder.yudao.module.signin.controller.admin.facephotos;

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

import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotosService;

@Tag(name = "管理后台 - 签到系统-人脸照片特征")
@RestController
@RequestMapping("/signin/face-photos")
@Validated
public class FacePhotosController {

    @Resource
    private FacePhotosService facePhotosService;

    @PostMapping("/create")
    @Operation(summary = "创建签到系统-人脸照片特征")
    @PreAuthorize("@ss.hasPermission('signin:face-photos:create')")
    public CommonResult<String> createFacePhotos(@Valid @RequestBody FacePhotosSaveReqVO createReqVO) {
        return success(facePhotosService.createFacePhotos(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到系统-人脸照片特征")
    @PreAuthorize("@ss.hasPermission('signin:face-photos:update')")
    public CommonResult<Boolean> updateFacePhotos(@Valid @RequestBody FacePhotosSaveReqVO updateReqVO) {
        facePhotosService.updateFacePhotos(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到系统-人脸照片特征")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('signin:face-photos:delete')")
    public CommonResult<Boolean> deleteFacePhotos(@RequestParam("id") String id) {
        facePhotosService.deleteFacePhotos(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除签到系统-人脸照片特征")
                @PreAuthorize("@ss.hasPermission('signin:face-photos:delete')")
    public CommonResult<Boolean> deleteFacePhotosList(@RequestParam("ids") List<String> ids) {
        facePhotosService.deleteFacePhotosListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到系统-人脸照片特征")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('signin:face-photos:query')")
    public CommonResult<FacePhotosRespVO> getFacePhotos(@RequestParam("id") String id) {
        FacePhotosDO facePhotos = facePhotosService.getFacePhotos(id);
        return success(BeanUtils.toBean(facePhotos, FacePhotosRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到系统-人脸照片特征分页")
    @PreAuthorize("@ss.hasPermission('signin:face-photos:query')")
    public CommonResult<PageResult<FacePhotosRespVO>> getFacePhotosPage(@Valid FacePhotosPageReqVO pageReqVO) {
        PageResult<FacePhotosDO> pageResult = facePhotosService.getFacePhotosPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FacePhotosRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到系统-人脸照片特征 Excel")
    @PreAuthorize("@ss.hasPermission('signin:face-photos:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFacePhotosExcel(@Valid FacePhotosPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FacePhotosDO> list = facePhotosService.getFacePhotosPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "签到系统-人脸照片特征.xls", "数据", FacePhotosRespVO.class,
                        BeanUtils.toBean(list, FacePhotosRespVO.class));
    }

}