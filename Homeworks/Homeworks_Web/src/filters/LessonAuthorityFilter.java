package filters;

import helper.IntHelper;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import debug.Debug;
import authority.LessonAuthority;
import logic.LessonService;
import entity.LessonPO;
import entity.User;

/**
 * Servlet Filter implementation class LessonAuthorityFilter
 */
@WebFilter(description = "filter", urlPatterns = { "/lesson.html", 
		"/lesson/assignments.html",
		"/lesson/tuitors.html",
		"/lesson/teachers.html",
		"/lesson/students.html",
		"/lesson/assignments/forstudents.html",
		"/lesson/students/score.html"})
public class LessonAuthorityFilter implements Filter {
	@EJB
	LessonService lessonService;
    /**
     * Default constructor. 
     */
    public LessonAuthorityFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		int lid =  IntHelper.toInt(request.getParameter("lid"));
		LessonPO lesson = this.lessonService.getLessonPOById(lid);
		
		if(lesson != null) {
			User user =(User) ((HttpServletRequest)request).getSession().getAttribute("user");
			LessonAuthority auth = this.lessonService.getLessonAuthority(lesson, user);	
			Debug.print(JSONObject.fromObject(auth).toString());
			request.setAttribute("auth", auth);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
		//LessonPO lesson = (LessonPO) request.getAttribute("lesson");
		//if(lesson == null) return;
		////User user =(User) ((HttpServletRequest)request).getSession().getAttribute("user");
		//LessonAuthority auth = this.lessonService.getLessonAuthority(lesson, user);	
		//Debug.print(JSONObject.fromObject(auth).toString());
		//request.setAttribute("auth", auth);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
