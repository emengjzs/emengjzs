package logic;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import data.LessonDao;
import data.TermDao;
import entity.LessonPO;
import entity.Term;

/**
 * Session Bean implementation class LessonService
 */
@Stateless
@LocalBean
public class SemesterService {
	
	@EJB
	TermDao termDao;
	
	@EJB
	LessonDao lessonDao;
	
    /**
     * Default constructor. 
     */
    public SemesterService() {
        // TODO Auto-generated constructor stub
    }
    
    public Semester getSemester(int sid) {
    	return Semester.getInstance((Term) termDao.getById(sid));
    }
    
    public Semester getNewestSemester() {
    	return Semester.getInstance(termDao.getNewestTerm());
    }
    
    public Term getNextTerm(int tid) {
    	return this.termDao.getNextTerm(tid);
    }
    
    public Term getLastTerm(int tid) {
    	return this.termDao.getLastTerm(tid);
    }
    
    public List<LessonPO> getLessonBySemester(int sid) {
    	return this.lessonDao.getLessonPOBySemester(sid);
    }
    
    public List<LessonPO> getLessonBySemesterByStudent(int sid, int termId) {
    	return this.lessonDao.getLessonPOByStudentBySemester(sid, termId);
    }
    
    
    public List<LessonPO> getLessonBySemesterByTeacher(int tid, int termId) {
    	return this.lessonDao.getLessonPOByTeacherBySemester(tid, termId);
    }
    
    public List<LessonPO> getLessonBySemesterByTuitor(int tid, int termId) {
    	return this.lessonDao.getLessonPOByTuitorBySemester(tid, termId);
    }
    
    
}
