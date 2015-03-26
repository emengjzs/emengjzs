package view.ui.gameui;

public class BoardSettings {
	/*static*/
	public static final int dimension = 9;	//9x9
	
	private Direction fallDirection;			//下落方向
	private int blockEdge     = 60;				//方块边长
	private boolean isEnemy   = false;			//是否为敌方棋盘
	@SuppressWarnings("unused")
	private boolean needMusic = true;			//开启音乐?
	
	public BoardSettings(Direction fallDirection) {
		this.fallDirection = fallDirection;
	}
	
	public BoardSettings(Direction fallDirection, int blockEdge) {
		this.fallDirection = fallDirection;
		this.blockEdge     = blockEdge;
	}
	
	/** 返回棋盘边长*/
	public int getEdge() {
		return dimension * blockEdge;
	}
	
	/**返回棋子边长*/
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
