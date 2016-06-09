package filters;

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

import debug.Debug;
import servlets.ServletHelper;
import timer.SemesterPlanTimer;

/**
 * Servlet Filter implementation class RequestContentTypeFilter
 */
@WebFilter(description = "translate to utf-8 code", urlPatterns = { "/*" })
public class RequestContentTypeFilter implements Filter {

    /**
     * Default constructor. 
     */
	//@EJB
	//SemesterPlanTimer spt;
	
    public RequestContentTypeFilter() {
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
		
		
		String path = ServletHelper.getPath((HttpServletRequest) request);
		if(! path.startsWith("/homework")) {
			request.setCharacterEncoding("UTF-8");
		}
		Debug.print(path);
		
		ServletHelper.printPara((HttpServletRequest) request);	
		
		ServletHelper.printSession((HttpServletRequest) request);
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//spt.scheduleTimer(1000 * 60 * 60);
		// TODO Auto-generated method stub
	}

}
