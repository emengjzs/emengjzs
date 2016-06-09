package model.vo;

import java.util.ArrayList;

public class UserVO {
	String userID;
	String password;
	int money;
	int exp;
	int direction;
	int power;
	int stage;
	int currentStage;
	ArrayList<Integer> starList;
	ArrayList<Integer> ballList;
	
	public UserVO(String userID,String password,int money,int exp,int direction,int power){
		this.userID=userID;
		this.password=password;
		this.money=money;
		this.exp=exp;
		this.direction=direction;
		this.power=power;
	}
	
	public String getID(){
		return userID;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password=password;
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
	
	public void setStage(int stage){
		this.stage=stage;
		setCurrentStage(stage);
	}
	
	public int getStage(){
		return stage;
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
	
	public void setDirection(int direction){
		this.direction=direction;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setStarList(ArrayList<Integer> starList){
		this.starList=starList;
	}
	
	public ArrayList<Integer> getStarList(){
		return starList;
	}
	
	public void setBallList(ArrayList<Integer> ballList){
		this.ballList=ballList;
	}
	
	public ArrayList<Integer> getBallList(){
		return ballList;
	}
	
	public void setCurrentStage(int currentStage){
		this.currentStage=currentStage;
	}
	
	public int getCurrentStage(){
		return currentStage;
	}
}
