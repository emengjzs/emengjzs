package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;
import common.BattleRoom;
import common.ResultMessage;
import common.RoomState;
import common.Service;
import common.UserState;
import view.ui.hallui.*;
import view.uiservice.BattleGameHallService;
import controller.netlistenerservice.NetBattleGameHallListenerService;

public class NetBattleGameHallListenerBL implements
		NetBattleGameHallListenerService {
	static BattleGameHallService battleGameHallController = null;

	static NetBattleGameHallListenerBL gameHallListenerBL = null;

	private NetBattleGameHallListenerBL() {

	}

	public static NetBattleGameHallListenerBL getGameHallListener() {
		if (gameHallListenerBL == null) {
			gameHallListenerBL = new NetBattleGameHallListenerBL();
		}

		return gameHallListenerBL;
	}

	@SuppressWarnings("static-access")
	public void setController(BattleGameHallService battleGameHallController) {
		this.battleGameHallController = battleGameHallController;
	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.UserLogin.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.UserQuit.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.ReplyBattleUserList.toString())) {
			setUserList(info[2]);
		} else if (info[1].equals(Service.ReplyBattleRoomList.toString())) {
			setRoomList(info[2]);
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			updateUserState(info[2], info[3]);
		} else if (info[1].equals(Service.ReplyEnterBattleRoom.toString())) {
			confirmEnterInfo(message);
		}
	}

	private void setUserList(String message) {
		ArrayList<ActiveUser> userList = getUserList(message);
		battleGameHallController.setUserList(userList);
	}

	private void setRoomList(String message) {
		BattleRoom[] roomList = getRoomList(message);
		battleGameHallController.setRoomList(roomList);
	}

	private BattleRoom[] getRoomList(String message) {
		String[] roomInfo = message.split("/");
		BattleRoom[] roomList = new BattleRoom[roomInfo.length];

		for (int i = 0; i < roomInfo.length; i++) {
			String[] single = roomInfo[i].split("-");

			int roomNO = Integer.parseInt(single[0]);
			RoomState state = Enum.valueOf(RoomState.class, single[1]);

			roomList[i] = new BattleRoom(roomNO, state);
		}

		return roomList;
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
			int power = Integer.parseInt(single[4]);
			
			ActiveUser user = new ActiveUser(userID, state,money,exp,power);
			userList.add(user);
		}

		return userList;
	}

	private void receiveMessage(String message) {
		// TODO Auto-generated method stub
		if (battleGameHallController != null) {
			battleGameHallController.receiveMessage(message);
		}
	}

	private void confirmEnterInfo(String message) {
		String[] info = message.split(" ");
		ResultMessage result = Enum.valueOf(ResultMessage.class, info[2]);

		if (result == ResultMessage.ENTER_BATTLEROOM_SUCCEDDFULLY) {
			UserVO user = battleGameHallController.getUser();
			BattleRoomUI roomUI = new BattleRoomUI(user,
					Integer.parseInt(info[4]));
			NetBattleRoomListenerBL roomController = NetBattleRoomListenerBL
					.getBattleRoomListener();
			roomController.setController(roomUI);
			roomUI.doSomething();
		} else if (result == ResultMessage.ENTER_BATTLEROOM_FAILED) {
			receiveMessage(message);
		}
	}

	private void updateUserState(String userID, String userState) {
		UserState state = Enum.valueOf(UserState.class, userState);
		ArrayList<ActiveUser> userList = battleGameHallController
				.getBattleUserList();

		for (ActiveUser user : userList) {
			if (userID.equals(user.getUserID())) {
				user.setUserState(state);
				break;
			}
		}

		battleGameHallController.setUserList(userList);
	}

}
