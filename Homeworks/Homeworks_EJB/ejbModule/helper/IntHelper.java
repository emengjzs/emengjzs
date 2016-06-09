package helper;

public class IntHelper {
	private static int invalidInt = -1;
	
	public static int toInt(String str) {
		return toInt(str, invalidInt);
	}
	
	public static int toInt(String str, int i) {
		try {
			int v = Integer.parseInt(str);
			return v;
		} catch(Exception e) {
			return i;
		}		
	}
}
