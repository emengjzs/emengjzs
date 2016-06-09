package cn.edu.nju.software.jzs.entity;

/**
 * Created by emengjzs on 2016/4/15.
 */
public class JobConfig {

    private String automationSVNPath;
    private boolean needAutomation = false;
    private boolean needDeploy = true;
    private boolean needSchedule = false;
    private String cronExpression;

    public JobConfig() {

    }


    public String getAutomationSVNPath() {
        return automationSVNPath;
    }

    public void setAutomationSVNPath(String automationSVNPath) {
        this.automationSVNPath = automationSVNPath;
    }

    public boolean isNeedAutomation() {
        return needAutomation;
    }

    public void setNeedAutomation(boolean needAutomation) {
        this.needAutomation = needAutomation;
    }

    public boolean isNeedDeploy() {
        return needDeploy;
    }

    public void setNeedDeploy(boolean needDeploy) {
        this.needDeploy = needDeploy;
    }

    public boolean isNeedSchedule() {
        return needSchedule;
    }

    public void setNeedSchedule(boolean needSchedule) {
        this.needSchedule = needSchedule;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
