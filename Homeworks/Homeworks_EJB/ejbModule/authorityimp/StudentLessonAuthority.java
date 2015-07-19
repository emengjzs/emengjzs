package authorityimp;

import authority.BaseLessonAuthority;
import authority.LessonAuthority;
import entity.LessonPO;
import entity.Student;

public class StudentLessonAuthority implements LessonAuthority {
	//private Student s;
	//private LessonPO l;
	private BaseLessonAuthority base;
	
	
	
	private boolean isOwnLesson; 
	private boolean isTuitorLesson;
	
	public StudentLessonAuthority(Student s, LessonPO l) {
		base = new BaseLessonAuthority(l);
		//this.s = s;
		//this.l = l;
		isTuitorLesson = l.hasTuitor(s.getSid());
		//isOwnLesson = lessonDao.hasStudentRecord(l.getLid(), s.getSid());
	}
	
	public void setIsOwnLesson(boolean n) {
		this.isOwnLesson = n;
	}
	

	@Override
	public boolean getCanAddTeacher() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanAddStudentAndTuitors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanAddAssignment() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanSubmitHomework() {
		
		return this.isOwnLesson && this.base.getCanSubmitHomework();
	}

	@Override
	public boolean getCanSeeAllHomework() {
		// TODO Auto-generated method stub
		return this.isTuitorLesson;
	}

	@Override
	public boolean getCanReviewHomework() {
		// TODO Auto-generated method stub
		return this.isTuitorLesson;
	}

	@Override
	public boolean getCanSeeOwnHomework() {
		// TODO Auto-generated method stub
		return this.isOwnLesson;
	}

	@Override
	public boolean getCanSeeStudent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanSeeTeacherAndTuitor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIsOwnTeacher() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
