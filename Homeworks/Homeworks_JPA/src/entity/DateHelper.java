package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {
	
	private SimpleDateFormat sdf; 
	
	public DateHelper(String format) {
		sdf = new SimpleDateFormat(format);		
	}
	
	public Calendar getCalendar(String dateStr) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {return c;}
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
		return c == null ? "нч" : sdf.format(c.getTime());
	}
}
