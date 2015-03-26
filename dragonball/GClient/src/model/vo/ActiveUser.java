package model.vo;
import common.UserState;

public class ActiveUser {
	String userID;	
	UserState state;
	int money;
	int exp;
	int power;
	
	public ActiveUser(String userID,UserState state, int money, int exp,int power){
		this.state=state;
		this.userID=userID;
		this.money = money;
		this.exp = exp;
		this.power=power;
	}
	
	public ActiveUser(String userID){
		this.userID=userID;
		state=null;
		money=0;
		exp=0;
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
	
	public int getPower(){
		return power;
	}
	
	public void setMoney(int money){
		this.money=money;
	}
	
	public void setExp(int exp){
		this.exp=exp;
	}
	
	public void setPower(int power){
		this.power=power;
	}
	
	public int getMoney(){
		return money;
	}
	
	public int getExp(){
		return exp;
	}
}
