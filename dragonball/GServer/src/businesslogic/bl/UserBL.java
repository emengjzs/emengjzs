package businesslogic.bl;
import java.io.PrintWriter;
import java.util.ArrayList;

import businesslogic.blservice.UserBLService;


import po.UserPO;
import po.ActiveUser;
import data.dataservice.DataFactory;
import data.dataservice.UserDataService;


import common.ResultMessage;


public class UserBL implements UserBLService {

	UserDataService userData=DataFactory.getUserData();
	GameHallBL gameHallBL;
	static ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
	
	public UserBL() {
		// TODO Auto-generated constructor stub
		gameHallBL=GameHallBL.getGameHall();
	}

	@Override
	public ResultMessage Login(String userID,String password,PrintWriter writer) {
		// TODO Auto-generated method stub
		System.out.println("UserID: "+userID+" Password: "+password);
		
		UserPO po=getUser(userID);
		
		if (po != null) {

			if ((po.getID().equals(userID)) && po.getPassword().equals(password)) {
				if(!findUser(userID)){
					ActiveUser user=new ActiveUser(userID,writer);
					userList.add(user);
					informAll();
//				gameHallBL.addUser(user);
					return ResultMessage.LOGIN_SUCCESSFULLY;
				}else{
					return ResultMessage.DUPLICATE_ACCOUNT_LOGIN;
				}

			} else if ((userID.equals("")) || (password.equals(""))) {
				return ResultMessage.INVALID_ID_OR_PASSWORD;

			} else {
				//return ResultMessage.LOGIN_FAILED;
//				ActiveUser user=new ActiveUser(userID,writer);
//				gameHallBL.addUser(user);
				return ResultMessage.LOGIN_FAILED;
			}

		} else {
			return ResultMessage.NO_ACCOUNT;
			//return ResultMessage.LOGIN_SUCCESSFULLY;
		}
		
	}
	
	public UserPO getUser(String id) {
		UserPO po = userData.FindByID(id);
		return po;
	}

	@Override
	public ResultMessage Register(String userID, String password,
			PrintWriter writer) {
		// TODO Auto-generated method stub
		UserPO po=getUser(userID);
		ResultMessage message=null;
		if(po == null){
			UserPO newUser = new UserPO(userID, password, 1000, 0, 1, 1);
			userData.Insert(newUser);
			userData.InsertProp(userID);
			userData.InsertRival(userID);
			userData.InsertDragonBall(userID);
			message=ResultMessage.REGISTER_SUCCESSFULLY;
		}else{
			message=ResultMessage.REGISTER_FAILED;
		}
		return message;
	}

	@Override
	public ResultMessage changePassword(String userID, String password) {
		// TODO Auto-generated method stub
		UserPO po =getUser(userID);
		ResultMessage message=null;
		if(po!=null){
			userData.UpdatePassword(userID, password);
			message=ResultMessage.CHANGE_PASSWORD_SUCCESSFULLY;
		}else{
			message=ResultMessage.CHANGE_PASSWORD_FAILED;
		}
		return message;
	}

	@Override
	public void changeDirection(String userID, int direction) {
		// TODO Auto-generated method stub
		userData.UpdateDirection(userID, direction);
	}
	
	public String getStageList(String userID){
		String result="";
		int currentStage=0;
		ArrayList<Integer> starList=new ArrayList<Integer>();
		String lastStageState="";
		
		for(int i=1;i<=9;i++){
			if(userData.FindStageState(userID, i).equals("current")){
				currentStage=i;
			}
			
			if(i==9){
				lastStageState=userData.FindStageState(userID, i);
			}
			starList.add(userData.FindStageStar(userID, i));
		}
		if(currentStage==0&&(userData.FindStageState(userID, 9).equals("lock"))){
			currentStage=8;
		}else if(currentStage==0&&(!userData.FindStageState(userID, 9).equals("lock"))){
			currentStage=9;
		}
		result=currentStage+" "+lastStageState+" ";
		for(Integer i:starList){
			result=result+i+"-";
		}
		
		return result;
	}
	
	public boolean findUser(String userID){
		boolean isIn=false;
		
		for(ActiveUser user:userList){
			if(user.getUserID().equals(userID)){
				isIn=true;
				break;
			}
		}
		
		return isIn;
	}
	
	public String getUserID(PrintWriter writer){
		String userID="";
		System.out.println(userList.size());
		
		for(ActiveUser user:userList){
			if(writer==user.getWriter()){
				userID=user.getUserID();
			}
		}
		
		return userID;
	}
	
	public void deleteUser(String userID){
		for(ActiveUser user:userList){
			if(user.getUserID().equals(userID)){
				userList.remove(user);
				break;
			}
		}
	}
	
	private void informAll(){
		for(ActiveUser user:userList){
			user.sendMessage("Hello");
		}
	}

	@Override
	public String getBallList(String userID) {
		// TODO Auto-generated method stub
		String ballString=userData.FindBall(userID);
		
		return ballString;
	}

}
