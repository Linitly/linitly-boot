package org.linitly.boot.base.utils;

import org.linitly.boot.base.exception.QuartzException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: linxiunan
 * @date: 2020/6/29 16:36
 * @descrption:
 */
@Component
public class QuartzUtil {

    private static String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP_NAME";

    @Autowired
    private Scheduler scheduler;

    // addSimpleJob简略版无额外参数
    public void addSimpleJob(Integer interval, TimeUnit timeUnit, Class<? extends Job> jobClass) {
        addSimpleJob(jobClass.getName(), interval, timeUnit, jobClass);
    }

    //addSimpleJob简略版无额外参数
    public void addSimpleJob(String jobName, Integer interval, TimeUnit timeUnit, Class<? extends Job> jobClass) {
        addSimpleJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, interval, timeUnit, null, jobClass);
    }

    // addSimpleJob简略版有额外参数
    public void addSimpleJob(Integer interval, TimeUnit timeUnit, Map<String, Object> extraParam, Class<? extends Job> jobClass) {
        addSimpleJob(jobClass.getName(), interval, timeUnit, extraParam, jobClass);
    }

    // addSimpleJob简略版有额外参数
    public void addSimpleJob(String jobName, Integer interval, TimeUnit timeUnit, Map<String, Object> extraParam, Class<? extends Job> jobClass) {
        addSimpleJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, interval, timeUnit, extraParam, jobClass);
    }

    // addCronJob简略版无额外参数
    public void addCronJob(String cronExpression, Class<? extends Job> jobClass) {
        addCronJob(jobClass.getName(), cronExpression, jobClass);
    }

    // addCronJob简略版无额外参数
    public void addCronJob(String jobName, String cronExpression, Class<? extends Job> jobClass) {
        addCronJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, cronExpression, null, jobClass);
    }

    //addCronJob简略版有额外参数
    public void addCronJob(String cronExpression, Map<String, Object> extraParam, Class<? extends Job> jobClass) {
        addCronJob(jobClass.getName(), cronExpression, extraParam, jobClass);
    }

    // addCronJob简略版有额外参数
    public void addCronJob(String jobName, String cronExpression, Map<String, Object> extraParam, Class<? extends Job> jobClass) {
        addCronJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, cronExpression, extraParam, jobClass);
    }

    // modifySimpleJobTime简略版
    public void modifySimpleJobTime(String triggerName, Integer interval, TimeUnit timeUnit) {
        modifySimpleJobTime(triggerName, TRIGGER_GROUP_NAME, interval, timeUnit);
    }

    // modifyCronJobTime简略版
    public void modifyCronJobTime(String triggerName, String cronExpression) {
        modifyCronJobTime(triggerName, TRIGGER_GROUP_NAME, cronExpression);
    }

    // removeJob简略版
    public void removeJob(String jobName) {
        removeJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME);
    }

    // pauseJob
    public void pauseJob(String jobName) {
        pauseJob(jobName, JOB_GROUP_NAME);
    }

    // resumeJob
    public void resumeJob(String jobName) {
        resumeJob(jobName, JOB_GROUP_NAME);
    }

    // checkExistJob
    public boolean checkExistJob(Class<? extends Job> jobClass) {
        return checkExistJob(jobClass.getName(), JOB_GROUP_NAME);
    }

    // checkExistTrigger
    public boolean checkExistTrigger(Class<? extends Job> jobClass) {
        return checkExistTrigger(jobClass.getName(), TRIGGER_GROUP_NAME);
    }

    // 检查是否存在某个任务类已经在quartz调度中
    public boolean checkExistJobAndTrigger(Class<? extends Job> jobClass) {
        return checkExistJob(jobClass) && checkExistTrigger(jobClass);
    }

    // triggerJob
    public void triggerJob(Class<? extends Job> jobClass) {
        triggerJob(jobClass.getName(), JOB_GROUP_NAME);
    }

    /**
     * @author linxiunan
     * @date 10:32 2020/6/30
     * @description 检查是否存在任务
     */
    public boolean checkExistJob(String jobName, String jobGroup) {
        try {
            return scheduler.checkExists(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("check the job if exist fail");
        }
    }

    /**
     * @author linxiunan
     * @date 10:32 2020/6/30
     * @description 检查是否存在触发器
     */
    public boolean checkExistTrigger(String triggerName, String triggerGroup) {
        try {
            return scheduler.checkExists(TriggerKey.triggerKey(triggerName, triggerGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("check the trigger if exist fail");
        }
    }

    /**
     * @author linxiunan
     * @date 11:44 2020/6/30
     * @description 触发定时任务
     */
    public void triggerJob(String jobName, String jobGroup) {
        try {
            scheduler.triggerJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("trigger the job fail，jobName is " + jobName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:27 2020/6/30
     * @description 启动调度器
     */
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("start the scheduler fail");
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 添加简单定时任务
     */
    public void addSimpleJob(String jobName, String jobGroup, String triggerName, String triggerGroup, Integer interval, TimeUnit timeUnit, Map<String, Object> extraParam, Class<? extends Job> JobClass) {
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(JobClass)
                    .withIdentity(jobName, jobGroup)
                    .build();
            if (extraParam != null) {
                jobDetail.getJobDataMap().putAll(extraParam);
            }
            SimpleTrigger simpleTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerName, triggerGroup)
                    .withSchedule(getSimpleScheduleBuilder(interval, timeUnit))
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, simpleTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("add simple job fail，jobName is " + jobName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 添加cron定时任务
     */
    public void addCronJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String cronExpression, Map<String, Object> extraParam, Class<? extends Job> JobClass) {
        try {
            JobDetail jobDetail = JobBuilder
                    .newJob(JobClass)
                    .withIdentity(jobName, jobGroup)
                    .build();
            if (extraParam != null) {
                jobDetail.getJobDataMap().putAll(extraParam);
            }
            CronTrigger cronTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerName, triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("add cron job fail，jobName is " + jobName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 修改simple任务的时间的触发时间
     */
    public void modifySimpleJobTime(String triggerName, String triggerGroup, Integer interval, TimeUnit timeUnit) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            SimpleTrigger oldTrigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
            if (oldTrigger == null) {
                throw new QuartzException("can not find the old trigger, please check your jobName");
            }
            //unit:milliseconds
            Long oldInterval = oldTrigger.getRepeatInterval();
            if (!oldInterval.equals(getMilliseconds(interval, timeUnit))) {
                SimpleTrigger simpleTrigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(triggerName, triggerGroup)
                        .withSchedule(getSimpleScheduleBuilder(interval, timeUnit))
                        .startNow()
                        .build();
                scheduler.rescheduleJob(triggerKey, simpleTrigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("modify the simple job fail，jobName is " + triggerName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:08 2020/6/30
     * @description 修改cron任务的时间的触发时间
     */
    public void modifyCronJobTime(String triggerName, String triggerGroup, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (oldTrigger == null) {
                throw new QuartzException("can not find the old trigger, please check your jobName");
            }
            String oldCronExpression = oldTrigger.getCronExpression();
            if (!oldCronExpression.equalsIgnoreCase(cronExpression)) {
                CronTrigger cronTrigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(triggerName, triggerGroup)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("modify the cron job fail，jobName is " + triggerName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 删除指定定时任务
     */
    public void removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("remove the job fail，jobName is " + jobName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 暂停定时任务
     */
    public void pauseJob(String jobName, String jobGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("pause the job fail，jobName is " + jobName);
        }
    }

    /**
     * @author linxiunan
     * @date 10:09 2020/6/30
     * @description 恢复定时任务
     */
    public void resumeJob(String jobName, String jobGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new QuartzException("resume the job fail，jobName is " + jobName);
        }
    }

    private SimpleScheduleBuilder getSimpleScheduleBuilder(Integer interval, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return SimpleScheduleBuilder.repeatSecondlyForever(interval);
            case MINUTES:
                return SimpleScheduleBuilder.repeatMinutelyForever(interval);
            case HOURS:
                return SimpleScheduleBuilder.repeatHourlyForever(interval);
            default:
                throw new QuartzException("The time interval set is out of range");
        }
    }

    private Long getMilliseconds(Integer interval, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return (long) (1000 * interval);
            case MINUTES:
                return (long) (60 * 1000 * interval);
            case HOURS:
                return (long) (60 * 60 * 1000 * interval);
            default:
                throw new QuartzException("Interval conversion error");
        }
    }
}
