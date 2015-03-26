package model.netservice;

public interface NetSingleBLService {
	//进入单人游戏房间
	public void Enter(String userID,int stage);
	//离开单人游戏房间
	public void Leave(String userID);
	//请求道具列表
	public void RequestProp(String userID);
	//购买道具
	public void BuyProp(String userID,int propNO);
	//开始游戏
	public void initGame(String userID);
}
