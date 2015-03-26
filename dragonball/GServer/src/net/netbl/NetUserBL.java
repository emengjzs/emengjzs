package net.netbl;

import java.io.PrintWriter;

import po.UserPO;

import net.netservice.NetUserBLService;

import businesslogic.bl.UserBL;
import businesslogic.blservice.UserBLService;
import common.Nameplate;
import common.ResultMessage;
import common.Service;

public class NetUserBL implements NetUserBLService {
	UserBLService userBL = new UserBL();
	static String nameplate;
	static String service;

	private void Login(String userID, String password, PrintWriter writer) {
		// TODO Auto-generated method stub
		ResultMessage info = userBL.Login(userID, password, writer);

		nameplate = Nameplate.User.toString();
		service = Service.LoginResult.toString();
		String userInfo="";

		if(info==ResultMessage.LOGIN_SUCCESSFULLY){
			userInfo = getUserInfo(userID, writer);
		}
		writer.println(nameplate + " " + service + " " + info + " " + userInfo);
		writer.flush();
	}
	
	private void getUser(String userID,PrintWriter writer){
		nameplate = Nameplate.User.toString();
		service = Service.ReplyUser.toString();
		
		String userInfo=getUserInfo(userID,writer);
		writer.println(nameplate + " " + service + " " + userInfo);
		writer.flush();
	}

	private void Register(String userID, String password, PrintWriter writer) {
		// TODO Auto-generated method stub
		ResultMessage info = userBL.Register(userID, password, writer);

		nameplate = Nameplate.User.toString();
		service = Service.ReplyRegister.toString();

		writer.println(nameplate + " " + service + " " + info);
		writer.flush();
	}
	
	private void changePassword(String userID, String password, PrintWriter writer) {
		// TODO Auto-generated method stub
		ResultMessage info = userBL.changePassword(userID, password);

		nameplate = Nameplate.User.toString();
		service = Service.ReplyChangePassword.toString();

		writer.println(nameplate + " " + service + " " + info);
		writer.flush();
	}
	
	private void getStageList(String userID,PrintWriter writer){
		String message=userBL.getStageList(userID);
		
		nameplate = Nameplate.User.toString();
		service = Service.ReplyStageList.toString();
		
		writer.println(nameplate + " " + service + " " + message);
		writer.flush();
	}
	
	private void getBallList(String userID,PrintWriter writer){
		String message=userBL.getBallList(userID);
		
		nameplate = Nameplate.User.toString();
		service=Service.ReplyBallList.toString();
		
		writer.println(nameplate + " " + service + " " + message);
		writer.flush();
	}
	
	private void changeDirection(String userID, int direction) {
		// TODO Auto-generated method stub
		userBL.changeDirection(userID, direction);

	}

	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub
		String[] result = message.split(" ");

		if (result[1].equals(Service.Login.toString())) {
			Login(result[2], result[3], writer);
		} else if (result[1].equals(Service.GetUserInfo.toString())) {
			getUserInfo(result[2], writer);
		} else if (result[1].equals(Service.Register.toString())) {
			Register(result[2], result[3], writer);
		} else if (result[1].equals(Service.ChangePassword.toString())){
			changePassword(result[2],result[3],writer);
		} else if(result[1].equals(Service.ChangeDirection.toString())){
			changeDirection(result[2],Integer.parseInt(result[3]));
		} else if(result[1].equals(Service.GetUser.toString())){
			getUser(result[2],writer);
		} else if(result[1].equals(Service.GetStageList.toString())){
			getStageList(result[2],writer);
		} else if(result[1].equals(Service.GetBallList.toString())){
			getBallList(result[2],writer);
		}

	}

	// @Override
	public String getUserInfo(String userID, PrintWriter writer) {
		// TODO Auto-generated method stub
		UserPO po = userBL.getUser(userID);
		String info = po.getID() + "-" + po.getPassword() + "-" +po.getMoney()+ "-"+ po.getExp()+ "-"+po.getDirection()+"-"+po.getPower();

		return info;
	}

}
