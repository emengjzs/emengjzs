package authority;

import debug.Debug;

public enum ServiceAuthority {
	Term_Update,
	Term_Delete,
	Term_Lesson_Add,
	Term_Lesson_Delete,
	Term_Lesson_Update,
	Lesson_Student_Add,
	Lesson_Teacher_Add,
	Lesson_Tuitor_Add,
	Lesson_Assignment_Add,
	Lesson_Assignment_Update,
	Lesson_Assignment_Submit,
	Lesson_Assignment_Mark;

	private boolean enable = false;
	
	public void setEnable(boolean t) {
		enable = t;
	}
	
	public boolean isAble() {
		Debug.print(this.toString() + " " + enable);
		return enable;
	}
}
