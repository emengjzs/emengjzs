package view.ui.gameui;

/**用户要求boardPanel的操作集*/
public interface GameServiceForUser {
	
	/* 用户请求调用交换 */
	public void swapFromUser(Position p, Direction d);
	
	/* 用户请求点击道具 */
	public void triggerItem(Position p);
}
