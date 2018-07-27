package com.zkyne.jobmanager.dto;

import com.zkyne.jobmanager.common.util.DateUtil;
import com.zkyne.jobmanager.common.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;

/**
 * @ClassName: ExcuteJob
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/11 16:05
 */
@Slf4j
public class ExcuteJob implements Job {
    public ExcuteJob() {

    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Trigger.TriggerState state = null;
        Trigger trigger = context.getTrigger();
        TriggerKey strigger = trigger.getKey();
        String urlString = context.getJobDetail().getJobDataMap().getString("url");
        String descript = context.getJobDetail().getJobDataMap().getString("descript");
        String httpResult = null;
        try {
            state = context.getScheduler().getTriggerState(strigger);
            try {
                httpResult = HttpUtils.doGet(urlString);
            } catch (Exception e) {
                log.error("执行job:{} 发生异常error:{}",descript, e);
            }
        } catch (SchedulerException e) {
            log.error("获取job:{},的当前状态发生异常error:{}",descript, e);
        }
        log.info("执行job: {} ,发送的URL: {} ,执行时间: {},当前任务状态: {},任务执行结果: {}",descript, urlString, DateUtil.format(new Date(),DateUtil.F24_PATTERN), state, httpResult);
    }
}
