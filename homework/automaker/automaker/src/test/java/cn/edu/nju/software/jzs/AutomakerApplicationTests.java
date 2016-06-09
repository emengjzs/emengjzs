package cn.edu.nju.software.jzs;

import cn.edu.nju.software.jzs.dao.JobDao;
import cn.edu.nju.software.jzs.dao.JobRunDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.entity.RunHistory;
import cn.edu.nju.software.jzs.service.JobService;
import cn.edu.nju.software.jzs.service.base.cmd.CommandExecutor;
import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.WindowsCommand;
import cn.edu.nju.software.jzs.service.base.svn.SVNService;
import cn.edu.nju.software.jzs.service.svn.CheckOutService;
import cn.edu.nju.software.jzs.utils.Page;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tmatesoft.svn.core.SVNException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AutomakerApplication.class)
//@WebAppConfiguration
public class AutomakerApplicationTests {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void mysqlConnectionTest() {
        Assert.assertEquals(jdbcTemplate.queryForList("select * FROM job").size(), 1);
	}

    @Autowired
    JobDao jobDao;

    @Autowired
    JobService jobService;

    @Test
    public void testJobDao() {
        Assert.assertEquals(jobDao.getJobById(1L).getName(), "firstjob");
    }

    @Autowired
    SVNService svnService;

    @Test
    public void svnExportTest() throws SVNException {
        svnService.export("file:///D:/Program/JAVA/AutoRepo/sampleproject", "D:/Program/JAVA/automaker-workspace/CheckoutWorkspace");
    }

    @Autowired
    CheckOutService checkOutService;

    @Test
    public void testCreateJob() throws SchedulerException {
        this.jobService.createJob("AreYouOk", "MiBand", "file:///dfdf", new JobConfig());
    }

    @Test
    public void testExportFromJob() throws SVNException {
        Job job = jobDao.getJobById(1L);
        this.checkOutService.export(job);
    }

    @Test
    public void testFindAllJobs() {
        log.debug(JSON.toJSONString(this.jobDao.getAll(), true));
    }

    @Autowired
    JobRunDao jobRunDao;

    @Test
    public void testJobRun() {
        RunHistory rh = this.jobRunDao.findRunHistoryById(1L);
        log.debug(JSON.toJSONString(rh, true));
    }

    @Test
    public void testJobTime() {

        Assert.assertThat(this.jobRunDao.getLastJobRunTime(6L), equalTo(12292));
    }

    @Test
    public void testJobRunNum() {
        Job job = jobDao.getJobById(6L);
        Long i = this.jobRunDao.getMaxRunNo(job);
        Assert.assertEquals((Long) i, (Long) 2L);
    }

    @Test
    public void testJobRunPage() {
        Page p = new Page();
        log.debug(JSON.toJSONString(this.jobRunDao.getRunHistory(p, 6L), true));
    }


    @Test
    public void testCmd() {
        WindowsCommand command = new WindowsCommand("D:/Program/JAVA/automaker-workspace/ProjectWorksapce/sampleproject", "mvn clean package");
        log.debug(command.getComplementCommand());
    }

    @Autowired
    CommandExecutor commandExecutor;

    @Test
    public void testExec() throws InterruptedException, IOException {
        WindowsCommand command = new WindowsCommand("D:/Program/JAVA/automaker-workspace/ProjectWorksapce/sampleproject", "mvn clean package");
        ExecuteResult prepare = commandExecutor.prepare();
        commandExecutor.run(prepare, command.getComplementCommand());
        Thread.sleep(30000)
        ;
    }


}
