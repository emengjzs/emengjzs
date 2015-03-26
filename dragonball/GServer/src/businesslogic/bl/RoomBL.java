package businesslogic.bl;

import java.util.ArrayList;

import po.ActiveUser;

public abstract class RoomBL {

	/**
	 * 对所有组发送消息
	 */
	public abstract void sendGameMsg(String message);

	/**
	 * 对某一小组发送消息
	 */
	public abstract void sendGameMsg(String message, int teamId);
	
	public abstract void sendEndGameMsg(String message, int teamId,int point);

	public abstract void superStart(int teamId);

	public abstract void superEnd(int teamId);

	public abstract ArrayList<ActiveUser> getUserList(int teamId);

	public abstract void endGame(int point, int combo, int teamId);

}
