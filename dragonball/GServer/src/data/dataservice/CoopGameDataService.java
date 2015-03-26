package data.dataservice;


import po.CoopRecordPO;

import java.util.ArrayList;

import po.ActiveUser;

public interface CoopGameDataService {
	abstract void InsertCooperation(ArrayList<ActiveUser> playerList, int point, int combo);
	abstract int getCooperationTotal(String id);
	abstract int getCooperationAverageScore(String id);
	abstract int getCooperationHighestPoint(String id);
	abstract int getCooperationHighestCombo(String id);
	abstract ArrayList<CoopRecordPO> getCooperationRecentTen(String id);
}
