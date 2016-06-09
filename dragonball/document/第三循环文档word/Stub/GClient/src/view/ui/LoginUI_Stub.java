package view.ui;

import model.IO.IOHelper;
import model.netbl.NetUserBL_Stub;
import model.netservice.NetUserBLService_Stub;

public class LoginUI_Stub {
	static NetUserBLService_Stub userController;
	static String userID;
	
	public LoginUI_Stub(){
		userController=NetUserBL_Stub.getUserController();
		userID=IOHelper.inputFromConsole("�������û���");
		String password=IOHelper.inputFromConsole("����������");
		
//		System.out.println(userController.Login(userID,password));
		userController.Login(userID, password);
	}

}
