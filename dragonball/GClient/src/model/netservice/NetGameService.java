package model.netservice;

import view.ui.gameui.Position;

public interface NetGameService {

	/**
	 * ����ʱ����
	 * 
	 */
	public void swap(Position p1, Position p2);

	/**
	 * ������������ã��鿴�Ƿ������������Ƿ����֡�
	 * 
	 */
	public void animateFinish();

	/**
	 * ��������
	 * 
	 */
	public void triggerItem(Position p);

	/**
	 * ʱ�䵽����ã��������е���
	 */
	public void triggerAllItem();

	void sendStage(int stage);

	/**
	 * ��ʼʱ��
	 */
	void timeStart();
}
