package servlets;

import helper.IntHelper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import debug.Debug;

/**
 * Servlet implementation class ScorePageServlet
 */
@WebServlet(description = "ddddddd", 
urlPatterns = { "/score/lesson.html" ,
		        "/score/student.html",
		        "/summary/assignment.html"})
public class ScorePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScorePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		 switch(path) {
		    case "/score/lesson.html":{
		    	int lid = IntHelper.toInt(request.getParameter("lid"));
				request.setAttribute("lid", lid);
				request.setAttribute("url", "score/lesson?lid=" + lid);
				ServletHelper.forward(this, "term.summary.jsp", request, response);
		    	break;
		    }
		    case "/score/student.html":{
		    	int lid = IntHelper.toInt(request.getParameter("sid"));
		    	Debug.print("sid = " + lid);
		    	request.setAttribute("url", "score/student?sid=" + lid);
				ServletHelper.forward(this, "term.summary.jsp", request, response);
		    	break;
		    }
		    case "/summary/assignment.html":{
		    	int aid = IntHelper.toInt(request.getParameter("aid"));
		    	Debug.print("aid = " + aid);
		    	request.setAttribute("aid", aid);
				ServletHelper.forward(this, "assignment.summary.jsp", request, response);
		    	break;
		    }
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
