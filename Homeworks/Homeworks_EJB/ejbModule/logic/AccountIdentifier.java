package logic;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.VerifyMessage;
import dataservice.BaseDaoService;
import debug.Debug;
import entity.Manager;
import entity.User;

@Stateless
@LocalBean
public class AccountIdentifier {

	@EJB
	BaseDaoService baseDao;
	
	
	public AccountIdentifier() {
		
	}
	
	public boolean isDaoNull() {
		return baseDao == null;
	}
	
	
	public AccountIdentifyResult checkExists(User user) {
		AccountIdentifyResult result = new AccountIdentifyResult();
    	if(user == null) {
    		result.setMsg(VerifyMessage.NOT_EXISTS);
    	}
    	else {
	    	User data = (User) baseDao.getById(user.getClass(), (user.getID()));
	    	result.setData(data);
	    	if(data == null) 
	    		result.setMsg(VerifyMessage.NOT_EXISTS);    
	    	else 
	    		result.setMsg(VerifyMessage.PASSWORD_UNCHECKED);
    	}
    	return result;
    }
    

	
	
	
	
	public AccountIdentifyResult verify(User input) throws Exception{
		Debug.print("User Type: " + input.getClass().getName());
		if(input instanceof Manager) {return verify((Manager)input);}
		AccountIdentifyResult result = checkExists(input);
    	if(result.isExists()) {
    		if(this.verify(result.getData(), input)) {
    			result.setMsg(VerifyMessage.PASS);
    		}
    		else {
    			result.setMsg(VerifyMessage.PASSWORD_ERROR);
    		}
    	}
    	return result;
    }
	
	public AccountIdentifyResult verify(Manager m) throws Exception {
		AccountIdentifyResult result = new AccountIdentifyResult();
		result.setData(m);
		result.setMsg(VerifyMessage.PASS);
		return result;
	}
	
	private boolean verify(User user, User input) {
    	return user.getPassword().equals(input.getPassword());
    }
	
}
