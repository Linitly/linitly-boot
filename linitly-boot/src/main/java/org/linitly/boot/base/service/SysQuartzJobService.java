package org.linitly.boot.base.service;

import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobInsertAndUpdateDTO;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobSearchDTO;
import org.linitly.boot.base.entity.SysQuartzJob;
import org.linitly.boot.base.helper.entity.ResponseResult;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/5/26 17:14
 * @descrption:
 */
public interface SysQuartzJobService {

    /**
     * @author linxiunan
     * @date 17:17 2020/5/26
     * @description job列表
     */
    List<SysQuartzJob> findQuartzJobs(SysQuartzJobSearchDTO dto);

    /**
     * @author linxiunan
     * @date 17:17 2020/5/26
     * @description 添加job
     */
    ResponseResult insertJob(SysQuartzJobInsertAndUpdateDTO dto);

    /**
     * @author linxiunan
     * @date 17:17 2020/5/26
     * @description 更新job
     */
    ResponseResult updateJob(SysQuartzJobInsertAndUpdateDTO dto);

    /**
     * @author linxiunan
     * @date 17:17 2020/5/26
     * @description 删除job
     */
    void deleteById(String encryptId);

    /**
     * @author linxiunan
     * @date 15:29 2020/5/27
     * @description 暂停或者唤醒任务
     */
    void pauseOrResumeJob(String encryptId);
}
