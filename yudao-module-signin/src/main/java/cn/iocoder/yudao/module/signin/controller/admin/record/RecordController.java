package cn.iocoder.yudao.module.signin.controller.admin.record;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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

import cn.iocoder.yudao.module.signin.controller.admin.record.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.record.RecordDO;
import cn.iocoder.yudao.module.signin.service.facesignin.SigninFaceVerifyService;
import cn.iocoder.yudao.module.signin.service.record.SigninRecordService;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/signin/record")
@Validated
public class RecordController {

    @Resource
    private SigninRecordService signinRecordService;

    @Resource
    private SigninFaceVerifyService signinFaceVerifyService;

    @PostMapping("/create")
    @Operation(summary = "创建签到记录")
    public CommonResult<Long> createRecord(@Valid @RequestBody RecordSaveReqVO createReqVO) {
        return success(signinRecordService.createRecord(createReqVO));
    }

    @PostMapping(value = "/verify-face", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "人脸比对签到", description = "表单字段：lessonId、personId、photoId、compareImage（现场人脸图）")
    public CommonResult<FaceVerifyAndSignRespVO> verifyFaceAndSignIn(
            @RequestParam("lessonId") @NotNull(message = "课堂编号不能为空") Long lessonId,
            @RequestParam("personId") @NotEmpty(message = "学员编号不能为空") String personId,
            @RequestParam("photoId") @NotEmpty(message = "照片ID不能为空") String photoId,
            @RequestParam("compareImage") MultipartFile compareImage) throws Exception {
        return success(signinFaceVerifyService.verifyFaceAndSignIn(lessonId, personId, photoId, compareImage));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到记录")
    public CommonResult<Boolean> updateRecord(@Valid @RequestBody RecordSaveReqVO updateReqVO) {
        signinRecordService.updateRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到记录")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteRecord(@RequestParam("id") Long id) {
        signinRecordService.deleteRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除签到记录")
    public CommonResult<Boolean> deleteRecordList(@RequestParam("ids") List<Long> ids) {
        signinRecordService.deleteRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<RecordRespVO> getRecord(@RequestParam("id") Long id) {
        RecordDO signinRecord = signinRecordService.getRecord(id);
        return success(BeanUtils.toBean(signinRecord, RecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到记录分页")
    public CommonResult<PageResult<RecordRespVO>> getRecordPage(@Valid RecordPageReqVO pageReqVO) {
        PageResult<RecordDO> pageResult = signinRecordService.getRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到记录 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRecordExcel(@Valid RecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RecordDO> list = signinRecordService.getRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "签到记录.xls", "数据", RecordRespVO.class,
                        BeanUtils.toBean(list, RecordRespVO.class));
    }

}