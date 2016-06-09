package cn.edu.nju.software.jzs.service.deploy;

import cn.edu.nju.software.jzs.service.task.TaskLogger;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by emengjzs on 2016/4/12.
 */
public class DeployContext implements TaskLogger {
    private final String AUTO = "-automation";
    private Integer port;
    private String host = "127.0.0.1";
    private Integer shutdownPort;
    private String workspaceName;
    private String autoWorkspacePath;
    private String productName;
    private String serverName;
    private String automationName;
    private boolean needAutomation;

    @JSONField(serialize = false)
    private transient File productFile;

    @JSONField(serialize = false)
    private transient File automationFile;

    @JSONField(serialize = false)
    private transient TaskLogger taskLogger;

    public void setTaskLogger(TaskLogger taskLogger) {
        this.taskLogger = taskLogger;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getShutdownPort() {
        return shutdownPort;
    }

    public void setShutdownPort(Integer shutdownPort) {
        this.shutdownPort = shutdownPort;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getAutoWorkspacePath() {
        return autoWorkspacePath;
    }

    public String getAutoWorkspaceServerPath() {
        return  autoWorkspacePath + "/" + getServerName();
    }

    public String getAutoWorkspaceProductPath() {
        return autoWorkspacePath + "/" + productName;
    }

    public String getAutoWorkspaceAutomationPath() {
        return autoWorkspacePath + "/" + automationName;
    }

    @JSONField(serialize = false)
    public File getAutoWorkspaceDictionary() {
        return new File(autoWorkspacePath);
    }

    public void setAutoWorkspacePath(String autoWorkspacePath) {
        this.autoWorkspacePath = autoWorkspacePath;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductFileName() {
        return this.getProductFile().getName();
    }

    @JSONField(serialize = false)
    public File getProductFile() {
        return productFile;
    }

    public void setProductFile(File productFile) {
        this.productFile = productFile;
        this.productName = StringUtils.substringBeforeLast(productFile.getName(), ".");
    }

    @JSONField(serialize = false)
    public File getAutomationFile() {
        return automationFile;
    }

    public void setAutomationFile(File automationFile) {
        this.automationFile = automationFile;
        this.automationName = StringUtils.substringBeforeLast(automationFile.getName(), ".");
    }

    public String getAutomationFileName() {
        return this.automationFile.getName();
    }

    @Override
    public void pushLog(String log) {

        this.taskLogger.pushLog(log);
    }

    @Override
    public void raiseError(Exception e) {
        this.taskLogger.raiseError(e);
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public Integer getShutDownHost() {
        return this.port == null ? 8500 : port + 100;
    }

    public boolean isNeedAutomation() {
        return needAutomation;
    }

    public void setNeedAutomation(boolean needAutomation) {
        this.needAutomation = needAutomation;
    }
}
