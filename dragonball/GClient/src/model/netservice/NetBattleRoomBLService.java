package model.netservice;

public interface NetBattleRoomBLService {
	//请求对战房间用户列表
	public void requestUserList(int roomNO);
	//离开对战房间
	public void quitBattleRoom(String userID, int roomNO);
	//设置准备状态
	public void setReady(String userID, int roomNO);
	//聊天
	public void chatInRoom(String message, String userID, int roomNO);
	//房主踢人
	public void kickPlayer(String roommasterID, String userID, int roomNO);
	//换队操作
	public void changeTeam(String userID, int roomNO);
	//选择道具
	public void chooseProp(int teamNO, int propNO, int roomNO);
	//开始游戏
	void initGame(String userID, int roomNO);
}
