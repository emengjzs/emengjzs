package view.ui.gameui;

/**�û�Ҫ��boardPanel�Ĳ�����*/
public interface GameServiceForUser {
	
	/* �û�������ý��� */
	public void swapFromUser(Position p, Direction d);
	
	/* �û����������� */
	public void triggerItem(Position p);
}
