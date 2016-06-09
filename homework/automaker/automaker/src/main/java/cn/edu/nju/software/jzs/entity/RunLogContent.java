package cn.edu.nju.software.jzs.entity;

import javax.persistence.*;

/**
 * Created by emengjzs on 2016/4/10.
 */
@Entity
public class RunLogContent {

    @Id
    @GeneratedValue
    private Long id;
    private Long runHistoryId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 16777215)
    private String logContent;


    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String testReport;

    private Long jobId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunHistoryId() {
        return runHistoryId;
    }

    public void setRunHistoryId(Long runHistoryId) {
        this.runHistoryId = runHistoryId;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
