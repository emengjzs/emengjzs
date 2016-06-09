package cn.edu.nju.software.jzs.service.task;

/**
 * Created by emengjzs on 2016/4/12.
 */
public interface TaskLogger {
    public void pushLog(String log);
    public void raiseError(Exception e);
}
