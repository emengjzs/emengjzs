package game.model;

public class ScoreController_Stub {
	public final double SUPER_MODE_RATE = BoardSetting_Stub.SUPER_MODE_RATE;
	
	public final static int SCORE_INDEX[] = {	
		0, 
		0, 
		0,
		BoardSetting_Stub.THREE_SCORE,
		BoardSetting_Stub.FOUR_SCORE,
		BoardSetting_Stub.FIVE_SCORE
	};
	
	public double baseRate = 1;
	private int  score = 0;
	private int increase = 0;
	private double rate = 1.0;
	
	public void output() {
		Debug.outputl("** Score : " + score + " **");
	}
	
	public ScoreController_Stub() {
		
	}
	
	private synchronized void addScore(int s) {
		score += (s * rate * baseRate);
	}
	
	/*回合分数*/
	public synchronized void addCurrentRoundScore(int s) {
		increase += (s * rate * baseRate);
	}
	
	
	/*回合分数合计*/
	public synchronized int addUp(){
		score += increase;
		int temp= increase;
		increase = 0;
		return temp;
	}
	
	public static int calScore(int num) {
		return num > 5 ? num * 100 : SCORE_INDEX[num];
	}

	
	public int getScore() {
		return score;
	}
	
	/*开启超级模式*/
	public void startSuperMode() {
		rate = SUPER_MODE_RATE;
	}
	
	/*结束超级模式*/
	public void endSuperMode() {
		rate = 1.0;
	}
	
	/*开启E道具效果*/
	public void openEItemEffect() {
		baseRate = BoardSetting_Stub.ITEM_RATE;
	}

}
