package businesslogic.bl;

import java.util.ArrayList;

import po.ActiveUser;

public abstract class RoomBL {

	/**
	 * �������鷢����Ϣ
	 */
	public abstract void sendGameMsg(String message);

	/**
	 * ��ĳһС�鷢����Ϣ
	 */
	public abstract void sendGameMsg(String message, int teamId);
	
	public abstract void sendEndGameMsg(String message, int teamId,int point);

	public abstract void superStart(int teamId);

	public abstract void superEnd(int teamId);

	public abstract ArrayList<ActiveUser> getUserList(int teamId);

	public abstract void endGame(int point, int combo, int teamId);

}
