package filechecker;

public class StrTransferTool {
	
	
	public static String getHexString(byte bytes[]) {
		if(bytes == null) 
			return "";
		StringBuilder sb = new StringBuilder();
		int len = bytes.length;
		for(int i = 0; i < len; ++ i) {
			sb.append(Integer.toHexString(bytes[i] & 0xFF));
		}
		return sb.toString().toUpperCase();
	}
	
	public static String getSuffix(String fileName) {
		int i = fileName.lastIndexOf('.');
		return i == -1 ? "" : fileName.substring(i + 1);
	}
}
