package data.dataservice;

import po.UserPO;

public interface UserDataService_Stub {
	abstract void Insert(UserPO po);
	
	abstract void Delete(UserPO po);
	
	abstract void Update(UserPO po);
	
	abstract public UserPO FindByID(String id);
}
