package businesslogic.bl;

import java.util.ArrayList;
import java.util.Iterator;

import po.ActiveUser;
import common.Mode;
import common.Nameplate;
import common.RoomState;
import common.Service;
import common.UserState;
import data.dataimpl.CoopGameDataServiceImpl;
import data.dataimpl.UserDataServiceImpl;
import data.dataservice.CoopGameDataService;
import data.dataservice.UserDataService;

public class CoopRoomBL extends RoomBL {
	int RoomNO;
	Team team;
	RoomState roomState;
	ArrayList<ActiveUser> userList;
	final int MAX_PLAYER = 4;
	int stage=8;
	static CoopRoomBL coopRoomBL = null;
	static String nameplate = Nameplate.CoopRoom.toString();
	static String service = "";
	CoopGameDataService coopGameData = new CoopGameDataServiceImpl();
	UserDataService userData = new UserDataServiceImpl();

	boolean prop1 = false;
	boolean prop2 = false;
	boolean prop3 = false;

	public CoopRoomBL(int RoomNO) {
		this.RoomNO = RoomNO;
		roomState = RoomState.AVAILABLE;
		userList = new ArrayList<ActiveUser>();
		team = new Team(this);
	}

	public void setRoomState(RoomState state) {
		this.roomState = state;
	}
	
	public String getPropList(){
		String message="";
		
		if(prop1==true){
			message=message+1+"-";
		}else{
			message=message+0+"-";
		}
		
		if(prop2==true){
			message=message+1+"-";
		}else{
			message=message+0+"-";
		}
		
		if(prop3==true){
			message=message+1+"-";
		}else{
			message=message+0+"-";
		}
		
		return message;
	}

	public void chooseProp(int propNO, String userID) {
		for (ActiveUser user : userList) {
			if (userID.equals(user.getUserID())) {
				int money = user.getMoney();
				if (propNO == 0) {
					money -= 388;
					if (money >= 0) {
						prop1 = true;
						user.setMoney(money);
						userData.UpdateMoney(userID, -388);
					}

				} else if (propNO == 1) {
					money -= 488;
					if (money >= 0) {
						prop2 = true;
						user.setMoney(money);
						userData.UpdateMoney(userID, -488);
					}
				} else if (propNO == 2) {
					money -= 88;
					if (money >= 0) {
						prop3 = true;
						user.setMoney(money);
						userData.UpdateMoney(userID, -88);
					}
				}
			}
		}

		service = Service.ReplyChooseProp.toString();

		String message = nameplate + " " + service + " " + propNO;
		sendToPlayer(message);
	}

	public void addPlayer(ActiveUser user, ArrayList<ActiveUser> coopUserList) {
		service = Service.ReplyEnterCoopRoom.toString();

		String message = nameplate + " " + service + " " + user.getUserID()
				+ " " + user.getMoney() + " " + user.getExp() + " "
				+ user.getPower() + " has entered in room!";
		sendToPlayer(message);

		if (userList.size() == 0) {
			user.setUserState(UserState.ROOMMASTER);
			informUserStateChange(user.getUserID(), user.getUserState(),
					coopUserList);
		}
		userList.add(user);
	}

	public void remove(String userID) {
		for (ActiveUser player : userList) {
			if (player.getUserID().equals(userID)) {
				userList.remove(player);
				break;
			}
		}
	}

	public void removePlayer(ActiveUser user, ArrayList<ActiveUser> coopUserList) {
		for (ActiveUser player : userList) {
			if (player.getUserID().equals(user.getUserID())) {
				userList.remove(player);
				break;
			}
		}
		// remove是否正确？

		if (userList.size() > 0 && user.getUserState() == UserState.ROOMMASTER) {
			userList.get(0).setUserState(UserState.ROOMMASTER);
			informUserStateChange(userList.get(0).getUserID(), userList.get(0)
					.getUserState(), coopUserList);
			informUserStateChangeInRoom(userList.get(0).getUserID(), userList
					.get(0).getUserState());
		}

		service = Service.ReplyQuitCoopRoom.toString();

		String message = nameplate + " " + service + " " + user.getUserID()
				+ " has left  room!";
		sendToPlayer(message);

		user.setUserState(UserState.LEISURE);
		informUserStateChange(user.getUserID(), user.getUserState(),
				coopUserList);
		if(userList.size()==0){
			Reset();
		}
	}

	public RoomState getState() {
		return roomState;
	}

	public ArrayList<ActiveUser> getPlayerList() {
		return userList;
	}

	public int getRoomNO() {
		return RoomNO;
	}

	public synchronized boolean canIn(ActiveUser user, ArrayList<ActiveUser> coopUserList) {
		boolean canIn = false;

		if (userList.size() < MAX_PLAYER && roomState == RoomState.AVAILABLE
				&& (!isIn(user))) {
			canIn = true;
			addPlayer(user, coopUserList);
			checkIn(coopUserList);
		}

		return canIn;
	}

	private boolean isIn(ActiveUser user) {
		boolean isIn = false;
		for (ActiveUser player : userList) {
			if (player.getUserID().equals(user.getUserID())) {
				isIn = true;
				break;
			}
		}

		return isIn;
	}

	public synchronized boolean canQuit(ActiveUser user, ArrayList<ActiveUser> coopUserList) {
		boolean canQuit = false;

		if (userList.indexOf(user) != -1
				&& user.getUserState() != UserState.GAMING) {
			canQuit = true;
			removePlayer(user, coopUserList);
			checkQuit(coopUserList);
		}

		return canQuit;
	}

	public boolean setReady(String userID, ArrayList<ActiveUser> coopUserList) {
		boolean canSet = false;

		for (ActiveUser user : userList) {
			if (user.getUserID().equals(userID)) {
				if (user.getUserState() == UserState.LEISURE) {
					user.setUserState(UserState.READY);

					service = Service.UserStateChange.toString();
					String message = nameplate + " " + service + " " + userID
							+ " " + UserState.READY.toString();
					sendToPlayer(message);

					informUserStateChangeInRoom(userID, UserState.READY);
					informUserStateChange(userID, UserState.READY, coopUserList);
					canSet = true;
				}
			}
		}

		return canSet;
	}

	@SuppressWarnings("unused")
	public void playGame(String message, int roomNO, String id) {
		if (message.startsWith("Start")) {
			roomState=RoomState.GAMING;
			ArrayList<ActiveUser> gameHallUserList=GameHallBL.getCoopUserList();
			informRoomStateChange(gameHallUserList);
			if (prop1) {
				message += "c";
			}
			if (prop2) {
				message += "d";
			}
			if (prop3) {
				message += "e";
			}
			String result = team.startGame(message, 0);
			Iterator<ActiveUser> i1 = userList.iterator();
			String power = "";
			while (i1.hasNext()) {
				ActiveUser user = i1.next();
				power = power + user.getUserID() + "=" + user.getPower() + "~";

			}
			Iterator<ActiveUser> iterator = userList.iterator();
			while (iterator.hasNext()) {
				ActiveUser user = iterator.next();
				int dir = userData.FindDirection(user.getUserID());
				String str = result + "-" + roomNO + "-" + user.getUserID()
						+ "-" + power + " 0 " + dir;
				user.sendMessage(Service.Game + " " + str + " " + Mode.Coop);
				user.sendMessage(nameplate + " " + Service.GameStart.toString());
			}
		} else if (message.startsWith("Stage")) {
			String[] str = message.split("@");

			System.out.println("---------------------------"+stage);

		}

		team.distibuteMessage(message, roomNO, id);
	}

	private void setState(RoomState state, ArrayList<ActiveUser> coopUserList) {
		roomState = state;

		informRoomStateChange(coopUserList);
	}

	private void checkIn(ArrayList<ActiveUser> coopUserList) {
		if (userList.size() == MAX_PLAYER) {
			setState(RoomState.FULL, coopUserList);
		}
	}

	private void checkQuit(ArrayList<ActiveUser> coopUserList) {
		if (userList.size() == MAX_PLAYER - 1) {
			setState(RoomState.AVAILABLE, coopUserList);
		}
	}

	public String getUserState(ActiveUser player) {
		String userState = "";

		for (ActiveUser user : userList) {
			if (user.getUserID().equals(player.getUserID())) {
				userState = user.getUserState().toString();
				break;
			}
		}

		return userState;
	}

	private void sendToPlayer(String message) {
		Iterator<ActiveUser> iterator = userList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	public void informRoomStateChange(ArrayList<ActiveUser> coopUserList) {
		service = Service.RoomStateChange.toString();

		String nameplate = Nameplate.GameHall.toString();
		String message = nameplate + " " + service + " " + RoomNO + " "
				+ roomState;
		Iterator<ActiveUser> iterator = coopUserList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	private void informUserStateChangeInRoom(String userID, UserState state) {
		String nameplate = Nameplate.CoopRoom.toString();
		String service = Service.UserStateChange.toString();

		String message = nameplate + " " + service + " " + userID + " "
				+ state.toString();

		Iterator<ActiveUser> iterator = userList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	private void informUserStateChange(String userID, UserState state,
			ArrayList<ActiveUser> coopUserList) {
		String nameplate = Nameplate.GameHall.toString();
		String service = Service.UserStateChange.toString();

		String message = nameplate + " " + service + " " + userID + " "
				+ state.toString();

		Iterator<ActiveUser> iterator = coopUserList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	@Override
	public void sendGameMsg(String message) {
		Iterator<ActiveUser> iterator = userList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(Service.Game + " " + message);
		}

	}

	@Override
	public void sendGameMsg(String message, int teamId) {
		if (teamId == 0) {
			Iterator<ActiveUser> iterator = userList.iterator();
			while (iterator.hasNext()) {
				ActiveUser user = iterator.next();
				user.sendMessage(Service.Game + " " + message + " " + teamId);
			}
		}

	}

	@Override
	public void superStart(int teamId) {
	}

	@Override
	public void superEnd(int teamId) {
	}

	@Override
	public ArrayList<ActiveUser> getUserList(int teamId) {
		return userList;
	}

	@Override
	public void endGame(int point, int combo, int teamId) {
		coopGameData.InsertCooperation(userList, point, combo);

		Reset();
	}

	private int[] getRewardAndDragonBall(int enemy) {
		int rewardList[] = { 0, 0, 0, 100, 200, 500, 1000, 2000 };
		int dragonBall[] = { 2, 5, 7, 6, 3, 4, 1, 8 };
		System.out.println("---------------------"+enemy);

		int reward = rewardList[enemy];
		int DB = dragonBall[enemy];
		System.out.println("DB: " + DB);
		int result[] = new int[3];
		// 几星珠，8=所有珠子一个
		result[0] = DB;
		// 随机获取何种奖励，1,2,3
		result[1] = -1;
		// 最后一关是否解锁，0不解锁，1解锁
		result[2] = 0;

		for (ActiveUser user : userList) {
			userData.UpdateMoney(user.getUserID(), reward);

			if (DB == 8) {
				for (int i = 1; i <= 7; i++) {
					userData.UpdateDragonBall(user.getUserID(), i, 1);
				}
			} else {
				userData.UpdateDragonBall(user.getUserID(), DB, 1);
			}

			boolean canUnLock = userData.canUnLock(user.getUserID());
			if (canUnLock) {
				for (int i = 1; i <= 7; i++) {
					userData.UpdateDragonBall(user.getUserID(), i, -1);
				}
				String state = userData.FindStageState(user.getUserID(), 9);
				System.out.println("State: " + state);
				if (state.equals("lock")) {
					userData.UpdateStage(user.getUserID(), 9, "notpass");
					result[2] = 1;
				}
				int rewardForBall = getReward(user.getUserID());
				result[1] = rewardForBall;
				System.out.println("奖励为：" + result[1]);
			}
		}
		return result;
	}

	private int getReward(String userID) {
		int reward = -1;
		reward = (int) (Math.random() * 3);

		switch (reward) {
		case 0: {
			for (ActiveUser user : userList) {
				if (userID.equals(user.getUserID())) {
					System.out.println(user.getPower());
					int power = (int) (user.getPower() * 0.35);
					System.out.println("Power1: " + power);
					user.setPower(power + user.getPower());
					userData.UpdatePower(userID, power);
					System.out.println(user.getPower());
					break;
				}
			}
			break;
		}
		case 1: {
			for (ActiveUser user : userList) {
				if (userID.equals(user.getUserID())) {
					int money = user.getMoney() + 5000;
					user.setMoney(money);
					userData.UpdateMoney(userID, 5000);
					break;
				}
			}
			break;
		}
		case 2: {
			for (ActiveUser user : userList) {
				if (userID.equals(user.getUserID())) {
					System.out.println(user.getPower());
					int power = (int) (user.getPower() * 0.3);
					user.setPower(power + user.getPower());
					System.out.println(user.getPower());
					userData.UpdatePower(userID, power);
					userData.UpdateProp1(userID, 1);
					userData.UpdateProp2(userID, 1);
					userData.UpdateProp3(userID, 1);
					break;
				}
			}
			break;
		}
		}

		return reward;
	}

	private int checkUpgrade(String userID, int preExp, int addExp) {
		int postExp = preExp + addExp;
		System.out.println("preExp: "+preExp);
		System.out.println("postExp: "+postExp);
		int rank = 0;
		int expList[] = { 30, 80, 180, 400, 700, 1000, 1300, 1600, 2000 };
		int rewardList[] = { 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000,
				9999 };
		int powerList[] = { 1, 2, 4, 7, 15, 24, 35, 48, 63 };

		for (int i = 0; i < expList.length; i++) {
			System.out.println(expList[i]);
			try {
				if (preExp < expList[i] && postExp >= expList[i]) {
					System.out.println("This is" + expList[i]);
					rank = i + 1;
					int reward = rewardList[i];
					int power = powerList[i];
					for (ActiveUser user : userList) {
						if (user.getUserID().equals(userID)) {
							user.setPower(power + user.getPower());
							user.setMoney(reward + user.getMoney());
							break;
						}
					}
					userData.UpdateMoney(userID, reward);
					userData.UpdatePower(userID, power);
					userData.UpdateProp1(userID, 1);
					userData.UpdateProp2(userID, 1);
					userData.UpdateProp3(userID, 1);
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		return rank;
	}

	public void Reset() {
		for (ActiveUser user : userList) {
			if (user.getUserState() == UserState.ROOMMASTER) {

			} else {
				user.setUserState(UserState.LEISURE);
			}
		}

		prop1 = false;
		prop2 = false;
		prop3 = false;

		if (userList.size() == MAX_PLAYER) {
			roomState = RoomState.FULL;
		} else {
			roomState = RoomState.AVAILABLE;
		}

		ArrayList<ActiveUser> cooperationUserList = GameHallBL
				.getCoopUserList();
		informRoomStateChange(cooperationUserList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void sendEndGameMsg(String message, int teamId, int point) {
		// TODO Auto-generated method stub
		if (teamId == 0) {
			ArrayList<ActiveUser> userList1=(ArrayList<ActiveUser>)userList.clone();
			for (ActiveUser user : userList) {
				int preMoney = user.getMoney();
				int money = point / 100;
				int exp = point / 1000;
				int upgrade = checkUpgrade(user.getUserID(), user.getExp(), exp);
				user.setMoney(money + user.getMoney());
				user.setExp(exp + user.getExp());
				userData.UpdateMoney(user.getUserID(), money);
				userData.UpdateExp(user.getUserID(), exp);
				int powerList[] = { 1, 2, 4, 7, 15, 24, 35, 48, 63 };
				int power = 0;
				try {
					int index = upgrade - 1;
					power = powerList[index];
				} catch (ArrayIndexOutOfBoundsException e) {
					power = 0;
				}
				int result[]=new int[3];
				
				result= getRewardAndDragonBall(stage-1);
				int postMoney = user.getMoney();
				int extraMoney = postMoney - preMoney;

				service = Service.ReplyCoopGameResult.toString();
				String msg = Service.Game + " " + message + " " + teamId + " "
						+ money + " " + exp + " " + upgrade + " "
						+ user.getUserID() + " " + power + " " + result[0]
						+ " " + result[1] + " " + result[2];
				user.sendMessage(msg);
			}
		}
		
		userList.removeAll(userList);
		Reset();

	}

}
