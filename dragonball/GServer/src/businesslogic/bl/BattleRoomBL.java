package businesslogic.bl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import po.ActiveUser;
import common.Mode;
import common.Nameplate;
import common.ResultMessage;
import common.RoomState;
import common.Service;
import common.UserState;
import data.dataimpl.BattleGameDataServiceImpl;
import data.dataimpl.UserDataServiceImpl;
import data.dataservice.BattleGameDataService;
import data.dataservice.UserDataService;

public class BattleRoomBL extends RoomBL {
	int RoomNO;
	RoomState roomState;
	ArrayList<ActiveUser> teamList1;
	ArrayList<ActiveUser> teamList2;
	final int MAX_TEAM_PLAYER = 4;
	final int MAX_PLAYER = MAX_TEAM_PLAYER * 2;
	Team[] team = new Team[2];
	BattleGameDataService battleGameData = new BattleGameDataServiceImpl();
	UserDataService userData = new UserDataServiceImpl();
	@SuppressWarnings("rawtypes")
	HashMap waitPoint = new HashMap();// 一方率先结束游戏后，分数信息缓存
	boolean oneProp1 = false;
	boolean oneProp2 = false;
	boolean oneProp3 = false;

	boolean twoProp1 = false;
	boolean twoProp2 = false;
	boolean twoProp3 = false;

	static String nameplate = Nameplate.BattleRoom.toString();
	static String service = "";

	public BattleRoomBL(int RoomNO) {
		this.RoomNO = RoomNO;
		roomState = RoomState.AVAILABLE;
		teamList1 = new ArrayList<ActiveUser>();
		teamList2 = new ArrayList<ActiveUser>();
		team[0] = new Team(this);
		team[1] = new Team(this);
	}

	public void setState(RoomState state) {
		this.roomState = state;
	}

	public RoomState getState() {
		return roomState;
	}

	public int getRoomNO() {
		return RoomNO;
	}

	public void remove(String userID) {
		for (ActiveUser player : teamList1) {
			if (player.getUserID().equals(userID)) {
				teamList1.remove(player);
				break;
			}
		}

		for (ActiveUser player : teamList2) {
			if (player.getUserID().equals(userID)) {
				teamList2.remove(player);
				break;
			}
		}
	}

	public boolean setReady(String userID, ArrayList<ActiveUser> battleUserList) {
		boolean canSet = false;

		for (ActiveUser user : teamList1) {
			if (user.getUserID().equals(userID)) {
				if (user.getUserState() == UserState.LEISURE) {
					user.setUserState(UserState.READY);

					service = Service.UserStateChange.toString();
					String message = nameplate + " " + service + " " + userID
							+ " " + UserState.READY.toString();
					sendToPlayer(message);

					informUserStateChangeInRoom(userID, UserState.READY);
					informUserStateChange(userID, UserState.READY,
							battleUserList);
					canSet = true;
				}
				break;
			}
		}

		for (ActiveUser user : teamList2) {
			if (user.getUserID().equals(userID)) {
				if (user.getUserState() == UserState.LEISURE) {
					user.setUserState(UserState.READY);

					service = Service.UserStateChange.toString();
					String message = nameplate + " " + service + " " + userID
							+ " " + UserState.READY.toString();
					sendToPlayer(message);

					informUserStateChangeInRoom(userID, UserState.READY);
					informUserStateChange(userID, UserState.READY,
							battleUserList);
					canSet = true;
				}
				break;
			}
		}

		return canSet;
	}

	public synchronized boolean canIn(ActiveUser user, ArrayList<ActiveUser> battleUserList) {
		boolean canIn = false;

		if (teamList1.size() < MAX_TEAM_PLAYER
				|| teamList2.size() < MAX_TEAM_PLAYER
				&& roomState == RoomState.AVAILABLE
				&& (!(isIn(user.getUserID(), teamList1) || isIn(
						user.getUserID(), teamList2)))) {
			canIn = true;
			userData.UpdateMoney(user.getUserID(), -300);
			addPlayer(user, battleUserList);
			checkIn(battleUserList);
		}

		return canIn;
	}

	public synchronized boolean canQuit(ActiveUser user, ArrayList<ActiveUser> battleUserList) {
		boolean canQuit = false;
		if (user != null) {

			if (isIn(user.getUserID(), teamList1)
					|| isIn(user.getUserID(), teamList2)
					&& user.getUserState() != UserState.GAMING) {
				canQuit = true;
				removePlayer(user, battleUserList);
				checkQuit(battleUserList);
			}
		}

		return canQuit;
	}

	private void deletePlayer(ActiveUser user) {
		boolean deleted = false;
		for (ActiveUser player : teamList1) {
			if (player.getUserID().equals(user.getUserID())) {
				teamList1.remove(player);
				deleted = true;
				break;
			}
		}

		if (!deleted) {
			for (ActiveUser player : teamList2) {
				if (player.getUserID().equals(user.getUserID())) {
					teamList2.remove(player);
					deleted = true;
					break;
				}
			}
		}
	}

	public void chooseProp(int teamNO, int propNO) {
		if (teamNO == 1) {
			if (propNO == 0) {
				oneProp1 = true;
			} else if (propNO == 1) {
				oneProp2 = true;
			} else if (propNO == 2) {
				oneProp3 = true;
			}
			service = Service.ReplyBattleProp.toString();

			String message = nameplate + " " + service + " " + propNO;
			sendToTeam1(message);
		} else if (teamNO == 2) {
			if (propNO == 0) {
				oneProp1 = true;
			} else if (propNO == 1) {
				oneProp2 = true;
			} else if (propNO == 2) {
				oneProp3 = true;
			}
			service = Service.ReplyBattleProp.toString();

			String message = nameplate + " " + service + " " + propNO;
			sendToTeam2(message);
		}
	}

	private void setNextRoommaster(ActiveUser user,
			ArrayList<ActiveUser> battleUserList) {
		if ((teamList1.size() + teamList2.size()) > 0
				&& user.getUserState() == UserState.ROOMMASTER) {
			if (teamList1.size() > 0) {
				teamList1.get(0).setUserState(UserState.ROOMMASTER);
				informUserStateChange(teamList1.get(0).getUserID(), teamList1
						.get(0).getUserState(), battleUserList);
				informUserStateChangeInRoom(teamList1.get(0).getUserID(),
						teamList1.get(0).getUserState());
			} else if (teamList2.size() > 0) {
				teamList2.get(0).setUserState(UserState.ROOMMASTER);
				informUserStateChange(teamList2.get(0).getUserID(), teamList2
						.get(0).getUserState(), battleUserList);
				informUserStateChangeInRoom(teamList2.get(0).getUserID(),
						teamList2.get(0).getUserState());
			}
		}
	}

	public void removePlayer(ActiveUser user,
			ArrayList<ActiveUser> battleUserList) {
		deletePlayer(user);

		setNextRoommaster(user, battleUserList);

		service = Service.ReplyQuitBattleRoom.toString();

		String message = nameplate + " " + service + " " + user.getUserID()
				+ " has left  room!";
		sendToPlayer(message);

		user.setUserState(UserState.LEISURE);
		informUserStateChange(user.getUserID(), user.getUserState(),
				battleUserList);

		if (teamList1.size() == 0) {
			oneProp1 = false;
			oneProp2 = false;
			oneProp3 = false;
		} else if (teamList2.size() == 0) {
			twoProp1 = false;
			twoProp2 = false;
			twoProp3 = false;
		}

	}

	private void checkQuit(ArrayList<ActiveUser> battleUserList) {
		if ((teamList1.size() + teamList2.size()) == MAX_PLAYER - 1) {
			setState(RoomState.AVAILABLE, battleUserList);
		}
	}

	private boolean isIn(String userID, ArrayList<ActiveUser> userList) {
		boolean isIn = false;

		for (ActiveUser player : userList) {
			if (player.getUserID().equals(userID)) {
				isIn = true;
				break;
			}
		}

		return isIn;
	}

	public void addPlayer(ActiveUser user, ArrayList<ActiveUser> battleUserList) {
		service = Service.ReplyEnterBattleRoom.toString();
		int teamNO = 0;

		if (teamList1.size() <= teamList2.size()) {
			teamNO = 1;
		} else {
			teamNO = 2;
		}
		System.out.println(user.getUserID());
		String message = nameplate + " " + service + " " + user.getUserID()
				+ " " + user.getMoney() + " " + user.getExp() + " " + teamNO
				+ " " + user.getPower() + " has entered in room!";
		sendToPlayer(message);

		String result = nameplate + " "
				+ Service.ReplyBattlePropList.toString();
		String propMessage = "";
		if (teamNO == 1) {
			if (oneProp1 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (oneProp2 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (oneProp3 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}
		} else if (teamNO == 2) {
			if (twoProp1 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (twoProp2 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (twoProp3 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}
		}
		result = result + " " + propMessage;
		user.sendMessage(result);

		if ((teamList1.size() + teamList2.size()) == 0) {
			user.setUserState(UserState.ROOMMASTER);
			informUserStateChange(user.getUserID(), user.getUserState(),
					battleUserList);
		}else{
			user.setUserState(UserState.LEISURE);
			informUserStateChange(user.getUserID(), user.getUserState(),
					battleUserList);
		}

		if (teamNO == 1) {
			teamList1.add(user);
		} else if (teamNO == 2) {
			teamList2.add(user);
		}
	}
	
	private String getPropString(int teamNO){
		String propMessage = "";
		if (teamNO == 1) {
			if (oneProp1 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (oneProp2 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (oneProp3 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}
		} else if (teamNO == 2) {
			if (twoProp1 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (twoProp2 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}

			if (twoProp3 == true) {
				propMessage = propMessage + 1 + "-";
			} else {
				propMessage = propMessage + 0 + "-";
			}
		}
		return propMessage;
	}

	public void changeTeam(String userID) {
		boolean canChange = false;
		String result = "";

		if (isIn(userID, teamList1)) {
			canChange = changeUser(userID, teamList1, teamList2);
			if(canChange){
				String propString = nameplate + " "
						+ Service.ReplyChangePropList.toString()+" "+getPropString(2);
				for(ActiveUser user:teamList2){
					if(user.getUserID().equals(userID)){
						user.sendMessage(propString);
					}
				}
			}
		} else if (isIn(userID, teamList2)) {
			canChange = changeUser(userID, teamList2, teamList1);
			if(canChange){
				String propString = nameplate + " "
						+ Service.ReplyChangePropList.toString()+" "+getPropString(1);
				for(ActiveUser user:teamList1){
					if(user.getUserID().equals(userID)){
						user.sendMessage(propString);
					}
				}
			}
		}

		if (canChange) {
			result = ResultMessage.CHANGE_TEAM_SUCCESSFULLY.toString();
		} else {
			result = ResultMessage.CHANGE_TEAM_FAILED.toString();
		}

		service = Service.ReplyChangeTeam.toString();
		String message = nameplate + " " + service + " " + result + " "
				+ userID;

		sendToPlayer(message);
		// 客户端处理信息//一队不能为空
	}

	private boolean changeUser(String userID, ArrayList<ActiveUser> userList1,
			ArrayList<ActiveUser> userList2) {
		boolean canChange = false;
		if (userList2.size() < MAX_TEAM_PLAYER && userList1.size() > 1) {
			for (ActiveUser user : userList1) {
				if (user.getUserID().equals(userID)
						&& user.getUserState() == UserState.LEISURE) {
					userList1.remove(user);
					userList2.add(user);
					canChange = true;
					break;
				}
			}
		}

		return canChange;

	}

	private void checkIn(ArrayList<ActiveUser> battleUserList) {
		if ((teamList1.size() == MAX_TEAM_PLAYER)
				&& (teamList2.size() == MAX_TEAM_PLAYER)) {
			setState(RoomState.FULL, battleUserList);
		}
	}

	private void setState(RoomState state, ArrayList<ActiveUser> battleUserList) {
		roomState = state;

		informRoomStateChange(battleUserList);
	}

	public void informRoomStateChange(ArrayList<ActiveUser> battleUserList) {
		service = Service.RoomStateChange.toString();

		String nameplate = Nameplate.BattleGameHall.toString();
		String message = nameplate + " " + service + " " + RoomNO + " "
				+ roomState;
		Iterator<ActiveUser> iterator = battleUserList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	public String getUserState(ActiveUser player) {
		String userState = "";
		boolean isInTeam1 = false;

		for (ActiveUser user : teamList1) {
			if (user.getUserID().equals(player.getUserID())) {
				userState = user.getUserState().toString();
				isInTeam1 = true;
				break;
			}
		}

		if (!isInTeam1) {

			for (ActiveUser user : teamList2) {
				if (user.getUserID().equals(player.getUserID())) {
					userState = user.getUserState().toString();
					break;
				}
			}
		}

		return userState;
	}

	private void sendToTeam1(String message) {
		Iterator<ActiveUser> iterator = teamList1.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	private void sendToTeam2(String message) {
		Iterator<ActiveUser> iterator2 = teamList2.iterator();
		while (iterator2.hasNext()) {
			ActiveUser user = iterator2.next();
			user.sendMessage(message);
		}
	}

	private void sendToPlayer(String message) {
		Iterator<ActiveUser> iterator = teamList1.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}

		Iterator<ActiveUser> iterator2 = teamList2.iterator();
		while (iterator2.hasNext()) {
			ActiveUser user = iterator2.next();
			user.sendMessage(message);
		}
	}

	private void informUserStateChange(String userID, UserState state,
			ArrayList<ActiveUser> battleUserList) {
		String nameplate = Nameplate.BattleGameHall.toString();
		String service = Service.UserStateChange.toString();

		String message = nameplate + " " + service + " " + userID + " "
				+ state.toString();

		Iterator<ActiveUser> iterator = battleUserList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	private void informUserStateChangeInRoom(String userID, UserState state) {
		String nameplate = Nameplate.BattleRoom.toString();
		String service = Service.UserStateChange.toString();

		String message = nameplate + " " + service + " " + userID + " "
				+ state.toString();

		sendToPlayer(message);
	}

	public int getPlayerTeam(ActiveUser user) {
		int teamNO = 0;

		for (ActiveUser player : teamList1) {
			if (player.getUserID().equals(user.getUserID())) {
				teamNO = 1;
				break;
			}
		}

		for (ActiveUser player : teamList2) {
			if (player.getUserID().equals(user.getUserID())) {
				teamNO = 2;
				break;
			}
		}

		return teamNO;
	}

	public void playGame(String message, int roomNO, String id, int teamId) {
		if (message.startsWith("Start")) {
			roomState=RoomState.GAMING;
			ArrayList<ActiveUser> gameHallUserList=GameHallBL.getBattleUserList();
			informRoomStateChange(gameHallUserList);
			String[] result = new String[2];
			String[] temp = new String[2];
			temp[0] = message;
			temp[1] = message;
			if (oneProp1) {
				temp[0] += "c";
			}
			if (oneProp2) {
				temp[0] += "d";
			}
			if (oneProp3) {
				temp[0] += "e";
			}
			if (twoProp1) {
				temp[1] += "c";
			}
			if (twoProp2) {
				temp[1] += "d";
			}
			if (twoProp3) {
				temp[1] += "e";
			}

			for (int i = 0; i < 2; i++) {
				result[i] = team[i].startGame(temp[i], i);
			}
			Iterator<ActiveUser> iterator1 = teamList1.iterator();
			while (iterator1.hasNext()) {
				ActiveUser user = iterator1.next();
				String[] finalresult = result[1].split(" ");
				int dir = userData.FindDirection(user.getUserID());
				String str = result[0] + "-" + roomNO + "-" + user.getUserID()
						+ "@" + finalresult[1] + "-" + roomNO + "-"
						+ user.getUserID() + " 0 " + dir;

				user.sendMessage(Service.Game + " " + str + " " + Mode.Battle);
				user.sendMessage(nameplate + " " + Service.GameStart.toString());
			}
			Iterator<ActiveUser> iterator2 = teamList2.iterator();
			while (iterator2.hasNext()) {
				ActiveUser user = iterator2.next();
				String[] finalresult = result[0].split(" ");
				int dir = userData.FindDirection(user.getUserID());
				String str = result[1] + "-" + roomNO + "-" + user.getUserID()
						+ "@" + finalresult[1] + "-" + roomNO + "-"
						+ user.getUserID() + " 1 " + dir;
				user.sendMessage(Service.Game + " " + str + " " + Mode.Battle);
				user.sendMessage(nameplate + " " + Service.GameStart.toString());
				// user.sendMessage(nameplate);
			}
		}
		team[teamId].distibuteMessage(message, roomNO, id);
	}

	public ArrayList<ActiveUser> getTeamOne() {
		return teamList1;
	}

	public ArrayList<ActiveUser> getTeamTwo() {
		return teamList2;
	}

	@Override
	public void sendGameMsg(String message) {
		ArrayList<ActiveUser> teamList = new ArrayList<ActiveUser>(teamList1);
		teamList.addAll(teamList2);
		Iterator<ActiveUser> iterator = teamList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(Service.Game + " " + message);
		}

	}

	@Override
	public void sendGameMsg(String message, int teamId) {
		Iterator<ActiveUser> iterator;
		if (teamId == 0) {
			iterator = teamList1.iterator();
		} else {
			iterator = teamList2.iterator();
		}
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(Service.Game + " " + message + " " + teamId);
		}

	}

	@Override
	public void superStart(int teamId) {
		team[(teamId + 1) % 2].oppoStartSuper();
	}

	@Override
	public void superEnd(int teamId) {
		team[(teamId + 1) % 2].oppoEndSuper();
	}

	@Override
	public ArrayList<ActiveUser> getUserList(int teamId) {
		if (teamId == 0) {
			return teamList1;
		} else {
			return teamList2;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void endGame(int point, int combo, int teamId) {
		waitPoint.put(teamId, point);
		if (teamId == 0) {
			ArrayList<ActiveUser> userList1=(ArrayList<ActiveUser>)teamList1.clone();
			for (ActiveUser user : userList1) {
				System.out.println(user.getUserID());
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

				service = Service.ReplyBattleGameResult.toString();
				String message = Service.Game + " " + service + " " + money
						+ " " + exp + " " + upgrade + " " + user.getUserID()
						+ " " + power;
				user.sendMessage(message);
			}
		} else {
			ArrayList<ActiveUser> userList2=(ArrayList<ActiveUser>)teamList2.clone();
			for (ActiveUser user : userList2) {
				System.out.println(user.getUserID());
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

				service = Service.ReplyBattleGameResult.toString();
				String message = Service.Game + " " + service + " " + money
						+ " " + exp + " " + upgrade + " " + user.getUserID()
						+ " " + power;
				user.sendMessage(message);
			}
		}

		if (waitPoint.size() == 2) {
			int point1, point2;
			point1 = (int) waitPoint.get(0);
			point2 = (int) waitPoint.get(1);
			battleGameData.InsertBattle(teamList1, teamList2, point1, point2);

			waitPoint.clear();

			Reset();
		}

	}

	private int checkUpgrade(String userID, int preExp, int addExp) {
		int postExp = preExp + addExp;
		int rank = 0;
		int expList[] = { 30, 80, 180, 400, 700, 1000, 1300, 1600, 2000 };
		int rewardList[] = { 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000,
				9999 };
		int powerList[] = { 1, 2, 4, 7, 15, 24, 35, 48, 63 };

		for (int i = 0; i < expList.length; i++) {
			System.out.println(expList[i]);
			System.out.println(preExp);
			System.out.println(postExp);
			try {
				if (preExp < expList[i] && postExp >= expList[i]) {
					System.out.println("This is" + expList[i]);
					rank = i + 1;
					int reward = rewardList[i];
					int power = powerList[i];
					for (ActiveUser user : teamList1) {
						if (user.getUserID().equals(userID)) {
							user.setPower(power + user.getPower());
							user.setMoney(reward + user.getMoney());
							break;
						}
					}
					for (ActiveUser user : teamList2) {
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
		for (ActiveUser user : teamList1) {
			if (user.getUserState() == UserState.ROOMMASTER) {

			} else {
				user.setUserState(UserState.LEISURE);
			}
		}

		for (ActiveUser user : teamList2) {
			if (user.getUserState() == UserState.ROOMMASTER) {

			} else {
				user.setUserState(UserState.LEISURE);
			}
		}

		oneProp1 = false;
		oneProp2 = false;
		oneProp3 = false;

		twoProp1 = false;
		twoProp2 = false;
		twoProp3 = false;

		if ((teamList1.size() + teamList2.size()) == MAX_PLAYER) {
			roomState = RoomState.FULL;
		} else {
			roomState = RoomState.AVAILABLE;
		}

		ArrayList<ActiveUser> battleUserList = GameHallBL.getBattleUserList();
		informRoomStateChange(battleUserList);
	}

	@Override
	public synchronized void sendEndGameMsg(String message, int teamId, int point) {
		// TODO Auto-generated method stub
		Iterator<ActiveUser> iterator;
		if (teamId == 0) {
			iterator = teamList1.iterator();
		} else {
			iterator = teamList2.iterator();
		}
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(Service.Game + " " + message + " " + teamId);
		}
		if(teamId==0){
			teamList1.removeAll(teamList1);
		}else{
			teamList2.removeAll(teamList2);
		}
		
		if(teamList1.size()+teamList2.size()==0){
			Reset();
		}
	}
}
