package cn.edu.nju.software.jzs.service.task;

import cn.edu.nju.software.jzs.service.cmd.CmdTaskPhaseContext;
import cn.edu.nju.software.jzs.service.cmd.CmdTaskService;
import cn.edu.nju.software.jzs.service.svn.CheckOutTask;
import cn.edu.nju.software.jzs.vo.BuildPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/9.
 */
@Service
public class TaskPhaseFactory {

    @Autowired
    CheckOutTask checkOutTask;

    @Autowired
    CmdTaskService cmdTaskService;


    private static final String BUILD_CMD = "mvn clean package";

    public TaskPhase getTaskPhase(int i, String phase, TaskContextAware taskContextAware) {
        BasicTaskPhase taskPhase = new BasicTaskPhase();
        taskPhase.setNo(i);
        taskPhase.setPhrase(phase);
        taskPhase.setTaskContextAware(taskContextAware);
        return taskPhase;
    }

    public TaskPhase getCheckOutTaskPhase(int i) {
        BasicTaskPhase taskPhase = new BasicTaskPhase();
        taskPhase.setTaskContextAware(checkOutTask);
        taskPhase.setPhrase(BuildPhase.CHECK_OUT);
        taskPhase.setNo(i);
        return taskPhase;
    }

    public TaskPhase getBuildTaskPhase(int i, String workspace) {
        return getBuildTaskPhase(i, workspace, "");
    }




    public TaskPhase getBuildTaskPhase(int i, String workspace, String arg) {
        BasicTaskPhase<CmdTaskPhaseContext> taskPhase = new BasicTaskPhase<CmdTaskPhaseContext>();
        CmdTaskPhaseContext cmdTaskPhaseContext = new CmdTaskPhaseContext(workspace, BUILD_CMD + " " + arg);
        taskPhase.setPhaseContext(cmdTaskPhaseContext);
        taskPhase.setTaskContextAware(cmdTaskService);
        taskPhase.setPhrase(BuildPhase.BUILDING);
        taskPhase.setNo(i);
        return taskPhase;
    }


}
