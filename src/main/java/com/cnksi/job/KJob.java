package com.cnksi.job;

import com.jfinal.kit.HttpKit;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by xyl on 2017/8/7, 007.
 */
public class KJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String action = context.getMergedJobDataMap().getString("action");
        System.out.println(action);

        try {
            String result = HttpKit.get(action);
            System.out.println(result);
        } catch (Exception e) {
            throw new JobExecutionException("执行调度任务业务逻辑错误", e);
        }

    }
}
