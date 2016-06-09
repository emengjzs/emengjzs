package logic;

import helper.DateHelper;

import java.util.Calendar;

import authority.ServiceAuthority;
import authority.ServiceAuthorityList;
import debug.Debug;
import entity.Term;

public class Semester {
	private final static int FIRST_SEMESTER_START_MONTH 	= 9;
	private final static int FIRST_SEMESTER_END_MONTH 	= 1;
	private final static int LAST_SEMESTER_START_MONTH 	= 2;
	private final static int LAST_SEMESTER_END_MONTH 		= 8;
	private final static int[][] index = { {FIRST_SEMESTER_START_MONTH-1, FIRST_SEMESTER_END_MONTH-1},
											 {LAST_SEMESTER_START_MONTH -1, LAST_SEMESTER_END_MONTH -1} };
	
	private final static int[][] yearOffSet = {{0, 1}, {1, 1}};
	
	/**
	 * 默认为year~year+1学年第no学期（no = 1, 2)设置默认开始日期和结束日期
	 * @param year
	 * @param no
	 */
	private void setSemester(int year, int no) {
		if(no > 2) {
			Debug.print("Error! no greater than 2!");
		}
		Term term = new Term();
		term.setYear(year);
		term.setNo(no);
		term.setStatus(Term.PLAN);
		term.setStartDate(createDate(year, no, 0));
		term.setEndDate(createDate(year, no, 1));
		this.setTerm(term);
	}
	
	private Calendar createDate(int year, int no, int isEndDate) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year + yearOffSet[no - 1][isEndDate]);
		c.set(Calendar.MONTH, index[no - 1][isEndDate]);
		if(isEndDate == 1 )
			this.setLastDayOfMonth(c) ;
		else 
			this.setFirstDayOfMonth(c);
		return c;
	}
	
	private Term term;
	private ServiceAuthorityList authorityList;
	
	public Calendar getStartDate() {
		return term.getStartDate();
	}
	
	public Calendar getEndDate() {
		return term.getEndDate();
	}
	
	private Semester() {
		authorityList = new ServiceAuthorityList();
	}
	
	private Semester(Term term) {
		authorityList = new ServiceAuthorityList();
		this.setTerm(term);
	}
	
	public static Semester getInstance(Term term) {
		if(term == null) {
			return null;
		}
		return new Semester(term);
	}
	
	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
		checkAuthority();
	}
	
	public String getJSStartDateString() {
		return DateHelper.getJSDateString(this.term.getStartDate());
	}
	
	public String getJSEndDateString() {
		return DateHelper.getJSDateString(this.term.getEndDate());
	}
	
	public boolean isAble(ServiceAuthority e) {
		return this.authorityList.isAble(e);
	}

	private void checkAuthority() {
		Calendar now = Calendar.getInstance();
		
		// 若计划还没开始
		if(this.getTerm().getStatus() == Term.PLAN) {
			ServiceAuthority list[] = {
					ServiceAuthority.Term_Delete,
					ServiceAuthority.Term_Lesson_Add,
					ServiceAuthority.Term_Lesson_Delete,
					ServiceAuthority.Term_Lesson_Update,
					ServiceAuthority.Term_Update,
			};
			this.authorityList.enable(list);
		}
		// 若计划没结束
		else if(this.getTerm().getEndDate().after(now)) {
			ServiceAuthority list[] = {
					//ServiceAuthority.Term_Delete,
					ServiceAuthority.Term_Lesson_Add,
					//ServiceAuthority.Term_Lesson_Delete,
					ServiceAuthority.Term_Lesson_Update,
					//ServiceAuthority.Term_Update,
			};
			this.authorityList.enable(list);
		}		
	}
	

	private int getNo() {
		return this.term.getNo();
	}
	
	private int getYear() {
		return this.term.getYear();
	}
	
	public void resetYear() {
		if(isFirstSemester(this.term.getStartDate().get(Calendar.MONTH) + 1)) {
			this.term.setYear(term.getStartDate().get(Calendar.YEAR));
		}
		else {
			this.term.setYear(term.getStartDate().get(Calendar.YEAR )-1);
		}
		Debug.print("reset:" + term.getYear());
	}
	
	public Semester getDefaultNextSemester() {
		Semester s = new Semester();
		int nextNo = (Math.min(2, getNo())) % 2 + 1;
		int nextYear = nextNo == 1 ? getYear() + 1 : getYear();
		s.setSemester(nextYear, nextNo);
		return s;
	}
	
	public static Semester getDefaultCurrentSemester() {
		Calendar c = Calendar.getInstance();
		int month =  c.get(Calendar.MONTH) + 1; 
		int year = c.get(Calendar.YEAR);
		Semester s = new Semester();
		if(isFirstSemester(month)) {
			if(FIRST_SEMESTER_START_MONTH <= month) 
				s.setSemester(year, 1);
			else
				s.setSemester(year - 1, 1);
		}
		else {
			s.setSemester(year - 1, 2);
		}
		return s;
	}
	
	
	
	private void setLastDayOfMonth(Calendar c) {
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
	
	private void setFirstDayOfMonth(Calendar c) {
		c.set(Calendar.DAY_OF_MONTH, 1);
	}
	
	public String getString() {
		return getYear() + "-" + this.term.getEndDate().get(Calendar.YEAR)+ "学年第" + getNo() + "学期";
	}
	
	public boolean getBeforeStart() {
		return  Calendar.getInstance().before(this.term.getStartDate());
	}
	
	public boolean getAfterEnd() {
		return Calendar.getInstance().after(this.term.getEndDate());
	}
	
	private static boolean isFirstSemester(int month) {
		return ((FIRST_SEMESTER_START_MONTH <= month && month <= 12) || 
				(1 <= month && month <= FIRST_SEMESTER_END_MONTH));
	}
	
	//private boolean isLastSemester(int month) {
	//	return (LAST_SEMESTER_START_MONTH <= month && month <= LAST_SEMESTER_END_MONTH);
	//}
		
	
}
