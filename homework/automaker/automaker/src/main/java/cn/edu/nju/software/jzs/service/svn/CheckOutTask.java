package cn.edu.nju.software.jzs.service.svn;

import cn.edu.nju.software.jzs.service.TaskRunContext;
import cn.edu.nju.software.jzs.service.task.TaskContextAware;
import cn.edu.nju.software.jzs.service.task.TaskPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;

/**
 * Created by emengjzs on 2016/4/6.
 */
@Service
public class CheckOutTask implements TaskContextAware {

    @Autowired
    private CheckOutService checkOutService;


    public void execute(TaskRunContext tc, TaskPhase taskPhase
    ) {
        try {
            checkOutService.export(tc.getJob());
            if (tc.getJobConfig().isNeedAutomation()) {
                checkOutService.exportAutomation(tc.getJob(), tc.getJobConfig());
            }
        } catch (SVNException e) {
            e.printStackTrace();
            throw new CheckOutException(e);
        }
    }
}
