package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysDeptDTO;
import org.linitly.boot.base.entity.SysDept;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Result
@RestController
//@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysDept")
@RequestMapping("/sysDept")
@Api(tags = "系统部门管理")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统部门")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysDeptDTO dto, BindingResult bindingResult) {
        sysDeptService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统部门")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysDeptDTO dto, BindingResult bindingResult) {
        sysDeptService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统部门")
    public SysDept findById(@PathVariable Long id) {
        return sysDeptService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "根据条件查询系统部门列表")
    public List<SysDept> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber,
                                 @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize,
                                 @RequestBody(required = false) SysDept sysDept) {
        PageHelper.startPage(pageNumber, pageSize, "sort asc, id desc");
        return sysDeptService.findAll(sysDept);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除系统部门")
    public void deleteById(@PathVariable Long id) {
        sysDeptService.deleteById(id);
    }
}