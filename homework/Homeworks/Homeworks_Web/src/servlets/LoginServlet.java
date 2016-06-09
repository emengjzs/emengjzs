package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import net.sf.json.JSONObject;
import logic.AccountIdentifyResult;
import service.AccountServiceRemote;
import debug.Debug;
import entity.Admin;
import entity.Manager;
import entity.Student;
import entity.Teacher;
import entity.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "login", urlPatterns = { "/login", "/logout" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int NumOfUserType = 3;
    
    @EJB
    AccountServiceRemote accountService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletHelper.getPath(request).equals("/logout")) {
			logout(request, response);
		}		
		ServletHelper.redirect("/index.html", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletHelper.getPath(request).equals("/login")) {
			login(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			session = null;
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		User user = getUser(request.getParameter("type"));
		
		user.setID(request.getParameter("id"));
		user.setPassword(request.getParameter("password"));
		
		AccountIdentifyResult result = accountService.login(user);
		Debug.outObjPropertyString(result);
		
		if(result.isPass()) {		
			setSession(request, result.getData());			
		}
		
		JSONObject json = new JSONObject();
		json.element("result", result.getMsg());
		ServletHelper.writeToResponse(response, json);
	}
	
	private void setSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			session = request.getSession(true);
		}				
		user.setPassword(null);
		session.setAttribute("user",user);
	}
	
	
	private User getUser(String type) {
		Debug.print("type : " + type);
		User user = null;
		if(type.equals("0")) {
			user = new Student();
		}
		else if(type.equals("2")){
			user = new Admin();
		}
		else if(type.equals("1") ){
			user = new Teacher();
		}
		else user = new Manager();
		return user;
	}

}
