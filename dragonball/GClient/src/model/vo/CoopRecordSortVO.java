package model.vo;

import java.util.ArrayList;

public class CoopRecordSortVO implements Comparable<CoopRecordSortVO> {
	int score;
	ArrayList<ActiveUser> userList;
	
	public CoopRecordSortVO(int score,ArrayList<ActiveUser> userList){
		this.score=score;
		this.userList=userList;
	}
	
	public int getScore(){
		return score;
	}
	
	public ArrayList<ActiveUser> getUserList(){
		return userList;
	}
	
	@Override
	public int compareTo(CoopRecordSortVO o) {
		// TODO Auto-generated method stub
		return -new Integer(score).compareTo(new Integer(o.getScore()));
	}
}
