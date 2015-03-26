package model.netservice;

public interface NetStatisBLService {
	//获取协作统计信息
	public void getStatistics(String userID);
	//获取单人统计信息（最高分，最高连击数，平均分数，总局数）
	public void getSingleStatic(String userID);
	//获取单人最近十次游戏信息
	public void getSingleTen(String userID);
	//获取单人每日统计信息
	public void getSingleDaily(String userID);
	//获取对战游戏形象
	public void getBattleStatic(String userID);
	//获取单人最高游戏信息（前5）
	public void getSingleTop(String userID);
}
