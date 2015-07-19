package statistic;

import helper.DateHelper;

import java.util.Calendar;

public class MonthDate {
	private Calendar start;
	private Calendar end;
	private static DateHelper dh = new DateHelper("yyyy-MM-dd");
	
	
	public MonthDate() {
		start = Calendar.getInstance();
		int year = start.get(Calendar.YEAR);
		int month = start.get(Calendar.MONTH) + 1;
		init(year, month);
	}
	
	public MonthDate(Calendar start, Calendar end) {
		this.end = end;
		this.start = start;
	}
	
	private void init(int year, int month) {
		end = Calendar.getInstance();
		end.set(Calendar.YEAR, year);
		end.set(Calendar.MONTH, month -1);
		setLastDayOfMonth(end);
		start = Calendar.getInstance();
		start.set(Calendar.YEAR, year);
		start.set(Calendar.MONTH, month -1);
		start.set(Calendar.DATE, 1);
	}
	
	public MonthDate(int year, int month) {
		init(year, month);
	}
	
	private void setLastDayOfMonth(Calendar c) {
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
	
	public String getMonthSQLString() {
		return " between '" + dh.getDateString(start) + "' and '" + dh.getDateString(end) + "' "; 
	}
	
	
	public String getFirstDateString() {
		return dh.getDateString(start);
	}
	
	public String getLastDateString() {
		return dh.getDateString(end);
	}
}
