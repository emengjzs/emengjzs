package model.vo;

public class SingleTenRecordVO implements Comparable<SingleTenRecordVO> {
	int gameID;
	int score;
	public SingleTenRecordVO(int gameID, int score){
		this.gameID=gameID;
		this.score=score;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public int getScore(){
		return score;
	}

	@Override
	public int compareTo(SingleTenRecordVO o) {
		// TODO Auto-generated method stub
		return (""+gameID).compareTo(""+o.getGameID());
	}
}
