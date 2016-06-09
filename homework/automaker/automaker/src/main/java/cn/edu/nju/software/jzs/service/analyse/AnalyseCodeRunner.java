package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.service.base.cmd.CommandExecutor;
import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.OSCommand;
import cn.edu.nju.software.jzs.service.base.cmd.WindowsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class AnalyseCodeRunner {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommandExecutor commandExecutor;

    public String RUN_CMD = "btrace %d %s";


    public ExecuteResult prepare() {
        return commandExecutor.prepare();
    }

    public void runScript(File f, ExecuteResult prepare, AnalyseContext a) throws IOException {
        OSCommand windowsCommand = new WindowsCommand(
                ".", String.format(RUN_CMD, a.getPid(), f.getAbsolutePath())
                );
        commandExecutor.run(prepare,  windowsCommand);
    }


}
