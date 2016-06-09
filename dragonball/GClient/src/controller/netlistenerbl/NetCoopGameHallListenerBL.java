package controller.netlistenerbl;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;
import common.CoopRoom;
import common.ResultMessage;
import common.RoomState;
import common.Service;
import common.UserState;
import controller.netlistenerservice.NetCoopGameHallListenerService;
import view.ui.cooperation.Coop_mainFrame;
import view.ui.hallui.*;
import view.uiservice.MultiGameHallService;

public class NetCoopGameHallListenerBL implements NetCoopGameHallListenerService {
	static MultiGameHallService multiGameHallController = null;

	static NetCoopGameHallListenerBL gameHallListenerBL = null;

	private NetCoopGameHallListenerBL() {

	}

	public static NetCoopGameHallListenerBL getGameHallListener() {
		if (gameHallListenerBL == null) {
			gameHallListenerBL = new NetCoopGameHallListenerBL();
		}

		return gameHallListenerBL;
	}

	@SuppressWarnings("static-access")
	public void setController(MultiGameHallService multiGameHallController) {
		this.multiGameHallController = multiGameHallController;
	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.UserLogin.toString())) {
			receiveMessage(message);
		} else if (info[1].equals(Service.UserQuit.toString())) {
			confirmQuitInfo(message);
		} else if (info[1].equals(Service.ReplyUserList.toString())) {
			setUserList(info[2]);
		} else if (info[1].equals(Service.ReplyCoopRoomList.toString())) {
			setRoomList(info[2]);
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			updateUserState(info[2], info[3]);
		} else if (info[1].equals(Service.ReplyEnterCoopRoom.toString())) {
			confirmEnterInfo(message);
		} else if (info[1].equals(Service.RoomStateChange.toString())) {
			updateRoomState(info[2], info[3]);
		}
	}

	// @Override
	private void receiveMessage(String message) {
		// TODO Auto-generated method stub
		if (multiGameHallController != null) {
			multiGameHallController.receiveMessage(message);
		}
	}

	private void setUserList(String message) {
		ArrayList<ActiveUser> userList = getUserList(message);
		multiGameHallController.setUserList(userList);
	}

	private void setRoomList(String message) {
		CoopRoom[] roomList = getRoomList(message);
		multiGameHallController.setRoomList(roomList);
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

	private CoopRoom[] getRoomList(String message) {
		String[] roomInfo = message.split("/");
		CoopRoom[] roomList = new CoopRoom[roomInfo.length];

		for (int i = 0; i < roomInfo.length; i++) {
			String[] single = roomInfo[i].split("-");

			int roomNO = Integer.parseInt(single[0]);
			RoomState state = Enum.valueOf(RoomState.class, single[1]);

			roomList[i] = new CoopRoom(roomNO, state);
		}

		return roomList;
	}

	private void updateUserState(String userID, String userState) {
		UserState state = Enum.valueOf(UserState.class, userState);
		ArrayList<ActiveUser> userList = multiGameHallController
				.getCoopUserList();

		for (ActiveUser user : userList) {
			if (userID.equals(user.getUserID())) {
				user.setUserState(state);
			}
		}

		multiGameHallController.setUserList(userList);
	}

	private void updateRoomState(String roomID, String roomState) {
		RoomState state = Enum.valueOf(RoomState.class, roomState);

		CoopRoom[] roomList = multiGameHallController.getRoomList();

		for (int i = 0; i < roomList.length; i++) {
			if (roomList[i].getRoomNO() == Integer.parseInt(roomID)) {
				roomList[i].setState(state);
			}
		}

		multiGameHallController.setRoomList(roomList);
	}

	private void confirmEnterInfo(String message) {
		String[] info = message.split(" ");
		ResultMessage result = Enum.valueOf(ResultMessage.class, info[2]);

		if (result == ResultMessage.ENTER_COOPROOM_SUCCEDDFULLY) {
			UserVO user = multiGameHallController.getUser();
			CoopRoomUI roomUI = new CoopRoomUI(user, Integer.parseInt(info[4]));
			NetCoopRoomListenerBL roomController = NetCoopRoomListenerBL
					.getCoopRoomListener();
			roomController.setController(roomUI);
			roomUI.doSomething();
		} else if (result == ResultMessage.ENTER_COOPROOM_FAILED) {
			receiveMessage(message);
		}
	}

	private void confirmQuitInfo(String message) {
		String[] info = message.split(" ");

		UserVO user = multiGameHallController.getUser();
		receiveMessage(message);
		if (info[2].equals(user.getID())) {
			System.out.println("Thank you for playing!");
//			MainFrame mainUI = new MainFrame(user);
			Coop_mainFrame main=new Coop_mainFrame(user);
			NetStatisListenerBL statisController=NetStatisListenerBL.getStatisControllerListener();
			statisController.setCoopController(main);
		}
	}

}
