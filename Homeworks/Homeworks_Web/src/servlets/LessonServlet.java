package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import message.Message;
import net.sf.json.JSONArray;
import logic.LessonService;

/**
 * Servlet implementation class LessonServlet
 */
@WebServlet(description = "lesson", urlPatterns = { "/lesson/student", "/lesson/teacher", "/lesson/tuitor", 
		"/lesson/except/student", 
		"/lesson/except/teacher",
		"/lesson/except/tuitor"
		})
public class LessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	LessonService lessonService;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		int lid = Integer.parseInt(request.getParameter("lid"));
		List results = new ArrayList(0);
		if(path.equals("/lesson/except/teacher")) {		
			results = lessonService.getTeacherExceptLesson(lid);		
		}		
		else if(path.equals("/lesson/except/student")) {			
			results = lessonService.getStudentExceptLesson(lid);			
		}
		else if(path.equals("/lesson/except/tuitor")) {			
			results = lessonService.getTuitorExceptLesson(lid);
		}
		else if(path.equals("/lesson/teacher")) {			
			results = lessonService.getLessonPOById(lid).getTeacherAssigns();
		}
		else if(path.equals("/lesson/tuitor")) {			
			results = lessonService.getLessonPOById(lid).getTuitorAssigns();
		}
		else if(path.equals("/lesson/student")) {			
			results = lessonService.getStudent(lid);
		}
		ServletHelper.writeToResponse(response, JSONArray.fromObject(results));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		int lid = Integer.parseInt(request.getParameter("lid"));
		String strids[] = request.getParameterMap().get("ids[]");
		List<Integer> ids = this.StrtoInt(strids);
		
		Message results = Message.AddMessage.ADD_FAILED;;
		if(path.equals("/lesson/teacher")) {			
			results = lessonService.addTeachers(ids, lid);
		}
		else if(path.equals("/lesson/tuitor")) {			
			results = lessonService.addTuitors(ids, lid);
		}
		else if(path.equals("/lesson/student")) {			
			results = lessonService.addStudents(ids, lid);
		}
		ServletHelper.writeMessage(response, results);
	}
	
	private ArrayList<Integer> StrtoInt(String[] str) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(String s : str) {
			addStrToInt(ids, s);
		}
		return ids;
	}
	
	private void addStrToInt(ArrayList<Integer> ids, String s) {
		try {
			Integer i = Integer.parseInt(s);
			ids.add(i);
		} catch(Exception e) {}
	}
}
