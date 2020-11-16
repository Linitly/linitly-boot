package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobInsertAndUpdateDTO;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobSearchDTO;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.entity.SysQuartzJob;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysQuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@Result
@RestController
@RequestMapping("/job")
//@RequestMapping(AdminCommonConstant.URL_PREFIX + "/job")
@Api(tags = "定时任务管理")
public class JobController {

    @Autowired
    private SysQuartzJobService jobService;

    @PostMapping("/add")
    @ApiOperation(value = "添加定时任务")
    public void add(@RequestBody @Validated SysQuartzJobInsertAndUpdateDTO dto, BindingResult bindingResult) {
        jobService.insertJob(dto);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改定时任务")
    public void update(@RequestBody @Validated({Default.class, UpdateValidGroup.class}) SysQuartzJobInsertAndUpdateDTO dto,
                                 BindingResult bindingResult) {
        jobService.updateJob(dto);
    }

    @Pagination
    @PostMapping("/list")
    @ApiOperation(value = "查询定时任务列表")
    public List<SysQuartzJob> list(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber,
                                                   @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize,
                                                   @RequestBody(required = false) SysQuartzJobSearchDTO dto) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return jobService.findQuartzJobs(dto);
    }

    @PostMapping("/pause_or_resume/{encryptId}")
    @ApiOperation(value = "启动或暂停一个任务")
    public void pauseOrResume(@PathVariable String encryptId) {
        jobService.pauseOrResumeJob(encryptId);
    }

    @PostMapping("/delete/{encryptId}")
    @ApiOperation(value = "删除任务")
    public void delete(@PathVariable String encryptId) {
        jobService.deleteById(encryptId);
    }
}
