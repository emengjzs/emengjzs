package authorityimp;

import entity.LessonPO;
import entity.Teacher;
import authority.BaseLessonAuthority;
import authority.LessonAuthority;

public class TeacherLessonAuthority implements LessonAuthority {
	
	private boolean isOwnLesson;
	private boolean isManager;
	private BaseLessonAuthority base;
	
	public TeacherLessonAuthority(Teacher t, LessonPO l) {
		base = new BaseLessonAuthority(l);
		//this.s = s;
		//this.l = l;
		isOwnLesson = l.hasTeacher(t.getTid());
		isManager = t.isManager();
	}
	
	
	@Override
	public boolean getCanAddTeacher() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanAddStudentAndTuitors() {
		// TODO Auto-generated method stub
		return isOwnLesson && this.base.getCanAddStudentAndTuitors();
	}

	@Override
	public boolean getCanAddAssignment() {
		// TODO Auto-generated method stub
		return this.isOwnLesson && this.base.getCanAddAssignment();
	}

	@Override
	public boolean getCanSubmitHomework() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanSeeAllHomework() {
		// TODO Auto-generated method stub
		return this.isOwnLesson || (this.isManager);
	}

	@Override
	public boolean getCanReviewHomework() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean getCanSeeOwnHomework() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean getCanSeeStudent() {
		// TODO Auto-generated method stub
		return this.isManager || this.isOwnLesson;
	}


	@Override
	public boolean getCanSeeTeacherAndTuitor() {
		// TODO Auto-generated method stub
		return this.isManager || this.isOwnLesson;
	}


	@Override
	public boolean getIsOwnTeacher() {
		// TODO Auto-generated method stub
		return this.isOwnLesson;
	}

}
