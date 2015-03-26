package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;


public interface CoopRoomService_Stub {
	public void receiveMessage(String message);
	public void setUserList(ArrayList<ActiveUser> userList);
	public ArrayList<ActiveUser> getPlayerList();
}
