package listeners;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.ProgressListener;

public class FileUploadProgressListener implements ProgressListener {
	
	
	private FileUploadStatus status;
    
    
    public FileUploadProgressListener(HttpServletRequest request){  
        status = new FileUploadStatus();
        request.getSession(true).setAttribute(FileUploadStatus.FILE_UPLOAD_PROGRESS_FIELD,this.status);  

    }  
    
    public FileUploadStatus getStatus() {
    	return this.status;
    }
    
    @Override  
    public void update(long bytesRead, long bytesTotal, int items) {  
        double percent= (double)bytesRead*100/(double)bytesTotal;  
        this.status.setPercentage(percent);  
        //session.setAttribute(FileUploadStatus.FILE_UPLOAD_PROGRESS_FIELD,this.status);  
    }    

}
