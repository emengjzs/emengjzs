package message;

public class MyMessage implements Message {
	private String info;
	private boolean success = true;
	
	public MyMessage(String info) {
		this.info = info;
	}
	
	
	public MyMessage() {
		this.info = "";
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return success;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void setInfo(String info) {
		// TODO Auto-generated method stub
		this.info = info;
	}

}
