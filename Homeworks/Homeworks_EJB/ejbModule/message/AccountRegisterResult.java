package message;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import message.Message.RegisterMessage;

public class AccountRegisterResult {
	private RegisterMessage msg;
	
	private List<User> registerFailedUserList;
	
	private ArrayList<String> failReasonList;

	public RegisterMessage getMsg() {
		return msg;
	}

	public void setMsg(RegisterMessage msg) {
		this.msg = msg;
	}

	public List<User> getRegisterFailedUserList() {
		return registerFailedUserList;
	}

	public void setRegisterFailedUserList(List<User> registerFailedUserList) {
		this.registerFailedUserList = registerFailedUserList;
	}

	public ArrayList<String> getFailReasonList() {
		return failReasonList;
	}

	public void setFailReasonList(ArrayList<String> failReasonList) {
		this.failReasonList = failReasonList;
	}
	
	
}
