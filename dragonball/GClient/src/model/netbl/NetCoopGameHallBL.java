package model.netbl;

import model.netservice.NetCoopGameHallBLService;
import common.Nameplate;
import common.Service;

public class NetCoopGameHallBL implements NetCoopGameHallBLService {
	SocketStart sock;
	static NetCoopGameHallBL netCoopGameHallBL = null;
	final static String NAMEPLATE = Nameplate.GameHall.toString();

	private NetCoopGameHallBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetCoopGameHallBL getGameHallController() {
		if (netCoopGameHallBL == null) {
			netCoopGameHallBL = new NetCoopGameHallBL();
		}

		return netCoopGameHallBL;
	}

	@Override
	public void Quit(String userID) {
		// TODO Auto-generated method stub
		String service = Service.LeaveCoopGameHall.toString();
		String message = NAMEPLATE + " " + service + " " + userID;
		sock.sendMessage(message);

	}

	@Override
	public void Enter(String userID, int money, int exp,int power) {
		// TODO Auto-generated method stub
		String service = Service.EnterCoopGameHall.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " " + money
				+ " " + exp+" "+power;
		sock.sendMessage(message);
	}

	@Override
	public void requestUserList() {
		// TODO Auto-generated method stub
		String service = Service.RequestUserList.toString();
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void requestCoopRoomList() {
		// TODO Auto-generated method stub
		String service = Service.RequestCoopRoomList.toString();
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void enterCoopRoom(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.EnterCoopRoom.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ roomNO;
		sock.sendMessage(message);
	}

}
