package cn.iocoder.yudao.module.signin.controller.admin.facephotos;

import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosPageReqVO;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosRespVO;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 签到系统-人脸照片特征")
@RestController
@RequestMapping("/signin/face-photos")
@Validated
public class FacePhotosController {

    @Resource
    private FacePhotosService facePhotosService;

    @Resource
    private FileService fileService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @Operation(summary = "创建签到系统-人脸照片特征")
    @Parameter(name = "file", description = "照片文件", required = true,
            schema = @Schema(type = "string", format = "binary"))
    @PreAuthorize("@ss.hasPermission('signin:face-photos:create')")
    public CommonResult<String> createFacePhotos(
            @RequestParam("personId") @NotNull(message = "关联的人员ID不能为空") String personId,
            @RequestParam(value = "photoId", required = false) String photoId,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary,
            @RequestParam(value = "faceVector", required = false) String faceVector,
            @RequestParam(value = "qualityScore", required = false) java.math.BigDecimal qualityScore,
            @RequestParam(value = "livenessScore", required = false) java.math.BigDecimal livenessScore,
            @RequestParam("file") @NotNull(message = "照片文件不能为空") MultipartFile file) throws Exception {
        byte[] content = IoUtil.readBytes(file.getInputStream());
        String imageUrl = fileService.createFile(content, file.getOriginalFilename(),
                "signin/face-photos", file.getContentType());
        FacePhotosSaveReqVO createReqVO = new FacePhotosSaveReqVO();
        createReqVO.setPersonId(personId);
        createReqVO.setPhotoId(photoId);
        createReqVO.setIsPrimary(isPrimary);
        createReqVO.setFaceVector(faceVector);
        createReqVO.setQualityScore(qualityScore);
        createReqVO.setLivenessScore(livenessScore);
        return success(facePhotosService.createFacePhotos(createReqVO, imageUrl,
                file.getOriginalFilename(), (int) (file.getSize() / 1024)));
    }

    @PutMapping(value = "/update", consumes = "multipart/form-data")
    @Operation(summary = "更新签到系统-人脸照片特征")
    @Parameter(name = "file", description = "照片文件（可选，传则替换原照片）",
            schema = @Schema(type = "string", format = "binary"))
    @PreAuthorize("@ss.hasPermission('signin:face-photos:update')")
    public CommonResult<Boolean> updateFacePhotos(
            @RequestParam("photoId") @NotNull(message = "照片ID不能为空") String photoId,
            @RequestParam(value = "personId", required = false) String personId,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary,
            @RequestParam(value = "faceVector", required = false) String faceVector,
            @RequestParam(value = "qualityScore", required = false) java.math.BigDecimal qualityScore,
            @RequestParam(value = "livenessScore", required = false) java.math.BigDecimal livenessScore,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        // 若携带新文件，上传并获取 URL
        String imageUrl = null;
        String fileName = null;
        Integer sizeKb = null;
        if (file != null && !file.isEmpty()) {
            byte[] content = IoUtil.readBytes(file.getInputStream());
            imageUrl = fileService.createFile(content, file.getOriginalFilename(),
                    "signin/face-photos", file.getContentType());
            fileName = file.getOriginalFilename();
            sizeKb = (int) (file.getSize() / 1024);
        }
        FacePhotosSaveReqVO updateReqVO = new FacePhotosSaveReqVO();
        updateReqVO.setPhotoId(photoId);
        updateReqVO.setPersonId(personId);
        updateReqVO.setIsPrimary(isPrimary);
        updateReqVO.setFaceVector(faceVector);
        updateReqVO.setQualityScore(qualityScore);
        updateReqVO.setLivenessScore(livenessScore);
        facePhotosService.updateFacePhotos(updateReqVO, imageUrl, fileName, sizeKb);
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
    @Operation(summary = "批量删除签到系统-人脸照片特征")
    @Parameter(name = "ids", description = "编号列表", required = true)
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
        ExcelUtils.write(response, "签到系统-人脸照片特征.xls", "数据", FacePhotosRespVO.class,
                BeanUtils.toBean(list, FacePhotosRespVO.class));
    }

}