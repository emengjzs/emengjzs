package model.netservice;

public interface NetUserBLService {
	//发送用户登录信息
	public void Login(String userID,String password);
	//发送用户注册信息
	public void Register(String userID,String password);
	//发送用户修改密码信息
	public void ChangePassowrd(String userID,String password);
	//发送用户设置下落方向信息
	public void ChangeDirection(String userID,int direction);
	//获取用户信息
	public void getUserInfo(String userID);
	//获取用户关卡信息
	public void getStage(String userID);
	//获取用户龙珠信息
	public void getUserBall(String userID);
}
