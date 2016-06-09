package cn.edu.nju.software.jzs.controller;

import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.handler.Msg;
import cn.edu.nju.software.jzs.service.JobService;
import org.hibernate.validator.constraints.NotEmpty;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by emengjzs on 2016/4/4.
 */
@RestController
@Validated
public class JobController {

    @Autowired
    JobService jobService;

    /*//this.job = {
        name: '',
        description: '描述',
        svnAdr: '',
        svnType: this.svnTypes[0],
    }*/
    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public Msg createJob(
            @RequestParam("name") @NotNull(message = "name 不为空") @NotEmpty(message = "name 不为空")
                    String name,
            @RequestParam("svnAdr") @NotNull(message = "name 不为空") @NotEmpty(message = "name 不为空")
                    String svnUrl,
            @RequestParam("svnAutoAdr") @NotNull(message = "name 不为空")
                    String svnAutoUrl,
            @RequestParam("description") @NotNull(message = "name 不为空")
                    String description,
            @RequestParam("needAuto") @NotNull(message = "name 不为空")
                    boolean needAuto,
            @RequestParam(value = "needSchedule", defaultValue = "false")
                    boolean needSchedule,
            @RequestParam(value = "cronExp", required = false)
                    String cronExp
    ) throws SchedulerException {
        JobConfig jobConfig = new JobConfig();
        jobConfig.setAutomationSVNPath(svnAutoUrl);
        jobConfig.setNeedAutomation(needAuto);
        jobConfig.setNeedDeploy(true);
        jobConfig.setNeedSchedule(needSchedule);
        jobConfig.setCronExpression(cronExp);
        jobService.createJob(name, description, svnUrl, jobConfig);
        return Msg.of("新建任务成功");
    }

    @RequestMapping(value = "/job", method = RequestMethod.GET)
    public Msg getJobList() {
        return Msg.of(this.jobService.getJobList());
    }

    @RequestMapping(value = "/job/disable", method = RequestMethod.POST)
    public Msg disable(@RequestParam("jobId") Long id) {
        this.jobService.disable(id);
        return Msg.of("");
    }
    @RequestMapping(value = "/job/enable", method = RequestMethod.POST)
    public Msg enable(@RequestParam("jobId") Long id) {
        this.jobService.enable(id);
        return Msg.of("");
    }

    @RequestMapping(value = "/jobDetail", method = RequestMethod.POST)
    public Msg getJobList(@RequestParam("jobId") Long id) {
        return Msg.of(this.jobService.getJobDetail(id));
    }

}
