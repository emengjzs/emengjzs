package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobStatus;
import cn.edu.nju.software.jzs.entity.RunLogContent;
import cn.edu.nju.software.jzs.entity.RunStatus;
import cn.edu.nju.software.jzs.service.task.TaskContextContainer;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import cn.edu.nju.software.jzs.vo.JobRunningInfo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testng.parser.ResultsParser;
import testng.results.TestNGResult;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by emengjzs on 2016/4/5.
 */
@Service
public class JobRunService {

    @Autowired
    private JobService jobService;

    @Autowired
    private RunHistoryService runHistoryService;


    @Autowired
    private TaskContextContainer taskContextContainer;

    @Autowired
    private TaskRunContextInitializer taskRunContextInitializer;

    @Autowired
    private JobRunExceptionHandler jobRunExceptionHandler;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Transactional
    @Async
    public void run(TaskRunContext tc) {
        if (tc.getJob().getStatus() == JobStatus.RUNNING) {
            log.warn("Job {}-{} is still running, skip the task", tc.getJob().getId(),
                    tc.getJob().getName());
            return;
        }
        runHistoryService.changeStateToStart(tc);
        try {
            Iterator<TaskPhase> phraseIterator = tc.getPhraseIterator();
            log.info("Exec Task {} Building No: {}", tc.getJob().getName(), tc.getRunHistory().getRunNo());
            while (phraseIterator.hasNext()) {
                TaskPhase phase = phraseIterator.next();
                log.info("Exec Phase {} -> {} Using {}",
                        phase.getNo(), phase.getPhrase(), phase.getTaskContextAware().getClass().getSimpleName());
                phase.getTaskContextAware().execute(tc, phase);
            }
            afterTaskFinish(tc);
        } catch (Exception e) {
            jobRunExceptionHandler.handle(e, tc);
        }
    }

    public TaskRunContext prepareTaskRunContext(Long jobId) {
        Job job = jobService.getJobDetail(jobId);
        TaskRunContext tc = taskRunContextInitializer.prepareTaskRunContext(job);
        taskContextContainer.submit(tc);
        return tc;
    }

    @Transactional
    private void afterTaskFinish(TaskRunContext tc) {
        taskContextContainer.finish(tc);
        runHistoryService.changeStateToSuccess(tc);
        RunLogContent runLogContent = new RunLogContent();
        runLogContent.setRunHistoryId(tc.getRunHistory().getId());
        runLogContent.setLogContent(tc.getAllCurrentLogNotClear());
        runLogContent.setJobId(tc.getJob().getId());

        runHistoryService.updateTestCount(tc, saveTestReports(tc, runLogContent));

        this.runHistoryService.saveRunLogContent(runLogContent);
    }


    public TestNGResult saveTestReports(TaskRunContext tc, RunLogContent runLogContent) {

        String xmlReports = null;
        FileInputStream fileInputStream = null;
        TestNGResult parse = null;
        try {
            fileInputStream = new FileInputStream(tc.getTestReportFile());
            xmlReports = IOUtils.toString(fileInputStream);
            ResultsParser resultsParser = new ResultsParser();
            parse = resultsParser.parse(new ByteArrayInputStream(xmlReports.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        runLogContent.setTestReport(xmlReports);
        return parse;
    }


    public JobRunningInfo getJobRunningInfo(Long runHistoryId, boolean needLog) {
        JobRunningInfo info = new JobRunningInfo();
        TaskRunContext taskRunContext = this.taskContextContainer.getTaskRunContext(runHistoryId);
        if (taskRunContext == null) {
            info.setStatus(RunStatus.SUCCESS);
            // info.setLog();
        } else {
            if (needLog) {
                info.setLog(Arrays.asList(taskRunContext.fetchLog()));
            }
            info.setStatus(RunStatus.RUNNING);
        }
        return info;
    }
}
