package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.entity.AnalyseRecord;
import cn.edu.nju.software.jzs.entity.HTTPMethod;
import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.http.HttpService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class AnalyseService {
    Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    AnalyseContextInitializer analyseContextInitializer;

    @Autowired
    AnalyseCodeGenerator analyseCodeGenerator;

    @Autowired
    AnalyseCodeRunner analyseCodeRunner;

    @Autowired
    HttpService httpService;

    @Autowired
    MethodTreeParser methodTreeParser;

    @Transactional
    public AnalyseContext prepareForAnalyse(Long planId) throws IOException {
       return analyseContextInitializer.init(planId);
    }


    @Async
    public ListenableFuture<AnalyseRecord> analyse(final AnalyseContext ac) {
        ListenableFuture<AnalyseRecord> analyseRecordListenableFuture = null;
        try {
            log.debug("generate code");
            // generate code
            File file = analyseCodeGenerator.generateCode(ac);

            ExecuteResult executeResult = analyseCodeRunner.prepare();
            AnalyseRunningHandler analyseRunningHandler = new AnalyseRunningHandler(executeResult);

            // run code
            log.debug("run code");
            analyseCodeRunner.runScript(file, executeResult, ac);

            analyseRunningHandler.waitToBeginHttpRequest();
            analyseRecordListenableFuture = readyForRequest(ac, analyseRunningHandler);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return analyseRecordListenableFuture;
    }




    @Async
    public ListenableFuture<AnalyseRecord> readyForRequest(AnalyseContext ac, AnalyseRunningHandler analyseRunningHandler) {
        try {
            log.debug("request http service");

            // request http service
            requestService(ac);

            log.debug("close process");
            // close process
            List<MethodInfo> methodInfos = analyseRunningHandler.endRunning();

            log.debug("close process done");

            log.debug("analyse info");
            // analyse info
            MethodTree parseTree = methodTreeParser.parse(methodInfos);
            log.debug("analyse info done!");
            log.debug(JSON.toJSONString(parseTree, true));

            log.debug("setLastDuration");
            parseTree.setLastDuration(ac.getDuration());

            // save report
            log.debug("save setReport");
            AnalyseRecord analyseRecord = ac.getAnalyseRecord();
            analyseRecord.setReport(JSON.toJSONString(parseTree));
            log.debug("set setReport");
            // set to end

            // save record
            analyseContextInitializer.setToSuccess(analyseRecord.getPlanId(), analyseRecord);


            log.debug("return record");
        } catch (Exception e) {
            e.printStackTrace();
            analyseContextInitializer.setToFail(ac.getAnalyseRecord().getPlanId());
            return new AsyncResult<AnalyseRecord>(ac.getAnalyseRecord());
        }
        return new AsyncResult<AnalyseRecord>(ac.getAnalyseRecord());

    }

    private void requestService(AnalyseContext a) {
        JSONObject j;
        long start = System.currentTimeMillis();
        ;
        if (a.getMethod() == HTTPMethod.POST) {
            j = httpService.postForJSONResponse(a.getUrl(), a.getParams()).getJSON();
        } else {
            j = httpService.getForObject(a.getUrl(), a.getParams());
        }
        a.getAnalyseRecord().setResponse(j.toJSONString());
        a.setDuration((System.currentTimeMillis() - start) * 1000);
    }


}
