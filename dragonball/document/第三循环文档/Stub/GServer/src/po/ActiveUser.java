package po;

import java.io.PrintWriter;

import common.UserState;

public class ActiveUser {
	String userID;
	PrintWriter writer;
	
	UserState state;
	
	public ActiveUser(String userID,PrintWriter writer){
		setUserState(UserState.LEISURE);
		this.userID=userID;
		this.writer=writer;
	}
	
	public ActiveUser(String userID){
		setUserState(UserState.LEISURE);
		this.userID=userID;
		this.writer=null;
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
	
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}
}
