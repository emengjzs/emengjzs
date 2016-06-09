package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import entity.Assignmentsummary;

/**
 * Session Bean implementation class AssignmentsummaryDao
 */
@Stateless
@LocalBean
public class AssignmentsummaryDao extends BaseDao{
	
	
    /**
     * Default constructor. 
     */
    public AssignmentsummaryDao() {
    	super();
    	this.mainEntityClass = Assignmentsummary.class;
        // TODO Auto-generated constructor stub
    }
    
    public List getByLesson(int lid) {
    	String sql = "from Assignmentsummary a where a.lid=:lid"; 
    	Query query = em.createQuery(sql);
    	query.setParameter("lid",lid);
    	return query.getResultList();
    }
    
}
