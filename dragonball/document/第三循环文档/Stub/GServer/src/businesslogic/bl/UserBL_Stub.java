package businesslogic.bl;
import java.io.PrintWriter;
import java.util.ArrayList;

import businesslogic.blservice.UserBLService_Stub;


import po.UserPO;
import po.ActiveUser;
import data.dataservice.DataFactory;
import data.dataservice.UserDataService_Stub;


import common.ResultMessage;


public class UserBL_Stub implements UserBLService_Stub {

	UserDataService_Stub userData=DataFactory.getUserData();
	GameHallBL_Stub gameHallBL;
	static ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
	
	public UserBL_Stub() {
		// TODO Auto-generated constructor stub
		gameHallBL=GameHallBL_Stub.getGameHall();
	}

	@Override
	public ResultMessage Login(String userID,String password,PrintWriter writer) {
		// TODO Auto-generated method stub
		return null;
		
	}
	
	public UserPO getUser(String id) {
		return null;
	}

}
