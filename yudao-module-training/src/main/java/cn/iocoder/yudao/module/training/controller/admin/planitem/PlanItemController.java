package cn.iocoder.yudao.module.training.controller.admin.planitem;

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

import cn.iocoder.yudao.module.training.controller.admin.planitem.vo.*;
import cn.iocoder.yudao.module.training.dal.dataobject.planitem.PlanItemDO;
import cn.iocoder.yudao.module.training.service.planitem.PlanItemService;

@Tag(name = "管理后台 - 训练项目")
@RestController
@RequestMapping("/training/plan-item")
@Validated
public class PlanItemController {

    @Resource
    private PlanItemService planItemService;

    @PostMapping("/create")
    @Operation(summary = "创建训练项目")
    @PreAuthorize("@ss.hasPermission('training:plan-item:create')")
    public CommonResult<Long> createPlanItem(@Valid @RequestBody PlanItemSaveReqVO createReqVO) {
        return success(planItemService.createPlanItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新训练项目")
    @PreAuthorize("@ss.hasPermission('training:plan-item:update')")
    public CommonResult<Boolean> updatePlanItem(@Valid @RequestBody PlanItemSaveReqVO updateReqVO) {
        planItemService.updatePlanItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除训练项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('training:plan-item:delete')")
    public CommonResult<Boolean> deletePlanItem(@RequestParam("id") Long id) {
        planItemService.deletePlanItem(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除训练项目")
                @PreAuthorize("@ss.hasPermission('training:plan-item:delete')")
    public CommonResult<Boolean> deletePlanItemList(@RequestParam("ids") List<Long> ids) {
        planItemService.deletePlanItemListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得训练项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('training:plan-item:query')")
    public CommonResult<PlanItemRespVO> getPlanItem(@RequestParam("id") Long id) {
        PlanItemDO planItem = planItemService.getPlanItem(id);
        return success(BeanUtils.toBean(planItem, PlanItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得训练项目分页")
    @PreAuthorize("@ss.hasPermission('training:plan-item:query')")
    public CommonResult<PageResult<PlanItemRespVO>> getPlanItemPage(@Valid PlanItemPageReqVO pageReqVO) {
        PageResult<PlanItemDO> pageResult = planItemService.getPlanItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PlanItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出训练项目 Excel")
    @PreAuthorize("@ss.hasPermission('training:plan-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPlanItemExcel(@Valid PlanItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PlanItemDO> list = planItemService.getPlanItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "训练项目.xls", "数据", PlanItemRespVO.class,
                        BeanUtils.toBean(list, PlanItemRespVO.class));
    }

}