package servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import message.Message;
import data.BaseDao;
import entity.Goal;

/**
 * Servlet implementation class GoalServlet
 */
@WebServlet(description = "ssssssss", urlPatterns = { "/goal" })
public class GoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	BaseDao baseDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("goals", baseDao.findAll(Goal.class));
		ServletHelper.forward(this, "goal.jsp", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		Goal g = new Goal();
		g.setYear(Calendar.getInstance().getTime());
		g.setContent(content);
		Message m = baseDao.add(g);
		ServletHelper.writeMessage(response, m);
	}

}
