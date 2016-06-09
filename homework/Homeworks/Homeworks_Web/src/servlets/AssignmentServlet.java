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

import net.sf.json.JSONArray;
import entity.Assignment;
import entity.AssignmentStudentPO;
import entity.User;
import logic.AssignmentService;
import message.Message;

/**
 * Servlet implementation class AssignmentServlet
 */
@WebServlet(description = "as", urlPatterns = { "/assignment",
		"assignment/summary", "assignment/status", 
		"/assignment/records", 
		"/assignment/records/review" ,
		"/assignment/prepare",
		"/assignments/forstudents"})
public class AssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      @EJB
      AssignmentService assignmentService; 
      
      DateHelper dateHelper;
      
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignmentServlet() {
        super();
        dateHelper = new DateHelper("yyyy/MM/dd HH:mm:ss");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		List result = null;
		switch(path) {
		case "/assignment/records":
			int aid = IntHelper.toInt(request.getParameter("aid"));
			result = this.assignmentService.getSubmitRecords(aid);
			//JSONArray arrays = JSONArray.fromObject(result);
			//ServletHelper.writeToResponse(response, arrays);
			break;
		case "/assignments/forstudents":
			int lid = IntHelper.toInt(request.getParameter("lid"));
			int sid =  (int) ((User)request.getSession().getAttribute("user")).getID();
			result = assignmentService.getAssignmentsForStudent(sid, lid);
			break;
		}
		if(result != null) {
			JSONArray arrays = JSONArray.fromObject(result);
			ServletHelper.writeToResponse(response, arrays);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		switch(path) {
		case "/assignment": {		
				Calendar reviewDeadline = dateHelper.getCalendar((request.getParameter("reviewDeadline")));
				Calendar deadline = dateHelper.getCalendar((request.getParameter("deadline")));
				String description = request.getParameter("description");
				int difficulty = IntHelper.toInt(request.getParameter("level"), 0);
				String fileType = request.getParameter("fileformat");
				int lid = IntHelper.toInt(request.getParameter("lid"));
				String title = request.getParameter("title");
				int score = IntHelper.toInt(request.getParameter("score"), 100);
				Assignment as = new Assignment();
				as.setCheckDeadline(reviewDeadline);
				as.setDeadline(deadline);
				as.setDescription(description);
				as.setDifficulty(difficulty);
				as.setFileType(fileType);
				as.setLid(lid);
				as.setName(title);
				as.setScore(score);
				Message r = assignmentService.publishAssignment(as);
				//JSONObject json = JSONObject.fromObject(new MessageObj(r));
				ServletHelper.writeMessage(response, r);
				break;
		}
		case "/assignment/records/review": {
			int score = IntHelper.toInt(request.getParameter("score"), 0);
			int arid  = IntHelper.toInt(request.getParameter("srid"), -1);
			String review = request.getParameter("review");
			User reviewer = (User) request.getSession().getAttribute("user");
			Message r = this.assignmentService.review(reviewer, arid, score, review);
			ServletHelper.writeMessage(response, r);
			break;
		}
		case "/assignment/summary": {
			String summary = request.getParameter("summary");
			int aid = IntHelper.toInt(request.getParameter("aid"));
			Message r = this.assignmentService.updateSummary(aid, summary);
			ServletHelper.writeMessage(response, r);
			break;
		}
		case "/assignment/status": {
			boolean pass = Boolean.valueOf(request.getParameter("pass"));
			int aid = IntHelper.toInt(request.getParameter("aid"));
			Message r = this.assignmentService.teacherReview(aid, pass);
			ServletHelper.writeMessage(response, r);
			break;
		}
		case "/assignment/prepare": {
			//boolean pass = Boolean.valueOf(request.getParameter("pass"));
			int aid = IntHelper.toInt(request.getParameter("aid"));
			Message r = this.assignmentService.prepareTeacherReview(aid);
			ServletHelper.writeMessage(response, r);
			break;
		}
		
		
		}
	}

}
