package cn.edu.nju.software.jzs.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emengjzs on 2016/4/5.
 */
@Entity
public class RunHistory {

    @Id
    @GeneratedValue
    private Long id;

    private Long runNo;

    @Enumerated(EnumType.ORDINAL)
    private RunStatus status;

    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "job_id")
    private Job job;

    private Date startTime;

    private Date endTime;

    private Long
            spendTime;

    private Integer successCount;
    private Integer failCount;
    private Integer totalCount;
    private Integer skipCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunNo() {
        return runNo;
    }

    public void setRunNo(Long runNo) {
        this.runNo = runNo;
    }

    public RunStatus getStatus() {
        return status;
    }

    public void setStatus(RunStatus status) {
        this.status = status;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }


    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }
}
