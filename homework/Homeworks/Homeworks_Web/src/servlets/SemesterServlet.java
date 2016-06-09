package servlets;

import helper.DateHelper;
import helper.IntHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.LessonDao;
import entity.Role;
import entity.Term;
import entity.User;
import message.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import logic.LessonPlanService;
import logic.SemesterService;
import logic.Semester;

/**
 * Servlet implementation class SemesterServlet
 */
@WebServlet(description = "sss", urlPatterns = {"/plan.html", "/semester", "/semester/lessons.html", "semester/lesson", "/semester/lesson/delete" })
public class SemesterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	@EJB
	LessonPlanService planService;
	
	@EJB
	SemesterService lessonService;
	
	@EJB
	LessonDao lessonDao;
	
	Semester semster;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SemesterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int termId = this.getTermID(request);	
		Role role = this.getRole(request);
		String path= ServletHelper.getPath(request);
		int userId = role == Role.ADMIN ? -1: this.getUserId(request);

		
		if(path.equals("/semester/lessons.html")) {
			List reuslts = null;
		

			if(role == Role.STUDENT) {
				reuslts = lessonService.getLessonBySemesterByStudent(userId, termId);
			}
			else if(role == Role.TUITOR) {
				reuslts = lessonService.getLessonBySemesterByTuitor(userId, termId);
				
			}
			else if(role == Role.TEACHER) {
				reuslts = lessonService.getLessonBySemesterByTeacher(userId, termId);
				
			}
			else {
				reuslts = lessonService.getLessonBySemester(termId);
			}
			
			request.setAttribute("lessons", reuslts);
			Semester semester = lessonService.getSemester(termId);
			if(semester == null) {
				return;
			}
			
			Term lastTerm = lessonService.getLastTerm(termId);
			int lastTermID = lastTerm == null ? -1 : lastTerm.getId();
			Term nextTerm = lessonService.getNextTerm(termId);
			int nextTermID = nextTerm == null ? -1 : nextTerm.getId();
			request.setAttribute("lastSemester", String.valueOf(lastTermID));
			request.setAttribute("nextSemester", String.valueOf(nextTermID));
			request.setAttribute("role", role.getStr());
			request.setAttribute("termString", semester.getString());
			request.setAttribute("term", semester.getTerm());
			request.setAttribute("semester", semester);
			ServletHelper.forward(this, "semester.jsp", request, response);
		}
		else if(path.equals("/semester/lesson")) {
			List reuslts = lessonService.getLessonBySemester(termId);
			JSONArray arrays = JSONArray.fromObject(reuslts);
			ServletHelper.writeToResponse(response, arrays);
		}
		else {
			Semester s = this.planService.getNewSemester();
			request.setAttribute("s", s);
			ServletHelper.forward(this, "plan.jsp", request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path= ServletHelper.getPath(request);
		if(path.equals("/semester/lesson")) {
			int termId = Integer.parseInt(request.getParameter("tid"));
			int cid = Integer.parseInt(request.getParameter("cid"));
			Message m = planService.addLesson(termId, cid);
			JSONObject data = new JSONObject();
			data.element("result", m.isSuccess());
			data.element("info", m);
			ServletHelper.writeToResponse(response, data);
			
		} else if(path.equals("/semester/lesson/delete")) {
			int termId = Integer.parseInt(request.getParameter("tid"));
			int cid = Integer.parseInt(request.getParameter("cid"));
			lessonDao.delete(termId, cid);
			JSONObject data = new JSONObject();
			data.element("result", true);
			ServletHelper.writeToResponse(response, data);
		}
		
		else {
			DateHelper dh = new DateHelper("yyyy/MM/dd");
			Calendar startDate = dh.getCalendar(request.getParameter("start"));
			Calendar endDate = dh.getCalendar(request.getParameter("end"));
			Term t = new Term();
			t.setEndDate(endDate);
			t.setStartDate(startDate);
			if(startDate.after(endDate)) {
				Message m = Message.AddMessage.ADD_FAILED;
				m.setInfo("请检查日期");
				ServletHelper.writeMessage(response, m);
				return;
			}
			Semester s = planService.createNewSemester(t);
			JSONObject data = new JSONObject();
			data.element("term", s.getTerm());
			data.element("string", s.getString());
			data.element("success", true);
			data.element("info", "新建成功,请选择课程，所有更改即时保存.");
			ServletHelper.writeToResponse(response, data);
		}
	}
	
	
	public int getTermID(HttpServletRequest request) {
			int termId = IntHelper.toInt((request.getParameter("tid")), -1);
			if(termId == -1) {
				Semester s = this.lessonService.getNewestSemester();
				return s == null ? -1 : s.getTerm().getId();
			}
			return termId;
	}
	
	public Role getRole(HttpServletRequest request) {	
		Role role = Role.getInstance(request.getParameter("role"));
		return role ;
	}
	
	public int getUserId(HttpServletRequest request) {
		User u = (User) ServletHelper.getSession(request, "user");
		return u == null ? -1 : (u instanceof entity.Admin ? -1 :(int) u.getID());
	}
	
}
