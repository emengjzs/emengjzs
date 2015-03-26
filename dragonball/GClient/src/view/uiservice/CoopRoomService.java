package view.uiservice;

import java.util.ArrayList;

import model.vo.ActiveUser;
import model.vo.UserVO;


public interface CoopRoomService {
	//��Ϣ�ַ��ͳ�������
	public void receiveMessage(String message);
	//����Э�������û��б�
	public void setUserList(ArrayList<ActiveUser> userList);
	//��ȡЭ�������û��б�
	public ArrayList<ActiveUser> getPlayerList();
	//��ȡ��ǰ�û�
	public UserVO getUser();
}
