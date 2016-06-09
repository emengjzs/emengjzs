package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;


import common.CoopRoom;
import common.ResultMessage;
import common.RoomState;
import common.Service;
import common.UserState;
import controller.netlistenerservice.NetGameHallListenerService_Stub;
import view.ui.RoomUI_Stub;
import view.uiservice.MultiGameHallService_Stub;



public class NetGameHallListenerBL_Stub implements NetGameHallListenerService_Stub {
	static MultiGameHallService_Stub multiGameHallController=null;
	
	static NetGameHallListenerBL_Stub gameHallListenerBL=null;
	
	private NetGameHallListenerBL_Stub(){
		
	}
	
	public static NetGameHallListenerBL_Stub getGameHallListener(){
		if(gameHallListenerBL==null){
			gameHallListenerBL = new NetGameHallListenerBL_Stub();
		}
		
		return gameHallListenerBL;
	}
	
	public void setController(MultiGameHallService_Stub multiGameHallController){
		this.multiGameHallController=multiGameHallController;
	}
	
	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub

	}

//	@Override
	private void receiveMessage(String message) {
		// TODO Auto-generated method stub
		if(multiGameHallController!=null){
			multiGameHallController.receiveMessage(message);
		}
	}
	
	
	private void setUserList(String message){

	}
	
	private void setRoomList(String message){

	}
	
	private ArrayList<ActiveUser> getUserList(String message){
		return null;
	}
	
	private CoopRoom[] getRoomList(String message){
		return null;
	}
	
	private void updateUserState(String userID,String userState){

	}
	
	private void updateRoomState(String roomID,String roomState){

	}
	
	private void confirmEnterInfo(String message){

	}

}
