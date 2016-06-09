package cn.edu.nju.software.jzs.service.base.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/8.
 */
@Service
public class CommandExecutor {

    @Autowired
    CommandLogFetcher commandLogFetcher;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public ExecuteResult prepare() {
        ExecuteResult r = new ExecuteResult();
        return r;
    }

    public void run(ExecuteResult result, OSCommand cmd) throws IOException {
        run(result, cmd.getComplementCommand());
    }

    public void run(ExecuteResult result, String cmd) throws IOException {
        log.debug("[cmd] {}", cmd);
        result.setProcess(Runtime.getRuntime().exec(cmd));
        result.run(commandLogFetcher);
    }



}
