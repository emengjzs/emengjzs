package model.netservice;

public interface NetUserBLService {
	//�����û���¼��Ϣ
	public void Login(String userID,String password);
	//�����û�ע����Ϣ
	public void Register(String userID,String password);
	//�����û��޸�������Ϣ
	public void ChangePassowrd(String userID,String password);
	//�����û��������䷽����Ϣ
	public void ChangeDirection(String userID,int direction);
	//��ȡ�û���Ϣ
	public void getUserInfo(String userID);
	//��ȡ�û��ؿ���Ϣ
	public void getStage(String userID);
	//��ȡ�û�������Ϣ
	public void getUserBall(String userID);
}
