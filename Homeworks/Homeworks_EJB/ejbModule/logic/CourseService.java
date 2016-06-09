package logic;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.Message;
import data.CourseDao;
import entity.Course;
import service.CourseServiceRemote;

/**
 * Session Bean implementation class CourseService
 */
@Stateless
@LocalBean
public class CourseService implements CourseServiceRemote {
	
	@EJB
	CourseDao baseDao;
    /**
     * Default constructor. 
     */
    public CourseService() {
        // TODO Auto-generated constructor stub
    }
    
    
    public Message addCourse(Course course) {
    	if(hasSetId(course)) {
    		if(this.baseDao.getById(Course.class, course.getCid()) != null) {
    			return Message.AddMessage.ID_DUMPLICATE;
    		}
    	} 
    	else {
    		course.setCid(this.baseDao.getNewCid());
    	}
    	//Debug.print(course.getName() == null );
    	   
    	if(course.getName().equals("") || course.getInstitute().equals("") ) {
    		Message m = Message.AddMessage.ADD_FAILED;
    		m.setInfo("请完整填写信息");
    		return m;
    	}
    	try {  	
    		return this.baseDao.add(course);
    		//Debug.outObjPropertyString(course);
    		//return r ? Message.AddMessage.ADD_SUCCESS : Message.AddMessage.ADD_FAILED;
    	}
    	catch (Exception e) {return Message.AddMessage.ADD_FAILED; }
    }
    
    
    private boolean hasSetId(Course course) {
    	return course.getCid() != null;
    }
    
    public  Message updateCourse(Course course) {
    	return this.baseDao.update(course);
    }
    
}
