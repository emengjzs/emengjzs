package cn.edu.nju.software.jzs.controller;

import cn.edu.nju.software.jzs.entity.AnalysePlan;
import cn.edu.nju.software.jzs.entity.HTTPMethod;
import cn.edu.nju.software.jzs.handler.Msg;
import cn.edu.nju.software.jzs.service.analyse.AnalyseFacade;
import cn.edu.nju.software.jzs.service.analyse.AnalysePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by emengjzs on 2016/5/1.
 */
@RestController
public class AnalysisController {

    @Autowired
    AnalysePlanService analysePlanService;

    @Autowired
    AnalyseFacade analyseFacade;

    @RequestMapping(value = "/analysePlan/create", method = RequestMethod.POST)
    public Msg createAnalysisPlan(
            @RequestParam("jobId") long jobID,
            @RequestParam("name") String name,
            @RequestParam("url") String url,
            @RequestParam(value = "param",defaultValue = "{}") String param,
            @RequestParam("httpMethod") String type,
            @RequestParam("base") String base,
            @RequestParam("modules") String modules) {
        AnalysePlan analysePlan = new AnalysePlan();
        analysePlan.setJobId(jobID);
        analysePlan.setName(name);
        analysePlan.setUrl(url);
        analysePlan.setParams(param);
        analysePlan.setHttpMethod(HTTPMethod.valueOf(type));
        analysePlan.setModules(modules);
        analysePlan.setBasePackage(base);
        analysePlanService.create(analysePlan);
        return  Msg.res("新建性能测试计划成功");
    }

    @RequestMapping(value = "/analysePlan", method = RequestMethod.POST)
    public Msg getAnalysisPlans(@RequestParam("jobId") long jobID) {
        return Msg.of(analysePlanService.getAnalysePlanList(jobID));
    }

    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    public Msg getAnalysisPlan(@RequestParam("planId") long planId) {
        return Msg.of(analysePlanService.getAnalysePlan(planId));
    }

    @RequestMapping(value = "/analysePlan/run", method = RequestMethod.POST)
    public Msg analyse(@RequestParam("planId") long planId) throws InterruptedException, ExecutionException, IOException {
        analyseFacade.analyseAsync(planId);
        return Msg.res("已开始运行性能测试分析");
    }

    @RequestMapping(value = "/analysePlan/records", method = RequestMethod.POST)
    public Msg records(@RequestParam("planId") long planId) {
        return Msg.of(analysePlanService.getAnalyseRecordList(planId));
    }

    @RequestMapping(value = "/analysePlan/record", method = RequestMethod.POST)
    public Msg record(@RequestParam("id") long id) {
        return Msg.of(analysePlanService.getAnalyseRecord(id));
    }

}
