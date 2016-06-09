package data.dataservice;

import po.DailyRecordPO;
import po.GameRecordPO;

import java.util.ArrayList;

public interface SingleGameDataService {
	abstract void InsertGameRecord(String userID,int point,int combo);
	abstract int getHighestPoint(String id);
	abstract int getHighestCombo(String id);
	abstract int getTotalGame(String id);
	abstract int getAverageScore(String id);
	abstract  ArrayList<GameRecordPO> getRecentTen(String id);
	abstract ArrayList<DailyRecordPO> getDailyRecord(String id);
	abstract ArrayList<GameRecordPO> getTopFive(String id);
}
