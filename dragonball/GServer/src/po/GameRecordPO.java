package po;


public class GameRecordPO implements Comparable<GameRecordPO> {
	int gameID;
	String date;
	int point;
	int combo;
	
	public GameRecordPO(int gameID, String date, int point, int combo) {
		this.gameID=gameID;
		this.date=date;
		this.point=point;
		this.combo=combo;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public String getDate(){
		return date;
	}
	
	public int getPoint(){
		return point;
	}
	
	public int getCombo(){
		return combo;
	}
	
	public String toString(){
		String message = gameID+":"+point;
		
		return message;
		
	}

	@Override
	public int compareTo(GameRecordPO o) {
		// TODO Auto-generated method stub
		return -new Integer(gameID).compareTo(new Integer(o.getGameID()));
	}
}
