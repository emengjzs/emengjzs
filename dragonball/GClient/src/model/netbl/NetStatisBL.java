package model.netbl;

import common.Nameplate;
import common.Service;

import model.netservice.NetStatisBLService;

public class NetStatisBL implements NetStatisBLService {
	SocketStart sock;
	static NetStatisBL netCoopStatisBL = null;
	final static String NAMEPLATE = Nameplate.Statis.toString();

	private NetStatisBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetStatisBL getStatisController() {
		if (netCoopStatisBL == null) {
			netCoopStatisBL = new NetStatisBL();
		}

		return netCoopStatisBL;
	}
	@Override
	public void getStatistics(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestCoopStatis.toString() + " " + userID;

		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void getSingleStatic(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestSingleStatis.toString()+" "+userID;
		
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void getSingleTen(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestSingleTen.toString()+" "+userID;
		
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void getSingleDaily(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestSingleDaily.toString()+" "+userID;
		
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void getBattleStatic(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestBattleStatis.toString()+" "+userID;
		
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}

	@Override
	public void getSingleTop(String userID) {
		// TODO Auto-generated method stub
		String service = Service.RequestSingleTop.toString()+" "+userID;
		
		String message = NAMEPLATE + " " + service;
		sock.sendMessage(message);
	}
	

	
}
