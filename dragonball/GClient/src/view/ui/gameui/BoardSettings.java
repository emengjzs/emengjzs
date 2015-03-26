package view.ui.gameui;

public class BoardSettings {
	/*static*/
	public static final int dimension = 9;	//9x9
	
	private Direction fallDirection;			//���䷽��
	private int blockEdge     = 60;				//����߳�
	private boolean isEnemy   = false;			//�Ƿ�Ϊ�з�����
	@SuppressWarnings("unused")
	private boolean needMusic = true;			//��������?
	
	public BoardSettings(Direction fallDirection) {
		this.fallDirection = fallDirection;
	}
	
	public BoardSettings(Direction fallDirection, int blockEdge) {
		this.fallDirection = fallDirection;
		this.blockEdge     = blockEdge;
	}
	
	/** �������̱߳�*/
	public int getEdge() {
		return dimension * blockEdge;
	}
	
	/**�������ӱ߳�*/
	public int getBlockEdge() {
		return blockEdge;
	}

	public Direction getFallDirection() {
		return fallDirection;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public void setEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

}
