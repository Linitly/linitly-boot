package org.linitly.boot.base.service;

import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.dao.SysQuartzJobMapper;
import org.linitly.boot.base.enums.JobStatusEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobInsertAndUpdateDTO;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobSearchDTO;
import org.linitly.boot.base.utils.QuartzUtil;
import org.linitly.boot.base.utils.log.ClassUtil;
import org.linitly.boot.base.entity.SysQuartzJob;
import org.linitly.boot.base.helper.entity.ResponseResult;
import org.linitly.boot.base.utils.encrypt.AESUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/5/26 17:34
 * @descrption:
 */
@Service
public class SysQuartzJobService {

    @Autowired
    private SysQuartzJobMapper jobMapper;
    @Autowired
    private QuartzUtil quartzUtil;

    public List<SysQuartzJob> findQuartzJobs(SysQuartzJobSearchDTO dto) {
        return jobMapper.findJobs(dto);
    }

    /**
     * @author linxiunan
     * @date 10:03 2020/5/27
     * @description 首先根据任务名称查询是否存在任务，如果存在返回错误信息，若不存在将dto中信息复制到任务信息中，初始化任务状态为停止，
     * 保存任务信息，但是不立即启动(添加时任务不立刻启动原则)
     */
    public ResponseResult insertJob(SysQuartzJobInsertAndUpdateDTO dto) {
        SysQuartzJob job = insertOrUpdateJob(dto);
        jobMapper.insert(job);
        return new ResponseResult();
    }

    /**
     * @author linxiunan
     * @date 11:27 2020/5/27
     * @description 首先根据id查询原任务信息，如果原任务执行类和修改后的执行类不一致，则将原任务删除，否则就停止该任务，
     * 根据任务名称查询是否存在任务，如果存在返回错误信息，若不存在将dto中信息复制到任务信息中，同时设置定时任务状态为停止，
     * 修改定时任务信息(修改时任务不立刻启动原则)
     */
    public ResponseResult updateJob(SysQuartzJobInsertAndUpdateDTO dto) {
        SysQuartzJob job = jobMapper.findById(dto.getId());
        if (!job.getJobClassName().equals(dto.getJobClassName())) {
            quartzUtil.removeJob(job.getJobClassName());
        } else {
            if (job.getStatus().equals(JobStatusEnum.RUNNING.getStatus())) {
                quartzUtil.pauseJob(job.getJobClassName());
            }
        }
        job = insertOrUpdateJob(dto);
        jobMapper.updateById(job);
        return new ResponseResult();
    }

    /**
     * @author linxiunan
     * @date 15:15 2020/5/27
     * @description 删除任务：首先解密id，通过解密后的id查询对应的job信息，如果job信息不为空，删除quartz中的job，再删除对应的job信息
     */
    public void deleteById(String encryptId) {
        Long id = AESUtil.getAdminId(encryptId);
        SysQuartzJob job = jobMapper.findById(id);
        if (job != null) {
            quartzUtil.removeJob(job.getJobClassName());
        }
        jobMapper.deleteById(id);
    }

    /**
     * @author linxiunan
     * @date 15:23 2020/5/27
     * @description quartz启动job
     */
    public void schedulerJob(SysQuartzJob job) {
        Class classes = ClassUtil.getClassByName(job.getJobClassName());
        quartzUtil.addCronJob(job.getCronExpression().trim(), classes);
    }

    /**
     * @author linxiunan
     * @date 15:29 2020/5/27
     * @description
     */
    public void pauseOrResumeJob(String encryptId) {
        Long id = AESUtil.getAdminId(encryptId);
        SysQuartzJob job = jobMapper.findById(id);
        if (job == null) return;
        if (job.getStatus().equals(JobStatusEnum.RUNNING.getStatus())) {
            // 目前为运行状态，即希望停止任务
            quartzUtil.pauseJob(job.getJobClassName());
            jobMapper.updateStatusById(id, JobStatusEnum.PAUSED.getStatus());
        } else if (job.getStatus().equals(JobStatusEnum.PAUSED.getStatus())) {
            // 目前为停止状态，即希望启动
            if (quartzUtil.checkExistJobAndTrigger(ClassUtil.getClassByName(job.getJobClassName().trim()))) {
                quartzUtil.resumeJob(job.getJobClassName());
            } else {
                schedulerJob(job);
            }
            jobMapper.updateStatusById(id, JobStatusEnum.RUNNING.getStatus());
        }
    }

    /**
     * @author linxiunan
     * @date 11:38 2020/5/27
     * @description 添加或修改job的前置工作，验证是否已存在相同的任务名，初始化job并设置状态
     */
    private SysQuartzJob insertOrUpdateJob(SysQuartzJobInsertAndUpdateDTO dto) {
        SysQuartzJob job = jobMapper.findByJobName(dto.getJobName());
        if (job != null) {
            throw new CommonException(GlobalConstant.GENERAL_ERROR, "任务名称已存在");
        } else {
            job = new SysQuartzJob();
        }
        BeanUtils.copyProperties(dto, job);
        job.setStatus(JobStatusEnum.PAUSED.getStatus());
        return job;
    }
}
