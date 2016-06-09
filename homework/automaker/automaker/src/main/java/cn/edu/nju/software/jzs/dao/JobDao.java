package cn.edu.nju.software.jzs.dao;

import cn.edu.nju.software.jzs.entity.Job;
import cn.edu.nju.software.jzs.entity.JobStatus;
import cn.edu.nju.software.jzs.vo.JobSimpleInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/2.
 */
@Transactional
@Repository
public class JobDao extends BaseDao {


    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public Job getJobById(Long id) {
        return this.findById(Job.class, id);
    }

    public void saveJob(Job job) {
        logger.debug(job.getName());
        this.save(job);
    }

    public List<Job> getAll() {
        return this.getAll(Job.class, Order.desc("createTime"));

    }

    public Integer getMaxPort() {
        return (Integer) this.session.getCurrentSession()
                .createQuery("select max(port) from Job")
                .uniqueResult();
    }

    public List<Job> getScheduleJobList() {
        return this.session.getCurrentSession().createQuery(
                "from Job job where job.status !=:status and job.needSchedule = :sch"
        ).setParameter("status", JobStatus.DEPRECATED)
        .setParameter("sch", true).list();
    }



    public List<JobSimpleInfo> getJobSimpleInfoList() {
        Date now = new Date();
        now = DateUtils.truncate(now, Calendar.DATE);
        Date before = DateUtils.addWeeks(now, -6);
        return this.session.getCurrentSession()
                .createSQLQuery("SELECT job.id AS id, job.name AS name, job.`status` AS STATUS, COUNT(CASE WHEN rh.`status`= 4 THEN 1 ELSE NULL END) / COUNT(rh.id) AS health, lt.latestRunTime AS latestRunTime\n" +
                        "FROM job\n" +
                        "LEFT JOIN run_history rh ON \n" +
                        "(rh.job_id = job.id AND rh.start_time BETWEEN :s AND :d) \n" +
                        "left join (select max(rh1.start_time) as latestRunTime, rh1.job_id from run_history rh1 group by(rh1.job_id)) lt\n" +
                        "on (lt.job_id = job.id)\n" +
                        "GROUP BY job.id")
                .addScalar("id")
                .addScalar("name")
                .addScalar("status")
                .addScalar("health", StandardBasicTypes.DOUBLE)
                .addScalar("latestRunTime", StandardBasicTypes.TIMESTAMP)
                .setParameter("s", before)
                .setParameter("d", now)
                .setResultTransformer(new AliasToBeanResultTransformer(JobSimpleInfo.class))
                .list();
    }


}
