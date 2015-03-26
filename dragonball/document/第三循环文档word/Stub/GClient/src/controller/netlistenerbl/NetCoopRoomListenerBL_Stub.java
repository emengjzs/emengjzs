package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;


import common.Service;
import common.UserState;
import controller.netlistenerservice.NetCoopRoomListenerService_Stub;

import view.uiservice.CoopRoomService_Stub;
import view.uiservice.MultiGameHallService_Stub;

public class NetCoopRoomListenerBL_Stub implements NetCoopRoomListenerService_Stub {
	static CoopRoomService_Stub coopRoomController=null;
	
	static NetCoopRoomListenerBL_Stub coopRoomListenerBL=null;
	
	private NetCoopRoomListenerBL_Stub(){
		
	}
	
	public static NetCoopRoomListenerBL_Stub getCoopRoomListener(){
		if(coopRoomListenerBL==null){
			coopRoomListenerBL = new NetCoopRoomListenerBL_Stub();
		}
		
		return coopRoomListenerBL;
	}
	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub

	}
	
	private void updateUserState(String userID,String userState){

	}
	
	private void setUserList(String message){

	}
	
	private ArrayList<ActiveUser> getUserList(String message){
		return null;
	}
	
	private void receiveMessage(String message) {
		// TODO Auto-generated method stub

	}
	
	public void setController(CoopRoomService_Stub coopRoomController){
		this.coopRoomController=coopRoomController;
	}

}
