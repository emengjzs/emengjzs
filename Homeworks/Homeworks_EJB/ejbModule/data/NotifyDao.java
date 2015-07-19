package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import entity.Assignment;
@Stateless
@LocalBean
public class NotifyDao extends BaseDao {
	public List<Assignment> getUnSubmitAssignment(int sid) {
		String sql = 
				"select ai from Assignment ai, AssignSubmitRecord ar " +
                " where ai.aid=ar.aid and now() < ai.deadline and ar.status=1 and ar.sid=:sid ";
		Query q = this.em.createQuery(sql);
		q.setParameter("sid", sid);
		return q.getResultList();
	}
	
	public List getUnReviewAssignment(int sid) {
		String sql = 
				"select ai from Assignment ai, Lesson l, TuitorAssign ta " +
                " where l.lid=ta.lessonLid and ta.lessonLid=ai.lid and ta.studentSid=:sid "
                + " and (ai.status=4 or (now() < ai.checkDeadline and ai.status=1 and exists("
                + "select ar from AssignSubmitRecord ar where ar.aid=ai.aid and ar.view=0)))";
		Query q = this.em.createQuery(sql);
		q.setParameter("sid", sid);
		return q.getResultList();
	}
	
	public List getUncheckAssignment(int tid) {
		String sql = 
				"select ai from Assignment ai, Lesson l, TeacherAssign ta "
				+ "where ai.lid=l.lid and ta.lid=l.lid and ta.tid=:tid and ai.status=2";
		Query q = this.em.createQuery(sql);
		q.setParameter("tid", tid);
		return q.getResultList();		
	}
	
}
