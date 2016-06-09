package cn.edu.nju.software.jzs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by emengjzs on 2016/4/2.
 */
@Entity
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date createTime;

    private String primarySvnPath;

    private String description;

    private String workspacePath;

    private JobStatus status;

    private String host;

    private Integer port;

    private String jobConfig;

    private boolean needSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPrimarySvnPath() {
        return primarySvnPath;
    }

    public void setPrimarySvnPath(String primarySvnPath) {
        this.primarySvnPath = primarySvnPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkspacePath() {
        return workspacePath;
    }

    public void setWorkspacePath(String workspacePath) {
        this.workspacePath = workspacePath;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(String jobConfig) {
        this.jobConfig = jobConfig;
    }


    public boolean isNeedSchedule() {
        return needSchedule;
    }

    public void setNeedSchedule(boolean needSchedule) {
        this.needSchedule = needSchedule;
    }
}
