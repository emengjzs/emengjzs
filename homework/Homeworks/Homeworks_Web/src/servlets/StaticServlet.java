package servlets;

import helper.DateHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import statistic.MonthDate;
import statistic.StatisticService;
import debug.Debug;

/**
 * Servlet implementation class Test
 */
@WebServlet(description = "test", urlPatterns = { "/statis" })
public class StaticServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateHelper dh;  
	
	@EJB
	StatisticService s;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticServlet() {
        super();
        dh = new DateHelper("yyyy/MM");// TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 Student student = new Student();
		 student.setID("121250066");
		 student.setPassword("123456");
		 //System.out.println(baseDao.setEntityClass(student.getClass().getName()));
		 //Object result = baseDao.getById(student.getClass().getName(), student.getSid());
		 //Object result = studentDao.getById(student.getSid());
		 Object result = accountService.login(student);
		 
		 Student anoStudent= new Student();
		 anoStudent.setID("121500");
		 anoStudent.setPassword("123456");
		 anoStudent.setInstitute("软件学院");
		 anoStudent.setName("student");
		 anoStudent.setYear(2015);;
		 Debug.split();
		 Debug.print(accountService.registerStudent(anoStudent));
		 Debug.split();
		 Student s =  studentDao.getStudentByYear(2012).get(0);
		 Debug.print("isTuitor: " + s.getIsTuitor());
		 s.setIsTuitor(! s.getIsTuitor());
		 Debug.print("after: " + s.getIsTuitor());
		 studentDao.update(s);
		 s =  studentDao.getStudentByYear(2012).get(0);
		 Debug.print("isTuitor: " + s.getIsTuitor());
		 Debug.split();
		 Debug.print(studentDao.getStudentByInstitute("软件学院").get(0).getPassword());
;		 System.out.println(Debug.outObjPropertyString(result));
		
		Course c = new Course();
		c.setInstitute("软件学院");
		c.setName("AV鉴赏");

		Debug.print(this.courseService.addCourse(c).toString());
		Debug.outObjPropertyString(c);
		
		Debug.outObjPropertyString(lessonPlanService.createNewSemester());
		*/
		
		//CheckMessage r = this.filec.check(new File("D:/Documents/财务处.docx"));
		/*
		Term t = new Term();
		t.setEndDate(Calendar.getInstance());
		t.setStartDate(Calendar.getInstance());
	  	t.setId(1);
		Semester s = Semester.getInstance(t);
		//s.getTerm().getId();
		*/
		//JSONObject a = new JSONObject();
		//a.element("reuslt", r.toString());
		//ServletHelper.writeToResponse(respon se, a);
		//ServletHelper.forward(this, "add_course.html", request, response);
		String d = request.getParameter("d");
		Calendar c = d == null ? Calendar.getInstance() : this.dh.getCalendar(d);
		
		Map<String,List> datas = new HashMap<String, List>();
		MonthDate md = new MonthDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
		request.setAttribute("jsmonth", "new Date(" + c.get(Calendar.YEAR) + ","+ c.get(Calendar.MONTH) + ")");
		request.setAttribute("month", c.get(Calendar.YEAR) + "年" +(c.get(Calendar.MONTH) + 1) + "月份");
		request.setAttribute("lessons", s.getLessonAna(md).get(0));
		datas.put("submitArray", s.getHomeworkSubmitAna(md));
		datas.put("unsubmitArray", s.getHomeworkUnsubmitAna(md));
		datas.put("homeworkArray", s.getHomeworkAna(md));
		datas.put("scoreArray", s.getScoreAna(md));
		datas.put("tuitorArray", s.getTuitorAna(md));
		datas.put("teacherArray", s.getTeacherAna(md));
		setToRequest(datas, request);
		ServletHelper.forward(this, "static.jsp", request, response);
	}
	
	
	private void setToRequest(Map<String,List> datas, HttpServletRequest request) {
		Iterator<String> itr = datas.keySet().iterator();
		while(itr.hasNext()) {
			String name = itr.next();
			List data= datas.get(name);
			JSONArray submitArray = JSONArray.fromObject(data);
			Debug.print(submitArray.toString());
			request.setAttribute(name, submitArray.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
