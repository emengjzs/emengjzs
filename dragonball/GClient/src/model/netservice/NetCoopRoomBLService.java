package model.netservice;

public interface NetCoopRoomBLService {
	//����Э�������û��б�
	public void requestUserList(int roomNO);
	//����׼��״̬
	public void setReady(String userID, int roomNO);
	//�ڷ���������
	public void chatInRoom(String message, String userID, int roomNO);
	//�뿪Э����Ϸ����
	public void quitCoopRoom(String userID, int roomNO);
	//��������
	public void kickPlayer(String roommasterID, String userID, int roomNO);
	//ѡ�����
	public void chooseProp(int propNO, int roomNO,String userID);
	//��ʼ��Ϸ
	public void initGame(String userID, int roomNO);
	//��������б�
	public void requestPropList(int roomNO);
}
