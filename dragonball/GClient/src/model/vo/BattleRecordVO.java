package model.vo;

import java.util.ArrayList;

public class BattleRecordVO {
	int gameID;
	int point1;
	int point2;
	ArrayList<ActiveUser> playerList1=new ArrayList<ActiveUser>();
	ArrayList<ActiveUser> playerList2=new ArrayList<ActiveUser>();
	
	public BattleRecordVO(int gameID,int point1,int point2,ArrayList<ActiveUser> playerList1,ArrayList<ActiveUser> playerList2){
		this.gameID=gameID;
		this.point1=point1;
		this.point2=point2;
		this.playerList1=playerList1;
		this.playerList2=playerList2;
	}
	
	public int getPoint1(){
		return point1;
	}
	
	public int getPoint2(){
		return point2;
	}
	
	public ArrayList<ActiveUser> getPlayerList1(){
		return playerList1;
	}
	
	public ArrayList<ActiveUser> getPlayerList2(){
		return playerList2;
	}
}
