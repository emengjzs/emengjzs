package logic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.Message;
import data.CommentDao;
import entity.Comment;

/**
 * Session Bean implementation class CommentService
 */
@Stateless
@LocalBean
public class CommentService {
	
	@EJB
	CommentDao dao;
    /**
     * Default constructor. 
     */
    public CommentService() {
        // TODO Auto-generated constructor stub
    }
    
    public List<Comment> getCommentBtAssignment(int aid) {
    	return dao.getCommentByAssignment(aid);
    }
    
    public Message putComment(Comment c) {
    	c.setTimestamp(Calendar.getInstance());
    	return dao.addComment(c);
    }
}
