package model.netservice;

public interface NetBattleGameHallBLService {
	//进入对战游戏大厅
	public void Enter(String userID,int money, int exp,int power);
	//离开对战游戏大厅
	public void Quit(String userID);
	//请求用户列表
	public void requestUserList();
	//请求对战游戏房间列表
	public void requestBattleRoomList();
	//进入
	public void enterBattleRoom(String userID,int roomNO);
}
