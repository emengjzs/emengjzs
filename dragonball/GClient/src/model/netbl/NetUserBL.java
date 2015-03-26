package model.netbl;

import model.netservice.NetUserBLService;
import common.Nameplate;
import common.Service;

public class NetUserBL implements NetUserBLService {
	SocketStart sock;
	static NetUserBL netUserBL = null;
	final static String NAMEPLATE = Nameplate.User.toString();

	private NetUserBL() {
//		sock = new SocketStart();
		sock = SocketStart.getSocket();
	}

	public static NetUserBL getUserController() {
		if (netUserBL == null) {
			netUserBL = new NetUserBL();
		}

		return netUserBL;
	}

	@Override
	public void Login(String userID, String password) {
		// TODO Auto-generated method stub
		String service = Service.Login.toString();
		String message = NAMEPLATE + " " + service + " " + userID + " "
				+ password;
		sock.sendMessage(message);
	}

	@Override
	public void Register(String userID, String password) {
		// TODO Auto-generated method stub
		String service = Service.Register.toString();
		String message = NAMEPLATE + " " + service + " " +userID+" "+ password;
		sock.sendMessage(message);
	}

	@Override
	public void ChangePassowrd(String userID, String password) {
		// TODO Auto-generated method stub
		String service = Service.ChangePassword.toString();
		String message = NAMEPLATE + " " + service + " " +userID+" "+password;
		sock.sendMessage(message);
	}

	@Override
	public void ChangeDirection(String userID, int direction) {
		// TODO Auto-generated method stub
		String service = Service.ChangeDirection.toString();
		String message = NAMEPLATE + " " +service +" "+userID+" "+direction;
		sock.sendMessage(message);
	}

	@Override
	public void getUserInfo(String userID) {
		// TODO Auto-generated method stub
		String service = Service.GetUser.toString();
		String message = NAMEPLATE + " " +service +" "+userID+" ";
		sock.sendMessage(message);
	}

	@Override
	public void getStage(String userID) {
		// TODO Auto-generated method stub
		String service = Service.GetStageList.toString();
		String message = NAMEPLATE + " " +service +" "+userID;
		sock.sendMessage(message);
	}

	@Override
	public void getUserBall(String userID) {
		// TODO Auto-generated method stub
		String service = Service.GetBallList.toString();
		String message = NAMEPLATE + " " +service +" "+userID;
		sock.sendMessage(message);
	}

}
