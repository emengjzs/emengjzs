package message;

import java.io.Serializable;

public interface Message {
	enum RegisterMessage implements Message, Serializable{
		REGISTER_SUCCESS("注册成功"), 
		USER_DUMPLICATE("ID已存在");
		String info;
		
		private RegisterMessage(String info) {
			this.info = info;
		}
		
		@Override
		public boolean isSuccess() {
			return this == REGISTER_SUCCESS;
		}
		
		
		@Override
		public String getInfo() {
			return info;
		}
		@Override
		public void setInfo(String info) {
			this.info = info;
		}
		
	}
	
	public enum Msg implements Message {
		PASS("检测通过"),
		INVALID_TYPE("不支持该类型文件"),
		FILE_NOT_EXIST("文件不存在"),
		INVALID_FILE("不合法的文件");
		String info;
		
		private Msg(String s) {
			this.info = s;
		}
		
		public String getInfo() {
			return this.info;
		}
		
		public boolean isPass() {
			return this.equals(PASS);
		}

		@Override
		public boolean isSuccess() {
			// TODO Auto-generated method stub
			return this.isPass();
		}

		@Override
		public void setInfo(String info) {
			this.info= info;
			
		}
}
	
	enum AddMessage implements Message, Serializable {
		ADD_SUCCESS("添加成功"),
		ID_DUMPLICATE("ID已存在"),
		ADD_FAILED("添加失败"),
		ADD_FORBID("添加无效");
		private String info;
		
		private AddMessage(String info) {
			this.info = info;
		}
		
		public boolean  isSuccess() {
			return this == ADD_SUCCESS;
		}
		@Override
		public String getInfo() {
			return info;
		}
		@Override
		public void setInfo(String info) {
			this.info = info;
		}
	}
	
	enum UpdateMessage implements Message, Serializable {
		UPDATE_SUCCESS("更新成功"),
		UPDATE_FORBID("更新无效"),
		UPDATE_FAILED("更新失败");
		private String info;
		
		private UpdateMessage(String info) {
			this.info = info;
		}
		
		
		public boolean  isSuccess() {
			return this == UPDATE_SUCCESS;
		}
		@Override
		public String getInfo() {
			return info;
		}
		@Override
		public void setInfo(String info) {
			this.info = info;
		}
		
	}	

	public boolean isSuccess();
	public String getInfo();
	public void setInfo(String info);
}
