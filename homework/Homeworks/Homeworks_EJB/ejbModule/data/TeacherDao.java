package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import dataservice.TeacherDaoService;
import entity.Student;
import entity.Teacher;

/**
 * Session Bean implementation class TeacherDao
 */
@Stateless
@LocalBean
public class TeacherDao extends BaseDao implements TeacherDaoService {

    /**
     * Default constructor. 
     */
    public TeacherDao() {
       super();
       this.mainEntityClass= Teacher.class;
    }
    
    
    public List<Teacher> getByLesson(int lid) {
    	String sql = "select t from Teacher t, TeacherAssign ta where ta.lid=:lid and ta.tid=t.tid";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
    }
    
	public List<Student> getTeacherExceptLesson(int lid) {
		String sql = "select t from Teacher t where not exists (from TeacherAssign ta where ta.lid = :lid and ta.tid = t.tid)";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
	}
	
}
