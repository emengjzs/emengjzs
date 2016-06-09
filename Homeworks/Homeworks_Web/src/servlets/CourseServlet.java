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

import data.CourseDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import entity.Course;
import logic.CourseService;
import message.Message;
import message.MessageObj;

/**
 * Servlet implementation class CourseServlet
 */
@WebServlet(description = "course", urlPatterns = { "/course", "/course.html","/course/add", "/course/except" })
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	CourseService courseService;
	
	@EJB
	CourseDao courseDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		if(path.equals("/course.html")) {
			ServletHelper.forward(this, "coursepage.html", request, response);
		}
		else if(path.equals("/course")) {
			List reuslts = courseDao.findAll();
			JSONArray arrays = JSONArray.fromObject(reuslts);
			ServletHelper.writeToResponse(response, arrays);
		}
		else if(path.equals("/course/except")) {
			int tid = Integer.parseInt(request.getParameter("tid"));
			List reuslts = courseDao.getCourseExceptTerm(tid);
			JSONArray arrays = JSONArray.fromObject(reuslts);
			ServletHelper.writeToResponse(response, arrays);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Course c = new Course();
		Message m = null;
		String path = ServletHelper.getPath(request);
		if(path.equals("/course")) {
			if(! request.getParameter("cid").equals("")) {
				c.setCid(IntHelper.toInt(request.getParameter("cid"), -1));
				if(c.getCid() == -1) {
					m = Message.AddMessage.ADD_FORBID;
					m.setInfo("ID必须是数字");
					ServletHelper.writeMessage(response, m);
					return;
				}			
			}	
			c.setInstitute(request.getParameter("institute"));
			c.setName(request.getParameter("name"));
			m = courseService.addCourse(c);
			JSONObject result = JSONObject.fromObject(new MessageObj(m));
			result.element("course", c);
			ServletHelper.writeToResponse(response, result);
			return;
		}
		else if(path.equals("/course/add")) {
			int cid = IntHelper.toInt(request.getParameter("cid"), -1);
			if(cid == -1) {
				m = Message.AddMessage.ADD_FORBID;
				m.setInfo("ID必须是数字");
				ServletHelper.writeMessage(response, m);
				return;
			}			
			c =(Course) this.courseDao.getById(cid);
			c.setInstitute(request.getParameter("institute"));
			c.setName(request.getParameter("name"));
			m = courseService.updateCourse(c);
			
		}
		ServletHelper.writeMessage(response, m);
	}

}
