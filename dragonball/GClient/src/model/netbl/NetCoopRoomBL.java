package model.netbl;

import common.Nameplate;
import common.Service;

import model.netservice.NetCoopRoomBLService;

public class NetCoopRoomBL implements NetCoopRoomBLService {
	SocketStart sock;
	static NetCoopRoomBL netCoopRoomBL = null;
	final static String NAMEPLATE = Nameplate.CoopRoom.toString();

	private NetCoopRoomBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetCoopRoomBL getCoopRoomController() {
		if (netCoopRoomBL == null) {
			netCoopRoomBL = new NetCoopRoomBL();
		}

		return netCoopRoomBL;
	}

	@Override
	public void requestUserList(int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.RequestUserList.toString() + " " + roomNO;

		String message = NAMEPLATE + " " + service;
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
	public void quitCoopRoom(String userID, int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.QuitCoopRoom.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ roomNO;
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

	public void initGame(String userID, int roomNO) {
		String message = NAMEPLATE + " " + Service.Game + " " + roomNO + " "
				+ userID + " Start@0";
		sock.sendMessage(message);
	}

	@Override
	public void chooseProp(int propNO, int roomNO,String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestChooseProp.toString();
		String message = NAMEPLATE + " " + service + " " + propNO + " "
				+ roomNO+" "+userID;
		sock.sendMessage(message);
	}

	@Override
	public void requestPropList(int roomNO) {
		// TODO Auto-generated method stub
		String service = Service.RequestCoopProp.toString();
		String message = NAMEPLATE + " " + service + " " +roomNO;
		sock.sendMessage(message);
	}

}
