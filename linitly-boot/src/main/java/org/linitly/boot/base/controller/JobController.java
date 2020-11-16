package org.linitly.boot.base.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobInsertAndUpdateDTO;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobSearchDTO;
import org.linitly.boot.base.utils.page.ResponseResultUtil;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.entity.SysQuartzJob;
import org.linitly.boot.base.helper.entity.ResponseResult;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.service.SysQuartzJobService;
import org.linitly.boot.base.utils.valid.BindingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping("/job")
//@RequestMapping(AdminCommonConstant.URL_PREFIX + "/job")
@Api(tags = "定时任务管理")
public class JobController {

    @Autowired
    private SysQuartzJobService jobService;

    @PostMapping("/add")
    @ApiOperation(value = "添加定时任务")
    public ResponseResult add(@RequestBody @Validated SysQuartzJobInsertAndUpdateDTO dto, BindingResult bindingResult) {
        jobService.insertJob(dto);
        return new ResponseResult();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改定时任务")
    public ResponseResult update(@RequestBody @Validated({Default.class, UpdateValidGroup.class}) SysQuartzJobInsertAndUpdateDTO dto,
                                 BindingResult bindingResult) {
        jobService.updateJob(dto);
        return new ResponseResult();
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询定时任务列表")
    public ResponseResult<List<SysQuartzJob>> list(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber,
                                                   @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize,
                                                   @RequestBody(required = false) SysQuartzJobSearchDTO dto) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        List<SysQuartzJob> jobs = jobService.findQuartzJobs(dto);
        return ResponseResultUtil.getResponseResult(jobs);
    }

    @PostMapping("/pause_or_resume/{encryptId}")
    @ApiOperation(value = "启动或暂停一个任务")
    public ResponseResult pauseOrResume(@PathVariable String encryptId) {
        jobService.pauseOrResumeJob(encryptId);
        return new ResponseResult();
    }

    @PostMapping("/delete/{encryptId}")
    @ApiOperation(value = "删除任务")
    public ResponseResult delete(@PathVariable String encryptId) {
        jobService.deleteById(encryptId);
        return new ResponseResult();
    }
}
