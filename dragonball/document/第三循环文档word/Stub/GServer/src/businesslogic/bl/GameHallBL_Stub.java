package businesslogic.bl;

import java.util.ArrayList;
import java.util.Iterator;


import common.Nameplate;
import common.ResultMessage;
import common.Service;
import common.UserState;

import po.ActiveUser;

public class GameHallBL_Stub {
	static GameHallBL_Stub gameHallBL=null;
	static ArrayList<ActiveUser> cooperationUserList;
	static CoopRoomBL_Stub[] coopRoomList;
	final int NUMBER_OF_ROOMS=2;
	
	static String nameplate="";
	static String service="";
	
	private  GameHallBL_Stub(){
		coopRoomList=new CoopRoomBL_Stub[NUMBER_OF_ROOMS];
		for(int i=0;i<NUMBER_OF_ROOMS;i++){
			coopRoomList[i]=new CoopRoomBL_Stub(i);
		}
		
		cooperationUserList=new ArrayList<ActiveUser>();
	}
	
	public static GameHallBL_Stub getGameHall(){
		if(gameHallBL==null){
			gameHallBL=new GameHallBL_Stub();
		}
		
		return gameHallBL;
	}
	
	public ArrayList<ActiveUser> getCoopUserList(){
		return cooperationUserList;
	}
	
	public CoopRoomBL_Stub[] getCoopRoomList(){
		return coopRoomList;
	}
	
	public void addUser(ActiveUser user){

	}
	
	public String enterCoopRoom(String userID,int roomNO){
		return null;
	}
	
	public String quitCoopRoom(String userID,int roomNO){
		return null;
	}
	
	public String setReady(int roomNO,String userID){
		return null;
	}
	
	public void kickPlayer(String roommasterID,String userID,int roomNO){

	}
	
	public ActiveUser getUser(String userID){
		return null;
	}
	
	public void deleteUser(ActiveUser user){

	}
	
	public void sendToAll(String message) {

	}
	
	public ArrayList<ActiveUser> getRoomPlayerInfo(int roomNO){
		return null;
	}
}
