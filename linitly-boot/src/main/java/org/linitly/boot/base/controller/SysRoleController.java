package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysRoleDTO;
import org.linitly.boot.base.dto.SysRoleEmpowerDTO;
import org.linitly.boot.base.entity.SysRole;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysRoleService;
import org.linitly.boot.base.vo.SysMenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Result
@RestController
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysRole")
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

    @PostMapping("/empower")
    @ApiOperation(value = "为角色添加或修改权限")
    public void updatePermissionsById(@RequestBody @Validated({UpdateValidGroup.class}) SysRoleEmpowerDTO dto, BindingResult bindingResult) {
        sysRoleService.empower(dto);
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

    @PostMapping("/tree")
    @ApiOperation(value = "获取树状权限列表")
    public List<SysMenuTreeVO> tree(@RequestParam(required = false) Long roleId, HttpServletRequest request) {
        return sysRoleService.tree(roleId, request, null, null);
    }

//    @PostMapping("/deleteById/{id}")
//    @ApiOperation(value = "根据id删除系统角色")
//    public void deleteById(@PathVariable Long id) {
//        sysRoleService.deleteById(id);
//    }
}