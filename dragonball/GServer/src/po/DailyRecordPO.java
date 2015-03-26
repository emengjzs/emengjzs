package po;

public class DailyRecordPO implements Comparable<DailyRecordPO> {
	String date;
	int totalGame;
	int averagePoint;
	int averageCombo;
	
	public DailyRecordPO(String date,int totalGame, int averagePoint, int averageCombo){
		this.date =date;
		this.totalGame= totalGame;
		this.averagePoint = averagePoint;
		this.averageCombo = averageCombo;
	}
	
	public String toString(){
		String message = totalGame+":"+averagePoint;
		return message;
	}
	
	public String getDate(){
		return date;
	}

	@Override
	public int compareTo(DailyRecordPO o) {
		// TODO Auto-generated method stub
		return -(""+date).compareTo(""+o.getDate());
	}
	
}
