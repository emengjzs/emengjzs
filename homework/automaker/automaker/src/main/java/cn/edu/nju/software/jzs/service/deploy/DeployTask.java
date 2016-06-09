package cn.edu.nju.software.jzs.service.deploy;

import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.service.task.TaskContextAware;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/12.
 */
@Service
public class DeployTask implements TaskContextAware {



    @Autowired
    private Deployer deployer;


    @Override
    public void execute(TaskRunContext tc, TaskPhase phase) {
        DeployContext deployContext = null;
        try {
            deployContext = deployer.configDeployContext(tc);

            this.deployer.initAutoWorkspace(deployContext);
            this.deployer.shutdownTomcat(deployContext);
            this.deployer.initTomcat(deployContext);
            this.deployer.clearProduct(deployContext);
            this.deployer.copyProductToAutoWorkspace(deployContext);
            this.deployer.unzipProduct(deployContext);
            this.deployer.startupTomcat(deployContext);
            if (deployContext.isNeedAutomation()) {
                this.deployer.clearAutomation(deployContext);
                this.deployer.copyAutomationToAutoWorkspace(deployContext);
                this.deployer.unzipAutomation(deployContext);
                tc.setProperties("automationDict", deployContext.getAutoWorkspaceAutomationPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
