package po;
import java.util.ArrayList;


public class CoopRecordPO implements Comparable<CoopRecordPO> {
	int gameID;
	String date;
	ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
	int point;
	
	public CoopRecordPO(int gameID,String date,ArrayList<ActiveUser> userList,int point){
		this.gameID=gameID;
		this.date=date;
		this.userList=userList;
		this.point=point;
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public String toString(){
		String message=gameID+":"+date+":";
		for(ActiveUser user:userList){
			message=message+user.getUserID()+"/";
		}
		message=message+":"+point;
		
		return message;
	}
	
	public void getSize(){
		System.out.println("This is"+userList.size());
	}
	

	@Override
	public int compareTo(CoopRecordPO o) {
		// TODO Auto-generated method stub
		return -new Integer(gameID).compareTo(new Integer(o.getGameID()));
	}
}
