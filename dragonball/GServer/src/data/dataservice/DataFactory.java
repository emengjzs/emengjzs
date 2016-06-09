package data.dataservice;

import data.dataimpl.UserDataServiceImpl;

public class DataFactory {
	private static UserDataService userData=null;
	
	public static UserDataService getUserData(){
		if (userData==null){
			userData=new UserDataServiceImpl();
		}
		
		return userData;
	}
}
