package model.netservice;

public interface NetCoopRoomBLService_Stub {
	public void requestUserList(int roomNO);
	public void setReady(String userID,int roomNO);
	public void chatInRoom(String message,String userID,int roomNO);
	public void quitCoopRoom(String userID,int roomNO);
	public void kickPlayer(String roommasterID,String userID,int roomNO);
}
