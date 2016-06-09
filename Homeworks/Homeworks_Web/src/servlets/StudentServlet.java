package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.LessonService;
import net.sf.json.JSONArray;
import data.StudentDao;
import entity.Student;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet(description = "student", urlPatterns = { "/student", "/student.html" })
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	@EJB 
	StudentDao studentDao;
	
	@EJB
	LessonService lessonService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		
		if(path.equals("/student")) {		
			List reuslts = studentDao.findAll();
			JSONArray arrays = JSONArray.fromObject(reuslts);
			ServletHelper.writeToResponse(response, arrays);
		}
		else if(path.equals("/student.html")) {
			ServletHelper.forward(this, "student.jsp", request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Student s = (Student) studentDao.getStudentById(Integer.parseInt(request.getParameter("sid")));
		//Calendar.getInstance().
		
		s.setYear(Integer.parseInt(request.getParameter("year")));
		s.setName((String) request.getParameter("name"));
		s.setPassword((String) request.getParameter("password"));
		s.setInstitute((String) request.getParameter("institute"));
			
		
		
		s.setTuitor(Boolean.valueOf((request.getParameter("tuitor"))));
		
		ServletHelper.writeMessage(response, studentDao.update(s));
	}

}
