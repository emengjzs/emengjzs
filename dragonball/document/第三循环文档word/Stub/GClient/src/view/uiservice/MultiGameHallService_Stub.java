package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;

import common.CoopRoom;


public interface MultiGameHallService_Stub {
	public void receiveMessage(String message);
	public void setUserList(ArrayList<ActiveUser> userList);
	public void setRoomList(CoopRoom[] roomList);
	public ArrayList<ActiveUser> getCoopUserList();
	public UserVO getUser(); 
	public CoopRoom[] getRoomList();
}
