package model.netbl;

import common.Nameplate;
import common.Service;

import model.netservice.NetSingleBLService;

public class NetSingleBL implements NetSingleBLService{
	SocketStart sock;
	static NetSingleBL netSingleBL = null;
	final static String NAMEPLATE = Nameplate.Single.toString();
	
	private NetSingleBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetSingleBL getSingleController() {
		if (netSingleBL == null) {
			netSingleBL = new NetSingleBL();
		}

		return netSingleBL;
	}

	@Override
	public void Enter(String userID,int stage) {
		// TODO Auto-generated method stub
		String service = Service.EnterSingleGame.toString();
		String message = NAMEPLATE + " " + service + " " + userID+" "+stage;
		sock.sendMessage(message);
	}
	
	@Override
	public void initGame(String userID) {
		String message = Nameplate.Single + " " + Service.Game + " "
				+ userID + " Start@0";
		sock.sendMessage(message);
	}

	@Override
	public void RequestProp(String userID) {
		// TODO Auto-generated method stub
		String message = Nameplate.Single + " " + Service.RequestSingleProp.toString()+" "+userID;
		sock.sendMessage(message);
		
	}

	@Override
	public void BuyProp(String userID, int propNO) {
		// TODO Auto-generated method stub
		String message = Nameplate.Single + " " + Service.BuyProp.toString()+" "+userID+" "+propNO;
		sock.sendMessage(message);
	}

	@Override
	public void Leave(String userID) {
		// TODO Auto-generated method stub
		String message = Nameplate.Single + " " + Service.QuitSingleGame.toString()+" "+userID;
		sock.sendMessage(message);
	}
}
