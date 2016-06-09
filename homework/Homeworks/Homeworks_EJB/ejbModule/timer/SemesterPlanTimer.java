package timer;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import data.TermDao;
import debug.Debug;
import entity.Term;
@Singleton
public class SemesterPlanTimer {
	
	@EJB
	TermDao termdao;
	
	//@Resource 
	//SessionContext sctx; 
	
	private static long gap = 1000* 60 * 60 * 24;
	
  
	@Schedule(dayOfWeek="*")  
	public void Timeout(Timer timer) {
		Debug.print("发生  +++ " + timer.getInfo());
		// TODO Auto-generated method stub
		List<Term> list = termdao.findAll();
		Calendar now = Calendar.getInstance();
		for(Term t: list) {
			if(t.getEndDate().before(now) && t.getStatus() != Term.OVER) {
				t.setStatus(Term.OVER);
				termdao.update(t);
			}
		}
	}

	// public void scheduleTimer(long milliseconds) {
	//       
    //    sctx.getTimerService().createTimer(0, gap,"我的第一个定时器");
    //    System.out.println("----------begin--------------");
   //  } 

}
