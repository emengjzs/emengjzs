package cn.edu.nju.software.jzs.vo;

import cn.edu.nju.software.jzs.entity.JobStatus;

import java.util.Date;

/**
 * Created by emengjzs on 2016/4/28.
 */
public class JobSimpleInfo {

    private Integer id;
    private String name;
    private JobStatus status;
    private Double health;
    private Date latestRunTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = JobStatus.values()[status];
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Date getLatestRunTime() {
        return latestRunTime;
    }

    public void setLatestRunTime(Date latestRunTime) {
        this.latestRunTime = latestRunTime;
    }
}
