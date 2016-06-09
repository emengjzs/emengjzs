package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import debug.Debug;

public class test {

	private static String sql = 
			"select new entity.LessonPO(cid, institute, name, lid, start_date, end_date, no, status, term, term_id) ";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Homeworks_JPA");  
        EntityManager em = factory.createEntityManager();
        String sql = test.sql + " from Lesson l , Course c where l.cid = c.cid and l.term_id=:term_id ";
    	
    	Query q = em.createQuery(sql);
    	q.setParameter("term_id", 1);
    	Debug.print("dddd");
    	q.getResultList();
	}

}
