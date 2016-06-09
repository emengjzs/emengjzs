package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import debug.Debug;

public class DateHelper {
	
	private SimpleDateFormat sdf; 
	
	public DateHelper(String format) {
		sdf = new SimpleDateFormat(format);		
	}
	
	public Calendar getCalendar(String dateStr) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {e.printStackTrace(); return c;}
		return c;
	}
	
	public String toString(Calendar c) {
		return sdf.format(c);
	}
	
	public static String getDateString(Calendar c) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static String getTimeString(Calendar c) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}
	
	public static String getJSDateString(Calendar c) {
		return "new Date("+ c.get(Calendar.YEAR) + "," + (c.get(Calendar.MONTH))+ "," 
				+ c.get(Calendar.DATE) + ")";
	}
	
	public static Calendar tomorrow(Calendar c) {
		Calendar r = (Calendar) c.clone();
		//r.s
		Debug.print(DateHelper.getDateString(c));
		r.add(Calendar.DATE, 1);
		Debug.print(DateHelper.getDateString(r));
		return r;
	}
}
