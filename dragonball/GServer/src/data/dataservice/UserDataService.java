package data.dataservice;

import po.UserPO;

public interface UserDataService {
	abstract void Insert(UserPO po);
	abstract void InsertProp(String id);
	abstract void InsertRival(String id);
	abstract void InsertDragonBall(String id);
	
	abstract void Delete(String id);
	abstract void DeleteProp(String id);
	abstract void DeleteRival(String id);
	
	abstract void UpdatePassword(String id, String password);
	abstract void UpdateMoney(String id, double money);
	abstract void UpdatePower(String id,int power);
	abstract void UpdateExp(String id, double exp);
	abstract void UpdateDirection(String id,int direction);
	abstract public UserPO FindByID(String id);
	abstract void UpdateDragonBall(String id,int ballNO,int number);
	
	abstract void UpdateProp1(String id, int prop1);
	abstract void UpdateProp2(String id, int prop2);
	abstract void UpdateProp3(String id, int prop3);
	
	abstract void UpdateStage(String id, int stage,String state);
	abstract void UpdateEvaluate(String id, int stage,int evaluate);
	
	
	abstract int FindRival(String id);
	abstract int FindProp1(String id);
	abstract int FindProp2(String id);
	abstract int FindProp3(String id);
	abstract int FindStageStar(String id,int stage);
	abstract String FindBall(String id);
	abstract String FindStageState(String id,int stage);
	abstract int FindDirection(String id);
	abstract boolean canUnLock(String id);
}
