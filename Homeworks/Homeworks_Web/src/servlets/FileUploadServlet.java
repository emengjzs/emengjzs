package servlets;

import helper.IntHelper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import entity.LessonPO;
import entity.AssignmentScore;
import entity.User;
import fileservice.PathHelper;
import fileservice.ScoreExcelCreater;
import fileservice.ScoreExcelReader;
import message.Message;
import listeners.FileUploadProgressListener;
import logic.AssignmentService;
import logic.FileStoreController;
import logic.LessonService;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(description = "upload", urlPatterns = { "/homework", "/homework/sample",
		                                            "/homework/scores"})
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String tempPath = "D:/Homework/temp/";
	@EJB
	AssignmentService assignmentService;
	
	@EJB
	LessonService lessonService;
	
	@EJB
	ScoreExcelReader reader;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        FileStoreController.setbasePath(PathHelper.getClassPath());
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int lid = IntHelper.toInt(request.getParameter("lid"));
		LessonPO lesson = lessonService.getLessonPOById(lid);
		if(lesson == null) {
			return;
		}
		List<AssignmentScore> list = this.assignmentService.getAllScores(lid);
		ScoreExcelCreater sec = new ScoreExcelCreater();
		HSSFWorkbook book = sec.create(list, assignmentService.getAssignments(lid).size(), lesson);
		response.setHeader("Content-Type", "application/force-download");  
		response.setHeader("Content-Disposition", "attachment; filename=\""+  
				new String(lesson.getName().getBytes("gb2312"), "ISO8859-1" )+ ".xls\"");   
		ServletOutputStream os = response.getOutputStream();
		try {  
           book.write(os);  
        } catch (Exception e) {  
            System.out.println("ÎÄ¼þÏÂÔØÊ§°Ü");  
        }
		 finally{  
            os.flush();     
            os.close();
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileUploadProgressListener listener = new FileUploadProgressListener(request);
		ServletFileUpload handler = this.initUploadHandler(listener);
		List<FileItem> list;
		
		FileItem file = null;
		int aid = -1;
		int lid = -1;
		try {
			list = handler.parseRequest(request);
			Iterator<FileItem> itr = list.iterator();
			while(itr.hasNext()) {
				FileItem item = itr.next();
				if(item.isFormField()) {
					if(item.getFieldName().equals("aid"))
						aid = IntHelper.toInt(item.getString());
					if(item.getFieldName().equals("lid"))
						lid = IntHelper.toInt(item.getString());
					continue;
				}
				else {
					file = item;
				}
			}
			String path = ServletHelper.getPath(request);
			Message m = null;
			if(path.equals("/homework")) {
				int sid = (int) ((User)request.getSession().getAttribute("user")).getID();
				m = this.assignmentService.submitHomework(file, sid, aid);
			}
			else if(path.equals("/homework/sample")) {
				m = this.assignmentService.submitSample(file, aid);
			}
			else if(path.equals("/homework/scores")) {
				m = reader.read(file, lid);
			}
			//JSONObject a = JSONObject.fromObject(mr);
			//a.element("result", mr.getInfo());
			ServletHelper.writeMessage(response, m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	private ServletFileUpload initUploadHandler(ProgressListener listener) {
		DiskFileItemFactory factory=new DiskFileItemFactory();         
	    //factory.setSizeThreshold(10240);         
	    factory.setRepository(new File(tempPath));       
	    ServletFileUpload upload=new ServletFileUpload(factory);              
	    upload.setHeaderEncoding("utf-8");  	     
	    upload.setProgressListener(listener);     
	    upload.setFileSizeMax(50 * 1024 * 1024);
	    upload.setSizeMax(50 * 1024 * 1024);
	   
	    return upload;
	}
}
