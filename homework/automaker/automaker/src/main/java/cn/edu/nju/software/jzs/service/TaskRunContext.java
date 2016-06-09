package cn.edu.nju.software.jzs.service;

import cn.edu.nju.software.jzs.entity.*;
import cn.edu.nju.software.jzs.service.task.TaskContextAware;
import cn.edu.nju.software.jzs.service.task.TaskLogger;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import cn.edu.nju.software.jzs.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by emengjzs on 2016/4/5.
 */
public class TaskRunContext implements TaskLogger {
    public static final Logger logger = LoggerFactory.getLogger(TaskRunContext.class);
    private Job job;
    private RunHistory runHistory;
    private Integer predictRunTime;
    private StringBuffer sb = new StringBuffer();
    private List<TaskPhase> phases = new ArrayList<>();
    private List<Exception> errors = new ArrayList<>();
    private Vector<String> logLines = new Vector<String>();
    private volatile TaskPhase currentTaskPhase;
    private JobConfig jobConfig;
    private Map<String, Object> porpertiesMap = new ConcurrentHashMap<>();
    private File testReportFile;
    public void pushLog(String log) {
        logLines.add(log);
    }

    public String getAllCurrentLogNotClear() {
        String log;
        synchronized (logLines) {
            log = StringUtils.join(logLines, '\n');
        }
        return sb.toString() + log;
    }


    public String[] fetchLog() {
        String[] logs = null;
        synchronized (logLines) {
            logs = logLines.toArray(new String[0]);
            sb.append(StringUtils.join(logs, '\n'));
            sb.append('\n');
            logLines.clear();
        }
        logger.info("Fetch Log {} Lines. ", logs.length);
        return logs;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getAllCurrentLog() {
        return sb.toString();
    }

    public RunHistory getRunHistory() {
        return runHistory;
    }

    public void setRunHistory(RunHistory runHistory) {
        this.runHistory = runHistory;
    }


    public void changeStateToStart() {
        changeStateTo(RunStatus.RUNNING, JobStatus.RUNNING);
        runHistory.setStartTime(new Date());
    }

    public void changeStateToFail() {
        changeStateTo(RunStatus.FAIL, JobStatus.FAIL);
        runHistory.setEndTime(new Date());
        calculatSpendTime();
    }

    public void changeStateTo(RunStatus runStatus, JobStatus jobStatus) {
        runHistory.setStatus(runStatus);
        job.setStatus(jobStatus);
    }

    public void changeStateToEnd() {
        changeStateTo(RunStatus.SUCCESS, JobStatus.SUCCESS);
        runHistory.setEndTime(new Date());
        calculatSpendTime();
    }


    private void calculatSpendTime() {
        runHistory.setSpendTime(DateUtils.timeDiff(runHistory.getStartTime(),
                runHistory.getEndTime()));
    }

    public Integer getPredictRunTime() {
        return predictRunTime;
    }

    public void setPredictRunTime(Integer predictRunTime) {
        this.predictRunTime = predictRunTime;
    }

    public void addPhase(TaskPhase phase) {
        this.phases.add(phase);
    }

    public void addPhase(TaskPhase phase, TaskContextAware taskContextAware) {
        phase.setTaskContextAware(taskContextAware);

        this.phases.add(phase);
    }

    public TaskPhase getCurrentPhase() {
        return this.currentTaskPhase;
    }

    public Iterator<TaskPhase> getPhraseIterator() {
        return new Iterator<TaskPhase>() {
            Iterator<TaskPhase> itr = phases.iterator();

            @Override
            public boolean hasNext() {
                if (!errors.isEmpty()) {
                    throw new RuntimeException(errors.get(0));
                }
                return itr.hasNext();
            }

            @Override
            public TaskPhase next() {
                currentTaskPhase = itr.next();
                pushLog("--------------------------------------------------");
                pushLog("                     "
                        + currentTaskPhase.getPhrase()
                        + "                     ");
                pushLog("--------------------------------------------------");
                return currentTaskPhase;
            }

            @Override
            public void remove() {
                throw new RuntimeException("This Iterator should not remove any phase!");
            }
        };
    }

    public void raiseError(Exception e) {
        this.errors.add(e);
    }

    public JobConfig getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(JobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }

    public void setProperties(String key, Object value) {
        this.porpertiesMap.put(key, value);
    }

    public <T> T  getProperties(String key, Class<T> clazz) {
        return (T) this.porpertiesMap.get(key);
    }

    public File getTestReportFile() {
        return testReportFile;
    }

    public void setTestReportFile(File testReportFile) {
        this.testReportFile = testReportFile;
    }
}
