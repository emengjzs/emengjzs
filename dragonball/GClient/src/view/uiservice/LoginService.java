package view.uiservice;

public interface LoginService {
	//消息分发和初步处理
	public void receiveMessage(String message);
	//关闭登录界面
	public void closeLoginFrame();
}
