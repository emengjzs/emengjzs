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

import entity.Comment;
import entity.User;
import message.Message;
import net.sf.json.JSONArray;
import logic.CommentService;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet(description = "n", urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      @EJB
      CommentService commentService; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int aid = IntHelper.toInt(request.getParameter("aid"), -1);
		if(aid != -1) {
			List result = commentService.getCommentBtAssignment(aid);
			request.setAttribute("comments", result);
			ServletHelper.forward(this, "assignment.comment.jsp", request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int aid = IntHelper.toInt(request.getParameter("aid"), -1);
		if(aid != -1) {
			Comment c = new Comment();
			c.setAid(aid);
			
			c.setConntent(request.getParameter("content"));
			c.setTo(IntHelper.toInt(request.getParameter("to")));
			User u = (User) request.getSession().getAttribute("user");
			if(u != null) {
			c.setToName(request.getParameter("toName"));
			c.setFromName(u.getName());
			c.setSid(IntHelper.toInt(u.getID().toString()));
			}
			Message m = this.commentService.putComment(c);
			ServletHelper.writeMessage(response, m);
		}
	}

}
