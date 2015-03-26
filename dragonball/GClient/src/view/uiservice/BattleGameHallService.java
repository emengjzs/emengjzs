package view.uiservice;

import java.util.ArrayList;

import common.BattleRoom;

import model.vo.ActiveUser;
import model.vo.UserVO;

public interface BattleGameHallService {
	//消息分发和初步处理
	public void receiveMessage(String message);
	//设置对战用户列表
	public void setUserList(ArrayList<ActiveUser> userList);
	//设置房间列表
	public void setRoomList(BattleRoom[] roomList);
	//获取对战用户列表
	public ArrayList<ActiveUser> getBattleUserList();
	//获取当前用户
	public UserVO getUser();
}
