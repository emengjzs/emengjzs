package model.vo;
import common.UserState;

public class ActiveUser {
	String userID;	
	UserState state;
	
	public ActiveUser(String userID,UserState state){
		this.state=state;
		this.userID=userID;
	}

	
	public void setUserState(UserState state){
		this.state=state;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public UserState getUserState(){
		return state;
	}
	
}
