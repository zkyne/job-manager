package com.zkyne.jobmanager.common.system;

import com.zkyne.jobmanager.dto.ExcuteJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

@Slf4j
public class JobManager {
    private static SchedulerFactory sf;

    static {
        try {
            sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            sched.start();
            log.info("正在初始化调度系统.....");
        } catch (Exception e) {
            log.error("调度系统初始化异常....", e);
        }
    }

    public static void startJob(String name, String url, String cron, String descript) throws ParseException, SchedulerException {
        Scheduler sched = sf.getScheduler();
        JobDetailImpl job = new JobDetailImpl();
        job.setName(name);
        log.info("正在添加Job：{}", name);
        job.setJobClass(ExcuteJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("url", url);
        jobDataMap.put("descript", descript);
        job.setJobDataMap(jobDataMap);

        CronTriggerImpl cornTrigger = new CronTriggerImpl();
        cornTrigger.setName(name + "cornTrigger");
        cornTrigger.setCronExpression(cron);
        cornTrigger.setJobName(name);
        // 注册并进行调度
        sched.scheduleJob(job, cornTrigger);
    }

    public static void stopJob(String name) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        JobKey jobKey = new JobKey(name);
        sched.deleteJob(jobKey);
    }

    public static void PauseJob(String name) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        JobKey jobKey = new JobKey(name);
        sched.pauseJob(jobKey);
    }

    public static void runstate(String jobid) {
        Trigger.TriggerState state = null;
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
            state = scheduler.getTriggerState(new CronTriggerImpl().getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        log.info("runstate state={}", state);
    }

}
