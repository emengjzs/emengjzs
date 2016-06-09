package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;


public interface CoopRoomService {
	//消息分发和初步处理
	public void receiveMessage(String message);
	//设置协作房间用户列表
	public void setUserList(ArrayList<ActiveUser> userList);
	//获取协作房间用户列表
	public ArrayList<ActiveUser> getPlayerList();
	//获取当前用户
	public UserVO getUser();
}
