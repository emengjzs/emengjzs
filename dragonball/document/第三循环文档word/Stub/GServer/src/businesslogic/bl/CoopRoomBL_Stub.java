package businesslogic.bl;

import java.util.ArrayList;
import java.util.Iterator;

import po.ActiveUser;

import common.Nameplate;
import common.RoomState;
import common.Service;
import common.UserState;

public class CoopRoomBL_Stub {
	int RoomNO;
	RoomState roomState;
	ArrayList<ActiveUser> userList;
	final int MAX_PLAYER=2;
	
	static String nameplate=Nameplate.CoopRoom.toString();
	static String service="";
	
	public CoopRoomBL_Stub(int RoomNO){
		this.RoomNO=RoomNO;
		roomState=RoomState.AVAILABLE;
		userList=new ArrayList<ActiveUser>();
	}
	
	public void addPlayer(ActiveUser user,ArrayList<ActiveUser> coopUserList){
		
	}
	
	public void removePlayer(ActiveUser user,ArrayList<ActiveUser> coopUserList){
		
	}
	
	public RoomState getState(){
		return roomState;
	}
	
	public ArrayList<ActiveUser> getPlayerList(){
		return userList;
	}
	
	public int getRoomNO(){
		return RoomNO;
	}
	
	public boolean canIn(ActiveUser user,ArrayList<ActiveUser> coopUserList){
		return true;
	}
	
	public boolean canQuit(ActiveUser user,ArrayList<ActiveUser> coopUserList){
		return true;
	}
	
	public boolean setReady(String userID,ArrayList<ActiveUser> coopUserList){
		return true;
	}
	
	private void setState(RoomState state,ArrayList<ActiveUser> coopUserList){
		roomState=state;
		
		informRoomStateChange(coopUserList);
	}
	
	private void checkIn(ArrayList<ActiveUser> coopUserList){
		if(userList.size()==MAX_PLAYER){
			setState(RoomState.FULL,coopUserList);
		}
	}
	
	private void checkQuit(ArrayList<ActiveUser> coopUserList){
		if(userList.size()==MAX_PLAYER-1){
			setState(RoomState.AVAILABLE,coopUserList);
		}
	}
	
	public String getUserState(ActiveUser player){
		return null;
	}
	
	private void sendToPlayer(String message) {
		
	}
	
	private void informRoomStateChange(ArrayList<ActiveUser> coopUserList){
		
	}
	
	private void informUserStateChange(String userID,UserState state,ArrayList<ActiveUser> coopUserList) {
		
	}
}
