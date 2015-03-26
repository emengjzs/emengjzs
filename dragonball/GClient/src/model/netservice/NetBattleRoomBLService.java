package model.netservice;

public interface NetBattleRoomBLService {
	//�����ս�����û��б�
	public void requestUserList(int roomNO);
	//�뿪��ս����
	public void quitBattleRoom(String userID, int roomNO);
	//����׼��״̬
	public void setReady(String userID, int roomNO);
	//����
	public void chatInRoom(String message, String userID, int roomNO);
	//��������
	public void kickPlayer(String roommasterID, String userID, int roomNO);
	//���Ӳ���
	public void changeTeam(String userID, int roomNO);
	//ѡ�����
	public void chooseProp(int teamNO, int propNO, int roomNO);
	//��ʼ��Ϸ
	void initGame(String userID, int roomNO);
}
