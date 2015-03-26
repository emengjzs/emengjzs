package model.vo;

import java.util.ArrayList;

public class CoopStatisVO {
	int total;
	int highestPoint;
	int highestCombo;
	int averagePoint;
	ArrayList<CoopRecordVO> recordList=new ArrayList<CoopRecordVO>();
	
	public CoopStatisVO(int total,int highestPoint,int highestCombo,int averagePoint,ArrayList<CoopRecordVO> recordList){
		this.total=total;
		this.highestPoint=highestPoint;
		this.highestCombo=highestCombo;
		this.averagePoint=averagePoint;
		this.recordList=recordList;
	}
	
	public int getTotal(){
		return total;
	}
	
	public int getHighestPoint(){
		return highestPoint;
	}
	
	public int getHighestCombo(){
		return highestCombo;
	}
	
	public int getAveragePoint(){
		return averagePoint;
	}
	
	public ArrayList<CoopRecordVO> getRecordList(){
		return recordList;
	}
}
