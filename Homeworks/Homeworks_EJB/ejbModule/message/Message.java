package message;

import java.io.Serializable;

public interface Message {
	enum RegisterMessage implements Message, Serializable{
		REGISTER_SUCCESS("ע��ɹ�"), 
		USER_DUMPLICATE("ID�Ѵ���");
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
		PASS("���ͨ��"),
		INVALID_TYPE("��֧�ָ������ļ�"),
		FILE_NOT_EXIST("�ļ�������"),
		INVALID_FILE("���Ϸ����ļ�");
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
		ADD_SUCCESS("��ӳɹ�"),
		ID_DUMPLICATE("ID�Ѵ���"),
		ADD_FAILED("���ʧ��"),
		ADD_FORBID("�����Ч");
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
		UPDATE_SUCCESS("���³ɹ�"),
		UPDATE_FORBID("������Ч"),
		UPDATE_FAILED("����ʧ��");
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
