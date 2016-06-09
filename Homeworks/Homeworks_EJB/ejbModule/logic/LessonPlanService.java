package logic;

import helper.DateHelper;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import authority.ServiceAuthority;
import message.Message;
import message.Message.AddMessage;
import data.CourseDao;
import data.LessonDao;
import data.TermDao;
import debug.Debug;
import entity.Course;
import entity.Lesson;
import entity.Term;

/**
 * Session Bean implementation class LessonPlanService
 */
@Stateless
@LocalBean
public class LessonPlanService {
	
	@EJB
	TermDao termDao;
	
	@EJB
	CourseDao courseDao;
	
	@EJB
	LessonDao lessonDao;
	
    /**
     * Default constructor. 
     */
    public LessonPlanService() {
        // TODO Auto-generated constructor stub
    }
    
    public Semester getNewSemester() {
    	Semester semester = null;
    	Term lastTerm = termDao.getNewestTerm();
    	Calendar today = Calendar.getInstance();
    	if(lastTerm == null) {
    		semester = Semester.getDefaultCurrentSemester();
    		semester.getTerm().setStartDate(DateHelper.tomorrow(today));
    	}
    	else {
    		semester= (Semester.getInstance(lastTerm)).getDefaultNextSemester(); 
    		if(today.after(lastTerm.getEndDate())){
    			semester.getTerm().setStartDate(DateHelper.tomorrow(today));
    		}
    		else {
    		semester.getTerm().setStartDate(DateHelper.tomorrow(lastTerm.getEndDate()));
    		}
    	}
    	return semester;
    }
    
    public Semester createNewSemester(Term t) {
    	//Semester semester = null;
    	Debug.print("term :" + t.getStartDateString());
    	Term lastTerm = termDao.getNewestTerm();
    	Semester s = Semester.getInstance(t);
    	s.resetYear();
    	Debug.print("year: " + t.getYear());
    	if(lastTerm != null) {
    		if(lastTerm.getYear() == t.getYear()) {
    			t.setNo(lastTerm.getNo()+1);
    		}
    		else {
    			t.setNo(1);
    		}
    	}
    	else {
    		t.setNo(1);
    	}
    
    	Debug.print(t.getNo());
    	
    	this.termDao.add(t);
    	return s;
    }
    
    public Message updateSemester(Term input) {
    	Semester s = Semester.getInstance((Term) termDao.getById(input.getId()));
    	if(s == null) {
    		return Message.UpdateMessage.UPDATE_FORBID;
    	}
    	else if(s.isAble(ServiceAuthority.Term_Update)) {
    		Term t = s.getTerm();
    		t.setStartDate(input.getStartDate());
    		t.setEndDate(input.getEndDate());
    		return termDao.update(t);
    		
    	}
    	return Message.UpdateMessage.UPDATE_FORBID;
    }
    
    
    
    private Lesson initLesson(Course course) {
    	Lesson l = new Lesson();
    	l.setCid(course.getCid());
    	return l;
    }

    
    public Message addLesson(int termId, int cid) {    	
    	Course c = (Course) this.courseDao.getById(cid);
    	if(c == null) return AddMessage.ADD_FORBID;
    	Semester s = this.termDao.getSemesterByTermId(termId);
    	if(s != null && s.isAble(ServiceAuthority.Term_Lesson_Add)) {   		
    			Lesson lesson = initLesson(c);
	    		lesson.setEndDate(s.getEndDate());
	    		lesson.setStartDate(s.getStartDate());
	    		lesson.setNo(1);
	    		lesson.setStatus(Lesson.READY);
	    		lesson.setTerm(s.getString());
	    		lesson.setTermId(termId);
	    	return 	lessonDao.add(lesson);  		
    		//return r ? AddMessage.ADD_SUCCESS : AddMessage.ADD_FAILED;
    	}
    	else {
    		return AddMessage.ADD_FORBID;
    	}
    }
    

}
