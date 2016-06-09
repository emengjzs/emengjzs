package cn.edu.nju.software.jzs.service.cmd;

import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.service.base.cmd.*;
import cn.edu.nju.software.jzs.service.task.TaskContextAware;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/9.
 */
@Service
public class CmdTaskService implements TaskContextAware<CmdTaskPhaseContext> {

    @Autowired
    CommandExecutor commandExecutor;


    public void execute(TaskRunContext tc, OSCommand osCommand) {
        ExecuteResult prepare = commandExecutor.prepare();
        prepare
                .register(new CommandFinishListener(tc))
                .register(new RecordLineListener(tc))
                .register(new NormalOutputLineListener(tc));
        try {
            commandExecutor.run(prepare, osCommand.getComplementCommand());
            prepare.getResult();
        } catch (IOException e) {
            tc.raiseError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void execute(TaskRunContext tc, TaskPhase<CmdTaskPhaseContext> taskPhase) {
        ExecuteResult prepare = commandExecutor.prepare();
        prepare
                .register(new CommandFinishListener(tc))
                .register(new RecordLineListener(tc))
                .register(new NormalOutputLineListener(tc));
        try {
            commandExecutor.run(prepare, taskPhase.getPhaseContext().getCommand());
            prepare.getResult();
        } catch (IOException e) {
            tc.raiseError(e);
            e.printStackTrace();
        }
    }


}
