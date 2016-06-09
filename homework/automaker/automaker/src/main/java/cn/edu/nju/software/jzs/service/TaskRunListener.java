package cn.edu.nju.software.jzs.service;

/**
 * Created by emengjzs on 2016/4/5.
 */
public interface TaskRunListener {

    void beforeRun(TaskRunContext tc);

    void afterRun(TaskRunContext tc);
}
