package cn.edu.nju.software.jzs.service.test;

import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.service.base.cmd.*;
import cn.edu.nju.software.jzs.service.task.TaskContextAware;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/16.
 */
@Service
public class TestTask implements TaskContextAware {

    public static final String AUTOMATION__DICT_KEY = "automationDict";
    public static final String TEST_OUTPUT_FILE_PATH = "test-output/testng-results.xml";
    public static final String TEST_COMMAND = "ant";
    @Autowired
    CommandExecutor commandExecutor;

    @Override
    public void execute(TaskRunContext tc, TaskPhase phase) {
        String automationWorkPath = tc.getProperties(AUTOMATION__DICT_KEY, String.class);
        ExecuteResult prepare = commandExecutor.prepare();
        prepare
                .register(new CommandFinishListener(tc))
                .register(new RecordLineListener(tc))
                .register(new NormalOutputLineListener(tc));
        try {
            OSCommand command = new WindowsCommand(automationWorkPath, TEST_COMMAND);
            commandExecutor.run(prepare, command);
            prepare.getResult();
            File f = new File(automationWorkPath, TEST_OUTPUT_FILE_PATH);
            tc.setTestReportFile(f);
        } catch (IOException e) {
            tc.raiseError(e);
            e.printStackTrace();
        }
    }



}
