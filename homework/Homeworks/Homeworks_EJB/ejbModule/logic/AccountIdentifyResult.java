package logic;

import java.io.Serializable;

import message.VerifyMessage;
import entity.Role;
import entity.User;


public class AccountIdentifyResult implements Serializable{
	
	private User data;
	private VerifyMessage msg;
	
	public AccountIdentifyResult() {
		msg = VerifyMessage.UNCHECKED;
	}
	
	public void setData(User data) {
		this.data = data;
	}

	public void setMsg(VerifyMessage msg) {
		this.msg = msg;
	}

	public VerifyMessage getMsg() {
		return msg;
	}

	public User getData() {
		return data;
	}
	

	public boolean isExists() {
		return msg.isExists() ;
	}
	

	public boolean isPass() {
		return msg.isPass();
	}
	
	public Role getFirstRole() {
		return data.getFirstRole();
	}
	
	
}
