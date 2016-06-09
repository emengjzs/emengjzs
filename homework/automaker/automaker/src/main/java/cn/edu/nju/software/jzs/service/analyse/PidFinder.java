package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.service.base.cmd.CommandExecutor;
import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.ReadLineListener;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class PidFinder {

    @Autowired
    CommandExecutor commandExecutor;

    String scriptPath;

    @Autowired
    public PidFinder(TaskResourceService taskResourceService) throws IOException {
        scriptPath = taskResourceService.getResource("templates/pid.bat").getAbsolutePath();
    }

    public Integer getPid(final int port) throws IOException {
        ExecuteResult prepare = commandExecutor.prepare();
        final Integer[] pid = new Integer[1];
        prepare.register(new ReadLineListener() {
            @Override
            public void onReadLine(String str) {
                System.out.println(str);
                if (! StringUtils.isEmpty(str)) {
                    String[] info = str.trim().split(" +");
                    if (info[1].contains(String.valueOf(port)))
                        pid[0] = Integer.parseInt(info[info.length-1]);
                }
            }
        });
        commandExecutor.run(prepare, scriptPath + " " + "0.0.0.0:" + port);
        prepare.getResult();
        return pid[0];
    }
}
