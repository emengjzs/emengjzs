package data;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import message.Message;
import dataservice.StudentDaoService;
import entity.Assignment;
import entity.Student;
import entity.TuitorAssign;

/**
 * Session Bean implementation class StudentDao
 */
@Stateless
@LocalBean
public class StudentDao extends BaseDao implements StudentDaoService {
     
    /**
     * @see BaseDao#BaseDao()
     */
    public StudentDao() {
        super();     
        this.mainEntityClass = Student.class;
    }

	@Override
	public Object getStudentById(int id) {
		return this.getById(id);
	}
	
	public List<Student> getStudentByLesson(int lid) {
		Query q = this.em.createQuery("select s from Student s, LessonRecord r where s.sid=r.sid and r.lid=:lid");
		q.setParameter("lid", lid);
		return q.getResultList();
	}
	
	public List<Student> getTuitorByLesson(int lid) {
		Query q = this.em.createQuery("select s from Student s, TuitorAssign ta where ta.studentSid=s.sid and ta.lessonLid=:lid");
		q.setParameter("lid", lid);
		return q.getResultList();
	}
	
	public Message addTuitor(int sid, int lid) {
		TuitorAssign ta = new TuitorAssign();
		ta.setLessonLid(lid);
		ta.setStudentSid(sid);
		return this.add(ta);
	}
	
	
	public List<Student> getStudentByInstitute(String institute) {
		return this.getBy("institute", institute);
	}
	
	public List<Student> getStudentByYear(int year) {
		Calendar date = Calendar.getInstance();
		date.set(year, 0, 1);
		return this.getBy("admissionYear", date);
	}
	
	public List<Student> getStudentExceptLesson(int lid) {
		String sql = "select s from Student s where not exists (from LessonRecord lr where lr.lid = :lid and lr.sid = s.sid)";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
	}
	
	public List<Student> getTuitorExceptLesson(int lid) {
		String sql = "select s from Student s where s.isTuitor = true and not exists (from TuitorAssign ta where ta.lessonLid = :lid and ta.studentSid = s.sid)";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
	}
    
	public List<Student> getStudentExceptAssignment(Assignment as) {
		String sql = "select s from Student s where exists ("
				+ "select lr from LessonRecord lr where lr.sid=s.sid and "
				+ "lr.lid=:lid) and not exists ( "
				+ " select ar from AssignSubmitRecord ar where ar.aid=:aid and ar.sid=s.sid)";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", as.getLid());
    	q.setParameter("aid", as.getAid());
    	return q.getResultList();
	}
	
	@Override
	public Message addStudent(Student student) {
			return this.add(student);
	}
    
	@Override
	public boolean addStudents(List<Student> students) {
		Iterator<Student> itr = students.iterator();
		
		while(itr.hasNext()) {
			this.em.persist(itr.next());
		}
		
		try {
			this.em.flush();
			this.em.clear();
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}
		return true;
	}
	
}
