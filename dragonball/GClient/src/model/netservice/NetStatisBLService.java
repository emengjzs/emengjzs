package model.netservice;

public interface NetStatisBLService {
	//��ȡЭ��ͳ����Ϣ
	public void getStatistics(String userID);
	//��ȡ����ͳ����Ϣ����߷֣������������ƽ���������ܾ�����
	public void getSingleStatic(String userID);
	//��ȡ�������ʮ����Ϸ��Ϣ
	public void getSingleTen(String userID);
	//��ȡ����ÿ��ͳ����Ϣ
	public void getSingleDaily(String userID);
	//��ȡ��ս��Ϸ����
	public void getBattleStatic(String userID);
	//��ȡ���������Ϸ��Ϣ��ǰ5��
	public void getSingleTop(String userID);
}
