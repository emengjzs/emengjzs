package businesslogic.blservice;
import java.io.PrintWriter;

import po.UserPO;

import common.ResultMessage;


public interface UserBLService {
	public ResultMessage Login(String userID,String password,PrintWriter writer) ;
	public ResultMessage Register(String userID,String password,PrintWriter writer) ;
	public UserPO getUser(String userID);
	public ResultMessage changePassword(String userID,String password);
	public void changeDirection(String userID,int direction);
	public String getStageList(String userID);
	public String getBallList(String userID);
}
