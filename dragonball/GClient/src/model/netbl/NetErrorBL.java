package model.netbl;

import common.Nameplate;

import model.netservice.NetErrorBLService;

public class NetErrorBL implements NetErrorBLService {
	SocketStart sock;
	static NetErrorBL netErrorBL = null;
	final static String NAMEPLATE = Nameplate.Error.toString();
	
	private NetErrorBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetErrorBL getErrorController() {
		if (netErrorBL == null) {
			netErrorBL = new NetErrorBL();
		}

		return netErrorBL;
	}

	@Override
	public void Leave(String userID) {
		// TODO Auto-generated method stub
		String message = NAMEPLATE + " " + userID;
		sock.sendMessage(message);
	}
}
