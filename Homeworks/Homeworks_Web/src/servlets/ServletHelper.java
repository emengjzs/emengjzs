package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import debug.Debug;
import message.Message;
import message.MessageObj;
import net.sf.json.JSONObject;

public class ServletHelper {
	public static void forward(HttpServlet servlet, String address,
			HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd = request.getRequestDispatcher("/" + address);
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void redirect(String path, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}
	
	public static void writeMessage(HttpServletResponse response, Message m) {
		MessageObj msg = new MessageObj(m);
		
		writeToResponse(response, JSONObject.fromObject(msg));
	}
	
	public static void writeToResponse(HttpServletResponse response, Object json) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
			out.print(json);
			Debug.print(json.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printPara(HttpServletRequest request) {
		Enumeration<String> string = request.getParameterNames();
		Debug.split();
		Debug.print("POST");
		while(string.hasMoreElements()) {
			String e = string.nextElement();
			Debug.print(  e + " : " + request.getParameter(e));
		}
		Debug.split();
	}
	
	public static void printSession(HttpServletRequest request) {
		Enumeration<String> string = request.getSession().getAttributeNames();
		Debug.split();
		Debug.print("»Ø»°");
		while(string.hasMoreElements()) {
			String e = string.nextElement();
			Debug.print(  e + " : " + request.getSession().getAttribute(e));
		}
		Debug.split();
	}
	
	public static Object getSession(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}
	
	
	public static String getPath(HttpServletRequest request) {
		return request.getServletPath();
	}
}