package com.cnksi.utils;

import com.cnksi.job.KJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Created by xyl on 2017/8/8, 008.
 */
public class JobKit {

    //    private static Log log = Log.getLog(JobKit.class);
//    public static QuartzPlugin plugin;

    private static SchedulerFactory schedulerFactory;
    private static Scheduler scheduler;
    private static String JOB_GROUP = Scheduler.DEFAULT_GROUP;
    private static String TRIGGER_PREFIX = "trigger_"; //trigger统一前缀标注


    static {
        init();
    }


    private static void init() {
        schedulerFactory = new StdSchedulerFactory();

        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
//            LogKit.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

//    public static void addJob(String jobCronExp, Job job) {
//        plugin.add(jobCronExp, job);
//    }

    /**
     * 启动一个任务
     *
     * @param jobName        任务名称
     * @param cronExpression 时间表达式
     * @param actionUrl 执行业务逻辑action url
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void startJob(String jobName, String cronExpression, String actionUrl) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(KJob.class).withIdentity(jobName, JOB_GROUP).usingJobData("action", actionUrl).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(TRIGGER_PREFIX + jobName, TRIGGER_PREFIX + JOB_GROUP)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

    }

    /**
     * 重置任务<br/>
     * 不存在的任务，添加一个新的调度任务<br/>
     * 已存在任务，删除后重新添加
     * @param jobName
     * @param cronExpression
     * @param actionUrl
     */
    public static void resetJob(String jobName, String cronExpression, String actionUrl) throws SchedulerException {
        if (checkExistsJob(jobName)){
            deleteJob(jobName);
        }
        startJob(jobName, cronExpression, actionUrl);
    }

    /**
     * 检查定时器状态
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static Trigger.TriggerState checkState(String name) throws SchedulerException {
        return scheduler.getTriggerState(new TriggerKey(getTriggerPrefix(name), getTriggerPrefix(JOB_GROUP)));
    }

    /**
     * 返回拼接trigger_前缀的字符串
     * @param name
     * @return
     */
    private static String getTriggerPrefix(String name) {
        return TRIGGER_PREFIX + name;
    }

    public static boolean deleteJob(String jobName) throws SchedulerException {
        return scheduler.deleteJob(new JobKey(jobName, JOB_GROUP));
    }

    public static boolean checkExistsJob(String jobName) throws SchedulerException{
        return scheduler.checkExists(new JobKey(jobName, JOB_GROUP));
    }

    public static void main(String[] args) throws Exception {

        startJob("job1", "0/5 * * * * ?", "http://localhost:90/ess/app/smstemplate/enable?id=590098556094386176");

    }
}
