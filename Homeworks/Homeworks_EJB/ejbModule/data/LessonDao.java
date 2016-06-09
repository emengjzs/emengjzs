package data;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import debug.Debug;
import logic.Semester;
import entity.AssignSubmitRecord;
import entity.Lesson;
import entity.LessonPO;
import entity.LessonRecord;

/**
 * Session Bean implementation class LessonDao
 */
@Stateless
@LocalBean
public class LessonDao extends BaseDao {
    
	@EJB
	TeacherDao teacherDao;
	
	@EJB
	StudentDao studentDao;
	
	private String sql = 
			"select new entity.LessonPO(l, c) from Lesson l , Course c ";
	
    /**
     * @see BaseDao#BaseDao()
     */
    public LessonDao() {
        super();
        this.mainEntityClass = Lesson.class;
        // TODO Auto-generated constructor stub
    }
    
    public List<Lesson> getBySemester(Semester s) {
    	return this.getBy("term_id", s.getTerm().getId());

    }
    
    private void addTeachers(List<LessonPO> list) {
    	for(LessonPO t: list) {
    		addTeacher(t);
    	}
    }
    
    private void addTeacher(LessonPO t) {
    	t.setTeacherAssigns(this.teacherDao.getByLesson(t.getLid()));
    }
    
    private void addTuitors(List<LessonPO> list) {
    	for(LessonPO t: list) {
    		addTuitor(t);
    	}
    }
    
    
    public boolean hasStudentRecord(int lid, int sid) {
    	
    	String sql = "select r from LessonRecord r where r.sid=:sid and r.lid=:lid";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	q.setParameter("sid", sid);
    	
    	try{
    	boolean r = q.getSingleResult() != null;
    	
    	return r;
    	} catch(Exception e) {return false;}
    }
    
    private void addTuitor(LessonPO t) {
    	t.setTuitorAssigns(this.studentDao.getTuitorByLesson(t.getLid()));
    }
    
    public List<LessonPO> getLessonPOByTeacherBySemester(int tid, int termId) {
    	Debug.print("tid " + tid);
    	String sql = this.sql + ", TeacherAssign ta where ta.tid=:tid and ta.lid=l.lid and l.termId=:termId and l.cid = c.cid";
    	Query q = em.createQuery(sql);
    	q.setParameter("termId", termId);
    	q.setParameter("tid", tid);
    	
    	List<LessonPO> list = q.getResultList();
    	setTuitorsAndTeachers(list);
    	return list;
    }
    
    private void setTuitorsAndTeachers(List<LessonPO> list) {
    	addTeachers(list);
    	addTuitors(list);
    }
    
    public List<LessonPO> getLessonPOByStudentBySemester(int sid, int termId) {
    	String sql = this.sql + ", LessonRecord r where r.sid=:sid and r.lid=l.lid and l.cid = c.cid and l.termId=:termId";
    	Query q = em.createQuery(sql);
    	q.setParameter("termId", termId);
    	q.setParameter("sid", sid);
    	List<LessonPO> list = q.getResultList();
    	setTuitorsAndTeachers(list);
    	return list;
    }
    
    public List<LessonPO> getLessonPOByTuitorBySemester(int sid, int termId) {
    	String sql = this.sql + ", TuitorAssign r where r.studentSid=:sid and r.lessonLid=l.lid and l.cid = c.cid and l.termId=:termId";
    	Query q = em.createQuery(sql);
    	q.setParameter("termId", termId);
    	q.setParameter("sid", sid);
    	List<LessonPO> list = q.getResultList();
    	setTuitorsAndTeachers(list);
    	return list;
    }
    public LessonRecord getLessonRecordByStudentByLesson(int lid, int sid) {
    	String sql = "select r from LessonRecord r where lid=:lid and sid=:sid";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	q.setParameter("sid", sid);
    	try {return (LessonRecord) q.getResultList().get(0);
    	} catch(Exception e){Debug.print("not found!");return null;}
    }
    
    public List<LessonRecord> getLessonRecordById(int lid, String orderby, boolean asc) {
    	String sql = "select r from LessonRecord r where lid=:lid order by " + orderby + (asc ? " asc " : " desc ");
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	return q.getResultList();
    }
    
    public LessonPO getLessonPOById(int lid) {
    	String sql = this.sql + " where l.cid = c.cid and l.lid=:lid";
    	Query q = em.createQuery(sql);
    	q.setParameter("lid", lid);
    	LessonPO result = (LessonPO) q.getSingleResult();
    	addTuitor(result);
    	addTeacher(result);
    	return result;
    }
    
    public List<LessonPO> getLessonPOBySemester(int termId) {
    	String sql = this.sql + " where l.cid = c.cid and l.termId=:termId ";
    	Debug.print(sql);
    	Query q = em.createQuery(sql);
    	q.setParameter("termId", termId);
    	List<LessonPO> list = q.getResultList();
    	setTuitorsAndTeachers(list);
    	return list;
    }
    
    public boolean delete(int termId, int cid) {
    	String sql = "delete Lesson l where l.cid=:cid and l.termId=:termId ";
    	Query q = em.createQuery(sql);
    	q.setParameter("termId", termId);
    	q.setParameter("cid", cid);
    	q.executeUpdate();
    	return true;
    }
}
