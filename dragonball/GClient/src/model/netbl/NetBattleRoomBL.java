package model.netbl;

import common.Nameplate;
import common.Service;

import model.netservice.NetBattleRoomBLService;

public class NetBattleRoomBL implements NetBattleRoomBLService {
	SocketStart sock;
	static NetBattleRoomBL netBattleRoomBL = null;
	final static String NAMEPLATE = Nameplate.BattleRoom.toString();

	private NetBattleRoomBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetBattleRoomBL getBattleRoomController() {
		if (netBattleRoomBL == null) {
			netBattleRoomBL = new NetBattleRoomBL();
		}

		return netBattleRoomBL;
	}

	@Override
	public void requestUserList(int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.RequestUserList.toString() + " " + roomNO;

		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void quitBattleRoom(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.QuitBattleRoom.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ roomNO;
		sock.sendMessage(message);
	}

	@Override
	public void setReady(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.SetReady.toString();

		String message = NAMEPLATE + " " + service + " " + roomNO + " "
				+ userID;
		sock.sendMessage(message);
	}

	@Override
	public void chatInRoom(String info, String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.ChatSend.toString();

		String message = NAMEPLATE + " " + service + " " + roomNO + " "
				+ userID + ": " + info;
		sock.sendMessage(message);
	}

	@Override
	public void kickPlayer(String roommasterID, String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.KickPlayer.toString();
		String message = NAMEPLATE + " " + service + " " + roommasterID + " "
				+ userID + " " + roomNO;
		sock.sendMessage(message);
	}

	@Override
	public void changeTeam(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.ChangeTeam.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ roomNO;
		sock.sendMessage(message);
	}

	@Override
	public void chooseProp(int teamNO, int propNO, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.RequestBattleProp.toString();
		String message = NAMEPLATE + " " + service + " " + teamNO + " "
				+ propNO + " " + roomNO;
		sock.sendMessage(message);
	}

	@Override
	public void initGame(String userID, int roomNO) {
		String message = NAMEPLATE + " " + Service.Game + " " + roomNO + " "
				+ userID + " 0 Start@0";
		sock.sendMessage(message);
	}

}
