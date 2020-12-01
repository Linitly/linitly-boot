package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysAdminUserDTO;
import org.linitly.boot.base.dto.SysAdminUserSearchDTO;
import org.linitly.boot.base.entity.SysAdminUser;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Result
@RestController
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysAdminUser")
@Api(tags = "系统用户管理")
public class SysAdminUserController {

    @Autowired
    private SysAdminUserService sysAdminUserService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统用户")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysAdminUserDTO dto, BindingResult bindingResult) {
        sysAdminUserService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统用户")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysAdminUserDTO dto, BindingResult bindingResult) {
        sysAdminUserService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统用户")
    public SysAdminUser findById(@PathVariable Long id) {
        return sysAdminUserService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询系统用户列表")
    public List<SysAdminUser> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber,
                                      @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize,
                                      @RequestBody(required = false) SysAdminUserSearchDTO dto) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysAdminUserService.findAll(dto);
    }

//    @PostMapping("/deleteById/{id}")
//    @ApiOperation(value = "根据id删除系统用户")
//    public void deleteById(@PathVariable Long id) {
//        sysAdminUserService.deleteById(id);
//    }
}