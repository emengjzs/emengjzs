package model.vo;

import java.util.ArrayList;

public class CoopRecordVO implements Comparable<CoopRecordVO> {
	int gameID;
	int point;
	ArrayList<ActiveUser> playerList=new ArrayList<ActiveUser>();
	
	public CoopRecordVO(int gameID,int point,ArrayList<ActiveUser>playerList){
		this.gameID=gameID;
		this.point=point;
		this.playerList=playerList;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public int getPoint(){
		return point;
	}
	
	public ArrayList<ActiveUser> getUserList(){
		return playerList;
	}

	@Override
	public int compareTo(CoopRecordVO o) {
		// TODO Auto-generated method stub
		return (""+gameID).compareTo(""+o.getGameID());
	}
}
