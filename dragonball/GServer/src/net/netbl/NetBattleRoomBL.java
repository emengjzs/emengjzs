package net.netbl;

import java.io.PrintWriter;
import java.util.ArrayList;

import po.ActiveUser;

import businesslogic.bl.BattleRoomBL;
import businesslogic.bl.GameHallBL;

import common.Nameplate;
import common.Service;

import net.netservice.NetBattleRoomBLService;

public class NetBattleRoomBL implements NetBattleRoomBLService {
	GameHallBL gameHallBL = GameHallBL.getGameHall();
	final static String NAMEPLATE = Nameplate.BattleRoom.toString();

	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub
		String[] result = message.split(" ");

		if (result[1].equals(Service.RequestUserList.toString())) {
			getPlayerList(result[2], writer);
		} else if (result[1].equals(Service.QuitBattleRoom.toString())) {
			quitBattleRoom(result[2], Integer.parseInt(result[3]));
		} else if (result[1].equals(Service.SetReady.toString())) {
			setReady(Integer.parseInt(result[2]), result[3]);
		} else if (result[1].equals(Service.ChatSend.toString())) {
			chatInRoom(Integer.parseInt(result[2]), message);
		} else if (result[1].equals(Service.KickPlayer.toString())) {
			kickPlayer(result[2], result[3], Integer.parseInt(result[4]));
		} else if (result[1].equals(Service.ChangeTeam.toString())) {
			changeTeam(result[2], Integer.parseInt(result[3]));
		} else if (result[1].equals(Service.Game.toString())) {
			playGame(result[5], Integer.parseInt(result[2]), result[3],
					Integer.parseInt(result[4]));
		} else if(result[1].equals(Service.RequestBattleProp.toString())){
			chooseProp(Integer.parseInt(result[2]),Integer.parseInt(result[3]),Integer.parseInt(result[4]));
		}
	}
	
	private void chooseProp(int teamNO,int propNO, int roomNO){
		gameHallBL.chooseBattleProp(teamNO, propNO, roomNO);
	}

	private void changeTeam(String userID, int roomNO) {
		BattleRoomBL battleRoom = gameHallBL.getBattleRoom(roomNO);
		battleRoom.changeTeam(userID);
	}

	private void kickPlayer(String roommasterID, String userID, int roomNO) {

		String service = Service.ReplyQuitBattleRoom.toString();
		String info = gameHallBL.kickBattlePlayer(roommasterID, userID, roomNO);
		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "battle");

		user.sendMessage(message);
	}

	private void chatInRoom(int roomNO, String info) {
		String[] token = info.split(" ");
		String newMessage = "";
		for (int i = 3; i < token.length; i++) {
			newMessage = newMessage + token[i] + " ";
		}

		BattleRoomBL battleRoom = gameHallBL.getBattleRoom(roomNO);
		ArrayList<ActiveUser> playerList1 = battleRoom.getTeamOne();
		ArrayList<ActiveUser> playerList2 = battleRoom.getTeamTwo();

		String service = Service.ChatReceive.toString();
		String message = NAMEPLATE + " " + service + " " + newMessage;

		for (ActiveUser player : playerList1) {
			player.sendMessage(message);
		}

		for (ActiveUser player : playerList2) {
			player.sendMessage(message);
		}
	}

	private void setReady(int roomNO, String userID) {
		String service = Service.ReplySetReady.toString();

		String info = gameHallBL.setBattleReady(roomNO, userID);

		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "battle");

		user.sendMessage(message);
	}

	private void quitBattleRoom(String userID, int roomNO) {
		String service = Service.ReplyQuitBattleRoom.toString();

		String info = gameHallBL.quitBattleRoom(userID, roomNO);

		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "battle");

		user.sendMessage(message);
	}

	private void getPlayerList(String roomNO, PrintWriter writer) {
		ArrayList<ActiveUser> teamList1 = gameHallBL.getBattleRoomTeam(
				Integer.parseInt(roomNO), 1);
		ArrayList<ActiveUser> teamList2 = gameHallBL.getBattleRoomTeam(
				Integer.parseInt(roomNO), 2);

		String service = Service.ReplyUserList.toString();

		StringBuffer br = new StringBuffer();
		for (ActiveUser user : teamList1) {
			br.append(userInString(user, 1));
			br.append("/");
		}

		for (ActiveUser user : teamList2) {
			br.append(userInString(user, 2));
			br.append("/");
		}

		String info = br.toString();
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private StringBuffer userInString(ActiveUser user, int teamNO) {
		StringBuffer br = new StringBuffer();
		br = br.append(user.getUserID());
		br = br.append('-');
		br = br.append(user.getUserState().toString());
		br = br.append('-');
		br = br.append(teamNO);
		br = br.append('-');
		br = br.append(user.getMoney());
		br = br.append('-');
		br = br.append(user.getExp());
		br = br.append('-');
		br = br.append(user.getPower());
		return br;
	}

	private void playGame(String message, int roomNO, String id, int teamId) {
		gameHallBL.playBattleGame(message, roomNO, id, teamId);
	}

}
