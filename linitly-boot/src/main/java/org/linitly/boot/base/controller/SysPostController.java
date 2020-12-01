package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysPostDTO;
import org.linitly.boot.base.entity.SysPost;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysPostService;
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
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysPost")
@Api(tags = "系统岗位管理")
public class SysPostController {

    @Autowired
    private SysPostService sysPostService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加系统岗位")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysPostDTO dto, BindingResult bindingResult) {
        sysPostService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改系统岗位")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysPostDTO dto, BindingResult bindingResult) {
        sysPostService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询系统岗位")
    public SysPost findById(@PathVariable Long id) {
        return sysPostService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询系统岗位列表")
    public List<SysPost> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber,
                                 @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize,
                                 @RequestBody(required = false) SysPost sysPost) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysPostService.findAll(sysPost);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除系统岗位")
    public void deleteById(@PathVariable Long id) {
        sysPostService.deleteById(id);
    }
}