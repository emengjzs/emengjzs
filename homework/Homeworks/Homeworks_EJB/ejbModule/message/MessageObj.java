package message;

public class MessageObj {
	private Message m;

	
	
	public MessageObj(Message m) {
		this.m = m;
	}
	

	public boolean isSuccess() {
		return this.m.isSuccess();
	}
	
	
	public String getInfo() {
		return this.m.getInfo();
	}

	public String getMessage() {
		return this.m.toString();
	}
}
