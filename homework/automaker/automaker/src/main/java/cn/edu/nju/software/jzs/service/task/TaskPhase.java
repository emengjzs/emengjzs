package cn.edu.nju.software.jzs.service.task;

/**
 * Created by emengjzs on 2016/4/9.
 */
public interface TaskPhase<T> {

    T getPhaseContext();

    String getPhrase();

    int getNo();


    TaskContextAware getTaskContextAware();

    void setTaskContextAware(TaskContextAware taskContextAware);
}
