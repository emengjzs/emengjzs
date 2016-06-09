package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;

public interface BattleRoomService {
	//消息分发和初步处理
	public void receiveMessage(String message);
	//设置用户列表
	public void setUserList(ArrayList<ActiveUser> userList,int teamNO);
	//获取当前用户
	public UserVO getUser();
}
