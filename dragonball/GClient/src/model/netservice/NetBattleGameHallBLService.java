package model.netservice;

public interface NetBattleGameHallBLService {
	//�����ս��Ϸ����
	public void Enter(String userID,int money, int exp,int power);
	//�뿪��ս��Ϸ����
	public void Quit(String userID);
	//�����û��б�
	public void requestUserList();
	//�����ս��Ϸ�����б�
	public void requestBattleRoomList();
	//����
	public void enterBattleRoom(String userID,int roomNO);
}
