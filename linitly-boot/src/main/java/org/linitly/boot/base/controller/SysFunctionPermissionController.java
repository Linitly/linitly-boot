package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysFunctionPermissionDTO;
import org.linitly.boot.base.entity.SysFunctionPermission;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysFunctionPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Result
@RestController
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysFunctionPermission")
@Api(tags = "系统功能权限管理")
public class SysFunctionPermissionController {

    @Autowired
    private SysFunctionPermissionService sysFunctionPermissionService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统功能权限")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysFunctionPermissionDTO dto, BindingResult bindingResult) {
        sysFunctionPermissionService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统功能权限")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysFunctionPermissionDTO dto, BindingResult bindingResult) {
        sysFunctionPermissionService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统功能权限")
    public SysFunctionPermission findById(@PathVariable Long id) {
        return sysFunctionPermissionService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询系统功能权限列表")
    public List<SysFunctionPermission> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) SysFunctionPermission sysFunctionPermission) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysFunctionPermissionService.findAll(sysFunctionPermission);
    }

    @PostMapping("/findBySysMenuId/{sysMenuId}")
    @ApiOperation(value = "根据菜单id查询功能权限列表")
    public List<SysFunctionPermission> findBySysMenuId(@PathVariable Long sysMenuId) {
        return sysFunctionPermissionService.findBySysMenuId(sysMenuId);
    }

//    @PostMapping("/deleteById/{id}")
//    @ApiOperation(value = "根据id删除系统功能权限")
//    public void deleteById(@PathVariable Long id) {
//        sysFunctionPermissionService.deleteById(id);
//    }
}