package cn.edu.nju.software.jzs.vo;

import cn.edu.nju.software.jzs.entity.RunStatus;

import java.util.List;

/**
 * Created by emengjzs on 2016/4/6.
 */
public class JobRunningInfo {

    private RunStatus status;

    private BuildPhase phase;

    private Long phaseNo;

    private List<String> log;

    public RunStatus getStatus() {
        return status;
    }

    public void setStatus(RunStatus status) {
        this.status = status;
    }

    public BuildPhase getPhase() {
        return phase;
    }

    public void setPhase(BuildPhase phase) {
        this.phase = phase;
    }

    public Long getPhaseNo() {
        return phaseNo;
    }

    public void setPhaseNo(Long phaseNo) {
        this.phaseNo = phaseNo;
    }

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }
}
