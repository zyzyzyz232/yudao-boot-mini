package cn.iocoder.yudao.module.signin.controller.admin.signin;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordPageReqVO;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordRespVO;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.signin.SigninRecordDO;
import cn.iocoder.yudao.module.signin.service.signin.SigninRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/signin/record")
@Validated
public class SigninRecordController {

    @Resource
    private SigninRecordService signinRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建签到记录")
    @PreAuthorize("@ss.hasPermission('signin:record:create')")
    public CommonResult<Long> createSigninRecord(@Valid @RequestBody SigninRecordSaveReqVO createReqVO) {
        return success(signinRecordService.createSigninRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到记录")
    @PreAuthorize("@ss.hasPermission('signin:record:update')")
    public CommonResult<Boolean> updateSigninRecord(@Valid @RequestBody SigninRecordSaveReqVO updateReqVO) {
        signinRecordService.updateSigninRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('signin:record:delete')")
    public CommonResult<Boolean> deleteSigninRecord(@RequestParam("id") Long id) {
        signinRecordService.deleteSigninRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除签到记录")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('signin:record:delete')")
    public CommonResult<Boolean> deleteSigninRecordList(@RequestParam("ids") List<Long> ids) {
        signinRecordService.deleteSigninRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('signin:record:query')")
    public CommonResult<SigninRecordRespVO> getSigninRecord(@RequestParam("id") Long id) {
        SigninRecordDO signinRecord = signinRecordService.getSigninRecord(id);
        return success(BeanUtils.toBean(signinRecord, SigninRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到记录分页")
    @PreAuthorize("@ss.hasPermission('signin:record:query')")
    public CommonResult<PageResult<SigninRecordRespVO>> getSigninRecordPage(@Valid SigninRecordPageReqVO pageReqVO) {
        PageResult<SigninRecordDO> pageResult = signinRecordService.getSigninRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SigninRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到记录 Excel")
    @PreAuthorize("@ss.hasPermission('signin:record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSigninRecordExcel(@Valid SigninRecordPageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SigninRecordDO> list = signinRecordService.getSigninRecordPage(pageReqVO).getList();
        ExcelUtils.write(response, "签到记录.xls", "数据", SigninRecordRespVO.class,
                BeanUtils.toBean(list, SigninRecordRespVO.class));
    }

}
