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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import data.AssignmentsummaryDao;
import data.BaseDao;
import data.LessonsummaryDao;
import data.StudentScoreSummaryDao;
import entity.Assignmentsummary;
import entity.Lessonsummary;

/**
 * Servlet implementation class SummaryServlet
 */
@WebServlet(description = "dfdf", 
urlPatterns = { "/summary/lessons" ,"/summary/lesson" ,
		"/summary/assignment", "/summary/assignments",
		"/score/lesson", "score/student", "/lessons/summary.html",
		"/institutes/summary.html", "/institutes/summary"})
public class SummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @EJB
	private AssignmentsummaryDao baseDao;
    
    @EJB
	private LessonsummaryDao lessonDao;
    
    @EJB
    private StudentScoreSummaryDao scoreDao;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SummaryServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String path = ServletHelper.getPath(request);
		 switch(path) {
		    case "/score/lesson":{
		    	int lid = IntHelper.toInt(request.getParameter("lid"));
				List result = scoreDao.getByLesson(lid);
				ServletHelper.writeToResponse(response, JSONArray.fromObject(result));    	
		    	break;
		    }
		    case "/score/student":{
		    	int sid = IntHelper.toInt(request.getParameter("sid"));
				List result = scoreDao.getByStudent(sid);
				ServletHelper.writeToResponse(response, JSONArray.fromObject(result));    	
		    	break;
		    }
		 
			case "/summary/lesson": {
				int lid = IntHelper.toInt(request.getParameter("lid"));
				Lessonsummary result = (Lessonsummary) baseDao.getById(Lessonsummary.class, lid);
				ServletHelper.writeToResponse(response, JSONObject.fromObject(result));
				break;
			}
			case "/summary/lessons": {
				List result = null;
				String ins = request.getParameter("lid");
				if(ins != null && ! ins.equals("")) {
					result = lessonDao.getByInstitute(ins);
				}
				else {
				   
				   result = baseDao.findAll(Lessonsummary.class);
				}
				ServletHelper.writeToResponse(response, JSONArray.fromObject(result));
				break;
			}
			case "/institutes/summary" : {
				ServletHelper.writeToResponse(response, JSONArray.fromObject(lessonDao.getGroup()));

				break;
			}
			case "/summary/assignment": {
				int aid = IntHelper.toInt(request.getParameter("aid"));
				ServletHelper.writeToResponse(response, JSONObject.fromObject(baseDao.getById(Assignmentsummary.class, aid)));
				break;
			}
			case "/summary/assignments": {
				int lid = IntHelper.toInt(request.getParameter("lid"), -1);
				if(lid == -1)
					ServletHelper.writeToResponse(response, JSONArray.fromObject(baseDao.findAll(Assignmentsummary.class)));
				else {
					ServletHelper.writeToResponse(response, JSONArray.fromObject(baseDao.getByLesson(lid)));
				}
				break;
			}
			case "/lessons/summary.html": {
				//request.setAttribute("ins", );
				ServletHelper.forward(this, "lessons.summary.jsp", request, response);
			}
			case "/institutes/summary.html": {
				ServletHelper.forward(this, "institutes.summary.jsp", request, response);
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
