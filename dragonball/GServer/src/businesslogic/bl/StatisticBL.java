package businesslogic.bl;

import java.util.ArrayList;

import po.BattleRecordPO;
import po.CoopRecordPO;
import po.DailyRecordPO;
import po.GameRecordPO;

import data.dataimpl.BattleGameDataServiceImpl;
import data.dataimpl.CoopGameDataServiceImpl;
import data.dataimpl.SingleGameDataServiceImpl;
import data.dataimpl.UserDataServiceImpl;
import data.dataservice.BattleGameDataService;
import data.dataservice.CoopGameDataService;
import data.dataservice.SingleGameDataService;
import data.dataservice.UserDataService;

public class StatisticBL {
	UserDataService userData=new UserDataServiceImpl();
	SingleGameDataService singleData=new SingleGameDataServiceImpl();
	CoopGameDataService coopData=new CoopGameDataServiceImpl();
	BattleGameDataService battleData=new BattleGameDataServiceImpl();
	
	public StatisticBL(){
		
	}
	
	public String getCoopData(String userID){
		int total=coopData.getCooperationTotal(userID);
		int highestScore=coopData.getCooperationHighestPoint(userID);
		int highestCombo=coopData.getCooperationHighestCombo(userID);
		int averageScore=coopData.getCooperationAverageScore(userID);
		
		ArrayList<CoopRecordPO> recordList=coopData.getCooperationRecentTen(userID);
		
		String message=total+"|"+highestScore+"|"+highestCombo+"|"+averageScore+"|";
		for(CoopRecordPO po:recordList){
			message=message+po.toString()+"=";
		}
		
		return message;
	}
	
	public String getSingleData(String userID){
		int total=singleData.getTotalGame(userID);
		int highestScore=singleData.getHighestPoint(userID);
		int highestCombo=singleData.getHighestCombo(userID);
		int averageScore=singleData.getAverageScore(userID);
		
		String message=total+"|"+highestScore+"|"+highestCombo+"|"+averageScore;
		
		return message;
	}
	
	public String getSingleTen(String userID){
		ArrayList<GameRecordPO> tenList=singleData.getRecentTen(userID);
		String message="";
		
		for(GameRecordPO record: tenList){
			message=message+record.toString()+"/";
		}
		
		return message;
	}
	
	public String getSingleTop(String userID){
		ArrayList<GameRecordPO> tenList=singleData.getTopFive(userID);
		String message="";
		
		for(GameRecordPO record: tenList){
			message=message+record.getPoint()+"/";
		}
		
		return message;
	}
	
	public String getSingleDaily(String userID){
		ArrayList<DailyRecordPO> weekList=singleData.getDailyRecord(userID);
		String message="";
		
		int i=0;
		for(DailyRecordPO record: weekList){
			message=message+i+":"+record.toString()+"/";
			i++;
		}
		
		return message;
	}
	
	public String getBattleStatis(String userID){
		int HighestPoint=battleData.getBattleHighestPoint(userID);
		ArrayList<BattleRecordPO> recordList=battleData.getBattleRecentTen(userID);
		String message=HighestPoint+"|";
		
		for(BattleRecordPO record: recordList){
			message=message+record.toString()+"=";
		}
		
		return message;
	}
}
