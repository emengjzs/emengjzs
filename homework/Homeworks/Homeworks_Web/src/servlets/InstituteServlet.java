package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import debug.Debug;
import entity.Institute;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class InstituteServlet
 */
@WebServlet(description = "institute", urlPatterns = { "/institute" })
public class InstituteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstituteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray arrays = new JSONArray();
		
		Debug.print("DoSfdf");
		for(String s : Institute.findAll()) {
			JSONObject o = new JSONObject();
			o.accumulate("i", s);
			arrays.add(o);
		}
		
		
		ServletHelper.writeToResponse(response, arrays);
	}

}
