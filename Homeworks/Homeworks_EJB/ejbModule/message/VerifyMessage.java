package message;

import java.io.Serializable;

public enum VerifyMessage implements Serializable {
		UNCHECKED,
		PASS,
		NOT_EXISTS,
		PASSWORD_ERROR,
		PASSWORD_UNCHECKED;
		
		public boolean isExists() {
			return this != VerifyMessage.NOT_EXISTS && this != VerifyMessage.UNCHECKED ;
		}
		

		public boolean isPass() {
			return this  == VerifyMessage.PASS;
		}
		
}
