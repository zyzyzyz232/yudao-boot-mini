package cn.iocoder.yudao.module.class.controller.admin.trainingplan;

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

import cn.iocoder.yudao.module.class.controller.admin.trainingplan.vo.*;
import cn.iocoder.yudao.module.class.dal.dataobject.trainingplan.TrainingPlanDO;
import cn.iocoder.yudao.module.class.service.trainingplan.TrainingPlanService;

@Tag(name = "管理后台 - 课堂训练计划")
@RestController
@RequestMapping("/class/training-plan")
@Validated
public class TrainingPlanController {

    @Resource
    private TrainingPlanService trainingPlanService;

    @PostMapping("/create")
    @Operation(summary = "创建课堂训练计划")
    @PreAuthorize("@ss.hasPermission('class:training-plan:create')")
    public CommonResult<Long> createTrainingPlan(@Valid @RequestBody TrainingPlanSaveReqVO createReqVO) {
        return success(trainingPlanService.createTrainingPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课堂训练计划")
    @PreAuthorize("@ss.hasPermission('class:training-plan:update')")
    public CommonResult<Boolean> updateTrainingPlan(@Valid @RequestBody TrainingPlanSaveReqVO updateReqVO) {
        trainingPlanService.updateTrainingPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课堂训练计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('class:training-plan:delete')")
    public CommonResult<Boolean> deleteTrainingPlan(@RequestParam("id") Long id) {
        trainingPlanService.deleteTrainingPlan(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除课堂训练计划")
                @PreAuthorize("@ss.hasPermission('class:training-plan:delete')")
    public CommonResult<Boolean> deleteTrainingPlanList(@RequestParam("ids") List<Long> ids) {
        trainingPlanService.deleteTrainingPlanListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课堂训练计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('class:training-plan:query')")
    public CommonResult<TrainingPlanRespVO> getTrainingPlan(@RequestParam("id") Long id) {
        TrainingPlanDO trainingPlan = trainingPlanService.getTrainingPlan(id);
        return success(BeanUtils.toBean(trainingPlan, TrainingPlanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课堂训练计划分页")
    @PreAuthorize("@ss.hasPermission('class:training-plan:query')")
    public CommonResult<PageResult<TrainingPlanRespVO>> getTrainingPlanPage(@Valid TrainingPlanPageReqVO pageReqVO) {
        PageResult<TrainingPlanDO> pageResult = trainingPlanService.getTrainingPlanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TrainingPlanRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课堂训练计划 Excel")
    @PreAuthorize("@ss.hasPermission('class:training-plan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTrainingPlanExcel(@Valid TrainingPlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TrainingPlanDO> list = trainingPlanService.getTrainingPlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "课堂训练计划.xls", "数据", TrainingPlanRespVO.class,
                        BeanUtils.toBean(list, TrainingPlanRespVO.class));
    }

}