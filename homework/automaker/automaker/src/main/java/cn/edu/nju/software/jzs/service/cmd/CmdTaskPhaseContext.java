package cn.edu.nju.software.jzs.service.cmd;

import cn.edu.nju.software.jzs.service.base.cmd.WindowsCommand;

/**
 * Created by emengjzs on 2016/4/9.
 */
public class CmdTaskPhaseContext {

    private WindowsCommand cmd;

    public CmdTaskPhaseContext(String workspace, String cmd) {
        this.cmd = new WindowsCommand(workspace, cmd);
    }


    public String getCommand() {
        return cmd.getComplementCommand();
    }

}
