package model.netservice;

public interface NetCoopRoomBLService {
	//请求协助房间用户列表
	public void requestUserList(int roomNO);
	//设置准备状态
	public void setReady(String userID, int roomNO);
	//在房间内聊天
	public void chatInRoom(String message, String userID, int roomNO);
	//离开协助游戏房间
	public void quitCoopRoom(String userID, int roomNO);
	//房主踢人
	public void kickPlayer(String roommasterID, String userID, int roomNO);
	//选择道具
	public void chooseProp(int propNO, int roomNO,String userID);
	//开始游戏
	public void initGame(String userID, int roomNO);
	//请求道具列表
	public void requestPropList(int roomNO);
}
