package filechecker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import message.Message;


@Stateful
@LocalBean
public class FileTypeChecker {
	private FileTypeData fileTypeData;
	private File file;
	private FileType ft;
	
	private static String checkMethods[] = {
		"checkFileSuffix",
		//"checkFileHeader",
	};
	
	public FileTypeChecker() {
		this.fileTypeData = new FileTypeData();
	}
	

	
	public CheckMessage check(File f) {
		this.file = f;  
		if( !this.file.exists()) {
			CheckMessage checkMsg = CheckMessage.getInstance(Message.Msg.INVALID_FILE);
			return checkMsg;
		}
		int mlen = checkMethods.length;
		CheckMessage checkMsg = CheckMessage.getInstance(Message.Msg.PASS);
		Class ins = this.getClass();
		try {
			for(int i = 0; i < mlen; i ++) {			
				checkMsg = (CheckMessage) ins.getMethod(checkMethods[i]).invoke(this);			
				if(! checkMsg.isPass()) {
					return checkMsg;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return checkMsg;
	}
	
	public CheckMessage checkFileHeader() {
		InputStream fis = null;
		CheckMessage msg;
		
		try {
			fis = new FileInputStream(file);
		
			byte bytes[] = new byte[ft.getCharacterCodeLen()];
			fis.read(bytes);			
			if(! ft.checkCharacterCode(StrTransferTool.getHexString(bytes))) {
				msg = CheckMessage.getInstance(Message.Msg.INVALID_FILE);
				fis.close();
				return msg;
			}
			
			fis.close();
		} catch(FileNotFoundException e) {return CheckMessage.getInstance(Message.Msg.FILE_NOT_EXIST);}
		  catch(IOException e) {return CheckMessage.getInstance(Message.Msg.INVALID_FILE);}
		return CheckMessage.getInstance(Message.Msg.PASS);
	}
	
	
	public String getSuffix(File f) {
		return StrTransferTool.getSuffix(f.getName());
	}
	
	public CheckMessage checkFileSuffix() {
		if((ft = fileTypeData.getFileType(StrTransferTool.getSuffix(file.getName()))) != null) {
			return CheckMessage.getInstance(Message.Msg.PASS);
		}
		return CheckMessage.getInstance(Message.Msg.INVALID_TYPE);
	}
		
	public CheckMessage checkFileSuffix(String s) {
		String suffix = this.getSuffix(file);
		if(s.equals(suffix) && (ft = fileTypeData.getFileType(getSuffix(file))) != null) {
			return CheckMessage.getInstance(Message.Msg.PASS);
		}
		return CheckMessage.getInstance(Message.Msg.INVALID_TYPE);
	}
}
