package po;

import java.io.PrintWriter;

import common.UserState;

public class ActiveUser {
	String userID;
	PrintWriter writer;
	int money;
	int exp;
	int power;
	
	UserState state;
	
	public ActiveUser(String userID,PrintWriter writer, int money, int exp,int power){
		setUserState(UserState.LEISURE);
		this.userID=userID;
		this.money = money;
		this.exp = exp;
		this.writer=writer;
		this.power=power;
	}
	
	public ActiveUser(String userID){
		setUserState(UserState.LEISURE);
		this.userID=userID;
		this.writer=null;
	}
	
	public ActiveUser(String userID,PrintWriter writer){
		this.userID=userID;
		this.writer=writer;
		exp=0;
		money=0;
		setUserState(UserState.LEISURE);
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
	
	public int getMoney(){
		return money;
	}
	
	public int getExp(){
		return exp;
	}
	
	public int getPower(){
		return power;
	}
	
	public void setPower(int power){
		this.power=power;
	}
	
	public void setMoney(int money){
		this.money=money;
	}
	
	public void setExp(int exp){
		this.exp=exp;
	}
	
	public void sendMessage(String message) {
		System.out.println(writer);
		System.out.println(message);
		writer.println(message);
		writer.flush();
	}
	
	public PrintWriter getWriter(){
		return writer;
	}
}
