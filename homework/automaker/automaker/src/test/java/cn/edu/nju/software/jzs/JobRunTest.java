package cn.edu.nju.software.jzs;

import cn.edu.nju.software.jzs.dao.JobDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.service.JobRunFacade;
import cn.edu.nju.software.jzs.service.base.cmd.CommandExecutor;
import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.NormalOutputLineListener;
import cn.edu.nju.software.jzs.service.deploy.BuildType;
import cn.edu.nju.software.jzs.service.deploy.DeployContext;
import cn.edu.nju.software.jzs.service.deploy.Deployer;
import cn.edu.nju.software.jzs.service.deploy.WindowsTomcatManager;
import cn.edu.nju.software.jzs.service.task.TaskLogger;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by emengjzs on 2016/4/9.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AutomakerApplication.class)
public class JobRunTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    JobRunFacade jobRunFacade;

    @Autowired
    JobDao jobDao;

    RestTemplate restTemplate  = new TestRestTemplate();;

    // @Test
    public void testRunJob() throws InterruptedException {
        jobRunFacade.run(6L);
        Thread.sleep(100000);
    }

    @Autowired
    Deployer deployer;

    TaskResourceService taskResourceService;

    @Test
    public void testFilePattern() throws IOException {
        File f = taskResourceService.getBuildProductFile(
                "D:/Program/JAVA/automaker-workspace/checkoutWorkspace/sampleproject/sampleproject",
                BuildType.WAR);
        Assert.assertThat(f.getName(), equalTo("sampleproject.war"));
    }

    @Test
    public void testTomcatFile() throws IOException {
        File f = taskResourceService.getTomcatPackageFile();
        Assert.assertThat(f.getName(), equalTo("apache-tomcat.zip"));
        Assert.assertThat(f.exists(), CoreMatchers.is(true));
        log.info(f.getAbsolutePath());
    }

    @Test
    public void testInitialDelployTest() throws IOException {
            jobRunFacade.run(6L);
            Job j = this.jobDao.getJobById(6L);
            DeployContext deployContext = deployer.configDeployContext(j);
            deployContext.setTaskLogger(new TaskLogger() {
                @Override
                public void pushLog(String logstr) {
                    log.info(logstr);
                }

                @Override
                public void raiseError(Exception e) {
                }
            });
        try {
            this.deployer.initAutoWorkspace(deployContext);
            this.deployer.shutdownTomcat(deployContext);
            this.deployer.initTomcat(deployContext);
            this.deployer.clearProduct(deployContext);
            this.deployer.copyProductToAutoWorkspace(deployContext);
            this.deployer.unzipProduct(deployContext);
            this.deployer.startupTomcat(deployContext);
            Thread.sleep(10000);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 11L);
            jsonObject.put("name", "ddd");

            String res = restTemplate.getForObject("http://" + deployContext.getHost()
                    + ":" + deployContext.getPort() + "/index", String.class);
            log.info(res);

            Assert.assertThat(jsonObject.toJSONString(), equalTo(
                    JSON.parseObject(res
                    ).toString())
            );
        }catch (Exception e) {
        e.printStackTrace();
        } finally {

        }
        //
    }

    @Autowired
    WindowsTomcatManager windowsTomcatManager;

    @Test
    public void testTomcatServerConfigTest() throws IOException {
        Job j = this.jobDao.getJobById(6L);
        DeployContext deployContext = deployer.configDeployContext(j);
        windowsTomcatManager.initServerConfig(deployContext);
    }

    @Test
    public void testsChedule() {
        Job j = this.jobDao.getScheduleJobList().get(0);
        Assert.assertThat(j.getId(), equalTo(12L)
        );
    }

    @Autowired
    CommandExecutor commandExecutor;
    @Test
    public void testTest() throws IOException, InterruptedException {
        ExecuteResult prepare = commandExecutor.prepare();
        prepare.register(new NormalOutputLineListener());
        commandExecutor.run(prepare, "cmd.exe /C ant");
        Thread.sleep(10000);
    }

}
