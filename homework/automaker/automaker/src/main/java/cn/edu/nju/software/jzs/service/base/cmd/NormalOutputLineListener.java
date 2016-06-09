package cn.edu.nju.software.jzs.service.base.cmd;

import cn.edu.nju.software.jzs.service.TaskRunContext;

/**
 * Created by emengjzs on 2016/4/8.
 */
public class NormalOutputLineListener implements ReadLineListener {

    TaskRunContext taskRunContext;

    public NormalOutputLineListener() {

    }

    public NormalOutputLineListener(TaskRunContext taskRunContext) {
        this.taskRunContext = taskRunContext;
    }


    @Override
    public void onReadLine(String str) {
        System.out.println("++" + str + "++");
    }
}
