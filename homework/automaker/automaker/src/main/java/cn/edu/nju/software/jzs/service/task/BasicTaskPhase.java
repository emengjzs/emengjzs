package cn.edu.nju.software.jzs.service.task;

import cn.edu.nju.software.jzs.vo.BuildPhase;

/**
 * Created by emengjzs on 2016/4/9.
 */
public class BasicTaskPhase<T> implements TaskPhase<T> {

    protected TaskContextAware taskContextAware;
    protected int i;
    protected T context;
    protected String phrase;


    public void setPhaseContext(T t) {
        this.context = t;
    }

    @Override
    public T getPhaseContext() {
        return context;
    }

    @Override
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setPhrase(BuildPhase phrase) {
        this.phrase = phrase.toString();
    }

    @Override
    public int getNo() {
        return i;
    }

    @Override
    public TaskContextAware getTaskContextAware() {
        return taskContextAware;
    }

    public void setNo(int i) {
        this.i = i;
    }

    @Override
    public void setTaskContextAware(TaskContextAware taskContextAware) {
        this.taskContextAware = taskContextAware;
    }
}
