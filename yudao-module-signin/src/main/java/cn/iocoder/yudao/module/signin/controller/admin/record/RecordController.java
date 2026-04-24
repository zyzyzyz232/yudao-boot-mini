package cn.iocoder.yudao.module.signin.controller.admin.record;

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

import cn.iocoder.yudao.module.signin.controller.admin.record.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.record.RecordDO;
import cn.iocoder.yudao.module.signin.service.record.SigninRecordService;

@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/signin/record")
@Validated
public class RecordController {

    @Resource
    private SigninRecordService signinRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建签到记录")
    @PreAuthorize("@ss.hasPermission('signin:record:create')")
    public CommonResult<Long> createRecord(@Valid @RequestBody RecordSaveReqVO createReqVO) {
        return success(signinRecordService.createRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到记录")
    @PreAuthorize("@ss.hasPermission('signin:record:update')")
    public CommonResult<Boolean> updateRecord(@Valid @RequestBody RecordSaveReqVO updateReqVO) {
        signinRecordService.updateRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('signin:record:delete')")
    public CommonResult<Boolean> deleteRecord(@RequestParam("id") Long id) {
        signinRecordService.deleteRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除签到记录")
                @PreAuthorize("@ss.hasPermission('signin:record:delete')")
    public CommonResult<Boolean> deleteRecordList(@RequestParam("ids") List<Long> ids) {
        signinRecordService.deleteRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('signin:record:query')")
    public CommonResult<RecordRespVO> getRecord(@RequestParam("id") Long id) {
        RecordDO signinRecord = signinRecordService.getRecord(id);
        return success(BeanUtils.toBean(signinRecord, RecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到记录分页")
    @PreAuthorize("@ss.hasPermission('signin:record:query')")
    public CommonResult<PageResult<RecordRespVO>> getRecordPage(@Valid RecordPageReqVO pageReqVO) {
        PageResult<RecordDO> pageResult = signinRecordService.getRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到记录 Excel")
    @PreAuthorize("@ss.hasPermission('signin:record:export')")
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