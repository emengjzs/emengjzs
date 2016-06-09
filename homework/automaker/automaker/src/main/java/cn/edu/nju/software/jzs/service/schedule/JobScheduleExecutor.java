package cn.edu.nju.software.jzs.service.schedule;

import cn.edu.nju.software.jzs.service.JobRunFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by emengjzs on 2016/4/16.
 */
@Service
public class JobScheduleExecutor implements Job {

    @Autowired
    JobRunFacade jobRunFacade;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务成功运行");
        Long scheduleJob = (Long) context.getMergedJobDataMap().get("jobId");
        System.out.println("任务名称 = [" + scheduleJob + "]");
        jobRunFacade.run(scheduleJob);
    }

}
