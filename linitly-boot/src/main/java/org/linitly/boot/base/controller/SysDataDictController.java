package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysDataDictDTO;
import org.linitly.boot.base.entity.SysDataDict;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
@Result
@RestController
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysDataDict")
@Api(tags = "数据字典管理")
public class SysDataDictController {

    @Autowired
    private SysDataDictService sysDataDictService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加数据字典")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysDataDictDTO dto, BindingResult bindingResult) {
        sysDataDictService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改数据字典")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysDataDictDTO dto, BindingResult bindingResult) {
        sysDataDictService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询数据字典")
    public SysDataDict findById(@PathVariable Long id) {
        return sysDataDictService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询数据字典列表")
    public List<SysDataDict> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) SysDataDict sysDataDict) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysDataDictService.findAll(sysDataDict);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除数据字典")
    public void deleteById(@PathVariable Long id) {
        sysDataDictService.deleteById(id);
    }
}