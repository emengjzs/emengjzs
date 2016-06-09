package view.ui;

import java.util.ArrayList;


import common.Service;
import common.UserState;

import model.IO.IOHelper;
import model.netbl.NetCoopRoomBL_Stub;
import model.netservice.NetCoopRoomBLService_Stub;
import model.vo.ActiveUser;
import model.vo.UserVO;

import view.uiservice.CoopRoomService_Stub;

public class RoomUI_Stub implements CoopRoomService_Stub {

	UserVO user;
	int roomNO;
	static ArrayList<ActiveUser> playerList=new ArrayList<ActiveUser>();
	static NetCoopRoomBLService_Stub coopRoomController;
	
	public RoomUI_Stub(UserVO user,int roomNO){
		this.roomNO=roomNO;
		this.user=user;
		coopRoomController=NetCoopRoomBL_Stub.getCoopRoomController();
	}
	
	private void requestPlayerList(){
		
	}
	
	private void setReady(){
		
	}
	
	private void chatInRoom(String message){
		
	}
	
	private void kickPlayer(String roommasterID,String userID,int roomNO){
		
	}
	
	private void quit(String userID,int roomNO){
		
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Coop Room receive message: "+message);
	}
	@Override
	public void setUserList(ArrayList<ActiveUser> userList) {
		// TODO Auto-generated method stub
		this.playerList=userList;
		System.out.println("Current Player In Room: "+userList.size());
	}

	@Override
	public ArrayList<ActiveUser> getPlayerList() {
		// TODO Auto-generated method stub
		return playerList;
	}
}
