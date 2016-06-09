package servlets;

import helper.IntHelper;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authority.LessonAuthority;
import debug.Debug;
import entity.Assignment;
import entity.AssignmentStudentPO;
import entity.Lesson;
import entity.LessonPO;
import entity.LessonRecord;
import entity.User;
import logic.AssignmentService;
import logic.LessonService;

/**
 * Servlet implementation class LessonPageServlet
 */
@WebServlet(description = "page", urlPatterns = { 
		"/lesson.html",
		"/lesson/students.html",
		"/lesson/teachers.html",
		"/lesson/tuitors.html",
		"/lesson/info.html",
		"/lesson/assignments.html",
		"/lesson/assignments/forstudents.html",
		"/lesson/score.html",
		"/lesson/students/score.html"})
public class LessonPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	@EJB
	LessonService lessonService;
	
	@EJB
	AssignmentService assignmentService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		request.setAttribute("lid", request.getParameter("lid"));
		Debug.print("lesson===" + path);
		switch(path) {
			case "/lesson.html": {	
				int lid =  IntHelper.toInt(request.getParameter("lid"));
				LessonPO lesson =  lessonService.getLessonPOById(lid);
				request.setAttribute("lesson", lesson);
				
				ServletHelper.forward(this, "lessonpage.jsp", request, response);
				break;
			}
			case "/lesson/info.html": {	
				int lid =  IntHelper.toInt(request.getParameter("lid"));
				LessonPO lesson =  lessonService.getLessonPOById(lid);
				request.setAttribute("lesson", lesson);
				ServletHelper.forward(this, "lesson.info.jsp", request, response);
				break;
			}
			case "/lesson/assignments.html": {
				int lid =  Integer.parseInt(request.getParameter("lid"));
				List<Assignment> result = assignmentService.getAssignments(lid);
				request.setAttribute("assignments", result);
				request.setAttribute("recent", assignmentService.getRecentAssignment(lid));
				ServletHelper.forward(this, "assignment.jsp", request, response);
				break;
			}
			case "/lesson/assignments/forstudents.html": {
				int lid =  Integer.parseInt(request.getParameter("lid"));
				int sid =  (int) ((User)request.getSession().getAttribute("user")).getID();
				List<AssignmentStudentPO> result = assignmentService.getAssignmentsForStudent(sid, lid);
				request.setAttribute("assignments", result);
				ServletHelper.forward(this, "assignment.jsp", request, response);
				break;
			}
			case "/lesson/teachers.html": {
				request.setAttribute("luser", "teacher");
				request.setAttribute("canAdd", ((LessonAuthority)request.getAttribute("auth")).getCanAddTeacher());
				ServletHelper.forward(this, "lesson.user.jsp", request, response);
				break;
			}
			case "/lesson/students.html": {
				request.setAttribute("luser", "student");
				request.setAttribute("canAdd", ((LessonAuthority)request.getAttribute("auth")).getCanAddStudentAndTuitors());
				ServletHelper.forward(this, "lesson.user.jsp", request, response);
				break;
			}
			case "/lesson/tuitors.html": {
				request.setAttribute("luser", "tuitor");
				request.setAttribute("canAdd", ((LessonAuthority)request.getAttribute("auth")).getCanAddStudentAndTuitors());
				ServletHelper.forward(this, "lesson.user.jsp", request, response);
				break;
			}
			case "/lesson/score.html" :{
				int lid =  IntHelper.toInt(request.getParameter("lid"));
				request.setAttribute("lid", lid);
				int sid =  (int) ((User)request.getSession().getAttribute("user")).getID();
				Lesson lesson =  lessonService.getLessonById(lid);
				LessonRecord record =lessonService.getLessonRecord(sid, lid);
				request.setAttribute("lesson", lesson);
				request.setAttribute("record", record);
				ServletHelper.forward(this, "lesson.score.jsp", request, response);
				break;
			}
			case "/lesson/students/score.html" :{
				int lid =  IntHelper.toInt(request.getParameter("lid"));
				request.setAttribute("lid", lid);
				LessonPO lesson =  lessonService.getLessonPOById(lid);	
				//Course c = lessonService.getLessonPOById(lid)
				request.setAttribute("lesson", lesson);			
				ServletHelper.forward(this, "lesson.students.score.jsp", request, response);
				break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
