package model.netservice;


public interface NetCoopGameHallBLService {
	//进入协助游戏大厅
	public void Enter(String userID,int money, int exp,int power);
	//离开协助游戏大厅
	public void Quit(String userID);
	//请求协助游戏大厅用户列表
	public void requestUserList();
	//请求协助游戏大厅房间列表
	public void requestCoopRoomList();
	//进入协助房间
	public void enterCoopRoom(String userID,int roomNO);
}
