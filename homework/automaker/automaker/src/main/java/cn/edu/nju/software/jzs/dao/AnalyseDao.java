package cn.edu.nju.software.jzs.dao;

import cn.edu.nju.software.jzs.entity.AnalysePlan;
import cn.edu.nju.software.jzs.entity.AnalyseRecord;
import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.vo.AnalyseRecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Repository
public class AnalyseDao extends BaseDao {


    public List<AnalysePlan> getAnalysePlan(Long jobId) {
        return this.session.getCurrentSession()
                .createQuery("from AnalysePlan where jobId=:id")
                .setParameter("id", jobId).list();
    }


    public List<AnalyseRecordInfo> getAnalyseRecord(Long planId) {
        return this.session.getCurrentSession()
                .createQuery("select new cn.edu.nju.software.jzs.vo.AnalyseRecordInfo(id, createTime, response) from AnalyseRecord where planId=:id order by createTime desc")
                .setParameter("id", planId).list();
    }

    public Job getJobByPlanId(Long planId) {
        return (Job) this.session.getCurrentSession()
                .createSQLQuery("select {j.*} from job j join analyse_plan ap " +
                        "on (ap.id = :planId and ap.job_id = j.id)")
                .addEntity("j", Job.class)
                .setParameter("planId", planId)
                .uniqueResult();
    }

    public AnalyseRecord getById(long id) {
        return this.findById(AnalyseRecord.class, id);
    }
}
