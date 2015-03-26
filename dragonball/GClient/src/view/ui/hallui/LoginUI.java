package view.ui.hallui;

import view.uiservice.LoginService;
import model.IO.IOHelper;
import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;

public class LoginUI implements LoginService {
	static NetUserBLService userController;
	
	public LoginUI(){
		userController=NetUserBL.getUserController();
	}
	
	public void Login(String userID,String password){
		userController.Login(userID, password);
	}
	
	public void Register(String userID,String password){
		
	}
	
	public void doSomething(){
		String userID=IOHelper.inputFromConsole("请输入用户名");
		String password=IOHelper.inputFromConsole("请输入密码");
		
		Login(userID, password);
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Login UI Receive: "+message);
	}

	@Override
	public void closeLoginFrame() {
		// TODO Auto-generated method stub
		
	}

}
