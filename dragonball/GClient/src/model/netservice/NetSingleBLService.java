package model.netservice;

public interface NetSingleBLService {
	//���뵥����Ϸ����
	public void Enter(String userID,int stage);
	//�뿪������Ϸ����
	public void Leave(String userID);
	//��������б�
	public void RequestProp(String userID);
	//�������
	public void BuyProp(String userID,int propNO);
	//��ʼ��Ϸ
	public void initGame(String userID);
}
