package entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LessonPO {
	private Lesson lesson;
	private Course course;
	private List<Teacher> teacherAssigns; 
	private List<Student> tuitorAssigns;
	
	public LessonPO() {
		lesson = new Lesson();
		course = new Course();
		teacherAssigns = new ArrayList<Teacher>(0);
		tuitorAssigns = new ArrayList<Student>(0);
	}
	/*
	
	public LessonPO(int cid,
					 String institute,
					 String name,
					 int lid,
					 Calendar startDate,
					 Calendar endDate,
					 int no,
					 int status,
					 String term,
					 int termId
					 ) {
		lesson = new Lesson();
		course = new Course();
		this.setCid(cid);
		this.setInstitute(institute);
		this.setName(name);
		this.setLid(lid);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setNo(no);
		this.setStatus(status);
		this.setTerm(term);
		this.setTermId(termId);
	}
	
	*/
	
	public LessonPO(Lesson lesson, Course course) {
		this.lesson = lesson;
		this.course = course;
	}
	
	public Integer getCid() {
		return this.course.getCid();
	}

	public void setCid(Integer cid) {
		this.course.setCid(cid);
	}

	public String getInstitute() {
		return this.course.getInstitute();
	}

	public void setInstitute(String institute) {
		this.course.setInstitute(institute);
	}

	public String getName() {
		return this.course.getName();
	}
	
	public String getFullName() {
		String s = this.course.getName();
		if(this.getNo() > 1)
			s =  s + "(" + this.lesson.getNo() + ")";
		return s;
	}
	
	public void setName(String name) {
		this.course.setName(name);
	}
	
	public int getLid() {
		return this.lesson.getLid();
	}

	public void setLid(int lid) {
		this.lesson.setLid(lid);
	}


	public Calendar getEndDate() {
		return this.lesson.getEndDate();
	}

	public void setEndDate(Calendar endDate) {
		this.lesson.setEndDate(endDate);
	}

	public int getNo() {
		return this.lesson.getNo();
	}

	public void setNo(int no) {
		this.lesson.setNo(no);
	}

	public Calendar getStartDate() {
		return this.lesson.getStartDate();
	}

	public void setStartDate(Calendar startDate) {
		this.lesson.setStartDate(startDate);
	}

	public int getStatus() {
		return this.lesson.getStatus();
	}

	public void setStatus(int status) {
		this.lesson.setStatus(status);
	}

	public String getTerm() {
		return this.lesson.getTerm();
	}

	public void setTerm(String term) {
		this.lesson.setTerm(term);
	}

	public int getTermId() {
		return this.lesson.getTermId();
	}

	public void setTermId(int termId) {
		this.lesson.setTermId(termId);
	}

	public List<Teacher> getTeacherAssigns() {
		return teacherAssigns;
	}

	public void setTeacherAssigns(List<Teacher> teacherAssigns) {
		this.teacherAssigns = teacherAssigns;
	}

	public List<Student> getTuitorAssigns() {
		return tuitorAssigns;
	}

	public void setTuitorAssigns(List<Student> tuitorAssigns) {
		this.tuitorAssigns = tuitorAssigns;
	}
	
	public boolean hasTeacher(int tid) {
		for(Teacher t : teacherAssigns) {
			if(t.getTid() == tid)
				return true;
		}
		return false;
	}
	
	
	public boolean hasTuitor(int sid) {
		for(Student t : tuitorAssigns) {
			if(t.getSid() == sid)
				return true;
		}
		return false;
	}
	
	public String getTuitorsNameString() {
		StringBuffer sb = new StringBuffer();
		for(Student t : this.tuitorAssigns) {
			sb.append(t.getName() + " ");
		}
		return sb.toString();
	}
	
	public String getTeachersNameString() {
		StringBuffer sb = new StringBuffer();
		for(Teacher t : this.teacherAssigns) {
			sb.append(t.getName() + " ");
		}
		return sb.toString();
	}
	
	public String getScoreDescription() {
		return this.lesson.getScoreDescription();
	}

	public void setScoreDescription(String scoreDescription) {
		this.lesson.setScoreDescription(scoreDescription);
	}

	
}
