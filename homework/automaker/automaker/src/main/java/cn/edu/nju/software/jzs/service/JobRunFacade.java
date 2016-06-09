package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.vo.JobRunningInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/6.
 */
@Service
public class JobRunFacade {

    @Autowired
    private JobRunService jobRunService;


    public TaskRunContext run(Long id) {
        TaskRunContext taskRunContext = jobRunService.prepareTaskRunContext(id);
        jobRunService.run(taskRunContext);
        return taskRunContext;
    }



    public JobRunningInfo getJobRunningInfo(Long runHistoryId, boolean needLog) {
        return jobRunService.getJobRunningInfo(runHistoryId, needLog);
    }
}


