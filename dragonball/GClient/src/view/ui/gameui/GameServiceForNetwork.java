package view.ui.gameui;

/**������Ҫ��boardPanel�Ĳ�����*/
public interface GameServiceForNetwork {
	/**�����ɹ����� */
	public void swapSuccess(String message);
	
	/**����ʧ�ܴ��� */
	public void swapFail(String message);
	
	/**����ɹ�����*/
	public void ItemSuccess(String message);
	
	/**������������*/
	public void SecondEliminate(String message);
	
	/**��ʾ����*/
	public void hint(String message);
	
	/**����ģʽ����*/
	public void StartSuper();
	
	/**����ģʽ��������*/
	public void EndSuper();
	
	/**��Ϸ��������*/
	public void EndGame(String message);
	
	/*����ɫ����*/
	public void LockBlock(int type);
	
	public void unLock();
}
