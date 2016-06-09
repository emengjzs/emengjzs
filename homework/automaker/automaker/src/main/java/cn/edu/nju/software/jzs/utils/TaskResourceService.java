package cn.edu.nju.software.jzs.utils;

import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.service.deploy.BuildType;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.internal.util.SVNPathUtil;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/9.
 */
@Component
public class TaskResourceService {

    public static final Logger logger = LoggerFactory.getLogger(TaskResourceService.class);

    @Value("${automaker.svn.export.path}")
    protected String checkoutWorkspacePath;

    @Value("${automaker.deploy.workspace.path}")
    protected String deployPath;

    @Value("${automaker.deploy.tomcat.path}")
    protected String tomcatPath;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;


    private String tomcatDictionaryName;

    @PostConstruct
    private void init()  {
        try {
            tomcatDictionaryName = StringUtils.substringBefore(getTomcatPackageFile().getName(), ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("\tResources Settings: \n" +
                    "\tcheckoutWorkspacePath: \t {} \n" +
                    "\tdeployWorkspacePath: \t {} \n" +
                    "\ttomcatPath \t {} ({})",
                checkoutWorkspacePath,
                deployPath,
                tomcatPath, tomcatDictionaryName);
    }

    public String getAbsoluteCheckOutProjectPath(Job job) {
        String projectName = SVNPathUtil.tail(job.getPrimarySvnPath());
        return checkoutWorkspacePath + "/" + job.getWorkspacePath() + "/" + projectName;
    }

    public String getAbsoluteCheckOutAutomationProjectPath(Job job) {
        JobConfig jobConfig = JSON.parseObject(job.getJobConfig(), JobConfig.class);
        return getAbsoluteCheckOutAutomationProjectPath(job, jobConfig);
    }

    public String getAbsoluteCheckOutAutomationProjectPath(Job job, JobConfig jc) {
        String projectName = SVNPathUtil.tail(jc.getAutomationSVNPath());
        return checkoutWorkspacePath + "/" + job.getWorkspacePath() + "/" + projectName;
    }

    public String getAbsoluteAutoWorkDictionaryPath(Job job) {
        return this.deployPath + job.getWorkspacePath();
    }

    public File getBuildProductFile(String workspace) throws IOException {
        return getBuildProductFile(workspace, BuildType.WAR);
    }

    public File getBuildProductFile(String workspace, BuildType type) throws IOException {
        String filePattern = "file:///" + workspace + "/target/*." + type.toString();
        Resource[] resources = resourcePatternResolver.getResources(filePattern);
        if (resources.length == 0) {
            throw new IOException("filePattern [" + filePattern + "] not found!");
        }
        logger.info("Product File Found! [{}]", resources[0].getURL());
        return resources[0].getFile();
    }

    public File getBuildProductFile(Job job, BuildType type) throws IOException {
        return getBuildProductFile(getAbsoluteCheckOutProjectPath(job), type);

    }

    public File getBuildProductFile(Job job) throws IOException {
        return getBuildProductFile(getAbsoluteCheckOutProjectPath(job));

    }

    public File getBuildAutomationFile(Job job) throws IOException {
        return getBuildProductFile(getAbsoluteCheckOutAutomationProjectPath(job));

    }

    public String getTomcatPackageFileName() {
        return tomcatPath;
    }

    public String getTomcatDictionaryName() {
        return tomcatDictionaryName;
    }

    public File getTomcatPackageFile() throws IOException {
        return resourcePatternResolver.getResource("classpath:" + this.tomcatPath).getFile();
    }

    public File getResource(String fileName) throws IOException {
        return this.resourcePatternResolver.getResource("classpath:" + fileName).getFile();
    }
}
