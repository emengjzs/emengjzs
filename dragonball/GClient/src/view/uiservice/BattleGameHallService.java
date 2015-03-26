package view.uiservice;

import java.util.ArrayList;

import common.BattleRoom;

import model.vo.ActiveUser;
import model.vo.UserVO;

public interface BattleGameHallService {
	//��Ϣ�ַ��ͳ�������
	public void receiveMessage(String message);
	//���ö�ս�û��б�
	public void setUserList(ArrayList<ActiveUser> userList);
	//���÷����б�
	public void setRoomList(BattleRoom[] roomList);
	//��ȡ��ս�û��б�
	public ArrayList<ActiveUser> getBattleUserList();
	//��ȡ��ǰ�û�
	public UserVO getUser();
}
