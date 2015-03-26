package model.vo;

import java.util.ArrayList;

public class BattleStatisVO {
	int highestPoint;
	ArrayList<BattleRecordVO> recordList=new ArrayList<BattleRecordVO>();
	
	public BattleStatisVO(int highestPoint,ArrayList<BattleRecordVO> recordList){
		this.highestPoint=highestPoint;
		this.recordList=recordList;
	}
	
	public int getHighestPoint(){
		return highestPoint;
	}
	
	public ArrayList<BattleRecordVO> getRecordList(){
		return recordList;
	}
}
