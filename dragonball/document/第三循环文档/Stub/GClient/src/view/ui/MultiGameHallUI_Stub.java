package view.ui;

import java.util.ArrayList;


import common.CoopRoom;
import common.Service;
import common.UserState;

import model.IO.IOHelper;
import model.netbl.NetGameHallBL_Stub;
import model.netservice.NetGameHallBLService_Stub;
import model.vo.ActiveUser;
import model.vo.UserVO;
import view.uiservice.MultiGameHallService_Stub;

public class MultiGameHallUI_Stub implements MultiGameHallService_Stub {
	static NetGameHallBLService_Stub gameHallController;
	UserVO user;
	static ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
	CoopRoom[] roomList;
	
	public MultiGameHallUI_Stub(UserVO user){
		System.out.println("Welcome to XXOO Game Hall, have fun");
		this.user=user;
		gameHallController=NetGameHallBL_Stub.getGameHallController();
		gameHallController.Enter(user.getID());
	}
	
	private void requestOnlineUser(){
		
	}
	
	private void requestRoom(){
		
	}
	
	private void enterCoopRoom(String userID,int roomNO){
		
	}


	@Override
	public  void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Game Hall receive message: "+message);		
	}

	@Override
	public void setUserList(ArrayList<ActiveUser> userList) {
		// TODO Auto-generated method stub
		this.userList=userList;
	}

	@Override
	public void setRoomList(CoopRoom[] roomList) {
		// TODO Auto-generated method stub
		this.roomList=roomList;
	}

	@Override
	public ArrayList<ActiveUser> getCoopUserList() {
		// TODO Auto-generated method stub
		return userList;
	}

	@Override
	public UserVO getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public CoopRoom[] getRoomList() {
		// TODO Auto-generated method stub
		return roomList;
	}

}
