package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataservice.TeacherDaoService;
import entity.Teacher;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet(description = "teacher", urlPatterns = { "/teacher", "/teacher.html" })
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @EJB
    TeacherDaoService teacherDao;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
           super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		
		if(path.equals("/teacher")) {		
			List reuslts = teacherDao.findAll();
			JSONArray arrays = JSONArray.fromObject(reuslts);
			ServletHelper.writeToResponse(response, arrays);
		}
		else if(path.equals("/teacher.html")) {
			ServletHelper.forward(this, "teacher.jsp", request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher s = (Teacher) teacherDao.getById(Integer.parseInt(request.getParameter("tid")));
		//Calendar.getInstance().
		s.setName((String) request.getParameter("name"));
		s.setPassword((String) request.getParameter("password"));
		s.setInstitute((String) request.getParameter("institute"));		
		s.setIsManager(Boolean.valueOf((request.getParameter("manager"))));	
		ServletHelper.writeMessage(response,teacherDao.update(s));
	}

}
