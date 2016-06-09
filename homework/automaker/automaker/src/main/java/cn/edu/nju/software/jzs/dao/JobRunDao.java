package cn.edu.nju.software.jzs.dao;

import cn.edu.nju.software.jzs.entity.*;
import cn.edu.nju.software.jzs.utils.Page;
import cn.edu.nju.software.jzs.vo.RunRecordVO;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by emengjzs on 2016/4/5.
 */
@Repository
public class JobRunDao extends BaseDao {

    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public RunHistory findRunHistoryById(Long id) {
        return this.findById(RunHistory.class, id);
    }


    public RunLogContent findRunLogContentByRunHistoryId(Long runHistoryId) {
        return (RunLogContent) this.session.getCurrentSession().createQuery(
                "from RunLogContent rc where rc.runHistoryId= :id")
                .setParameter("id", runHistoryId)
                .uniqueResult();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Long getMaxRunNo(Job job) {
        RunNo no = (RunNo) this.session.getCurrentSession().createQuery(
                "select rh from RunNo as rh join rh.job as job on (job.id = :id) "
        ).setParameter("id", job.getId()).uniqueResult();
        no.setNo(1 + no.getNo());
        return no.getNo();
    }

    public Integer getLastJobRunTime(Long jobId) {
        return (Integer) this.session.getCurrentSession().createSQLQuery(
                "select spend_time as spendTime from run_history rh " +
                        "where rh.job_id =:jobId and rh.`status` = :status order by rh.start_time desc limit 1;")

                .setParameter("jobId", jobId)
                .setParameter("status", RunStatus.SUCCESS.ordinal())
                .uniqueResult();
    }


    public RunHistory getRunHistory(Long jobRunHistoryId) {
        return this.findById(RunHistory.class, jobRunHistoryId);
    }

    public List<RunRecordVO> getRunHistory(Page p, Long jobId) {
        return p.setPage(this.session.getCurrentSession()
                .createQuery("select rh.id as id, rh.job.id as jobId," +
                        "rh.startTime as startTime , rh.endTime as endTime , rh.spendTime as  spendTime," +
                        "rh.runNo as runNo , rh.status as status , rh.successCount as successCount, " +
                        "rh.failCount as failCount, "+
                        "rh.skipCount as skipCount, "+
                        "rh.totalCount as totalCount "+
                        "from RunHistory rh " +
                        "where rh.job.id=:jobId" +
                        " order by rh.runNo desc").setParameter("jobId", jobId)
        ).setResultTransformer(new AliasToBeanResultTransformer(RunRecordVO.class)).list();
    }


    public String findLatestStabledLogContent(Long jobId) {
        return (String) session.getCurrentSession().createSQLQuery(
                "select lc.log_content from run_log_content lc where lc.job_id=:jobId order by run_history_id desc limit 1"
        ).setParameter("jobId", jobId).uniqueResult();
    }


    public String findLongContent(Long jhId) {
        return (String) session.getCurrentSession().createSQLQuery(
                "select lc.log_content from run_log_content lc where lc.run_history_id=:jhId"
        ).setParameter("jhId", jhId).uniqueResult();
    }

    public String findTestReportContent(Long jhId) {
        return (String) session.getCurrentSession().createSQLQuery(
                "select lc.test_report from run_log_content lc where lc.run_history_id=:jhId"
        ).setParameter("jhId", jhId).uniqueResult();
    }
}
