package authorityimp;

import authority.LessonAuthority;

public class NoneLessonAuthority implements LessonAuthority {

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
