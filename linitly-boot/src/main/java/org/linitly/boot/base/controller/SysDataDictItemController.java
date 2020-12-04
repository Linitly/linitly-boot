package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysDataDictItemDTO;
import org.linitly.boot.base.entity.SysDataDictItem;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysDataDictItemService;
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
@RequestMapping(AdminCommonConstant.URL_PREFIX + "/sysDataDictItem")
@Api(tags = "数据字典详情管理")
public class SysDataDictItemController {

    @Autowired
    private SysDataDictItemService sysDataDictItemService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加数据字典详情")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) SysDataDictItemDTO dto, BindingResult bindingResult) {
        sysDataDictItemService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改数据字典详情")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) SysDataDictItemDTO dto, BindingResult bindingResult) {
        sysDataDictItemService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询数据字典详情")
    public SysDataDictItem findById(@PathVariable Long id) {
        return sysDataDictItemService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询数据字典详情列表")
    public List<SysDataDictItem> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) SysDataDictItem sysDataDictItem) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return sysDataDictItemService.findAll(sysDataDictItem);
    }

    @PostMapping("/findByDictId/{dictId}")
    @ApiOperation(value = "根据字典id查询字典数据")
    public List<SysDataDictItem> findByDictId(@PathVariable Long dictId) {
        return sysDataDictItemService.findByDictId(dictId);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除数据字典详情")
    public void deleteById(@PathVariable Long id) {
        sysDataDictItemService.deleteById(id);
    }
}