package cn.edu.nju.software.jzs.service.base.cmd;

/**
 * Created by emengjzs on 2016/4/8.
 */
public class WindowsCommand implements OSCommand{
    private String basePath = "./";
    private final String SHELL_PREFIX = ".bat";
    private String cmd;
    public WindowsCommand() {

    }

    public WindowsCommand(String basePath) {
        this.basePath = basePath;
    }

    public WindowsCommand(String basePath, String cmd) {
        this.basePath = basePath;
        this.cmd = cmd;
    }

    public String getComplementCommand() {
        return getComplementCommand(cmd);
    }

    private String getComplementCommand(String cmd) {
        return String.format("cmd.exe /C cd /d %s & %s",
                basePath, cmd);
    }

    public String getComplementShellCommand() {
        return getComplementCommand(cmd + SHELL_PREFIX);
    }



    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
