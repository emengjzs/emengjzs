package data.dataimpl;

import po.UserPO;
import data.dataservice.UserDataService_Stub;

public class UserDataServiceImpl_Stub implements UserDataService_Stub {
	static int i=0;
	@Override
	public void Insert(UserPO po) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(UserPO po) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update(UserPO po) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserPO FindByID(String id) {
		// TODO Auto-generated method stub
		UserPO po=null;

		if(i==1){
			po = new UserPO("12345","12345");
		}else{
			po = new UserPO("abcde","abcde");
		}
		i++;
		
		return po;
	}

}
