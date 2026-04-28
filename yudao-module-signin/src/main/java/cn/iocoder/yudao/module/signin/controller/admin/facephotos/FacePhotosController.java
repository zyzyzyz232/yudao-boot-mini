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
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotoSyncFeatureRespVO;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.FacePhotosSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotoFeatureSyncService;
import cn.iocoder.yudao.module.signin.service.facephotos.FacePhotosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.FACE_PHOTOS_NOT_EXISTS;

@Tag(name = "管理后台 - 签到系统-人脸照片特征")
@RestController
@RequestMapping("/signin/face-photos")
@Validated
public class FacePhotosController {

    @Resource
    private FacePhotosService facePhotosService;

    @Resource
    private FileService fileService;

    @Resource
    private FacePhotoFeatureSyncService facePhotoFeatureSyncService;


    @PostMapping(value = "/sync-feature", consumes = "multipart/form-data")
    @Operation(summary = "同步人脸特征（上传照片、调算法、写特征）",
            description = "数据仅存 signin_face_photos。必填：studentNo、classId、file；按 classId+studentNo upsert。photoId 选传：传则更新该记录（并校验其 studentNo、classId 与入参一致），不传则按 classId+studentNo 定位更新（若存在）或新建。teachingClassStudentId（班级学员关联 ID）选传，仅用于主键 id 与业务表对齐，不参与必填校验")
    @Parameter(name = "file", description = "照片文件", required = true,
            schema = @Schema(type = "string", format = "binary"))
    public CommonResult<FacePhotoSyncFeatureRespVO> syncFaceFeature(
            @RequestParam("studentNo") @NotEmpty(message = "学员编号不能为空") String studentNo,
            @RequestParam("classId") @NotNull(message = "班级编号不能为空") Long classId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "photoId", required = false) Long photoId,
            @RequestParam(value = "teachingClassStudentId", required = false) Long teachingClassStudentId,
            @RequestParam("file") @NotNull(message = "照片文件不能为空") MultipartFile file) throws Exception {
        Long outPhotoId = facePhotoFeatureSyncService.syncFeatureFromUpload(studentNo, classId, name, photoId,
                teachingClassStudentId, file);
        return success(new FacePhotoSyncFeatureRespVO(outPhotoId));
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @Operation(summary = "创建签到系统-人脸照片特征",
            description = "必填：studentNo、classId。file 选传；不传时不走 OSS，image_url 置空串、image_size_kb 为 0，可仅写入 faceVector 等字段。teachingClassStudentId 选传，仅用于将表主键 id 与班级学员关联表对齐")
    @Parameter(name = "file", description = "照片文件（选传）",
            schema = @Schema(type = "string", format = "binary"))
    public CommonResult<Long> createFacePhotos(
            @RequestParam("studentNo") @NotEmpty(message = "学员编号不能为空") String studentNo,
            @RequestParam("classId") @NotNull(message = "班级编号不能为空") Long classId,
            @RequestParam(value = "photoId", required = false) Long photoId,
            @RequestParam(value = "teachingClassStudentId", required = false) Long teachingClassStudentId,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary,
            @RequestParam(value = "faceVector", required = false) String faceVector,
            @RequestParam(value = "qualityScore", required = false) java.math.BigDecimal qualityScore,
            @RequestParam(value = "livenessScore", required = false) java.math.BigDecimal livenessScore,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String imageUrl;
        String fileName;
        int sizeKb;
        if (file != null && !file.isEmpty()) {
            byte[] content = IoUtil.readBytes(file.getInputStream());
            imageUrl = fileService.createFile(content, file.getOriginalFilename(),
                    "signin/face-photos", file.getContentType());
            fileName = file.getOriginalFilename();
            sizeKb = (int) (file.getSize() / 1024);
        } else {
            imageUrl = "";
            fileName = "";
            sizeKb = 0;
        }
        FacePhotosSaveReqVO createReqVO = new FacePhotosSaveReqVO();
        createReqVO.setStudentNo(studentNo);
        createReqVO.setClassId(classId);
        createReqVO.setPhotoId(photoId);
        createReqVO.setTeachingClassStudentId(teachingClassStudentId);
        createReqVO.setIsPrimary(isPrimary);
        createReqVO.setFaceVector(faceVector);
        createReqVO.setQualityScore(qualityScore);
        createReqVO.setLivenessScore(livenessScore);
        createReqVO.setName(name);
        return success(facePhotosService.createFacePhotos(createReqVO, imageUrl, fileName, sizeKb));
    }

    @PutMapping(value = "/update", consumes = "multipart/form-data")
    @Operation(summary = "更新签到系统-人脸照片特征",
            description = "必填 studentNo、classId；按班级+学员定位记录（优先主图，否则取最新一条），无需传 photoId")
    @Parameter(name = "file", description = "照片文件（可选，传则替换原照片）",
            schema = @Schema(type = "string", format = "binary"))
    public CommonResult<Boolean> updateFacePhotos(
            @RequestParam("studentNo") @NotEmpty(message = "学员编号不能为空") String studentNo,
            @RequestParam("classId") @NotNull(message = "班级编号不能为空") Long classId,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary,
            @RequestParam(value = "faceVector", required = false) String faceVector,
            @RequestParam(value = "qualityScore", required = false) java.math.BigDecimal qualityScore,
            @RequestParam(value = "livenessScore", required = false) java.math.BigDecimal livenessScore,
            @RequestParam(value = "name", required = false) String name,
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
        FacePhotosDO target = facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(classId, studentNo);
        if (target == null) {
            throw exception(FACE_PHOTOS_NOT_EXISTS);
        }
        FacePhotosSaveReqVO updateReqVO = new FacePhotosSaveReqVO();
        updateReqVO.setPhotoId(target.getPhotoId());
        updateReqVO.setStudentNo(studentNo);
        updateReqVO.setClassId(classId);
        updateReqVO.setIsPrimary(isPrimary);
        updateReqVO.setFaceVector(faceVector);
        updateReqVO.setQualityScore(qualityScore);
        updateReqVO.setLivenessScore(livenessScore);
        updateReqVO.setName(name);
        facePhotosService.updateFacePhotos(updateReqVO, imageUrl, fileName, sizeKb);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到系统-人脸照片特征")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteFacePhotos(@RequestParam("id") Long id) {
        facePhotosService.deleteFacePhotos(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除签到系统-人脸照片特征")
    @Parameter(name = "ids", description = "编号列表", required = true)
    public CommonResult<Boolean> deleteFacePhotosList(@RequestParam("ids") List<Long> ids) {
        facePhotosService.deleteFacePhotosListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到系统-人脸照片特征")
    @Parameter(name = "id", description = "表主键 id（BIGINT），与分页/创建返回的 photoId 一致；勿用学员号代替，学员号请用 get-by-student-no", required = true)
    public CommonResult<FacePhotosRespVO> getFacePhotos(@RequestParam("id") Long id) {
        FacePhotosDO facePhotos = facePhotosService.getFacePhotos(id);
        if (facePhotos == null) {
            throw exception(FACE_PHOTOS_NOT_EXISTS);
        }
        return success(BeanUtils.toBean(facePhotos, FacePhotosRespVO.class));
    }

    @GetMapping("/get-by-student-no")
    @Operation(summary = "按班级+学员编号获得人脸照片特征", description = "优先主图，否则取最近更新的一条（与更新接口定位一致）")
    public CommonResult<FacePhotosRespVO> getFacePhotosByStudentNo(
            @RequestParam("studentNo") @NotEmpty(message = "学员编号不能为空") String studentNo,
            @RequestParam("classId") @NotNull(message = "班级编号不能为空") Long classId) {
        FacePhotosDO facePhotos = facePhotosService.getFacePhotoForUpdateByStudentNoAndClassId(classId, studentNo);
        if (facePhotos == null) {
            throw exception(FACE_PHOTOS_NOT_EXISTS);
        }
        return success(BeanUtils.toBean(facePhotos, FacePhotosRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到系统-人脸照片特征分页",
            description = "查询条件必填：studentNo、classId（与 signin_face_photos 结构一致）；其余筛选可选")
    public CommonResult<PageResult<FacePhotosRespVO>> getFacePhotosPage(@Valid FacePhotosPageReqVO pageReqVO) {
        PageResult<FacePhotosDO> pageResult = facePhotosService.getFacePhotosPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FacePhotosRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到系统-人脸照片特征 Excel",
            description = "查询条件必填：studentNo、classId，与分页接口一致")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFacePhotosExcel(@Valid FacePhotosPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FacePhotosDO> list = facePhotosService.getFacePhotosPage(pageReqVO).getList();
        ExcelUtils.write(response, "签到系统-人脸照片特征.xls", "数据", FacePhotosRespVO.class,
                BeanUtils.toBean(list, FacePhotosRespVO.class));
    }

}