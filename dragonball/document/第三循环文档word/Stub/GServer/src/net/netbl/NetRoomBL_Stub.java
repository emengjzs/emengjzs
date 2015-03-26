package net.netbl;

import java.io.PrintWriter;
import java.util.ArrayList;

import po.ActiveUser;

import net.netservice.NetRoomBLService_Stub;
import businesslogic.bl.GameHallBL_Stub;

import common.Nameplate;
import common.Service;

public class NetRoomBL_Stub implements NetRoomBLService_Stub {
	GameHallBL_Stub gameHallBL=GameHallBL_Stub.getGameHall();
	final static String NAMEPLATE=Nameplate.CoopRoom.toString();
	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub

	}
	
	private void kickPlayer(String roommasterID,String userID,int roomNO){

	}
	
	private void quitCoopRoom(String userID,int roomNO){

	}
	
	private void setReady(int roomNO,String userID){

	}
	
	private void chatInRoom(int roomNO,String info){

	}
	
	private void getPlayerList(String roomNO,PrintWriter writer){

	}
	
	private StringBuffer userInString(ActiveUser user){
		return null;
	}
}
