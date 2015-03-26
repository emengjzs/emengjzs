package model.vo;

public class BattleSortRecordVO implements Comparable<BattleSortRecordVO> {
	int score;
	String message;
	
	public BattleSortRecordVO(int score,String message){
		this.score=score;
		this.message=message;
	}
	
	public int getScore(){
		return score;
	}
	
	public String getMessage(){
		return message;
	}

	@Override
	public int compareTo(BattleSortRecordVO o) {
		// TODO Auto-generated method stub
		return -new Integer(score).compareTo(new Integer(o.getScore()));
	}
}
