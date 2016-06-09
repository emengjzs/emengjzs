package data.dataservice;

import java.util.ArrayList;

import po.ActiveUser;
import po.BattleRecordPO;

public interface BattleGameDataService {
	abstract void InsertBattle(ArrayList<ActiveUser> playerList1,ArrayList<ActiveUser> playerList2,int point1, int point2);
	abstract int getBattleHighestPoint(String id);
	abstract ArrayList<BattleRecordPO> getBattleRecentTen(String id);
}
