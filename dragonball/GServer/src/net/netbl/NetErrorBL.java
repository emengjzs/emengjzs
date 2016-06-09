package net.netbl;

import java.io.PrintWriter;

import businesslogic.bl.GameHallBL;
import businesslogic.bl.UserBL;

public class NetErrorBL {
	UserBL userBL=new UserBL();
	GameHallBL gameHallBL=GameHallBL.getGameHall();
	
	public void removeUser(PrintWriter writer){
		String userID=userBL.getUserID(writer);
		
		userBL.deleteUser(userID);
		
		gameHallBL.userQuitAbnormal(userID);
	}
	
	public void removeUser(String userID){
		gameHallBL.userQuitNormal(userID);
	}
}
