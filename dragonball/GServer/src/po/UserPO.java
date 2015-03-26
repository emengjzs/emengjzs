package po;


public class UserPO {
	String userID;
	String password;
	int money;
	int exp;
	int direction;
	int power;
	
	public UserPO(String userID,String password,int money,int exp,int direction,int power){
		this.userID=userID;
		this.password=password;
		this.money=money;
		this.exp=exp;
		this.direction = direction;
		this.power = power;
	}
	
	public String getID(){
		return userID;
	}
	
	public String getPassword(){
		return password;
	}
	
	public int getMoney(){
		return money;
	}
	
	public int getExp(){
		return exp;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public int getPower(){
		return power;
	}
}
