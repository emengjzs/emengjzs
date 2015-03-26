package model.vo;

public class UserVO {
	String userID;
	String password;
	
//	UserState state;
	
	public UserVO(String userID,String password){
		this.userID=userID;
		this.password=password;
//		this.state=state;
	}
	
	public String getID(){
		return userID;
	}
	
	public String getPassword(){
		return password;
	}
	
//	public UserState getState(){
//		return state;
//	}
}
