package model.netservice;


public interface NetCoopGameHallBLService {
	//����Э����Ϸ����
	public void Enter(String userID,int money, int exp,int power);
	//�뿪Э����Ϸ����
	public void Quit(String userID);
	//����Э����Ϸ�����û��б�
	public void requestUserList();
	//����Э����Ϸ���������б�
	public void requestCoopRoomList();
	//����Э������
	public void enterCoopRoom(String userID,int roomNO);
}
