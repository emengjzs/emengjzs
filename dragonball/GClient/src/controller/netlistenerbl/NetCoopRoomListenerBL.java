package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;
import common.ResultMessage;
import common.Service;
import common.UserState;
import controller.netlistenerservice.NetCoopRoomListenerService;
import view.ui.hallui.CoopGameHallUI;
import view.uiservice.CoopRoomService;

public class NetCoopRoomListenerBL implements NetCoopRoomListenerService {
	static CoopRoomService coopRoomController = null;

	static NetCoopRoomListenerBL coopRoomListenerBL = null;

	private NetCoopRoomListenerBL() {

	}

	public static NetCoopRoomListenerBL getCoopRoomListener() {
		if (coopRoomListenerBL == null) {
			coopRoomListenerBL = new NetCoopRoomListenerBL();
		}

		return coopRoomListenerBL;
	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.ReplyEnterCoopRoom.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyUserList.toString())) {
			setUserList(info[2]);
		} else if (info[1].equals(Service.UserStateChange.toString())) {
//			updateUserState(info[2], info[3]);
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplySetReady.toString())) {
			confirmReadyInfo(message);
		} else if (info[1].equals(Service.ChatReceive.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyQuitCoopRoom.toString())) {
			confirmQuitInfo(message);
		} else if (info[1].equals(Service.ReplyChooseProp.toString())){
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyCoopGameResult.toString())){
			receiveMessage(message);
		} else if (info[1].equals(Service.GameStart.toString())){
			receiveMessage(message);
		} else if(info[1].equals(Service.ReplyCoopProp.toString())){
			receiveMessage(message);
		}
 	}

/*	private void updateUserState(String userID, String userState) {
		UserState state = Enum.valueOf(UserState.class, userState);
		ArrayList<ActiveUser> userList = coopRoomController.getPlayerList();

		for (ActiveUser user : userList) {
			if (userID.equals(user.getUserID())) {
				user.setUserState(state);
			}
		}

		coopRoomController.setUserList(userList);
	}*/

	private void setUserList(String message) {
		ArrayList<ActiveUser> userList = getUserList(message);
		coopRoomController.setUserList(userList);
	}

	private ArrayList<ActiveUser> getUserList(String message) {
		ArrayList<ActiveUser> userList = new ArrayList<ActiveUser>();

		String[] userInfo = message.split("/");
		for (int i = 0; i < userInfo.length; i++) {
			String[] single = userInfo[i].split("-");

			String userID = single[0];
			UserState state = Enum.valueOf(UserState.class, single[1]);
			int money = Integer.parseInt(single[2]);
			int exp = Integer.parseInt(single[3]);
			int power=Integer.parseInt(single[4]);

			ActiveUser user = new ActiveUser(userID, state,money,exp,power);
			userList.add(user);
		}

		return userList;
	}

	private void receiveMessage(String message) {
		// TODO Auto-generated method stub
		if (coopRoomController != null) {
			coopRoomController.receiveMessage(message);
		}
	}

	@SuppressWarnings("static-access")
	public void setController(CoopRoomService coopRoomController) {
		this.coopRoomController = coopRoomController;
	}

	private void confirmQuitInfo(String message) {
		String[] info = message.split(" ");

		UserVO user = coopRoomController.getUser();
		System.out.println(info[5]);
		System.out.println(user.getID());
		if (info[5].equals(user.getID())) {
			System.out.println("Thank you for playing!");
//			Coop_mainFrame mainUI = new Coop_mainFrame(user);
			CoopGameHallUI gameHall=new CoopGameHallUI(user);
			NetCoopGameHallListenerBL gameController=NetCoopGameHallListenerBL.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
		}
		receiveMessage(message);
	}

	@SuppressWarnings("unused")
	private void confirmReadyInfo(String message) {
		String[] info = message.split(" ");

		if (info[2].equals(ResultMessage.SET_READY_SUCCESSFULLY.toString())) {
			UserVO user = coopRoomController.getUser();
			receiveMessage(message);
//			updateUserState(user.getID(), "READY");
		} 
	}

}
