package cn.edu.nju.software.jzs.service.base.cmd;

import cn.edu.nju.software.jzs.service.TaskRunContext;

/**
 * Created by emengjzs on 2016/4/8.
 */
public class RecordLineListener implements ReadLineListener {

    private final String MAVEN_ERROR_LOG = "[INFO] BUILD FAILURE";


    TaskRunContext taskRunContext;

    public RecordLineListener(TaskRunContext tc) {
        this.taskRunContext = tc;
    }


    @Override
    public void onReadLine(String str) {
        if (str.equals(MAVEN_ERROR_LOG)) {
            taskRunContext.raiseError(new Exception(MAVEN_ERROR_LOG));
        }


            taskRunContext.pushLog(str);

    }
}
