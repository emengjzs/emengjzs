package cn.edu.nju.software.jzs.controller;

import cn.edu.nju.software.jzs.entity.RunHistory;
import cn.edu.nju.software.jzs.handler.Msg;
import cn.edu.nju.software.jzs.service.JobRunFacade;
import cn.edu.nju.software.jzs.service.RunHistoryService;
import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.utils.Page;
import cn.edu.nju.software.jzs.vo.RunFeedback;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emengjzs on 2016/4/5.
 */
@RestController
public class JobRunController {

    @Autowired
    JobRunFacade jobRunFacade;

    @Autowired
    RunHistoryService runHistoryService;

    @RequestMapping(value = "/job/run", method = RequestMethod.POST)
    public Msg run(@RequestParam("jobId") Long jobId)  {
        TaskRunContext run1 = jobRunFacade.run(jobId);
        RunHistory run = run1.getRunHistory();
        RunFeedback feedback = new RunFeedback(run.getJob().getId(), run.getId(), run.getRunNo());
        feedback.setPredictRunTime(run1.getPredictRunTime());
        return Msg.of(feedback);
    }


    @RequestMapping(value = "/job/runcontext/" , method = RequestMethod.POST)
    public Msg runContext(@RequestParam("runHistoryId") Long runHistoryId,
                          @RequestParam(value = "log", defaultValue = "true") boolean needLog) {
        return Msg.of(this.jobRunFacade.getJobRunningInfo(runHistoryId, needLog));
    }


    @RequestMapping(value = "/job/runlist", method = RequestMethod.POST)
    public Msg runHistoryList(
            @RequestParam("jobId") Long jobId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page p = Page
                .pageSize(pageSize)
                .pageNo(pageNo)
                .build();
        return Msg.of(this.runHistoryService.getRunHistory(p, jobId));
    }


    @RequestMapping(value = "/job/run/history", method = RequestMethod.POST)
    public Msg runHistory(
            @RequestParam("runHistoryId") Long runHistoryId
    ){
        return Msg.of(this.runHistoryService.getRunHistory(runHistoryId));
    }

    @RequestMapping(value = "/job/log", method = RequestMethod.POST)
    public Msg runLog(
            @RequestParam("runHistoryId") Long runHistoryId
            ){
        return Msg.of(this.runHistoryService.getLongContent(runHistoryId));
    }

    @RequestMapping(value = "/job/log/latest", method = RequestMethod.POST)
    public Msg latestRunLog(
            @RequestParam("runHistoryId") Long jobId
    ){
        return Msg.of(this.runHistoryService.getLatestStabledLogContent(jobId));
    }

    @RequestMapping(value = "/job/testreport", method = RequestMethod.POST)
    public Msg testReport(
            @RequestParam("runHistoryId") Long jobId
    ){
        return Msg.of(JSON.toJSONString(this.runHistoryService.getTestReport(jobId)));
    }

}
