package org.linitly.boot.business.start;

import org.linitly.boot.base.utils.QuartzUtil;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: linxiunan
 * @date: 2020/6/30 10:37
 * @descrption:
 */
@Component
public class StartJobs implements CommandLineRunner {

    @Autowired
    private QuartzUtil quartzUtil;
    @Autowired
    private Scheduler scheduler;

    private static final String TEST_JOB_CORN = "0/3 0 0 0 0 ?";

    @Override
    public void run(String... args) throws Exception {

    }

    private void startOneJob(Class classess) {

    }
}
