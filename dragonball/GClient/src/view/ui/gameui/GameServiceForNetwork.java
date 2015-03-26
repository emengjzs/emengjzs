package view.ui.gameui;

/**服务器要求boardPanel的操作集*/
public interface GameServiceForNetwork {
	/**交换成功处理 */
	public void swapSuccess(String message);
	
	/**交换失败处理 */
	public void swapFail(String message);
	
	/**点击成功处理*/
	public void ItemSuccess(String message);
	
	/**二次消除处理*/
	public void SecondEliminate(String message);
	
	/**提示处理*/
	public void hint(String message);
	
	/**超级模式处理*/
	public void StartSuper();
	
	/**超级模式结束处理*/
	public void EndSuper();
	
	/**游戏结束处理*/
	public void EndGame(String message);
	
	/*锁颜色处理*/
	public void LockBlock(int type);
	
	public void unLock();
}
