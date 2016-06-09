package net.netbl;

import java.io.PrintWriter;

import po.UserPO;

import net.netservice.NetUserBLService_Stub;

import businesslogic.bl.UserBL_Stub;
import businesslogic.blservice.UserBLService_Stub;
import common.Nameplate;
import common.ResultMessage;
import common.Service;

public class NetUserBL_Stub implements NetUserBLService_Stub {
	UserBLService_Stub userBL=new UserBL_Stub();
	static String nameplate;
	static String service;
	
//	@Override
	public void Login(String userID, String password, PrintWriter writer) {
		// TODO Auto-generated method stub

	}
	@Override
	public void selectService(String message,PrintWriter writer) {
		// TODO Auto-generated method stub

		
	}
//	@Override
	public String getUserInfo(String userID,PrintWriter writer) {
		// TODO Auto-generated method stub
		return null;
	}

}
