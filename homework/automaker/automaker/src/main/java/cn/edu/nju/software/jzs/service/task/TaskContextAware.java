package cn.edu.nju.software.jzs.service.task;

import cn.edu.nju.software.jzs.service.TaskRunContext;

/**
 * Created by emengjzs on 2016/4/6.
 */
public interface TaskContextAware<T> {


    public void execute(TaskRunContext tc, TaskPhase<T> phase);
}
