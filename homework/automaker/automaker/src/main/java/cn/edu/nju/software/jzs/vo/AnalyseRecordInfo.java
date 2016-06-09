package cn.edu.nju.software.jzs.vo;

import java.util.Date;

/**
 * Created by emengjzs on 2016/5/1.
 */
public class AnalyseRecordInfo {

    private long id;
    private Date startTime;
    private String response;

    public AnalyseRecordInfo(long id, Date startTime, String response) {
        this.id = id;
        this.startTime = startTime;
        this.response = response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
