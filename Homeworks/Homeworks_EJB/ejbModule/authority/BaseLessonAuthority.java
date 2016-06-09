package authority;

import java.util.Calendar;

import entity.LessonPO;

public class BaseLessonAuthority implements LessonAuthority{
	
	private LessonPO l;
	
	
	public BaseLessonAuthority(LessonPO l) {
		this.l = l;
	}
	
	@Override
	public boolean getCanAddTeacher() {
		Calendar today = Calendar.getInstance();
		if(today.after(l.getStartDate()))
		
			return false;
		return true;
	}

	@Override
	public boolean getCanAddStudentAndTuitors() {
		Calendar today = Calendar.getInstance();
		if(today.after(l.getStartDate()))
		
			return false;
		return true;
	}

	@Override
	public boolean getCanAddAssignment() {
		Calendar today = Calendar.getInstance();
		if(today.after(l.getEndDate()) || today.before(l.getStartDate()))		
			return false;
		return true;
	}

	@Override
	public boolean getCanSubmitHomework() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCanSeeAllHomework() {
		// TODO Auto-generated method stub
		return true;
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
