package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysMenuDTO;
import org.linitly.boot.base.entity.SysMenu;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysMenuService;
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
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysMenu")
@Api(tags = "系统菜单管理")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统菜单")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysMenuDTO dto, BindingResult bindingResult) {
        sysMenuService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统菜单")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysMenuDTO dto, BindingResult bindingResult) {
        sysMenuService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统菜单")
    public SysMenu findById(@PathVariable Long id) {
        return sysMenuService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询系统菜单列表")
    public List<SysMenu> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) SysMenu sysMenu) {
        PageHelper.startPage(pageNumber, pageSize, "sort asc, id desc");
        return sysMenuService.findAll(sysMenu);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除系统菜单")
    public void deleteById(@PathVariable Long id) {
        sysMenuService.deleteById(id);
    }
}