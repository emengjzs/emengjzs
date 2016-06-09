package statistic;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import data.BaseDao;
import debug.Debug;

@Stateless
@LocalBean
public class StatisticService extends BaseDao {
	
	//private DateHelper dh = new DateHelper("yyyy-MM-dd");
	
	
	
	public List getLessonAna(MonthDate md ) {
		//MonthDate md = new MonthDate(year, month);
		return this.getResult(this.getLessonSql(md));
	}
	
	public List getHomeworkAna(MonthDate md ) {
		//MonthDate md = new MonthDate(year, month);
		return this.getResult(this.getHomeworkSql(md));
	}
	
	public List getHomeworkSubmitAna(MonthDate md) {
		//MonthDate md = new MonthDate(year, month);
		return this.getResult(this.getHomeworkSubmitSQL(md));
	}
	
	public List getTuitorAna(MonthDate md) {
		return this.getResult(this.getTuitorSQL(md));
	}
	
	public List getHomeworkUnsubmitAna(MonthDate md) {
		//MonthDate md = new MonthDate(year, month);
		Debug.print(this.getHomeworkUnsubmitSQL(md));
		return this.getResult(this.getHomeworkUnsubmitSQL(md));
	}
	
	public List getScoreAna(MonthDate md) {
		return this.getResult(this.getScoreSQL(md));
	}
	
	public List getTeacherAna(MonthDate md) {
		return this.getResult(this.getTeacherSql(md));
	}
	
	private String getLessonSql(MonthDate md) {
		return "SELECT count(*) FROM homeworks.lesson where start_date <= '" 
	               + md.getLastDateString() +  "' and end_date >='" + md.getFirstDateString() + "'";
	}
	
	private String getHomeworkSql(MonthDate md) {
		return "SELECT l.lid, l.name, count(a.aid), avg(a.score) from (select lesson.lid, course.name FROM "
				+ "lesson, course where course.cid=lesson.cid and start_date <= '" + md.getLastDateString() + "' and "			
				+ "end_date >= '" + md.getFirstDateString() + "') as l left join assignment a on a.lid = l.lid and a.publish_date "
				+ md.getMonthSQLString() 
				+ " group by l.lid;";

	}
	
	private String getTeacherSql(MonthDate md) {
		return "SELECT t.tid, t.name,  count(distinct ai.aid), avg(ar.score/ai.score)*100 from teacher t, teacher_assign ta,"
				+ " assignment ai, assign_submit_record ar, lesson l where ai.publish_date " + md.getMonthSQLString() +
				  " and ar.aid=ai.aid and ai.lid=l.lid and l.lid=ta.lid and ta.tid=t.tid group by(t.tid)";

	}
	
	private String getHomeworkSubmitSQL(MonthDate md) {
		return "SELECT DATEDIFF(ai.deadline, ar.submit_date) as days, count(*) "
				+ " from assign_submit_record ar, assignment ai "
				+ " where ai.aid= ar.aid and ar.status != 1 " 
				+ " and ai.deadline " +  md.getMonthSQLString() +" group by days";
	}
	
	private String getHomeworkUnsubmitSQL(MonthDate md) {
		return "SELECT s.sid, count(*) , s.name from student s, assignment ai ,assign_submit_record ar, lesson_record lr "
		+ " where ai.deadline " + md.getMonthSQLString()
		+ " and ai.status!=0 and lr.sid=s.sid and lr.lid=ai.lid and ar.aid=ai.aid and ar.sid = s.sid and ar.status=1 group by(s.sid); ";
		
	}
	
	private String getScoreSQL(MonthDate md) {
		return "select (ar.score / ai.score)*100 as score, count(*) from assign_submit_record ar,  assignment ai " 
				 + " where ai.status=3 and ai.aid=ar.aid "
				 + " and ar.status!=1 and ai.check_deadline "
				 + md.getMonthSQLString() + " group by(score);";
	}
	
	private String getTuitorSQL(MonthDate md) {
		return "select s.name, count(*), avg(ar.score / ai.score)*100 as score" +
				" from student s, tuitor_assign ta, assignment ai, assign_submit_record ar where ai.check_deadline "+
				md.getMonthSQLString() + " and ai.lid=ta.lesson_lid "+
				                                      "  and s.sid=ta.student_sid"+
				                                      "  and ar.aid=ai.aid"+
				                                       " and ar.tutid=s.sid group by(s.name);";

	}
	
	
	private List getResult(String sql) {
		Query query =  em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	
	
	
}
