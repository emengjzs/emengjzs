package fileservice;

import java.io.File;
import java.net.URL;

import debug.Debug;

public class PathHelper {
	  public static String getClassPath() {
	        try {
	        	Class classn = PathHelper.class;
	            String strClassName = classn.getName();
	            String strPackageName = "";
	            if (classn.getPackage() != null) {
	                strPackageName = classn.getPackage().getName();
	            }
	            String strClassFileName = "";
	            if (!"".equals(strPackageName)) {
	                strClassFileName = strClassName.substring(strPackageName.length() + 1,
	                        strClassName.length());
	            } else {
	                strClassFileName = strClassName;
	            }
	            URL url = null;
	            url = classn.getResource(strClassFileName + ".class");
	            String strURL = url.toString();
	            strURL = strURL.substring(strURL.indexOf('/') + 1, strURL
	                    .lastIndexOf('/'));
	            //返回当前类的路径，并且处理路径中的空格，因为在路径中出现的空格如果不处理的话，
	            //在访问时就会从空格处断开，那么也就取不到完整的信息了，这个问题在web开发中尤其要注意
	            return (new File(strURL.replaceAll("%20", " "))).getParentFile()
	            		.getParentFile()
	            		.getParentFile()
	            		.getAbsolutePath() + "\\";
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return "";
	            //throw ex;
	        }
	    }
	public static void main(String[] args) {
		try {
			Debug.print(PathHelper.getClassPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
