package model.netservice;


public interface NetGameHallBLService_Stub {
	public void Enter(String userID);
	public void Quit(String userID);
	public void requestUserList();
	public void requestCoopRoomList();
	public void enterCoopRoom(String userID,int roomNO);
}
