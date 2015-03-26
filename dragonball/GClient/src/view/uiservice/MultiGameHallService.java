package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;

import common.CoopRoom;


public interface MultiGameHallService {
	//��Ϣ�ַ��ͳ�������
	public void receiveMessage(String message);
	//����Э�������û��б�
	public void setUserList(ArrayList<ActiveUser> userList);
	//����Э�������б�
	public void setRoomList(CoopRoom[] roomList);
	//��ȡЭ����Ϸ�����û��б�
	public ArrayList<ActiveUser> getCoopUserList();
	//��ȡ��ǰ�û�
	public UserVO getUser(); 
	//��ȡ�����б�
	public CoopRoom[] getRoomList();
}
