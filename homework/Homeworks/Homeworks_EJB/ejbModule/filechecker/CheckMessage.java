package filechecker;

import message.Message;
import message.Message.Msg;



public class CheckMessage {
	
	
	private Msg msg;
	
	public CheckMessage(Msg msg) {
		this.msg = msg;
	}
	
	public static CheckMessage getInstance(Msg msg) {
		return new CheckMessage(msg);
	}
	
	public String toString() {
		return msg.getInfo();
	}
	
	public Message getMessage() {
		return this.msg;
	}
	
	public void Combine(CheckMessage msg) {
		if(this.msg.isPass())
			this.msg = msg.msg;
	}
	
	public boolean isPass() {
		return this.msg.isPass();
	}
	
}
