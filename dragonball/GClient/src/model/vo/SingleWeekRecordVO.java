package model.vo;

public class SingleWeekRecordVO implements Comparable<SingleWeekRecordVO> {
	int gameID;
	int totalGame;
	int averagePoint;
	
	public SingleWeekRecordVO(int gameID,int totalGame,int averagePoint){
		this.gameID=gameID;
		this.totalGame=totalGame;
		this.averagePoint=averagePoint;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public int getTotal(){
		return totalGame;
	}
	
	public int getAveragePoint(){
		return averagePoint;
	}

	@Override
	public int compareTo(SingleWeekRecordVO o) {
		// TODO Auto-generated method stub
		return -(""+gameID).compareTo(""+o.getGameID());
	}
}
