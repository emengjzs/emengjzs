package model.netbl;

import model.netservice.NetGameHallBLService_Stub;
import common.Nameplate;
import common.Service;


public class NetGameHallBL_Stub implements NetGameHallBLService_Stub {
	SocketStart sock;
	static NetGameHallBL_Stub netGameHallBL_Stub=null;
	final static String NAMEPLATE=Nameplate.GameHall.toString();

	private NetGameHallBL_Stub() {
		sock = new SocketStart();
	}
	
	public static NetGameHallBL_Stub getGameHallController(){
		if(netGameHallBL_Stub==null){
			netGameHallBL_Stub=new NetGameHallBL_Stub();
		}
		
		return netGameHallBL_Stub;
	}
	
	@Override
	public void Quit(String userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Enter(String userID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestUserList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCoopRoomList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterCoopRoom(String userID,int roomNO) {
		// TODO Auto-generated method stub

	}

}
