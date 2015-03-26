package net.netbl;

import java.io.PrintWriter;
import java.util.ArrayList;

import po.ActiveUser;

import net.netservice.NetCoopRoomBLService;
import businesslogic.bl.GameHallBL;

import common.Nameplate;
import common.Service;

public class NetCoopRoomBL implements NetCoopRoomBLService {
	GameHallBL gameHallBL = GameHallBL.getGameHall();
	final static String NAMEPLATE = Nameplate.CoopRoom.toString();

	@Override
	public void selectService(String message, PrintWriter writer) {
		String[] result = message.split(" ");

		if (result[1].equals(Service.RequestUserList.toString())) {
			getPlayerList(result[2], writer);
		} else if (result[1].equals(Service.SetReady.toString())) {
			setReady(Integer.parseInt(result[2]), result[3]);
		} else if (result[1].equals(Service.ChatSend.toString())) {
			chatInRoom(Integer.parseInt(result[2]), message);
		} else if (result[1].equals(Service.QuitCoopRoom.toString())) {
			quitCoopRoom(result[2], Integer.parseInt(result[3]));
		} else if (result[1].equals(Service.KickPlayer.toString())) {
			kickPlayer(result[2], result[3], Integer.parseInt(result[4]));
		} else if (result[1].equals(Service.Game.toString())) {
			playGame(result[4], Integer.parseInt(result[2]), result[3]);
		} else if(result[1].equals(Service.RequestChooseProp.toString())){
			chooseProp(Integer.parseInt(result[2]),Integer.parseInt(result[3]),result[4]);
		} else if(result[1].equals(Service.RequestCoopProp.toString())){
			getPropList(Integer.parseInt(result[2]),writer);
		}
	}
	
	private void getPropList(int roomNO,PrintWriter writer){
		String info=gameHallBL.getCoopPropList(roomNO);
		String service = Service.ReplyCoopProp.toString();
		
		String message = NAMEPLATE + " " + service + " " + info;
		
		writer.println(message);
		writer.flush();
	}
	
	private void chooseProp(int propNO,int roomNO,String userID){
		gameHallBL.chooseProp(propNO, roomNO,userID);
	}

	private void kickPlayer(String roommasterID, String userID, int roomNO) {
		String service = Service.ReplyQuitCoopRoom.toString();
		String info = gameHallBL.kickPlayer(roommasterID, userID, roomNO);
		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "cooperation");

		user.sendMessage(message);
	}

	private void quitCoopRoom(String userID, int roomNO) {
		String service = Service.ReplyQuitCoopRoom.toString();

		String info = gameHallBL.quitCoopRoom(userID, roomNO);

		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "cooperation");

		user.sendMessage(message);
	}

	private void setReady(int roomNO, String userID) {
		String service = Service.ReplySetReady.toString();

		String info = gameHallBL.setReady(roomNO, userID);

		String message = NAMEPLATE + " " + service + " " + info;

		ActiveUser user = gameHallBL.getUser(userID, "cooperation");

		user.sendMessage(message);
	}

	private void chatInRoom(int roomNO, String info) {
		String[] token = info.split(" ");
		String newMessage = "";
		for (int i = 3; i < token.length; i++) {
			newMessage = newMessage + token[i] + " ";
		}
		ArrayList<ActiveUser> playerList = gameHallBL.getRoomPlayerInfo(roomNO);

		String service = Service.ChatReceive.toString();
		String message = NAMEPLATE + " " + service + " " + newMessage;

		for (ActiveUser player : playerList) {
			player.sendMessage(message);
		}
	}

	private void getPlayerList(String roomNO, PrintWriter writer) {
		ArrayList<ActiveUser> playerList = gameHallBL.getRoomPlayerInfo(Integer
				.parseInt(roomNO));

		String service = Service.ReplyUserList.toString();

		StringBuffer br = new StringBuffer();
		for (ActiveUser user : playerList) {
			br.append(userInString(user));
			br.append("/");
		}

		String info = br.toString();
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private StringBuffer userInString(ActiveUser user) {
		StringBuffer br = new StringBuffer();
		br = br.append(user.getUserID());
		br = br.append('-');
		br = br.append(user.getUserState().toString());
		br=br.append('-');
		br=br.append(user.getMoney());
		br=br.append('-');
		br=br.append(user.getExp());
		br=br.append('-');
		br=br.append(user.getPower());

		return br;
	}

	private void playGame(String message, int roomNO, String id) {
		gameHallBL.playCoopGame(message, roomNO, id);
	}
}
