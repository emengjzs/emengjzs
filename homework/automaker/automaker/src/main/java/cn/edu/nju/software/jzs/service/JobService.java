package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.dao.JobDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.entity.JobStatus;
import cn.edu.nju.software.jzs.entity.RunNo;
import cn.edu.nju.software.jzs.service.schedule.JobScheduleInitializer;
import com.alibaba.fastjson.JSON;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/3.
 */
@Service
public class JobService {

    @Autowired
    private JobDao jobDao;

    @Value("${automaker.deploy.tomcat.init.port}")
    private int initialPort;

    @Autowired
    JobScheduleInitializer jobScheduleInitializer;

    @Transactional
    public void createJob(String name, String description, String svnAdr, JobConfig jobConfig) throws SchedulerException {
        Job job = new Job();
        job.setName(name);
        job.setPrimarySvnPath(svnAdr);
        job.setDescription(description);
        job.setCreateTime(new Date());
        job.setWorkspacePath(job.getName());
        job.setStatus(JobStatus.NOT_RUN);
        job.setJobConfig(JSON.toJSONString(jobConfig));
        job.setNeedSchedule(job.isNeedSchedule());
        Integer maxPort = jobDao.getMaxPort();
        int port = maxPort == null ? initialPort : maxPort + 1;
        job.setPort(port);
        job.setHost("127.0.0.1");
        RunNo runNo = new RunNo();
        runNo.setJob(job);
        runNo.setNo(0L);
        jobDao.save(job);
        jobDao.save(runNo);
        if (job.isNeedSchedule()) {
            jobScheduleInitializer.addJobToSheduler(job);
        }
    }

    public List getJobList() {
        return this.jobDao.getJobSimpleInfoList();
    }


    public Job getJobDetail(Long id) {
        return this.jobDao.getJobById(id);
    }

    @Transactional
    public void disable(Long id) {
        this.jobDao.getJobById(id).setStatus(JobStatus.DEPRECATED);
    }

    @Transactional
    public void enable(Long id) {
        Job job = this.jobDao.getJobById(id);
        job.setStatus(JobStatus.NOT_RUN);
        if (job.isNeedSchedule()) {
            try {
                jobScheduleInitializer.addJobToSheduler(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}
