package cn.edu.nju.software.jzs.service.deploy;

import cn.edu.nju.software.jzs.service.base.cmd.CommandExecutor;
import cn.edu.nju.software.jzs.service.base.file.FileService;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/12.
 */
public abstract class TomcatManager {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected TaskResourceService taskResourceService;

    @Autowired
    protected FileService fileService;

    @Autowired
    protected CommandExecutor commandExecutor;

    abstract void init(DeployContext deployContext) throws IOException;

    abstract void startup(DeployContext deployContext) throws IOException;

    abstract void shutdown(DeployContext deployContext) throws IOException;

    public static final String BIN = "bin";

    public static final String STARTUP_SHELL_FILE_NAME = "startup";

    public static final String SHUTDOWN_SHELL_FILE_NAME = "shutdown";

    public void unzipTomcat(DeployContext deployContext) throws IOException {
        File autoWorkDictionary = new File(deployContext.getAutoWorkspacePath());
        fileService.unzip(new File(autoWorkDictionary, taskResourceService.getTomcatPackageFileName()), autoWorkDictionary.getAbsolutePath());
    }

    public void downloadTomcat(DeployContext deployContext) throws IOException {
        File autoWorkDictionary = new File(deployContext.getAutoWorkspacePath());
        File tomcatFile = taskResourceService.getTomcatPackageFile();
        fileService.copy(tomcatFile, autoWorkDictionary);
    }
}
