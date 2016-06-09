package net.netbl;

import java.io.PrintWriter;
import java.util.ArrayList;

import common.Nameplate;
import common.Service;

import po.ActiveUser;
import businesslogic.bl.CoopRoomBL_Stub;
import businesslogic.bl.GameHallBL_Stub;
import net.netservice.NetGameHallService_Stub;

public class NetGameHallBL_Stub implements NetGameHallService_Stub {
	GameHallBL_Stub gameHallBL=GameHallBL_Stub.getGameHall();
	final static String NAMEPLATE=Nameplate.GameHall.toString();


//	@Override
	private void Enter(String userID, PrintWriter writer) {
		// TODO Auto-generated method stub

	}

//	@Override
	private void Quit(String userID, PrintWriter writer) {
		// TODO Auto-generated method stub

	}
	
	private void getCoopUserList(PrintWriter writer){

	}
	
	private StringBuffer userInString(ActiveUser user){
		return null;
	}
	
	private StringBuffer roomInString(CoopRoomBL_Stub room){
		return null;
	}
	
	private void getCoopRoomList(PrintWriter writer){

	}
	
	private void enterCoopRoom(String userID,int roomNO,PrintWriter writer){

	}

	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub

	}

}
