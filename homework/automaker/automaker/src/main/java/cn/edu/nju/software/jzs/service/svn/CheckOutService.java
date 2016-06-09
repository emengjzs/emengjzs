package cn.edu.nju.software.jzs.service.svn;

import cn.edu.nju.software.jzs.dao.JobDao;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobConfig;
import cn.edu.nju.software.jzs.service.base.svn.SVNService;
import cn.edu.nju.software.jzs.utils.TaskResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;

/**
 * Created by emengjzs on 2016/4/3.
 */
@Service
public class CheckOutService {
    @Autowired
    private SVNService svnService;

    @Autowired
    private JobDao jobDao;

    @Autowired
    TaskResourceService jobUtils;

    public void export(Job job) throws SVNException {
        String exportDict = jobUtils.getAbsoluteCheckOutProjectPath(job);
        svnService.export(job.getPrimarySvnPath(), exportDict);
    }

    public void exportAutomation(Job job, JobConfig jobConfig) throws SVNException  {
        String exportDict = jobUtils.getAbsoluteCheckOutAutomationProjectPath(job, jobConfig);
        svnService.export(jobConfig.getAutomationSVNPath(), exportDict);
    }
}
