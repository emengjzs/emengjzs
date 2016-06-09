package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.entity.AnalyseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class AnalyseFacade {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    AnalyseService analyseService;


    public void analyse(final Long planId) throws IOException, ExecutionException, InterruptedException {
        AnalyseContext analyseContext = analyseService.prepareForAnalyse(planId);
        ListenableFuture<AnalyseRecord> analyse = analyseService.analyse(analyseContext);
        log.debug("wait for result!");
        AnalyseRecord analyseRecord = analyse.get();
        log.debug("get the result!");
        log.debug("end analyse!");
    }

    public void analyseAsync(final Long planId) throws IOException, ExecutionException, InterruptedException {
        AnalyseContext analyseContext = analyseService.prepareForAnalyse(planId);
        ListenableFuture<AnalyseRecord> analyse = analyseService.analyse(analyseContext);
    }

}
