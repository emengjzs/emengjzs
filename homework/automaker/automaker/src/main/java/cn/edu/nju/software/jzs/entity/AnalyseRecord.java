package cn.edu.nju.software.jzs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Entity
public class AnalyseRecord {
    @Id
    @GeneratedValue
    private Long id;
    private Long planId;
    private Long jobId;
    private Date createTime;
    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String response;
    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String report;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
