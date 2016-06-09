package authority;

public interface LessonAuthority {
	public boolean getCanAddTeacher();
	public boolean getCanAddStudentAndTuitors();
	public boolean getCanSeeStudent();
	public boolean getCanSeeTeacherAndTuitor();
	public boolean getCanSeeOwnHomework();
	public boolean getCanAddAssignment();
	public boolean getCanSubmitHomework();
	public boolean getCanSeeAllHomework();
	public boolean getCanReviewHomework();
	public boolean getIsOwnTeacher();
}
