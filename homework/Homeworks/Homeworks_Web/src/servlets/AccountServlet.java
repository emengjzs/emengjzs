package servlets;

import helper.IntHelper;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;
import entity.Teacher;
import entity.User;
import logic.AccountService;
import message.Message;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet(description = "user", urlPatterns = { "/user", "/user/password","/add/student", "/add/teacher" })
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@EJB
	AccountService accountService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ServletHelper.forward(this, "account.jsp", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = ServletHelper.getPath(request);
		Message m = null;
		int invalidTag = -1;
		if(path.equals("/add/student")) {
			int sid = IntHelper.toInt(request.getParameter("sid"), invalidTag);
			if(sid == invalidTag) {
				m = Message.AddMessage.ADD_FORBID;
				m.setInfo("学号必须是数字");
			}
			else {
				Student s = new Student();
				s.setSid(sid);
				m = accountService.registerStudent(s);
			}
		}
		else if(path.equals("/add/teacher")) {
			int sid = IntHelper.toInt(request.getParameter("tid"), invalidTag);
			if(sid == invalidTag) {
				m = Message.AddMessage.ADD_FORBID;
				m.setInfo("工号必须是数字");
			}
			else {
				Teacher s = new Teacher();
				s.setID(sid);
				m = accountService.registerTeacher(s);
			}
		}
		else if(path.equals("/user/password")) {
			String password = request.getParameter("password");
			m = this.accountService.changePassword((User) request.getSession().getAttribute("user"), password);
		}
		ServletHelper.writeMessage(response, m);
		
	}

}
