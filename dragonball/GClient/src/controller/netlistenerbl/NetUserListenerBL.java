package controller.netlistenerbl;

import model.vo.UserVO;
import view.ui.login.MainFrame;
import view.uiservice.GameResultService;
import view.uiservice.LoginService;
import view.uiservice.MainService;
import view.uiservice.RegisterService;
import common.ResultMessage;
import common.Service;
import controller.netlistenerservice.NetUserListenerService;

public class NetUserListenerBL implements NetUserListenerService {
	static LoginService loginController = null;
	static GameResultService gameController =null;
	static RegisterService registerController=null;
	static MainService mainController=null;

	static NetUserListenerBL userListenerBL = null;

	private NetUserListenerBL() {

	}

	public static NetUserListenerBL getUserControllerListener() {
		if (userListenerBL == null) {
			userListenerBL = new NetUserListenerBL();
		}

		return userListenerBL;
	}

	@SuppressWarnings("static-access")
	public void setController(LoginService loginController) {
		this.loginController = loginController;
	}
	
	@SuppressWarnings("static-access")
	public void setResultController(GameResultService gameController) {
		this.gameController = gameController;
	}
	
	@SuppressWarnings("static-access")
	public void setRegisterController(RegisterService registerController){
		this.registerController=registerController;
	}
	
	@SuppressWarnings("static-access")
	public void setMainController(MainService mainController){
		this.mainController=mainController;
	}

	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.LoginResult.toString())) {
			confirmLogin(message);
		} else if (info[1].equals(Service.ReplyRegister.toString())) {
			registerController.receiveMessage(message);
		} else if (info[1].equals(Service.ReplyChangePassword.toString())){
			
		} else if(info[1].equals(Service.ReplyUser.toString())){
			gameController.receiveMessage(info[2]);
		} else if(info[1].equals(Service.ReplyStageList.toString())){
			mainController.receiveMessage(message);
		} else if(info[1].equals(Service.ReplyBallList.toString())){
			mainController.receiveMessage(message);
		}
	}

	private void confirmLogin(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");
		ResultMessage result = Enum.valueOf(ResultMessage.class, info[2]);

		if (result == ResultMessage.LOGIN_SUCCESSFULLY) {
			UserVO user = getUser(info[3]);
			System.out.println(message);
			MainFrame main = new MainFrame(user);
			NetUserListenerBL userController=NetUserListenerBL.getUserControllerListener();
			userController.setMainController(main);
			main.init();
			loginController.closeLoginFrame();
//			MainUI m=new MainUI(user);
		} else if (result == ResultMessage.INVALID_ID_OR_PASSWORD) {
			loginController.receiveMessage(message);
		} else if (result == ResultMessage.LOGIN_FAILED) {
			loginController.receiveMessage(message);
		} else if (result == ResultMessage.NO_ACCOUNT) {
			loginController.receiveMessage(message);
		} else if (result == ResultMessage.DUPLICATE_ACCOUNT_LOGIN){
			loginController.receiveMessage(message);
		}
	}

	private UserVO getUser(String message) {
		String[] info = message.split("-");
		UserVO user = new UserVO(info[0], info[1] ,Integer.parseInt(info[2]), Integer.parseInt(info[3]),Integer.parseInt(info[4]),Integer.parseInt(info[5]));

		return user;
	}
}
