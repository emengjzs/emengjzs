package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import entity.Studentscoresummary;

/**
 * Session Bean implementation class StudentScoreSummaryDao
 */
@Stateless
@LocalBean
public class StudentScoreSummaryDao extends BaseDao{

    /**
     * Default constructor. 
     */
    public StudentScoreSummaryDao() {
    	super();
    	this.mainEntityClass = Studentscoresummary.class;
        // TODO Auto-generated constructor stub
    }
    
    public List getByStudent(int sid) {
    	String sql = "from Studentscoresummary s where s.sid=:sid";
    	Query q = em.createQuery(sql);
    	q.setParameter("sid", sid);
    	return q.getResultList();
    }
    
    public List getByLesson(int lid) {
    	String sql = "select s from Studentscoresummary s where s.lid=:lid";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
    }
    

}
