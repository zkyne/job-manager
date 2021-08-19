package com.zkyne.jobmanager.listenter;

import com.zkyne.jobmanager.po.Crontab;
import com.zkyne.jobmanager.service.ICrontabService;
import com.zkyne.jobmanager.common.system.JobManager;
import com.zkyne.jobmanager.common.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author zkyne
 */
@Component
@Slf4j
public class StartJobListener{

	@Resource
	private ICrontabService crontabService;


	@EventListener
	public void registJob(ContextRefreshedEvent evt){
        if (evt.getApplicationContext().getParent() == null) {
         try {
				createSitemap();			
			} catch (ParseException | SchedulerException e) {
			 log.error("任务调度系统初始化异常.....{}",e.getMessage(), e);
			}
        }
    }

    private void createSitemap() throws ParseException, SchedulerException {
		log.info("正在初始化任务调度系统.....");
    	List<Crontab> crontabs = crontabService.selectAllEnableCrontab();
    	if(!DataUtil.isEmpty(crontabs)){
			for(Crontab crontab:crontabs){
				if(crontab.getStatus() != Crontab.DISABLED){
					JobManager.startJob(crontab.getJobId(), crontab.getUrl(), crontab.getCronExp(),crontab.getDescript());
				}
			}
		}else{
			log.info("正在初始化任务调度系统.....暂无定时任务...");
		}
    }
}

	
	

