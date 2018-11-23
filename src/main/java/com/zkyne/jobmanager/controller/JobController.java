package com.zkyne.jobmanager.controller;

import com.google.common.collect.Maps;
import com.zkyne.jobmanager.aspect.LogHandle;
import com.zkyne.jobmanager.common.constant.Constants;
import com.zkyne.jobmanager.common.enums.CodeEnum;
import com.zkyne.jobmanager.po.Crontab;
import com.zkyne.jobmanager.service.ICrontabService;
import com.zkyne.jobmanager.common.system.JobManager;
import com.zkyne.jobmanager.common.util.KeyGenerator;
import com.zkyne.jobmanager.common.util.Page;
import com.zkyne.jobmanager.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: JobController
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/13 10:43
 */
@Controller
@RequestMapping("job")
@Slf4j
public class JobController {
    @Resource
    private ICrontabService crontabService;


    @RequestMapping("query")
    public String query(@RequestParam("key") String key, @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, Model model) throws Exception {
        if(StringUtils.isNotBlank(key)){
            key = URLDecoder.decode(key, "UTF-8");
        }
        if(pageNo == null || pageNo < 1){
            pageNo = 1;
        }
        if(pageSize == null || pageSize < 1 || pageSize > 10){
            pageSize = 10;
        }
        Page<Crontab> page = crontabService.selectByPage(pageNo,pageSize,key);
        Map<String,String> query = Maps.newHashMap();
        if(StringUtils.isNotBlank(key)){
            key = URLEncoder.encode(key,"UTF-8");
        }
        query.put("key",key);
        page.setUri("/job/query");
        page.setQuery(query);
        model.addAttribute("page",page);
        model.addAttribute("crontabs",page.getResult());
        return "index/queryResult";
    }



    @RequestMapping(value = "/check", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String, Object> checkUrl(@RequestParam("jobUrl") String jobUrl) {
        log.info("(校验任务Url) ->参数jobUrl:{}", jobUrl);
        if (StringUtils.isBlank(jobUrl)) {
            return ResultUtils.error("路径Url不能为空");
        }
        Crontab crontab = crontabService.findByJobUrl(jobUrl);
        if (crontab == null) {
            return ResultUtils.success();
        } else {
            return ResultUtils.error("该路径任务已存在");
        }
    }

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String, Object> addCrontab(@RequestParam("jobUrl") String jobUrl, @RequestParam("cronExp") String cronExp,
                                          @RequestParam("status") Integer status, @RequestParam("descript") String descript) {
        log.info("(新建任务)->参数jobUrl:{},cronExp:{},status:{},descript:{}", jobUrl, cronExp, status, descript);
        Map<String, Object> result = checkParams(jobUrl, cronExp, descript);
        if ((int) result.get(Constants.CODE) != CodeEnum.CODE_SUCCESS.getCode()) {
            return result;
        }
        if (status == null) {
            status = Crontab.DISABLED;
        }
        Crontab crontab = new Crontab();
        crontab.setJobId(KeyGenerator.getUUID());
        crontab.setUrl(jobUrl);
        crontab.setCronExp(cronExp);
        crontab.setDescript(descript);
        crontab.setStatus(status);
        crontab.setPerformdate(new Date());
        int save = crontabService.saveCrontab(crontab);
        if (save != 1) {
            return ResultUtils.error("新增失败");
        }
        if (status == Crontab.USABLE) {
            try {
                JobManager.startJob(crontab.getJobId(), crontab.getUrl(), crontab.getCronExp(), crontab.getDescript());
                return ResultUtils.success();
            } catch (ParseException | SchedulerException e) {
                log.error("(新建任务) -> jobId:{},执行失败异常e:{}", crontab.getJobId(), e);
                try {
                    JobManager.startJob(crontab.getJobId(), crontab.getUrl(), crontab.getCronExp(), crontab.getDescript());
                    return ResultUtils.success();
                } catch (ParseException | SchedulerException e1) {
                    log.error("(新建任务) -> jobId:{},继续执行失败异常e:{}", crontab.getJobId(), e);
                    return ResultUtils.error("新增成功,执行任务失败");
                }
            }
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/modify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String, Object> modifyCrontab(@RequestParam("jobId") String jobId, @RequestParam("jobUrl") String jobUrl,
                                             @RequestParam("cronExp") String cronExp, @RequestParam("descript") String descript) {
        log.info("(更新任务) -> 参数jobId:{},jonUrl:{},cronExp:{},descript:{}", jobId, jobUrl, cronExp, descript);
        if (StringUtils.isBlank(jobId)) {
            return ResultUtils.error("参数异常");
        }
        Crontab crontab = crontabService.findById(jobId);
        if (crontab == null) {
            return ResultUtils.error("参数异常");
        }
        Map<String, Object> result = checkParams(jobUrl, cronExp, descript);
        if ((int) result.get(Constants.CODE) != CodeEnum.CODE_SUCCESS.getCode()) {
            return result;
        }
        crontab.setUrl(jobUrl);
        crontab.setCronExp(cronExp);
        crontab.setDescript(descript);
        int update = crontabService.updateById(crontab);
        if (update != 1) {
            return ResultUtils.error("更新失败");
        }
        if (crontab.getStatus() == Crontab.USABLE) {
            try {
                JobManager.stopJob(crontab.getJobId());
                try {
                    JobManager.startJob(crontab.getJobId(), crontab.getUrl(), crontab.getCronExp(), crontab.getDescript());
                    return ResultUtils.success();
                } catch (ParseException e) {
                    log.error("(更新任务) -> jobId:{}任务更新成功,停止任务成功,重启任务失败异常e", jobId, e);
                    return ResultUtils.error("更新成功,重启任务失败");
                }
            } catch (SchedulerException e) {
                log.error("(更新任务) -> jobId:{}任务更新成功,停止任务失败异常e", jobId, e);
                return ResultUtils.error("更新成功,重启任务失败");
            }
        }
        return ResultUtils.success();
    }


    private Map<String, Object> checkParams(String jobUrl, String cronExp, String descript) {
        if (StringUtils.isBlank(jobUrl)) {
            return ResultUtils.error("路径Url不能为空");
        }
        if (StringUtils.isBlank(cronExp)) {
            return ResultUtils.error("执行规则不能为空");
        }
        if (StringUtils.isBlank(descript)) {
            return ResultUtils.error("任务描述不能为空");
        }
        if (descript.length() > 180) {
            return ResultUtils.error("任务描述不能超过180个字符");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/reverse", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String, Object> reverseCrontab(@RequestParam("jobId") String jobId, @RequestParam("isStop") Boolean isStop) {
        log.info("(切换任务) -> 参数jobId:{},isStop:{}", jobId, isStop);
        if (StringUtils.isBlank(jobId)) {
            return ResultUtils.error("参数异常");
        }
        if (isStop == null) {
            return ResultUtils.error("参数异常");
        }
        Crontab crontab = crontabService.findById(jobId);
        if (crontab == null) {
            return ResultUtils.error("参数异常");
        }
        if (isStop) {
            if (crontab.getStatus() == Crontab.USABLE) {
                crontab.setStatus(Crontab.DISABLED);
                int update = crontabService.updateById(crontab);
                if (update > 0) {
                    try {
                        JobManager.stopJob(jobId);
                        return ResultUtils.success();
                    } catch (SchedulerException e) {
                        log.error("(停止任务) -> 任务jobId:{},停止失败e:{}", jobId, e);
                        try {
                            JobManager.stopJob(jobId);
                            return ResultUtils.success();
                        } catch (SchedulerException e1) {
                            log.error("(停止任务) -> 任务jobId:{},继续停止失败e:{}", jobId, e);
                            return ResultUtils.error("任务状态更新成功,执行停止失败");
                        }
                    }
                } else {
                    return ResultUtils.error("任务状态更新失败");
                }
            }
        } else {
            if (crontab.getStatus() == Crontab.DISABLED) {
                crontab.setStatus(Crontab.USABLE);
                int update = crontabService.updateById(crontab);
                if (update > 0) {
                    try {
                        JobManager.startJob(jobId, crontab.getUrl(), crontab.getCronExp(), crontab.getDescript());
                        return ResultUtils.success();
                    } catch (ParseException | SchedulerException e) {
                        log.error("(开启任务) -> 任务jobId:{},开启失败e:{}", jobId, e);
                        try {
                            JobManager.startJob(jobId, crontab.getUrl(), crontab.getCronExp(), crontab.getDescript());
                            return ResultUtils.success();
                        } catch (ParseException | SchedulerException e1) {
                            log.error("(开启任务) -> 任务jobId:{},继续开启失败e:{}", jobId, e);
                            return ResultUtils.error("任务状态更新成功,执行开启失败");
                        }
                    }
                } else {
                    return ResultUtils.error("任务状态更新失败");
                }
            }
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String, Object> deleteCrontab(@RequestParam("jobId") String jobId) {
        log.info("(删除任务) -> 参数jobId:{}", jobId);
        if (StringUtils.isBlank(jobId)) {
            return ResultUtils.error("参数异常");
        }
        Crontab crontab = crontabService.findById(jobId);
        if (crontab == null) {
            return ResultUtils.error("参数异常");
        }
        int delete = crontabService.deleteById(jobId);
        if (delete != 1) {
            return ResultUtils.error("删除失败");
        }
        try {
            JobManager.stopJob(jobId);
            return ResultUtils.success();
        } catch (SchedulerException e) {
            log.error("(删除任务) -> jobId:{}任务删除成功,执行停止任务失败异常e{}", jobId, e);
            try {
                JobManager.stopJob(jobId);
                return ResultUtils.success();
            } catch (SchedulerException e1) {
                log.error("(删除任务) -> jobId:{}任务删除成功,继续执行停止任务失败异常e{}", jobId, e);
                return ResultUtils.error("任务删除成功,停止任务失败");
            }
        }
    }
}
