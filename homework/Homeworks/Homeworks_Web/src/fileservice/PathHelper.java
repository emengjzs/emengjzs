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
	            //���ص�ǰ���·�������Ҵ���·���еĿո���Ϊ��·���г��ֵĿո����������Ļ���
	            //�ڷ���ʱ�ͻ�ӿո񴦶Ͽ�����ôҲ��ȡ������������Ϣ�ˣ����������web����������Ҫע��
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
