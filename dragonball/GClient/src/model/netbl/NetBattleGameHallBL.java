package model.netbl;

import common.Nameplate;
import common.Service;

import model.netservice.NetBattleGameHallBLService;

public class NetBattleGameHallBL implements NetBattleGameHallBLService {
	SocketStart sock;
	static NetBattleGameHallBL netBattleGameHallBL = null;
	final static String NAMEPLATE = Nameplate.GameHall.toString();

	private NetBattleGameHallBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetBattleGameHallBL getGameHallController() {
		if (netBattleGameHallBL == null) {
			netBattleGameHallBL = new NetBattleGameHallBL();
		}

		return netBattleGameHallBL;
	}

	@Override
	public void Enter(String userID, int money, int exp,int power) {
		// TODO Auto-generated method stub
		String service = Service.EnterBattleGameHall.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " " + money
				+ " " + exp+" "+power;
		sock.sendMessage(message);
	}

	@Override
	public void Quit(String userID) {
		// TODO Auto-generated method stub
		String service = Service.LeaveBattleGameHall.toString();
		String message = NAMEPLATE + " " + service + " " + userID;
		sock.sendMessage(message);
	}

	@Override
	public void requestUserList() {
		// TODO Auto-generated method stub
		String service = Service.RequestBattleUserList.toString();
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void requestBattleRoomList() {
		// TODO Auto-generated method stub
		String service = Service.RequestBattleRoomList.toString();
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void enterBattleRoom(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.EnterBattleRoom.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ roomNO;
		sock.sendMessage(message);
	}
}
