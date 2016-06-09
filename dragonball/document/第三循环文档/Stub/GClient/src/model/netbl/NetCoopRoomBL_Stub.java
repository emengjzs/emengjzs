package model.netbl;


import common.Nameplate;
import common.Service;

import model.netservice.NetCoopRoomBLService_Stub;

public class NetCoopRoomBL_Stub implements NetCoopRoomBLService_Stub {
	SocketStart sock;
	static NetCoopRoomBL_Stub netCoopRoomBL_Stub=null;
	final static String NAMEPLATE=Nameplate.CoopRoom.toString();

	private NetCoopRoomBL_Stub() {
		sock = new SocketStart();
	}
	
	public static NetCoopRoomBL_Stub getCoopRoomController(){
		if(netCoopRoomBL_Stub==null){
			netCoopRoomBL_Stub=new NetCoopRoomBL_Stub();
		}
		
		return netCoopRoomBL_Stub;
	}

	@Override
	public void requestUserList(int roomNO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReady(String userID,int roomNO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void chatInRoom(String info, String userID, int roomNO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quitCoopRoom(String userID, int roomNO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void kickPlayer(String roommasterID, String userID, int roomNO) {
		// TODO Auto-generated method stub

	}
	
	
}
