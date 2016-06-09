package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import entity.Lessonsummary;
import entity.Studentscoresummary;

/**
 * Session Bean implementation class LessonsummaryDao
 */
@Stateless
@LocalBean
public class LessonsummaryDao extends BaseDao {

    /**
     * Default constructor. 
     */
   
    public LessonsummaryDao() {
    	super();
    	this.mainEntityClass = Lessonsummary.class;
        // TODO Auto-generated constructor stub
    }
    
    public List getByInstitute(String ins) {
    	String sql = "from Lessonsummary s where s.institute=:ins";
    	Query q = em.createQuery(sql);
    	q.setParameter("ins", ins);
    	return q.getResultList();
    }
    
    public List getGroup() {
    	String sql = "select new entity.Institutesummary(institute, term, count(*), max(maxscore), avg(avgscore),"
    			+ " sum(perfect), sum(great), sum(good), sum(bad), avg(badrate)) "
    			+ "from Lessonsummary s group by institute, term ";
    	Query q = em.createQuery(sql);
    	return q.getResultList();
    }
}
