package cn.edu.nju.software.jzs.service.deploy;

import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.service.base.file.FileService;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/11.
 */
@Service
public class Deployer {

    @Autowired
    private FileService fileService;

    @Autowired
    private TaskResourceService taskResourceService;

    @Autowired
    private TomcatManager tomcatManager;

    public static final Logger logger = LoggerFactory.getLogger(Deployer.class);

    public void initAutoWorkspace(DeployContext deployContext) {
        File autoWorkDictionary = deployContext.getAutoWorkspaceDictionary();
        deployContext.pushLog("[Deploy] init workspace ["
                + autoWorkDictionary.getAbsolutePath() + "]");
        autoWorkDictionary.mkdirs();
    }

    public void copyAutomationToAutoWorkspace(DeployContext deployContext) throws IOException {
        File autoWorkDictionary = new File(deployContext.getAutoWorkspacePath());
        File automation = deployContext.getAutomationFile();
        deployContext.pushLog("[Deploy] copy file from "
                + automation.getAbsolutePath() + " to " + autoWorkDictionary.getAbsolutePath());
        fileService.copy(automation, autoWorkDictionary);
    }

    public void copyProductToAutoWorkspace(DeployContext deployContext) throws IOException {
        File autoWorkDictionary = new File(deployContext.getAutoWorkspacePath());
        File product = deployContext.getProductFile();
        deployContext.pushLog("[Deploy] copy file from "
                + product.getAbsolutePath() + " to " + autoWorkDictionary.getAbsolutePath());
        fileService.copy(product, autoWorkDictionary);
    }

    public void clearProduct(DeployContext deployContext) throws IOException {
        File productDictionary = new File(deployContext.getAutoWorkspaceProductPath());
        fileService.deleteDictionary(productDictionary);
    }

    public void clearAutomation(DeployContext deployContext) throws IOException {
        File productDictionary = new File(deployContext.getAutoWorkspaceAutomationPath());
        fileService.deleteDictionary(productDictionary);
    }

    public void unzipAutomation(DeployContext deployContext) throws IOException {
        fileService.unzip(new File(deployContext.getAutoWorkspaceDictionary(),
                        deployContext.getAutomationFileName()),
                deployContext.getAutoWorkspaceAutomationPath());
    }

    public void unzipProduct(DeployContext deployContext) throws IOException {
        fileService.unzip(new File(deployContext.getAutoWorkspaceDictionary(),
                        deployContext.getProductFileName()),
                deployContext.getAutoWorkspaceProductPath());
    }

    public DeployContext configDeployContext(Job j) throws IOException {
        DeployContext d = new DeployContext();
        d.setAutoWorkspacePath(taskResourceService.getAbsoluteAutoWorkDictionaryPath(j));
        d.setWorkspaceName(j.getName());
        d.setProductFile(taskResourceService.getBuildProductFile(j));
        d.setPort(j.getPort());
        d.setServerName(taskResourceService.getTomcatDictionaryName());

        return d;
    }

    public DeployContext configDeployContext(TaskRunContext tc) throws IOException {
        Job j = tc.getJob();
        DeployContext d = configDeployContext(j);
        d.setNeedAutomation(tc.getJobConfig().isNeedAutomation());

        if (tc.getJobConfig().isNeedAutomation()) {
            d.setAutomationFile(taskResourceService.getBuildAutomationFile(j));
        }

        d.setTaskLogger(tc);
        return d;
    }

    public void shutdownTomcat(DeployContext deployContext) throws IOException {
        tomcatManager.shutdown(deployContext);
    }

    public void startupTomcat(DeployContext deployContext) throws IOException {
        tomcatManager.startup(deployContext);
        deployContext.pushLog("[Deploy] Web Host is " + deployContext.getHost()
                + ":" + deployContext.getPort());
    }

    public void initTomcat(DeployContext deployContext) throws IOException {

        tomcatManager.init(deployContext);
    }
}
