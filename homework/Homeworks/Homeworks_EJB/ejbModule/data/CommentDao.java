package data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import message.Message;
import entity.Comment;
@Stateless
@LocalBean
public class CommentDao extends BaseDao {
	public CommentDao() {
        super();
        this.mainEntityClass = Comment.class;
        // TODO Auto-generated constructor stub
    }
	
	public List<Comment> getCommentByAssignment(int aid) {
		String sql = 
				"select c from Comment c where c.aid=:aid order by c.timestamp asc";
		Query q = this.em.createQuery(sql);
		q.setParameter("aid", aid);
		return q.getResultList();
	}
	
	public Message addComment(Comment c) {
		return this.add(c);
	}
	
}
