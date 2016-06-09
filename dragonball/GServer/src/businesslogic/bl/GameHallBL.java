package businesslogic.bl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import common.Nameplate;
import common.ResultMessage;
import common.RoomState;
import common.Service;
import common.UserState;

import po.ActiveUser;

public class GameHallBL {
	static GameHallBL gameHallBL = null;
	static ArrayList<ActiveUser> cooperationUserList;
	static ArrayList<ActiveUser> battleUserList;
	static ArrayList<SingleBL> singleList;
	static CoopRoomBL[] coopRoomList;
	static BattleRoomBL[] battleRoomList;
	final int NUMBER_OF_ROOMS = 4;

	static String nameplate = "";
	static String service = "";

	private GameHallBL() {
		coopRoomList = new CoopRoomBL[NUMBER_OF_ROOMS];
		battleRoomList = new BattleRoomBL[NUMBER_OF_ROOMS];
		for (int i = 0; i < NUMBER_OF_ROOMS; i++) {
			coopRoomList[i] = new CoopRoomBL(i);
			battleRoomList[i] = new BattleRoomBL(i);
		}

		singleList = new ArrayList<SingleBL>();
		cooperationUserList = new ArrayList<ActiveUser>();
		battleUserList = new ArrayList<ActiveUser>();
	}

	public static GameHallBL getGameHall() {
		if (gameHallBL == null) {
			gameHallBL = new GameHallBL();
		}

		return gameHallBL;
	}

	public static ArrayList<ActiveUser> getCoopUserList() {
		return cooperationUserList;
	}

	public static ArrayList<ActiveUser> getBattleUserList() {
		return battleUserList;
	}

	public CoopRoomBL[] getCoopRoomList() {
		return coopRoomList;
	}

	public BattleRoomBL[] getBattleRoomList() {
		return battleRoomList;
	}

	public void addSingleUser(String userID,int stage, PrintWriter writer) {
		boolean found=false;
		for (SingleBL single : singleList) {
			ActiveUser user = single.getUser();
			if (user.getUserID().equals(userID)) {
				System.out.println("----------------------于GameHallBL.addSingleUser-----------------------------");
				System.out.println("！！！！！！！！！！！！单机重复加人！！！！！！！！！！！！！！！！！！！！！！！！！");
				found=true;
				break;
			}
		}
		if(!found){
			SingleBL single = new SingleBL(userID,stage, writer);
			singleList.add(single);
		}
	}

	public void removeSingleUser(String userID) {
		for (SingleBL single : singleList) {
			ActiveUser user = single.getUser();
			if (user.getUserID().equals(userID)) {
				singleList.remove(single);
				break;
			}
		}
	}
	
	public void removeCoopUser(String userID){
		for(int i=0;i<coopRoomList.length;i++){
			coopRoomList[i].remove(userID);
			coopRoomList[i].setRoomState(RoomState.AVAILABLE);
			coopRoomList[i].informRoomStateChange(cooperationUserList);
		}
	}
	
	public void removeBattleUser(String userID){
		for(int i=0;i<battleRoomList.length;i++){
			battleRoomList[i].remove(userID);
			battleRoomList[i].setState(RoomState.AVAILABLE);
			battleRoomList[i].informRoomStateChange(battleUserList);
		}
	}

	public void addUser(ActiveUser user) {
		boolean isIn = false;
		for (ActiveUser player : cooperationUserList) {
			if (player.getUserID().equals(user.getUserID())) {
				isIn = true;
				player.setExp(user.getExp());
				player.setMoney(user.getMoney());
				player.setPower(user.getPower());
				break;
			}
		}

		if (!isIn) {
			nameplate = Nameplate.GameHall.toString();
			service = Service.UserLogin.toString();

			String message = nameplate + " " + service + " " + user.getUserID()
					+ " " + user.getMoney() + " " + user.getExp()+" "+user.getPower()
					+ " has logined in!";
			sendToAll(message, cooperationUserList);
			cooperationUserList.add(user);
		}
	}

	public void addBattleUser(ActiveUser user) {
		boolean isIn = false;
		for (ActiveUser player : battleUserList) {
			if (player.getUserID().equals(user.getUserID())) {
				isIn = true;
				player.setExp(user.getExp());
				player.setMoney(user.getMoney());
				player.setPower(user.getPower());
				break;
			}
		}
		if (!isIn) {
			nameplate = Nameplate.BattleGameHall.toString();
			service = Service.UserLogin.toString();

			String message = nameplate + " " + service + " " + user.getUserID()
					+ " " + user.getMoney() + " " + user.getExp()+" "+user.getPower()
					+ " has logined in !";
			sendToAll(message, battleUserList);
			battleUserList.add(user);
		}
	}

	public void deleteBattleUser(ActiveUser user) {
		nameplate = Nameplate.BattleGameHall.toString();
		service = Service.UserQuit.toString();

		String message = nameplate + " " + service + " " + user.getUserID()
				+ " has quitted!";
		sendToAll(message, battleUserList);

		for (ActiveUser player : battleUserList) {
			if (player.getUserID().equals(user.getUserID())) {
				battleUserList.remove(player);
				break;
			}
		}
	}

	public String enterCoopRoom(String userID, int roomNO) {
		boolean canIn = false;
		String message = "";

		ActiveUser user = getUser(userID, cooperationUserList);

		if (roomNO < NUMBER_OF_ROOMS) {
			canIn = coopRoomList[roomNO].canIn(user, cooperationUserList);
		}

		if (canIn) {
			message = ResultMessage.ENTER_COOPROOM_SUCCEDDFULLY.toString()
					+ " " + coopRoomList[roomNO].getUserState(user) + " "
					+ roomNO;

			for (ActiveUser player : cooperationUserList) {
				if (player.getUserID().equals(user.getUserID())) {
					UserState state = Enum.valueOf(UserState.class,
							coopRoomList[roomNO].getUserState(user));
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.ENTER_COOPROOM_FAILED.toString();
		}

		return message;
	}

	public String enterBattleRoom(String userID, int roomNO) {
		boolean canIn = false;
		String message = "";

		ActiveUser user = getUser(userID, battleUserList);

		if (roomNO < NUMBER_OF_ROOMS) {
			canIn = battleRoomList[roomNO].canIn(user, battleUserList);
		}

		if (canIn) {
			int teamNO = battleRoomList[roomNO].getPlayerTeam(user);
			message = ResultMessage.ENTER_BATTLEROOM_SUCCEDDFULLY.toString()
					+ " " + battleRoomList[roomNO].getUserState(user) + " "
					+ roomNO + " " + teamNO;

			for (ActiveUser player : battleUserList) {
				if (player.getUserID().equals(user.getUserID())) {
					UserState state = Enum.valueOf(UserState.class,
							battleRoomList[roomNO].getUserState(user));
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.ENTER_BATTLEROOM_FAILED.toString();
		}

		return message;
	}

	public String quitCoopRoom(String userID, int roomNO) {
		boolean canQuit = false;
		String message = "";

		ActiveUser user = getUser(userID, cooperationUserList);
		// System.out.println(user.getUserID() + " " + user.getUserState());
		canQuit = coopRoomList[roomNO].canQuit(user, cooperationUserList);
		if (canQuit) {
			message = ResultMessage.QUIT_COOPROOM_SUCCESSFULLY.toString() + " "
					+ UserState.LEISURE + " " + roomNO + " " + userID;

			for (ActiveUser player : cooperationUserList) {
				if (player.getUserID().equals(user.getUserID())) {
					UserState state = UserState.LEISURE;
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.QUIT_COOPROOM_FAILED.toString();
		}

		return message;
	}

	public String quitBattleRoom(String userID, int roomNO) {
		boolean canQuit = false;
		String message = "";

		ActiveUser user = getUser(userID, battleUserList);
		// System.out.println(user.getUserID() + " " + user.getUserState());
		canQuit = battleRoomList[roomNO].canQuit(user, battleUserList);
		if (canQuit) {
			message = ResultMessage.QUIT_BATTLEROOM_SUCCESSFULLY.toString()
					+ " " + UserState.LEISURE + " " + roomNO + " " + userID;

			for (ActiveUser player : battleUserList) {
				if (player.getUserID().equals(user.getUserID())) {
					UserState state = UserState.LEISURE;
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.QUIT_BATTLEROOM_FAILED.toString();
		}

		return message;
	}

	public String setReady(int roomNO, String userID) {
		boolean canSet = false;

		String message = "";

		ActiveUser user = getUser(userID, cooperationUserList);
		canSet = coopRoomList[roomNO].setReady(userID, cooperationUserList);
		if (canSet) {
			message = ResultMessage.SET_READY_SUCCESSFULLY.toString();

			for (ActiveUser player : cooperationUserList) {
				if (player.getUserID().equals(userID)) {
					UserState state = Enum.valueOf(UserState.class,
							coopRoomList[roomNO].getUserState(user));
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.SET_READY_FALIED.toString();
		}

		return message;
	}

	public String setBattleReady(int roomNO, String userID) {
		boolean canSet = false;

		String message = "";

		ActiveUser user = getUser(userID, battleUserList);
		canSet = battleRoomList[roomNO].setReady(userID, battleUserList);
		if (canSet) {
			message = ResultMessage.SET_READY_SUCCESSFULLY.toString();

			for (ActiveUser player : battleUserList) {
				if (player.getUserID().equals(userID)) {
					UserState state = Enum.valueOf(UserState.class,
							battleRoomList[roomNO].getUserState(user));
					player.setUserState(state);
				}
			}
		} else {
			message = ResultMessage.SET_READY_FALIED.toString();
		}

		return message;
	}

	public String kickPlayer(String roommasterID, String userID, int roomNO) {
		ActiveUser roommaster = getUser(roommasterID, cooperationUserList);
		ActiveUser player = getUser(userID, cooperationUserList);
		String message = "";

		if (player != null && roommaster != null) {
			if (roommaster.getUserState() == UserState.ROOMMASTER
					&& player.getUserState() != UserState.ROOMMASTER) {
				message = quitCoopRoom(userID, roomNO);
			}
		}

		return message;
	}

	public void chooseProp(int propNO, int roomNO,String userID) {
		coopRoomList[roomNO].chooseProp(propNO,userID);
	}
	
	public String getCoopPropList(int roomNO){
		String message=coopRoomList[roomNO].getPropList();
		
		return message;
	}

	public void chooseBattleProp(int teamNO, int propNO, int roomNO) {
		battleRoomList[roomNO].chooseProp(teamNO, propNO);
	}

	public String kickBattlePlayer(String roommasterID, String userID,
			int roomNO) {
		ActiveUser roommaster = getUser(roommasterID, battleUserList);
		ActiveUser player = getUser(userID, battleUserList);
		String message = "";

		if (player != null && roommaster != null) {
			if (roommaster.getUserState() == UserState.ROOMMASTER
					&& player.getUserState() != UserState.ROOMMASTER) {
				message = quitBattleRoom(userID, roomNO);
			}
		}

		return message;
	}

	public ActiveUser getUser(String userID, String type) {
		ActiveUser user = null;
		if (type.equals("cooperation")) {
			user = getUser(userID, cooperationUserList);
		} else if (type.equals("battle")) {
			user = getUser(userID, battleUserList);
		}

		return user;
	}

	private ActiveUser getUser(String userID, ArrayList<ActiveUser> userList) {
		ActiveUser player = null;
		for (ActiveUser user : userList) {
			if (userID.equals(user.getUserID())) {
				player = user;
				break;
			}
		}

		return player;
	}

	public void deleteUser(ActiveUser user) {
		nameplate = Nameplate.GameHall.toString();
		service = Service.UserQuit.toString();

		String message = nameplate + " " + service + " " + user.getUserID()
				+ " has quitted!";
		sendToAll(message, cooperationUserList);

		for (ActiveUser player : cooperationUserList) {
			if (player.getUserID().equals(user.getUserID())) {
				cooperationUserList.remove(player);
				break;
			}
		}
	}

	public void sendToAll(String message, ArrayList<ActiveUser> userList) {
		Iterator<ActiveUser> iterator = userList.iterator();
		while (iterator.hasNext()) {
			ActiveUser user = iterator.next();
			user.sendMessage(message);
		}
	}

	public ArrayList<ActiveUser> getRoomPlayerInfo(int roomNO) {
		return coopRoomList[roomNO].getPlayerList();
	}

	public BattleRoomBL getBattleRoom(int roomNO) {
		return battleRoomList[roomNO];
	}

	public ArrayList<ActiveUser> getBattleRoomTeam(int roomNO, int teamNO) {
		ArrayList<ActiveUser> teamList = new ArrayList<ActiveUser>();
		if (teamNO == 1) {
			teamList = battleRoomList[roomNO].getTeamOne();
		} else if (teamNO == 2) {
			teamList = battleRoomList[roomNO].getTeamTwo();
		}
		return teamList;
	}

	public void playCoopGame(String message, int roomNO, String id) {
		coopRoomList[roomNO].playGame(message, roomNO, id);
	}

	public void playBattleGame(String message, int roomNO, String id, int teamId) {
		battleRoomList[roomNO].playGame(message, roomNO, id, teamId);
	}

	public void playSingleGame(String message, String id) {
		Iterator<SingleBL> i = singleList.iterator();
		while (i.hasNext()) {
			SingleBL single = i.next();
			if (single.getUser().getUserID().equals(id)) {
				single.playGame(message, id);
				
			}
		}
	}
	
	public String getSingleProp(String userID){
		String message="";
		for(SingleBL single:singleList){
			ActiveUser user=single.getUser();
			if(user.getUserID().equals(userID)){
				message=single.getPropList(userID);
			}
		}
		return message;
	}
	
	public void buySingleProp(String userID,int propNO){
		for(SingleBL single:singleList){
			ActiveUser user=single.getUser();
			if(user.getUserID().equals(userID)){
				single.buyProp(userID,propNO);
			}
		}
	}

	public void userQuitNormal(String userID){
		removeSingleUser(userID);
/*		removeCoopUser(userID);
		removeBattleUser(userID);
		
		for(ActiveUser user:cooperationUserList){
			if(user.getUserID().equals(userID)){
				cooperationUserList.remove(user);
				break;
			}
		}
		for(ActiveUser user:battleUserList){
			if(user.getUserID().equals(userID)){
				battleUserList.remove(user);
				break;
			}
		}*/
	}
	
	public void userQuitAbnormal(String userID) {
		for (int i = 0; i < coopRoomList.length; i++) {
			quitCoopRoom(userID, i);
		}

		for (int i = 0; i < battleRoomList.length; i++) {
			quitBattleRoom(userID, i);
		}

		ActiveUser user = getUser(userID, cooperationUserList);
		if (user != null) {
			deleteUser(user);
		}

		ActiveUser user1 = getUser(userID, battleUserList);
		if (user1 != null) {
			deleteUser(user1);
		}

		removeSingleUser(userID);
	}

}
