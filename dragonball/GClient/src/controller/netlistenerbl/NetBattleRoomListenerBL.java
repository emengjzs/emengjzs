package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;
import common.Service;
import common.UserState;
import view.ui.hallui.*;
import view.uiservice.BattleRoomService;
import controller.netlistenerservice.*;

public class NetBattleRoomListenerBL implements NetBattleRoomListenerService {
	static BattleRoomService battleRoomController = null;

	static NetBattleRoomListenerBL battleRoomListenerBL = null;

	private NetBattleRoomListenerBL() {

	}

	public static NetBattleRoomListenerBL getBattleRoomListener() {
		if (battleRoomListenerBL == null) {
			battleRoomListenerBL = new NetBattleRoomListenerBL();
		}

		return battleRoomListenerBL;
	}

	@SuppressWarnings("static-access")
	public void setController(BattleRoomService battleRoomController) {
		this.battleRoomController = battleRoomController;
	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.ReplyEnterBattleRoom.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyUserList.toString())) {
			setUserList(info[2]);
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyQuitBattleRoom.toString())) {
			confirmQuitInfo(message);
		} else if (info[1].equals(Service.ReplySetReady.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ChatReceive.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyChangeTeam.toString())) {
			receiveMessage(message);
		} else if(info[1].equals(Service.ReplyBattleProp.toString())){
			receiveMessage(message);
		} else if(info[1].equals(Service.ReplyBattleGameResult.toString())){
			receiveMessage(message);
		} else if(info[1].equals(Service.GameStart.toString())){
			receiveMessage(message);
		} else if(info[1].equals(Service.ReplyBattlePropList.toString())){
			receiveMessage(message);
		} else if(info[1].equals(Service.ReplyChangePropList.toString())){
			receiveMessage(message);
		}
		// Client read: BattleRoom ReplySetReady SET_READY_SUCCESSFULLYŒ¥¥¶¿Ì
	}

	private void receiveMessage(String message) {
		// TODO Auto-generated method stub
		if (battleRoomController != null) {
			battleRoomController.receiveMessage(message);
		}
	}

	private void setUserList(String message) {
		ArrayList<ActiveUser> teamList1 = getUserList(message, 1);
		ArrayList<ActiveUser> teamList2 = getUserList(message, 2);
		battleRoomController.setUserList(teamList1, 1);
		battleRoomController.setUserList(teamList2, 2);
	}

	private ArrayList<ActiveUser> getUserList(String message, int teamNO) {
		ArrayList<ActiveUser> userList = new ArrayList<ActiveUser>();

		String[] userInfo = message.split("/");
		for (int i = 0; i < userInfo.length; i++) {
			String[] single = userInfo[i].split("-");
			int NO = Integer.parseInt(single[2]);

			if (NO == teamNO) {
				String userID = single[0];
				UserState state = Enum.valueOf(UserState.class, single[1]);
				int money = Integer.parseInt(single[3]);
				int exp = Integer.parseInt(single[4]);
				int power = Integer.parseInt(single[5]);
				
				ActiveUser user = new ActiveUser(userID, state,money,exp,power);
				userList.add(user);
			}
		}

		return userList;
	}

	private void confirmQuitInfo(String message) {
		String[] info = message.split(" ");

		UserVO user = battleRoomController.getUser();
		if (info[5].equals(user.getID())) {
			System.out.println("Thank you for playing!");
//			Fight_mainFrame gameHallUI = new Fight_mainFrame(user);
//			gameHallUI.Request();
			BattleGameHallUI gameHall=new BattleGameHallUI(user);
			NetBattleGameHallListenerBL gameController=NetBattleGameHallListenerBL.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
		}
		receiveMessage(message);
	}
}
