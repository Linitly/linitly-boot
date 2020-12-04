package org.linitly.boot.base.dao;

import org.linitly.boot.base.annotation.DeleteBackup;
import org.linitly.boot.base.dto.SysQuartzJobSearchDTO;
import org.linitly.boot.base.entity.SysQuartzJob;

import java.util.List;

public interface SysQuartzJobMapper {

    SysQuartzJob findByJobName(String jobName);

    SysQuartzJob findById(Long id);

    List<SysQuartzJob> findJobs(SysQuartzJobSearchDTO dto);

    void insert(SysQuartzJob job);

    void updateStatusById(SysQuartzJob job);

    void updateById(SysQuartzJob job);

    @DeleteBackup
    void deleteById(Long id);
}
