package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;

import common.CoopRoom;


public interface MultiGameHallService {
	//消息分发和初步处理
	public void receiveMessage(String message);
	//设置协作房间用户列表
	public void setUserList(ArrayList<ActiveUser> userList);
	//设置协作房间列表
	public void setRoomList(CoopRoom[] roomList);
	//获取协作游戏大厅用户列表
	public ArrayList<ActiveUser> getCoopUserList();
	//获取当前用户
	public UserVO getUser(); 
	//获取房间列表
	public CoopRoom[] getRoomList();
}
