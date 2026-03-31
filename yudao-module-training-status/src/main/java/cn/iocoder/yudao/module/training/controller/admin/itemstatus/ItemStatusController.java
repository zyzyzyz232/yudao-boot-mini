package cn.iocoder.yudao.module.training.controller.admin.itemstatus;

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

import cn.iocoder.yudao.module.training.controller.admin.itemstatus.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.itemstatus.ItemStatusDO;
import cn.iocoder.yudao.module.training.service.itemstatus.ItemStatusService;

@Tag(name = "管理后台 - 训练项目执行状态")
@RestController
@RequestMapping("/training/item-status")
@Validated
public class ItemStatusController {

    @Resource
    private ItemStatusService itemStatusService;

    @PostMapping("/create")
    @Operation(summary = "创建训练项目执行状态")
    @PreAuthorize("@ss.hasPermission('training:item-status:create')")
    public CommonResult<Long> createItemStatus(@Valid @RequestBody ItemStatusSaveReqVO createReqVO) {
        return success(itemStatusService.createItemStatus(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新训练项目执行状态")
    @PreAuthorize("@ss.hasPermission('training:item-status:update')")
    public CommonResult<Boolean> updateItemStatus(@Valid @RequestBody ItemStatusSaveReqVO updateReqVO) {
        itemStatusService.updateItemStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除训练项目执行状态")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('training:item-status:delete')")
    public CommonResult<Boolean> deleteItemStatus(@RequestParam("id") Long id) {
        itemStatusService.deleteItemStatus(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除训练项目执行状态")
                @PreAuthorize("@ss.hasPermission('training:item-status:delete')")
    public CommonResult<Boolean> deleteItemStatusList(@RequestParam("ids") List<Long> ids) {
        itemStatusService.deleteItemStatusListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得训练项目执行状态")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('training:item-status:query')")
    public CommonResult<ItemStatusRespVO> getItemStatus(@RequestParam("id") Long id) {
        ItemStatusDO itemStatus = itemStatusService.getItemStatus(id);
        return success(BeanUtils.toBean(itemStatus, ItemStatusRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得训练项目执行状态分页")
    @PreAuthorize("@ss.hasPermission('training:item-status:query')")
    public CommonResult<PageResult<ItemStatusRespVO>> getItemStatusPage(@Valid ItemStatusPageReqVO pageReqVO) {
        PageResult<ItemStatusDO> pageResult = itemStatusService.getItemStatusPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ItemStatusRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出训练项目执行状态 Excel")
    @PreAuthorize("@ss.hasPermission('training:item-status:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportItemStatusExcel(@Valid ItemStatusPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ItemStatusDO> list = itemStatusService.getItemStatusPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "训练项目执行状态.xls", "数据", ItemStatusRespVO.class,
                        BeanUtils.toBean(list, ItemStatusRespVO.class));
    }

}