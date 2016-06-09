package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import entity.Course;

/**
 * Session Bean implementation class CourseDao
 */
@Stateless
@LocalBean
public class CourseDao extends BaseDao {
       
    /**
     * @see BaseDao#BaseDao()
     */
    public CourseDao() {
        super();
        this.mainEntityClass = Course.class;
        // TODO Auto-generated constructor stub
    }
    
    
    public List<Course> getCourseByInstitute(String institute) {
    	return this.getBy("institute", institute);
    }
    
    public List<Course> getCourseExceptTerm(int tid) {
    	String sql = "select c from Course c where not exists (from Lesson l where l.cid = c.cid and l.termId = :tid)";
    	Query q = em.createQuery(sql);
    	q.setParameter("tid", tid);
    	return q.getResultList();
    }
    
    public int getNewCid() {
    	String hql = "select count(*) from Course";
    	Query q = em.createQuery(hql);
    	int cid = new Long((long)q.getSingleResult()).intValue();
    	while(this.getById(++ cid) != null) {}
    	return cid;
    }
}
