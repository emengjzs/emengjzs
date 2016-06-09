package logic;

import java.io.File;
import java.util.Calendar;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.Message;

import org.apache.commons.fileupload.FileItem;

import debug.Debug;
import entity.AssignSubmitRecord;
import entity.Assignment;

@Stateless
@LocalBean
public class FileStoreController {
	
	
	
	private static String basePath = "";
	
	public static void setbasePath(String p) {
		basePath = p;
	}
	
	private File initFile(String path) {
		File f = new File(path);
		if(! f.exists()) {
			f.getParentFile().mkdirs();
		}
		return f;
	}
	
	public FileStoreController() {
		
	}
	
	public void deleteFile(AssignSubmitRecord as) {
		if( as != null && as.getFileUrl() != null) {
			this.getFile(as).delete();
		}
	}
	
	
	public Message saveSample(FileItem item, Assignment as) {
		if(as.getFile() != null && as.getFile() != "") {
			this.getSampleFile(as).delete();
		}
		String path = as.getAid() + "/sample/" + item.getName();
		String fullpath = basePath + path;
		File f = initFile(fullpath);
		try{
			item.write(f);
			item.delete();
			as.setFile(path);
		} catch(Exception e) {return Message.AddMessage.ADD_FAILED;}
		return Message.AddMessage.ADD_SUCCESS;
	}
	
	public AssignSubmitRecord saveFile(FileItem item, Assignment as, int sid) {
		AssignSubmitRecord ar = new AssignSubmitRecord();
		ar.setAid(as.getAid());
		ar.setSid(sid);
		String rawName = item.getName();
		String path = ar.getAid() + "/" + this.getFileName(rawName, sid);
		String fullpath = basePath + path;
		Debug.print("save file : " + fullpath);
		File f = initFile(fullpath);
		try {
			//InputStream  input = item.getInputStream();
			//input.
			item.write(f);
			item.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		ar.setFileUrl(path);
		ar.setSubmitDate(Calendar.getInstance());
		return ar;
	}
	
	
	public File getFile(AssignSubmitRecord ar) {
		return new File(basePath + ar.getFileUrl());
	}
	
	public File getSampleFile(Assignment as) {
		return new File(basePath + as.getFile());
	}
	
		
	public String getFileName(String rawName, int sid) {
		int lastIndex=rawName.lastIndexOf(".");  
		return lastIndex == -1 ? String.valueOf(sid) : sid + rawName.substring(lastIndex); 
	}
	
	
	
	private String getHomeworkPath(AssignSubmitRecord ar) {
		return basePath + ar.getAid() + "/";
	}
	

}
