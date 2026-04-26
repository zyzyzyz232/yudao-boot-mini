package cn.iocoder.yudao.module.signin.controller.admin.persons;

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

import cn.iocoder.yudao.module.signin.controller.admin.persons.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.persons.PersonsDO;
import cn.iocoder.yudao.module.signin.service.persons.PersonsService;

@Tag(name = "管理后台 - 签到系统-人员基本信息")
@RestController
@RequestMapping("/signin/persons")
@Validated
public class PersonsController {

    @Resource
    private PersonsService personsService;

    @PostMapping("/create")
    @Operation(summary = "创建签到系统-人员基本信息")
    public CommonResult<String> createPersons(@Valid @RequestBody PersonsSaveReqVO createReqVO) {
        return success(personsService.createPersons(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到系统-人员基本信息")
    public CommonResult<Boolean> updatePersons(@Valid @RequestBody PersonsSaveReqVO updateReqVO) {
        personsService.updatePersons(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到系统-人员基本信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletePersons(@RequestParam("id") String id) {
        personsService.deletePersons(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除签到系统-人员基本信息")
    public CommonResult<Boolean> deletePersonsList(@RequestParam("ids") List<String> ids) {
        personsService.deletePersonsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到系统-人员基本信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PersonsRespVO> getPersons(@RequestParam("id") String id) {
        PersonsDO persons = personsService.getPersons(id);
        return success(BeanUtils.toBean(persons, PersonsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得签到系统-人员基本信息分页")
    public CommonResult<PageResult<PersonsRespVO>> getPersonsPage(@Valid PersonsPageReqVO pageReqVO) {
        PageResult<PersonsDO> pageResult = personsService.getPersonsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PersonsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签到系统-人员基本信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPersonsExcel(@Valid PersonsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PersonsDO> list = personsService.getPersonsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "签到系统-人员基本信息.xls", "数据", PersonsRespVO.class,
                        BeanUtils.toBean(list, PersonsRespVO.class));
    }

}