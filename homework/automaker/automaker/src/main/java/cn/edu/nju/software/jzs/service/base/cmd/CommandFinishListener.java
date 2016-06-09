package cn.edu.nju.software.jzs.service.base.cmd;

import cn.edu.nju.software.jzs.service.TaskRunContext;

/**
 * Created by emengjzs on 2016/4/8.
 */
public class CommandFinishListener {

    TaskRunContext taskRunContext;

    public CommandFinishListener(TaskRunContext taskRunContext) {
        this.taskRunContext = taskRunContext;
    }


    public void onFinish(ExecuteResult result) {
        System.out.println("Finish!");
    }
}
