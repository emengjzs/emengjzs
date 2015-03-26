package model.netservice;

public interface NetUserBLService_Stub {
	public void Login(String userID,String password);
	public void selectService(String message);
	public void confirmLogin(String message); 
}
