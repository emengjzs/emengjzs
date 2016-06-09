package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.dao.AnalyseDao;
import cn.edu.nju.software.jzs.entity.AnalysePlan;
import cn.edu.nju.software.jzs.entity.AnalyseRecord;
import cn.edu.nju.software.jzs.entity.RunStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by emengjzs on 2016/5/1.
 */
@Service
public class AnalysePlanService {

    @Autowired
    AnalyseDao analyseDao;



    public void create(AnalysePlan analysePlan) {
        analysePlan.setCreateTime(new Date());
        analysePlan.setRunStatus(RunStatus.NOT_READY);
        analyseDao.save(analysePlan);
    }

    public List getAnalysePlanList(long jobId) {
        return analyseDao.getAnalysePlan(jobId);
    }

    public List getAnalyseRecordList(long jobId) {
        return analyseDao.getAnalyseRecord(jobId);
    }

    public AnalyseRecord getAnalyseRecord(long id) {
        return analyseDao.getById(id);
    }

    public Object getAnalysePlan(long planId) {
        return analyseDao.findById(AnalysePlan.class, planId);
    }
}
