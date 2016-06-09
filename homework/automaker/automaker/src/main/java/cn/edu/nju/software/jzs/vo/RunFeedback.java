package cn.edu.nju.software.jzs.vo;

/**
 * Created by emengjzs on 2016/4/6.
 */
public class RunFeedback {
    private Long jobId;
    private Long runHistoryId;
    private Long runNo;
    private Integer predictRunTime;
    public RunFeedback(Long jobId, Long runHistoryId, Long runNo) {
        this.jobId = jobId;
        this.runHistoryId = runHistoryId;
        this.runNo = runNo;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getRunHistoryId() {
        return runHistoryId;
    }

    public void setRunHistoryId(Long runHistoryId) {
        this.runHistoryId = runHistoryId;
    }

    public Long getRunNo() {
        return runNo;
    }

    public void setRunNo(Long runNo) {
        this.runNo = runNo;
    }

    public Integer getPredictRunTime() {
        return predictRunTime;
    }

    public void setPredictRunTime(Integer predictRunTime) {
        this.predictRunTime = predictRunTime;
    }
}
