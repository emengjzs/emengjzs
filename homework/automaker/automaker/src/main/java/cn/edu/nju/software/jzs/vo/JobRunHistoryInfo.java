package cn.edu.nju.software.jzs.vo;

import cn.edu.nju.software.jzs.entity.RunStatus;

import java.util.Date;

/**
 * Created by emengjzs on 2016/4/13.
 */
public class JobRunHistoryInfo {

    private Long id;

    private Long runNo;

    private RunStatus status;

    private Date startTime;

    private Date endTime;

    private Long spendTime;

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

}
