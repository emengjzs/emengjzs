package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.dao.AnalyseDao;
import cn.edu.nju.software.jzs.entity.*;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class AnalyseContextInitializer {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PidFinder pidFinder;

    @Autowired
    TaskResourceService taskResourceService;

    @Autowired
    AnalyseDao analyseDao;


    @Transactional
    public AnalyseContext init(long planId)  throws IOException {
        Job job = analyseDao.getJobByPlanId(planId);
        if (job.getStatus() == JobStatus.RUNNING) {
            throw new RuntimeException("有其他任务进行中,请稍后再试！");
        }
        AnalysePlan plan = analyseDao.findById(AnalysePlan.class, planId);
        Validate.notNull(job, "Job not found");
        setToStart(plan, job);
        return init(job, plan);
    }

    @Transactional
    public AnalyseContext init(Job job, AnalysePlan analysePlan) throws IOException {
        AnalyseContext analyseContext = new AnalyseContext();
        analyseContext.setName(job.getName().replaceAll("[^a-z^A-Z]", ""));
        analyseContext.setPid(pidFinder.getPid(job.getPort()));
        analyseContext.setBasePackage(analysePlan.getBasePackage());
        analyseContext.setModules(analysePlan.getModules().split(","));
        analyseContext.setCreateTime();

        analyseContext.setMethod(analysePlan.getHttpMethod());
        analyseContext.setParams(JSON.parseObject(analysePlan.getParams()));
        analyseContext.setUrl("http://" + job.getHost() + ":" + job.getPort() + analysePlan.getUrl());
        analyseContext.setBasePath(taskResourceService.getAbsoluteAutoWorkDictionaryPath(job));

        AnalyseRecord analyseRecord = analyseContext.getAnalyseRecord();
        analyseRecord.setJobId(job.getId());
        analyseRecord.setPlanId(analysePlan.getId());

        return analyseContext;
    }

    private void setToStart(AnalysePlan plan, Job job) {
        job.setStatus(JobStatus.RUNNING);
        plan.setRunStatus(RunStatus.RUNNING);
        analyseDao.save(job, plan);
    }

    @Transactional
    public void setToSuccess(Long planId, AnalyseRecord ac) {
        Job job = analyseDao.getJobByPlanId(planId);
        AnalysePlan plan = analyseDao.findById(AnalysePlan.class, planId);
        Validate.notNull(job, "Job not found");
        job.setStatus(JobStatus.SUCCESS);
        plan.setRunStatus(RunStatus.SUCCESS);
        analyseDao.save(job, plan);
        analyseDao.save(ac);
        log.debug("return record");
    }

    @Transactional
    public void setToFail(Long planId) {
        Job job = analyseDao.getJobByPlanId(planId);
        AnalysePlan plan = analyseDao.findById(AnalysePlan.class, planId);
        Validate.notNull(job, "Job not found");
        job.setStatus(JobStatus.FAIL);
        plan.setRunStatus(RunStatus.FAIL);
        analyseDao.save(job, plan);
    }


}
