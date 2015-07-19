package data;

import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import statistic.MonthDate;
import debug.Debug;
import entity.AssignSubmitRecord;
import entity.AssignSubmitRecordPO;
import entity.Assignment;
import entity.AssignmentStudentPO;
import entity.AssignmentScore;

/**
 * Session Bean implementation class AssignmentDao
 */
@Stateless
@LocalBean
public class AssignmentDao extends BaseDao {
       
    /**
     * @see BaseDao#BaseDao()
     */
    public AssignmentDao() {
        super();
        this.mainEntityClass = Assignment.class;
        // TODO Auto-generated constructor stub
    }
    
    public List<Assignment> getRecent(int lid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, 10);
    	MonthDate md = new MonthDate(Calendar.getInstance(), c);
    	String sql = "from Assignment a "
    			+ " where a.deadline " + md.getMonthSQLString();
    	Query query = em.createQuery(sql);
    	//query.setParameter("lid",lid);
    	return query.getResultList();
    }
    
    public List<Assignment> getByLesson(int lid) {
    	String parameter = "lid";
    	Query query = em.createQuery("from " + mainEntityClass.getSimpleName()  + " s where s." + parameter + "=:" + parameter + " order by s.deadline desc");
		query.setParameter(parameter,lid);
		return query.getResultList();
    }
    
    public AssignSubmitRecord getSubmitRecord(int sid, int aid) {
    	String sql = "select ar from AssignSubmitRecord ar where ar.sid=:sid and ar.aid=:aid";
    	Query query = em.createQuery(sql);
    	query.setParameter("aid",aid);
    	query.setParameter("sid", sid);
    	Debug.print("aid:" + aid + "sid: " + sid);
    	try {return (AssignSubmitRecord) query.getResultList().get(0);
    	} catch(Exception e){Debug.print("not found!");return null;}
    }
    
    public List<AssignSubmitRecordPO> getSubmitRecordPOs(int aid) {
    	String sql = "select new entity.AssignSubmitRecordPO(s, asr) from Student s, AssignSubmitRecord asr "
    			+ "where asr.sid=s.sid and asr.aid=:aid";
    	Query query = em.createQuery(sql);
    	query.setParameter("aid",aid);
    	return query.getResultList();
    }
    
    public List<AssignSubmitRecord> getSubmitRecordsWithDesc(int aid) {
    	Query query = em.createQuery("select * from AssignSubmitRecord asr where asr.aid=:aid order by asr.score desc");
    	query.setParameter("aid",aid);
    	return query.getResultList();
    }
    
    public List<AssignmentStudentPO> getAssignmentForStudent(int sid, int lid) {
    	String sql = "select new entity.AssignmentStudentPO(ass, ar) from Assignment ass, AssignSubmitRecord ar where ass.lid=:lid and ar.sid=:sid and ass.aid=ar.aid order by ass.deadline desc";
    	Query query = em.createQuery(sql);
    	query.setParameter("lid",lid);
    	query.setParameter("sid", sid);
    	return query.getResultList();
    }
    
    public List<AssignmentScore> getAllScore(int lid) {
    	String sql = 
    			"select new entity.AssignmentScore(s.sid, s.name, ai.name, ar.score, ai.aid) from Student s, Assignment ai, AssignSubmitRecord ar " +
    			" where ai.lid=:lid and ar.aid=ai.aid and ar.sid=s.sid order by s.sid, ai.aid";
    	Query query = em.createQuery(sql);
    	query.setParameter("lid",lid);
    	return query.getResultList();
    }
}
