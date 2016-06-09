package data.dataservice;

import data.dataimpl.UserDataServiceImpl_Stub;

public class DataFactory {
	private static UserDataService_Stub userData=null;
	
	public static UserDataService_Stub getUserData(){
		if (userData==null){
			userData=new UserDataServiceImpl_Stub();
		}
		
		return userData;
	}
}
