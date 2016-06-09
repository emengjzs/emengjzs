package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.entity.RunLogContent;
import cn.edu.nju.software.jzs.service.task.TaskContextContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/11.
 */
@Service
public class JobRunExceptionHandler {

    @Autowired
    private RunHistoryService runHistoryService;

    @Autowired
    private TaskContextContainer taskContextContainer;

    private Logger log = LoggerFactory.getLogger(getClass());

    public void handle(Exception e, TaskRunContext tc) {
        log.info("Handle Error Job=[{}] No=[{}] ErrorMsg=[{}]",
                tc.getJob().getName(), tc.getRunHistory().getRunNo(), e.getMessage());
        taskContextContainer.finish(tc);
        runHistoryService.changeStateToFail(tc);
        RunLogContent runLogContent = new RunLogContent();
        runLogContent.setRunHistoryId(tc.getRunHistory().getId());
        runLogContent.setLogContent(tc.getAllCurrentLogNotClear());
        this.runHistoryService.saveRunLogContent(runLogContent);
    }

}
