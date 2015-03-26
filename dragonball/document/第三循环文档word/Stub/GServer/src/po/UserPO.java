package po;

public class UserPO {
	String userID;
	String password;
	
	public UserPO(String userID,String password){
		this.userID=userID;
		this.password=password;
	}
	
	public String getID(){
		return userID;
	}
	
	public String getPassword(){
		return password;
	}
}
