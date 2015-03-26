package po;
import java.util.ArrayList;


public class BattleRecordPO implements Comparable<BattleRecordPO> {
	int gameID;
	String date;
	ArrayList<ActiveUser> userList1=new ArrayList<ActiveUser>();
	ArrayList<ActiveUser> userList2=new ArrayList<ActiveUser>();
	int point1;
	int point2;
	
	public BattleRecordPO(int gameID,String date,ArrayList<ActiveUser> userList1,ArrayList<ActiveUser> userList2,int point1,int point2){
		this.gameID=gameID;
		this.date=date;
		this.userList1=userList1;
		this.userList2=userList2;
		this.point1=point1;
		this.point2=point2;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public void print(){
		System.out.println(gameID+" "+date+" "+point1+" "+point2);
	}
	
	public String toString(){
		String message=gameID+":";
		for(ActiveUser user:userList1){
			message=message+user.getUserID()+"/";
		}
		message =message+":";
		for(ActiveUser user:userList2){
			message=message+user.getUserID()+"/";
		}
		message=message+":"+point1+":"+point2;
		
		return message;
	}
	

	@Override
	public int compareTo(BattleRecordPO o) {
		// TODO Auto-generated method stub
		return -new Integer(gameID).compareTo(new Integer(o.getGameID()));
	}
}
