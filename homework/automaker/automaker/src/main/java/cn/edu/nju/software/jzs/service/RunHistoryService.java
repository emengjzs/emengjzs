package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.dao.JobRunDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.RunHistory;
import cn.edu.nju.software.jzs.entity.RunLogContent;
import cn.edu.nju.software.jzs.entity.RunStatus;
import cn.edu.nju.software.jzs.utils.Page;
import cn.edu.nju.software.jzs.vo.JobRunHistoryInfo;
import cn.edu.nju.software.jzs.vo.RunRecordVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import testng.parser.ResultsParser;
import testng.results.TestNGResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/6.
 */
@Service
public class RunHistoryService {

    public static final int FIRST_PREDICT_TIME = 30 * 1000;

    @Autowired
    JobRunDao jobRunDao;
    private Logger log = LoggerFactory.getLogger(getClass());
    // @Transactional
    public RunHistory createNewRunHistory(Job job) {
        RunHistory rh = new RunHistory();
        rh.setJob(job);
        rh.setRunNo(this.jobRunDao.getMaxRunNo(job));
        rh.setStatus(RunStatus.RUNNING);
        this.jobRunDao.save(rh);
        return rh;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeStateToStart(TaskRunContext tc) {
        tc.changeStateToStart();
        jobRunDao.save(tc.getJob(), tc.getRunHistory());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeStateToSuccess(TaskRunContext tc) {
        tc.changeStateToEnd();
        jobRunDao.save(tc.getJob(), tc.getRunHistory());
    }

    @Transactional
    public void updateTestCount(TaskRunContext tc, TestNGResult testNGResult) {
        if (tc.getJobConfig().isNeedAutomation()) {

            RunHistory runHistory = tc.getRunHistory();
            runHistory.setTotalCount(testNGResult.getTotalCount());
            runHistory.setSuccessCount(testNGResult.getPassCount());
            runHistory.setFailCount(testNGResult.getFailCount());
            runHistory.setSkipCount(testNGResult.getSkipCount());

            if(! runHistory.getTotalCount().equals(runHistory.getSuccessCount())) {
                runHistory.setStatus(RunStatus.WARN);
            }
            jobRunDao.save(runHistory);
            log.info("Run History: \n{} ", JSON.toJSONString(runHistory, true));
        }

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeStateToFail(TaskRunContext tc) {
        tc.changeStateToFail();
        jobRunDao.save(tc.getJob(), tc.getRunHistory());
    }

    public Integer getPredictJobRunTime(Long jobId) {
        Integer time = jobRunDao.getLastJobRunTime(jobId);
        return time == null ? FIRST_PREDICT_TIME : time;
    }

    public List<RunRecordVO> getRunHistory(Page p, Long jobId) {
        return this.jobRunDao.getRunHistory(p, jobId);
    }

    public JobRunHistoryInfo getRunHistory(Long jobHistoryId) {
        RunHistory rh = this.jobRunDao.getRunHistory(jobHistoryId);
        JobRunHistoryInfo jobRunHistoryInfo = new JobRunHistoryInfo();
        try {
            BeanUtils.copyProperties(jobRunHistoryInfo, rh);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return jobRunHistoryInfo;
    }


    public void saveRunLogContent(RunLogContent runLogContent) {
        this.jobRunDao.save(runLogContent);
    }

    public String getLatestStabledLogContent(Long jobId) {
        return this.jobRunDao.findLatestStabledLogContent(jobId);
    }

    public TestNGResult getTestReport(Long runHistoryId) {
        String testReportContent = jobRunDao.findTestReportContent(runHistoryId);
        ByteArrayInputStream is= new ByteArrayInputStream(testReportContent.getBytes());
        ResultsParser resultsParser = new ResultsParser();
        TestNGResult parse = resultsParser.parse(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  parse;
    }

    public String getLongContent(Long jhId) {
        return this.jobRunDao.findLongContent(jhId);
    }
}

