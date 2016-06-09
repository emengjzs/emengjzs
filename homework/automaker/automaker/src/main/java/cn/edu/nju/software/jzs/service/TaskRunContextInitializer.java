package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.entity.RunHistory;
import cn.edu.nju.software.jzs.service.deploy.DeployTask;
import cn.edu.nju.software.jzs.service.task.TaskPhaseFactory;
import cn.edu.nju.software.jzs.service.test.TestTask;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import cn.edu.nju.software.jzs.vo.BuildPhase;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by emengjzs on 2016/4/9.
 */
@Service
public class TaskRunContextInitializer {

    @Autowired
    RunHistoryService runHistoryService;

    @Autowired
    TaskPhaseFactory taskPhaseFactory;

    @Autowired
    DeployTask deployTask;

    @Autowired
    TestTask testTask;

    @Autowired
    TaskResourceService jobUtils;

    public TaskRunContext prepareTaskRunContext(Job job) {
        RunHistory runHistory = initNewRunHistory(job);
        return initTaskRunContext(runHistory);
    }

    @Transactional
    public RunHistory initNewRunHistory(Job job) {
        return runHistoryService.createNewRunHistory(job);
    }


    public TaskRunContext initTaskRunContext(RunHistory rh) {
        TaskRunContext tc = new TaskRunContext();
        JobConfig jobConfig;
        try {
            jobConfig = JSON.parseObject(rh.getJob().getJobConfig(), JobConfig.class);
        } catch (Exception e) {
            jobConfig = new JobConfig();
        }
        tc.setJob(rh.getJob());
        tc.setJobConfig(jobConfig);
        tc.setRunHistory(rh);
        tc.setPredictRunTime(runHistoryService.getPredictJobRunTime(tc.getJob().getId()));
        int phase = 0;
        tc.addPhase(taskPhaseFactory.getCheckOutTaskPhase(++ phase));

        tc.addPhase(taskPhaseFactory.getBuildTaskPhase(++ phase,
                jobUtils.getAbsoluteCheckOutProjectPath(rh.getJob())));

        if (jobConfig.isNeedAutomation()) {
            tc.addPhase(taskPhaseFactory.getBuildTaskPhase(++ phase,
                    jobUtils.getAbsoluteCheckOutAutomationProjectPath(rh.getJob(), jobConfig)));
        }

        if (jobConfig.isNeedDeploy()) {
            tc.addPhase(taskPhaseFactory.getTaskPhase(++ phase, BuildPhase.DEPLOYING.toString(), deployTask));
        }

        if (jobConfig.isNeedAutomation()) {
            tc.addPhase(taskPhaseFactory.getTaskPhase(++ phase, BuildPhase.TESTING.toString(), testTask));
        }
        return tc;
    }


}


