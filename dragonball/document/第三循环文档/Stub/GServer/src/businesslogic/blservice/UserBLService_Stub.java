package businesslogic.blservice;
import java.io.PrintWriter;

import po.UserPO;

import common.ResultMessage;


public interface UserBLService_Stub {
	public ResultMessage Login(String userID,String password,PrintWriter writer) ;
	public UserPO getUser(String userID);
}
