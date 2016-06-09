package filechecker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import debug.Debug;


public class FileTypeData {
	public final static Map<String, FileType> FILE_TYPE_MAP = new TreeMap<String, FileType>(); 
	private String TYPEXML_PATH = "/file_types.xml";
	
	public FileType getFileType(String type) {
		FileType ft = null;
		if((ft = FILE_TYPE_MAP.get(type)) != null) {
			return ft;
		}
		return getFileTypeFromData(type);
	}
	
	private FileType getFileTypeFromData(String type) {
		try {
			Debug.print(this.getClassPath());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			
		}
		SAXBuilder builder = new SAXBuilder(); 
		Document data = null;
		try {
			data = builder.build(new File(this.getClassPath() + TYPEXML_PATH));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = data.getRootElement();
		FileType ft = getFileTypeFromData(root, type);
		
		if(ft != null) {
			FILE_TYPE_MAP.put(type, ft);
		}
		
		return ft;
	}
	
	private FileType getFileTypeFromData(Element root, String type) {
		Iterator<Element> itr = root.getChildren().iterator();
		while(itr.hasNext()) {
			Element e = itr.next();
			//System.out.println(e.getAttributeValue("type"));
			if(type.equals(e.getAttributeValue("type"))) {
				FileType ft = new FileType();
				ft.setType(type);
				ft.setCharacterCode(e.getChildText("character_code"));
				//System.out.println(ft.getCharacterCode());
				return ft;
			}
		}
		return null;
	}
	
	
	  public String getClassPath() throws Exception {
	        try {
	            String strClassName = getClass().getName();
	            String strPackageName = "";
	            if (getClass().getPackage() != null) {
	                strPackageName = getClass().getPackage().getName();
	            }
	            String strClassFileName = "";
	            if (!"".equals(strPackageName)) {
	                strClassFileName = strClassName.substring(strPackageName.length() + 1,
	                        strClassName.length());
	            } else {
	                strClassFileName = strClassName;
	            }
	            URL url = null;
	            url = getClass().getResource(strClassFileName + ".class");
	            String strURL = url.toString();
	            strURL = strURL.substring(strURL.indexOf('/') + 1, strURL
	                    .lastIndexOf('/'));
	            //返回当前类的路径，并且处理路径中的空格，因为在路径中出现的空格如果不处理的话，
	            //在访问时就会从空格处断开，那么也就取不到完整的信息了，这个问题在web开发中尤其要注意
	            return strURL.replaceAll("%20", " ");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
	    }
	
}
