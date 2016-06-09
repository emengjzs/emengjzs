package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import logic.Semester;
import entity.Term;

/**
 * Session Bean implementation class TermDao
 */
@Stateless
@LocalBean
public class TermDao extends BaseDao {

    /**
     * Default constructor. 
     */
    public TermDao() {
    	this.mainEntityClass = Term.class;
        // TODO Auto-generated constructor stub
    }
    
    public Semester getSemesterByTermId(int id) {
    	return Semester.getInstance((Term) getById(id));
    }
    
    public Term getNewestTerm() {
    	List<Term> results = this.em.createQuery("from Term t order by start_date desc").getResultList();
    	if(results.isEmpty()) return null;
    	return results.get(0);
    }
    
    
    public Term getLastTerm(int tid) {
    	return (Term) this.getById(tid -1);
    }
    
    
    public Term getNextTerm(int tid) {
    	return (Term) this.getById(tid + 1);
    }
    
    public List<Term> findAll() {
    	List<Term> results = this.em.createQuery("from Term t order by start_date desc").getResultList();
    	return results;
    }

}
