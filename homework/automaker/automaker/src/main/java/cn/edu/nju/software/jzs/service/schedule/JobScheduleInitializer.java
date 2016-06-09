package cn.edu.nju.software.jzs.service.schedule;

import cn.edu.nju.software.jzs.dao.JobDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/16.
 */
@Service
public class JobScheduleInitializer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    JobDao jobDao;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    public void init() throws SchedulerException {
        List<Job> jobList = jobDao.getScheduleJobList();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (Job job : jobList) {
            addJobToSheduler(job);
        }

    }


    public void addJobToSheduler(Job job) throws SchedulerException {
        TriggerKey key = getTaskId(job);
        if (! existsTrigger(job)) {
            JobConfig jobConfig = JSON.parseObject(job.getJobConfig(), JobConfig.class);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig
                    .getCronExpression());
            JobDetail jobDetail = JobBuilder.newJob(JobScheduleExecutor.class)
                    .withIdentity(job.getId().toString()).build();
            jobDetail.getJobDataMap().put("jobId", job.getId());
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getId().toString())
                    .withSchedule(scheduleBuilder).build();
            log.info("[Schedule] Add Job {}-{} with {}", job.getId(), job.getName(), jobConfig
                    .getCronExpression());
            schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
        }



    }



    public boolean existsTrigger(Job j) {
        return findTrigger(j) != null;
    }

    public CronTrigger findTrigger(Job j) {
        try {
            return (CronTrigger) schedulerFactoryBean.getScheduler().getTrigger(getTaskId(j));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TriggerKey getTaskId(Job job) {
        return TriggerKey.triggerKey(job.getId().toString());
    }
}
