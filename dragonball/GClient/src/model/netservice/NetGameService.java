package model.netservice;

import view.ui.gameui.Position;

public interface NetGameService {

	/**
	 * 交换时调用
	 * 
	 */
	public void swap(Position p1, Position p2);

	/**
	 * 动画结束后调用，查看是否有消除或者是否死局。
	 * 
	 */
	public void animateFinish();

	/**
	 * 触发道具
	 * 
	 */
	public void triggerItem(Position p);

	/**
	 * 时间到后调用，触发所有道具
	 */
	public void triggerAllItem();

	void sendStage(int stage);

	/**
	 * 开始时间
	 */
	void timeStart();
}
