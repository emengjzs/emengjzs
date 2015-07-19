package authorityimp;

import authority.BaseLessonAuthority;
import authority.LessonAuthority;
import entity.LessonPO;

public class AdminLessonAuthority implements LessonAuthority {
	
	private BaseLessonAuthority base;
	//private LessonPO l;
	
	public AdminLessonAuthority(LessonPO l) {
		base = new BaseLessonAuthority(l);
		//this.s = s;
		//this.l = l;

	}
	
	
	@Override
	public boolean getCanAddTeacher() {
		// TODO Auto-generated method stub
		return base.getCanAddTeacher();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanSeeAllHomework() {
		// TODO Auto-generated method stub
		return false;
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
		return true;
	}


	@Override
	public boolean getCanSeeTeacherAndTuitor() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean getIsOwnTeacher() {
		// TODO Auto-generated method stub
		return false;
	}

}
