package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;

public interface BattleRoomService {
	//��Ϣ�ַ��ͳ�������
	public void receiveMessage(String message);
	//�����û��б�
	public void setUserList(ArrayList<ActiveUser> userList,int teamNO);
	//��ȡ��ǰ�û�
	public UserVO getUser();
}
