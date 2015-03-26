package model.vo;

import java.util.ArrayList;

public class SingleStatisVO {
	int total;
	int highestPoint;
	int highestCombo;
	int averagePoint;
	ArrayList<SingleWeekRecordVO> weekRecordList=new ArrayList<SingleWeekRecordVO>();
	ArrayList<SingleTenRecordVO> tenRecordList=new ArrayList<SingleTenRecordVO>();
	
	public SingleStatisVO(){
		
	}
	
	public void setTotal(int total){
		this.total=total;
	}
	
	public int getTotal(){
		return total;
	}
	
	public void setHighestPoint(int highestPoint){
		this.highestPoint=highestPoint;
	}
	
	public int getHighestPoint(){
		return highestPoint;
	}
	
	public void setHighestCombo(int highestCombo){
		this.highestCombo=highestCombo;
	}
	
	public int getHighestCombo(){
		return highestCombo;
	}
	
	public void setAveragePoint(int averagePoint){
		this.averagePoint=averagePoint;
	}
	
	public int getAveragePoint(){
		return averagePoint;
	}
	
	public void setWeekList(ArrayList<SingleWeekRecordVO> weekRecordList){
		this.weekRecordList=weekRecordList;
	}
	
	public ArrayList<SingleWeekRecordVO> getWeekList(){
		return weekRecordList;
	}
	
	public void setTenList(ArrayList<SingleTenRecordVO> tenRecordList){
		this.tenRecordList=tenRecordList;
	}
	
	public ArrayList<SingleTenRecordVO> getTenList(){
		return tenRecordList;
	}
	
}
