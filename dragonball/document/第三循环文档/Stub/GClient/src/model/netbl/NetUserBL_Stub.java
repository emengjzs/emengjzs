package model.netbl;

import model.netservice.NetUserBLService_Stub;
import model.vo.UserVO;
import common.Nameplate;
import common.ResultMessage;
import common.Service;

import view.ui.MainUI_Stub;


public class NetUserBL_Stub implements NetUserBLService_Stub {
	SocketStart sock;
	static NetUserBL_Stub netUserBL_Stub=null;
	final static String NAMEPLATE=Nameplate.User.toString();

	private NetUserBL_Stub() {
		sock = new SocketStart();
	}
	
	public static NetUserBL_Stub getUserController(){
		if(netUserBL_Stub==null){
			netUserBL_Stub=new NetUserBL_Stub();
		}
		
		return netUserBL_Stub;
	}

	@Override
	public void Login(String userID, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void confirmLogin(String message) {
		// TODO Auto-generated method stub

	}
	
	private UserVO getUser(String message){
		return new UserVO("12345","12345");
	}

}
