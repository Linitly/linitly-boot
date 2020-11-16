package org.linitly.boot.base.dao;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.dto.sys_quatrtz_job.SysQuartzJobSearchDTO;
import org.linitly.boot.base.entity.SysQuartzJob;

import java.util.List;

public interface SysQuartzJobMapper {

    SysQuartzJob findByJobName(String jobName);

    SysQuartzJob findById(Long id);

    List<SysQuartzJob> findJobs(SysQuartzJobSearchDTO dto);

    void insert(SysQuartzJob job);

    void updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    void updateById(SysQuartzJob job);

    void deleteById(Long id);
}
