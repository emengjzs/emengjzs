package businesslogic.bl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.ResultMessage;

public class UserBLTest {
	static UserBL userBL=new UserBL();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogin() {
		ResultMessage message=userBL.Login("12345", "12345", null);
		assertEquals(ResultMessage.LOGIN_SUCCESSFULLY,message);
		ResultMessage message1=userBL.Login("12345", "12345", null);
		assertEquals(ResultMessage.DUPLICATE_ACCOUNT_LOGIN,message1);
		ResultMessage message2=userBL.Login("23456", "12345", null);
		assertEquals(ResultMessage.NO_ACCOUNT,message2);
		ResultMessage message3=userBL.Login("12345", "1234", null);
		assertEquals(ResultMessage.LOGIN_FAILED,message3);
	}

	@Test
	public void testRegister() {
		ResultMessage message=userBL.Register("12345", "12345", null);
		assertEquals(ResultMessage.REGISTER_FAILED,message);
		ResultMessage message2=userBL.Login("23456", "12345", null);
		assertEquals(ResultMessage.REGISTER_SUCCESSFULLY,message2);
	}

	@Test
	public void testChangePassword() {
		ResultMessage message=userBL.changePassword("12345", "12345");
		assertEquals(ResultMessage.CHANGE_PASSWORD_SUCCESSFULLY,message);
		ResultMessage message2=userBL.Login("23456", "12345", null);
		assertEquals(ResultMessage.CHANGE_PASSWORD_FAILED,message2);
	}
	
}
