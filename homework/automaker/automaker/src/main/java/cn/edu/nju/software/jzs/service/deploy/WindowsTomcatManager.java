package cn.edu.nju.software.jzs.service.deploy;

import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.NormalOutputLineListener;
import cn.edu.nju.software.jzs.service.base.cmd.WindowsCommand;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/12.
 */
@Service
public class WindowsTomcatManager extends TomcatManager {

    private String CUSTOM_STARTUP_SHELL_FILE_NAME = "mystartup";

    @Autowired
    VelocityEngine velocityEngine;

    @Override
    public void init(DeployContext deployContext) throws IOException {

        deployContext.pushLog("[Deploy] Prepare Tomcat...");
        downloadTomcat(deployContext);

        deployContext.pushLog("[Deploy] Unzip Tomcat...");
        unzipTomcat(deployContext);

        // windows 需要使用修改后的bat文件
        deployContext.pushLog("[Deploy] Config Tomcat startup script...");
        fileService.copy(taskResourceService.getResource("templates/" + CUSTOM_STARTUP_SHELL_FILE_NAME + ".bat"),
                new File(deployContext.getAutoWorkspacePath(),
                        this.taskResourceService.getTomcatDictionaryName() + "/bin"));

        deployContext.pushLog("[Deploy] Config Tomcat server.xml config...");
        initServerConfig(deployContext);

    }

    public void initServerConfig(DeployContext deployContext) throws IOException {
        // 修改server.xml文件
        File f = new File(deployContext.getAutoWorkspaceServerPath(), "conf/server.xml");
        FileWriter fw = new FileWriter(f);
        VelocityEngineUtils.mergeTemplate(velocityEngine,
                "server.vm", "utf-8", (JSONObject) JSON.toJSON(deployContext),
        fw);
        fw.flush();
        fw.close();
    }


    @Override
    public void startup(DeployContext deployContext) throws IOException {
        deployContext.pushLog("[Deploy] Start Tomcat");
        triggerTomcat(deployContext, CUSTOM_STARTUP_SHELL_FILE_NAME);
    }

    @Override
    public void shutdown(DeployContext deployContext) throws IOException {
        deployContext.pushLog("[Deploy] Shutdown Tomcat");
        triggerTomcat(deployContext, SHUTDOWN_SHELL_FILE_NAME);
    }

    private void triggerTomcat(DeployContext deployContext, String order) throws IOException {
        WindowsCommand windowsCommand = new WindowsCommand(
                deployContext.getAutoWorkspacePath() + "/" + this.taskResourceService.getTomcatDictionaryName()
                 + "/bin",order)
                ;
        ExecuteResult prepare = this.commandExecutor.prepare()
                .register(new NormalOutputLineListener(null));
        this.commandExecutor.run(prepare,
                windowsCommand.getComplementShellCommand());
        prepare.getResult();
    }

}
