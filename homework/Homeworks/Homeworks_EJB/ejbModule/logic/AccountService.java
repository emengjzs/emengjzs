package logic;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.Message;
import data.TeacherDao;
import dataservice.StudentDaoService;
import service.AccountServiceRemote;
import entity.Student;
import entity.Teacher;
import entity.User;

/**
 * Session Bean implementation class AccountService
 */
@Stateless
@LocalBean
public class AccountService implements AccountServiceRemote {
	
	@EJB
	AccountIdentifier identifier;
	
	@EJB
	StudentDaoService studentDao;
	
	@EJB
	TeacherDao teacherDao;
	
    /**
     * Default constructor. 
     */
    public AccountService() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public AccountIdentifyResult login(User input) {
    	try {
    		AccountIdentifyResult result = identifier.verify(input);
    		return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return null;
    }
    
    @Override
    public String SayHello(User input) {
    	return "Fuck you!";
    }
    
    @Override
    public Message registerStudent(Student input) {
    	if(this.exists(input)) {
    		return Message.AddMessage.ID_DUMPLICATE;
    	}
    	if(input.getAdmissionYear() == null) {
    		input.setYear(Calendar.getInstance().get(Calendar.YEAR));
    	}
    	setPassword(input);
    	return studentDao.addStudent(input);
    }
    
    public boolean exists(User user) {
    	return identifier.checkExists(user).isExists();
    }
    
    public boolean registerStudents(List<Student> students) {
    	
    	boolean result = studentDao.addStudents(students);
    	if(result) {
    		
    	}
    	return true;
    }
  
    public Message registerTeacher(Teacher input) {
    	if(this.exists(input)) {
    		return Message.AddMessage.ID_DUMPLICATE;
    	}
    	setPassword(input);
    	return this.teacherDao.add(input);
    }
    
    public Message changePassword(User user, String password) {
    	if(user == null) return Message.UpdateMessage.UPDATE_FORBID;
    	user.setPassword(password);
    	return this.studentDao.update(user);
    }
    
    private void setPassword(User user) {
    	if(user.getPassword() == null) {
    		user.setPassword(String.valueOf(user.getID()));
    	}
    }
}
