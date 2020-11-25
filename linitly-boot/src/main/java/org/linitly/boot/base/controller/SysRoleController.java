package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.sys_admin_user.SysRoleDTO;
import org.linitly.boot.base.entity.SysRole;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Result
@RestController
@RequestMapping("/sysRole")
@Api(tags = "系统角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统角色")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysRoleDTO dto, BindingResult bindingResult) {
        sysRoleService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统角色")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysRoleDTO dto, BindingResult bindingResult) {
        sysRoleService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统角色")
    public SysRole findById(@PathVariable Long id) {
        return sysRoleService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询系统角色列表")
    public List<SysRole> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) SysRole sysRole) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysRoleService.findAll(sysRole);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除系统角色")
    public void deleteById(@PathVariable Long id) {
        sysRoleService.deleteById(id);
    }
}